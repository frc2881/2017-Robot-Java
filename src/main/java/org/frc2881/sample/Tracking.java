package org.frc2881.sample;

import java.util.stream.IntStream;

public class Tracking {

    private static final short MOTOR_LEFT_F = 2;
    private static final short MOTOR_RIGHT_F = 3;
    private static final short MOTOR_RIGHT_R = 8;
    private static final short MOTOR_LEFT_R = 9;

    private static final double AXLE_LENGTH  = 10.25;   // inches
    private static final double WHEEL_RADIUS  = 2;      // inches
    private static final double RADIANS_PER_TICK  = (Math.PI * 2 /360);
    private static final int VELOCITY_SAMPLES  = 4;
    private static final double DEGREES_PER_RADIAN  = (360/(Math.PI * 2));

    private static final int MILLIS_PER_SECOND = 1000;

    private static final double MAX_ROTATIONS_PER_MIN = 170.0;
    private static final double MAX_RADIANS_PER_SEC = (MAX_ROTATIONS_PER_MIN * Math.PI * 2 / 60);
    private static final double MAX_LINEAR_SPEED = (MAX_RADIANS_PER_SEC * WHEEL_RADIUS);                   // inches/sec
    private static final double MAX_TURN_SPEED = (MAX_RADIANS_PER_SEC * 2 * WHEEL_RADIUS / AXLE_LENGTH);   // radians/sec

    // Difference in encoder tick count over a time period
    private static class TickDelta {
        long millis;
        int left;
        int right;
    }

    private static class Position {
        long time;  // millis since robot start
        int left;   // encoder tick count
        int right;  // encoder tick count
        double x;   // relative to robot start location (inches)
        double y;   // relative to robot start location (inches)
        double a;   // current heading (radians)
        double v;   // forward velocity (inches/second)
        double w;   // rate of turning left (positive) or right (negative) (radians/second)
        double vLeft;
        double vRight;
        final TickDelta deltaAccum = new TickDelta();
        final TickDelta[] deltaHistory = IntStream.range(0, 4).mapToObj(i -> new TickDelta()).toArray(TickDelta[]::new);
        int deltaPos;                                       // index of 'deltaHistory' containing the most recent
    }

    private static class PositionTarget {
        double x;  // inches
        double y;  // inches
        double a;  // radians
        double v;  // inches/sec
        double w;  // radians/sec
        double k_1;
        double k_2_v;
        double k_3;
    }

    private final Position position = new Position();
    private final PositionTarget positionTarget = new PositionTarget();

    public static void main(String[] args) {
        Tracking t = new Tracking();
        Position p = t.position;
        long now = 0;
        t.trackingUpdate(now += 20, 0, 1);
        t.trackingSetDriveTarget(p.x, p.y, p.a + Math.PI / 2);
        t.trackingDriveToTarget();

        t.trackingUpdate(now += 20, 0, 1);
        t.trackingDriveToTarget();

        for (int i = 0; i < 10; i++) {
            t.trackingUpdate(now += 20, 20, 21);
            t.trackingDriveToTarget();
        }
    }

    void trackingUpdate(long now, int left, int right) {
        // Calculate change vs. the last time
        long deltaMillis = now - position.time;
        int deltaLeft = left - position.left;
        int deltaRight = right - position.right;

        position.time = now;
        position.left = left;
        position.right = right;

        if (Math.abs(left) > 360 || Math.abs(right) > 360) {
            return;  // ignore bad data due to encoder resets etc.
        }

        // Update our estimate of our position and heading
        double radiansLeft = deltaLeft * RADIANS_PER_TICK;
        double radiansRight = deltaRight * RADIANS_PER_TICK;
        double deltaPosition = (radiansLeft + radiansRight) * (WHEEL_RADIUS / 2);  // this averages left and right
        double deltaHeading = (radiansRight - radiansLeft) * (WHEEL_RADIUS / AXLE_LENGTH);
        double midHeading = position.a + deltaHeading / 2;  // estimate of heading 1/2 way through the sample period
        position.x += deltaPosition * Math.cos(midHeading);
        position.y += deltaPosition * Math.sin(midHeading);
        position.a = normalizeAngle(position.a + deltaHeading, 0, Math.PI * 2);

        // Update the sliding window we use to create a moving average of velocity
        position.deltaPos = (position.deltaPos + 1) % VELOCITY_SAMPLES;
        TickDelta history = position.deltaHistory[position.deltaPos];
        updateTickDelta(history, position.deltaAccum, -1);
        history.millis = deltaMillis;
        history.left = deltaLeft;
        history.right = deltaRight;
        updateTickDelta(history, position.deltaAccum, 1);

        // Update our estimate of our velocity by averaging across the last few updates
        long accumMillis = position.deltaAccum.millis;
        if (accumMillis > 0) {
            double ticksPerMilliToInchesPerSecond = RADIANS_PER_TICK * WHEEL_RADIUS * MILLIS_PER_SECOND;
            position.vLeft = position.deltaAccum.left / (double) accumMillis * ticksPerMilliToInchesPerSecond;
            position.vRight = position.deltaAccum.right / (double) accumMillis * ticksPerMilliToInchesPerSecond;
            position.v = (position.vLeft + position.vRight) / 2;
            position.w = (position.vRight - position.vLeft) / AXLE_LENGTH;   // radians/second
        }
    }

    void updateTickDelta(TickDelta src, TickDelta dest, int sign) {
        dest.millis += src.millis * sign;
        dest.left += src.left * sign;
        dest.right += src.right * sign;
    }

    void setChassisSpeed(double linearSpeed, double turnSpeed) {
        // Convert desired linear & turn values into tank chassis left & right
        double left = linearSpeed + turnSpeed * AXLE_LENGTH / 2;
        double right = linearSpeed - turnSpeed * AXLE_LENGTH / 2;

        // Scale down inputs to maintain curvature radius if requested speeds exceeds physical limits
        double saturation = Math.max(Math.abs(left) / MAX_LINEAR_SPEED, Math.abs(right) / MAX_LINEAR_SPEED);
        if (saturation > 1) {
            left /= saturation;
            right /= saturation;
        }

        setWheelSpeed(MOTOR_LEFT_F, MOTOR_LEFT_R, left, position.vLeft);
        setWheelSpeed(MOTOR_RIGHT_F, MOTOR_RIGHT_R, right, position.vRight);
    }

    void setWheelSpeed(short front, short rear, double desired, double actual) {
        // Simple P controller w/both feedforward and feedback components
        double levelFeedforward = desired;
        double levelFeedback = 0.1 * (desired - actual);
        int value = (int) Math.round(127 * (levelFeedforward + levelFeedback) / MAX_LINEAR_SPEED);
        smartMotorSet(front, value);
        smartMotorSet(rear, value);
    }

    void trackingSetDriveSpeed(short forward, short turn) {
        setChassisSpeed(forward * (MAX_LINEAR_SPEED / 127), turn * (MAX_TURN_SPEED / 127));
    }

    void trackingSetDriveTarget(double x, double y, double a) {
        trackingSetDriveWaypoint(x, y, a, MAX_LINEAR_SPEED / 2, 0);
    }

    void trackingSetDriveWaypoint(double x, double y, double a, double v, double w) {
        // Don't come to a complete stop at the end, avoid singularities in the math of the feedback design
        v = (Math.abs(v) < 0.01) ? 0.01 : v;
        w = (Math.abs(w) < 0.01) ? 0.01 : w;

        // Set destination location, heading and velocities
        positionTarget.x = x;
        positionTarget.y = y;
        positionTarget.a = normalizeAngle(a, -Math.PI, Math.PI);
        positionTarget.v = v;
        positionTarget.w = w;

        // Compute the gain matrix for this target
        double zeta = 0.7;  // damping factor, adjust 0 < zeta < 1 to get good results
        double g = 60;      // gain constant, adjust 0 < gain as necessary to get good results
        positionTarget.k_1 = 2 * zeta * Math.sqrt(w * w + g * v * v);
        positionTarget.k_2_v = g * v;
        positionTarget.k_3 = positionTarget.k_1;
    }

    void trackingDriveToTarget() {
        // Algorithm based on the Nonlinear Controller described at:
        // https://www.researchgate.net/publication/224115822_Experimental_comparison_of_trajectory_tracking_algorithms_for_nonholonomic_mobile_robot

        // Compute deviation from desired position and heading
        double d_x = positionTarget.x - position.x;
        double d_y = positionTarget.y - position.y;
        double d_a = normalizeAngle(positionTarget.a - position.a, -Math.PI, Math.PI);

        // Transform error values to the perspective of the robot
        double cos_a = Math.cos(position.a);
        double sin_a = Math.sin(position.a);
        double e_1 = d_x * cos_a + d_y * sin_a;   // Distance to go straight ahead (may be negative)
        double e_2 = d_y * cos_a - d_x * sin_a;   // Distance to go side-to-side (obviously can't move directly in that direction)
        double e_3 = d_a;                         // Deviation from desired heading

        // Compute feedforward linear and angular velocity
        double uf_v = positionTarget.v * Math.cos(e_3);
        double uf_w = positionTarget.w;

        // Compute feedback linear and angular velocity
        double ub_v = e_1 * positionTarget.k_1;
        double ub_w = e_2 * positionTarget.k_2_v * (Math.abs(e_3) >= 0.001 ? Math.sin(e_3) / e_3 : 1) + e_3 * positionTarget.k_3;

        double linearSpeed = uf_v + ub_v;  // inches/sec
        double turnSpeed = uf_w + ub_w;   // radians/sec

        setChassisSpeed(linearSpeed, turnSpeed);
    }

    double normalizeAngle(double rads, double min, double max) {
        if (rads < min) {
            rads += Math.PI * 2;
        } else if (rads >= max) {
            rads -= Math.PI * 2;
        }
        return rads;
    }

    void smartMotorSet(short channel, int value) {
        System.out.printf("motor[%d] = %d%n", channel, value);
    }
}

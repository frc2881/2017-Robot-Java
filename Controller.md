Controller Notes
----------------

## Port Mapping

PS4 Dualshock interpreted as Joystick
 - 6 Axis
   - 0 - Left Joystick Horiz (center at 0, move left -> -1, move right -> 1)
   - 1 - Left Joystick Vert (center at 0, drives backward--move forward -> -1)
   - 2 - Right Joystick Horiz
   - 5 - Right Joystick Vert
   - 3 - Left Trigger (starts at -1, natural stop near 0, all the way down -> 1, shared with Button 6)
   - 4 - Right Trigger (shared with Button 7)
 - 14 Buttons
   - 0 - Right Group Left (pink square)
   - 1 - Right Group Down (blue X)
   - 2 - Right Group Right (red circle)
   - 3 - Right Group Up (green triangle)
   - 4 - Left Trigger Upper
   - 5 - Right Trigger Upper
   - 6 - Left Trigger Lower (shared with Axis 3)
   - 7 - Right Trigger Lower (shared with Axis 4)
   - 8 - Share Button (upper left)
   - 9 - Options Button (upper right)
   - 10 - Left Joystick Press
   - 11 - Right Joystick Press
   - 12 - Playstation button (middle)
   - 13 - Center trackpad Press
 - 8 POV - Left Group
   - N, E, S, W, NE, SE, SW, NW

Xbox One Controller
 - 6 Axis (all center at 0)
   - 0 - Left Joystick Horiz (center at 0, move left -> -1, move right -> 1)
   - 1 - Left Joystick Vert (center at 0, drives backward--move forward -> -1)
   - 2 - Left Trigger Lower (starts at 0, all the way down -> 1)
   - 3 - Right Trigger Lower
   - 4 - Right Joystick Horiz
   - 5 - Right Joystick Vert
 - 11 Buttons
   - 0 - Right Group Down (A)
   - 1 - Right Group Right (B)
   - 2 - Right Group Left (X)
   - 3 - Right Group Up (Y)
   - 4 - Left Trigger Upper
   - 5 - Right Trigger Upper
   - 6 - Dual screen button (upper left small button)
   - 7 - Options button (upper right small button)
   - 8 - Left Joystick Press
   - 9 - Right Joystick Press
   - 10 - ?????
 - 8 POV - Left Group
   - N, E, S, W, NE, SE, SW, NW
 - 2 Rumble
   - Left Hand (0 -> 1)
   - Right Hand (0 -> 1)

Logitech Gamepad F310
 - Identical to Xbox One Controller EXCEPT:
 - 10 Buttons (fwiw don't know what 11th button is in Xbox)
   - 6 - Back button
   - 7 - Start button
 - Mode button swaps POV and Left Joystick when green light is on
 - Rumble appears in UI but does nothing


## Rumble Feedback

The LabVIEW Driver Station only supports rumble feedback with controllers that use XInput (DirectX?).  Currently,
that's the Xbox One Controller.

However, a program called [InputMapper](https://inputmapper.com/) can be used to make a PS4 Dualshock controller act
like an Xbox controller. When you run InputMapper then LabVIEW actually detects two controllers, one PS4 and one Xbox,
where both are mapped to the same physical PS4 controller device.  Note that the port mappings are different between
the two controllers and the "center" positions of the axes are different.  The Java program can be written to use
either one for input, but Rumble feedback must be sent to the id of the virtual Xbox controller.


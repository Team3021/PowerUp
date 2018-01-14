package org.usfirst.frc.team3021.robot.controller.station;

public class DefaultController extends BaseController {
	
	public DefaultController(int port) {
		// override to do nothing and not construct a Joystick
	}

	@Override
	public boolean getRawButton(String action) {
		return false;
	}
}
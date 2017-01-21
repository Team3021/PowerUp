package org.usfirst.frc.team3021.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Xbox360Controller {
	// Controller Buttons
	private static final int A_BUTTON = 1;
	private static final int B_BBUTTON = 2;
	private static final int X_BUTTON = 3;
	private static final int Y_BUTTON = 4;
	private static final int LEFT_SHOULDER = 5;
	private static final int RIGHT_SHOULDER = 6;
	private static final int BACK_BUTTON = 7;
	private static final int START_BUTTON = 8;
	private static final int LEFT_STICK_CLICK = 9;
	private static final int RIGHT_STICK_CLICK = 10;
	
	// Controller axes
	private static final int LEFT_STICK_X = 0;
	private static final int LEFT_STICK_Y = 1;
	private static final int LEFT_TRIGGER = 2;
	private static final int RIGHT_TRIGGER = 3;
	private static final int RIGHT_STICK_X = 4;
	private static final int RIGHT_STICK_Y = 5;

	// Member Attributes
	Joystick controller;

	public Xbox360Controller(int port){
		controller = new Joystick(port);
	}

	public double getMoveValue(){
		return controller.getRawAxis(LEFT_STICK_Y);
	}

	public double getTurnValue(){
		return controller.getRawAxis(LEFT_STICK_X);
	}

	public boolean isSpinnerForward(){
		return controller.getRawButton(A_BUTTON);
	}
	
	// The methods below are not currently used by Stanley as they do not correspond to a component on the robot.
	
//	public boolean isHighGear(){
//		return controller.getRawButton();
//	}
//
//
//
//	public boolean isSpinnerBackward(){
//		return controller.getRawButton();
//	}
//
//	public boolean isFiring(){
//		return controller.getRawButton();
//	}
//
//	public boolean isAutomaticAiming(){
//		return !controller.getRawButton();
//	}
//
//	public boolean isFlashlightOn(){
//		return controller.getRawButton();
//		
//	}
//	
//	public boolean isFeederIn(){
//		return controller.getRawButton();
//	}
//	
//	public boolean isFeederOut(){
//		return controller.getRawButton();
//	}
//	
//	public boolean isShortShot(){
//		return controller.getRawButton();
//	}
//
//	public boolean isFastShot(){
//		return controller.getRawButton();
//	}
//	
//	public boolean isLauncherEnabled(){
//		return !controller.getRawButton();
//	}
//
//	
//	public boolean isLauncherMovingUp(){
//		if (controller.get(1) > 0) {
//			return true;
//		}
//		
//		return false;
//	}
//
//	public boolean isLauncherMovingDown(){
//		if (controller.getRawAxis(1) < 0) {
//			return true;
//		}
//		
//		return false;
//	}
}

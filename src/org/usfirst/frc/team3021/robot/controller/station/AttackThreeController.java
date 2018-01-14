package org.usfirst.frc.team3021.robot.controller.station;

public class AttackThreeController extends BaseController {
	
	public AttackThreeController() {
		setButtons();
	}
	
	public AttackThreeController(int port) {
		super(port);
		
		setButtons();
	}

	private void setButtons() {
		buttonActions.add(new ButtonAction(1, "STICK_TRIGGER", "isLaunching"));
		buttonActions.add(new ButtonAction(2, "STICK_MIDDLE_BUTTON", "isRotatingToOneHundredEighty"));
		buttonActions.add(new ButtonAction(3, "STICK_CENTER", "isCollecting"));
		buttonActions.add(new ButtonAction(4, "STICK_LEFT_BUTTON", "isRotatingToNegativeNinety"));
		buttonActions.add(new ButtonAction(5, "STICK_RIGHT_BUTTON", "isRotatingToNinety"));
	
		buttonActions.add(new ButtonAction(6, "BASE_LEFT_FRONT", "isRotatingLeft45"));
		buttonActions.add(new ButtonAction(7, "BASE_LEFT_BACK", "isZeroGyro"));
	
		buttonActions.add(new ButtonAction(8, "BASE_BOTTOM_LEFT", "isSwitchingCamera"));
		buttonActions.add(new ButtonAction(9, "BASE_BOTTOM_RIGHT", "isStoppingCommands"));
		
		buttonActions.add(new ButtonAction(10, "BASE_RIGHT_BACK", "isZeroEncoders"));
		buttonActions.add(new ButtonAction(11, "BASE_RIGHT_FRONT", "isRotatingRight45"));
	}
	
	@Override
	public double getMoveValue() {
		return controller.getY();
	}
	
	@Override
	public double getTurnValue() {
		return controller.getX();
	}
}

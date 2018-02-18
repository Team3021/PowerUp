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
		buttonActions.add(new ButtonAction(1, "STICK_TRIGGER", "Not Assigned"));
		buttonActions.add(new ButtonAction(2, "STICK_MIDDLE_BUTTON", "Not Assigned"));
		buttonActions.add(new ButtonAction(3, "STICK_CENTER", "Not Assigned"));
		buttonActions.add(new ButtonAction(4, "STICK_LEFT_BUTTON", "Not Assigned"));
		buttonActions.add(new ButtonAction(5, "STICK_RIGHT_BUTTON", "Not Assigned"));
	
		buttonActions.add(new ButtonAction(6, "BASE_LEFT_FRONT", "Not Assigned"));
		buttonActions.add(new ButtonAction(7, "BASE_LEFT_BACK", "isZeroGyro"));
	
		buttonActions.add(new ButtonAction(8, "BASE_BOTTOM_LEFT", "Not Assigned"));
		buttonActions.add(new ButtonAction(9, "BASE_BOTTOM_RIGHT", "isStoppingCommands"));
		
		buttonActions.add(new ButtonAction(10, "BASE_RIGHT_BACK", "isZeroEncoders"));
		buttonActions.add(new ButtonAction(11, "BASE_RIGHT_FRONT", "Not Assigned"));
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

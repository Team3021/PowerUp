package org.usfirst.frc.team3021.robot.controller.station;

public class AuxController2017 extends BaseController {
	
	public AuxController2017() {
		setButtons();
	}
	
	public AuxController2017(int port) {
		super(port);
		
		setButtons();
	}
	private void setButtons() {
		buttonActions.add(new ButtonAction(1, "RIGHT_TOGGLE_BUTTON", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(2, "MIDDLE_TOGGLE_BUTTON", "UNASSIGNED"));
		buttonActions.add(new ButtonAction(3, "LEFT_TOGGLE_BUTTON", "UNASSIGNED"));

		buttonActions.add(new ButtonAction(4, "SAFETY_TRIGGER", "isClimberSafteyOn"));
	
		buttonActions.add(new ButtonAction(5, "TOP_BLUE_BUTTON", "isCollectorDeploying"));
		buttonActions.add(new ButtonAction(8, "TOP_RED_BUTTON", "isCollectorStowing"));

		buttonActions.add(new ButtonAction(6, "MIDDLE_BLUE_BUTTON", "isCollecting"));
		buttonActions.add(new ButtonAction(7, "BOTTOM_BLUE_BUTTON", "isLaunching"));
	
		buttonActions.add(new ButtonAction(9, "MIDDLE_RED_BUTTON", "isClimberExtending"));
		buttonActions.add(new ButtonAction(10, "BOTTOM_RED_BUTTON", "isClimberContracting"));
	}
	
	@Override
	public boolean getRawButton(String action) {
		if (!configuration.isAuxPanelEnabled()) {
			return false;
		}
		
		return super.getRawButton(action);
	}
}
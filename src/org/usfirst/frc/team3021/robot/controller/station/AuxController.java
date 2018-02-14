package org.usfirst.frc.team3021.robot.controller.station;

public class AuxController extends BaseController {
	
	public AuxController() {
		setButtons();
	}
	
	public AuxController(int port) {
		super(port);
		
		setButtons();
	}
	private void setButtons() {
		buttonActions.add(new ButtonAction(9, "JOYSTICK_UP", "isClimberExtending"));
		buttonActions.add(new ButtonAction(10, "JOYSTICK_DOWN", "isClimberContracting"));
		buttonActions.add(new ButtonAction(4, "SAFETY_TRIGGER", "isClimberSafteyOn"));

		buttonActions.add(new ButtonAction(5, "PNEUMATIC_SWITCH", "isCollectorDeploying"));
		buttonActions.add(new ButtonAction(8, "PNEUMATIC_SWITCH", "isCollectorStowing"));
	
		//buttonActions.add(new ButtonAction(5, "TOP_BLUE_BUTTON", "isClimbing"));
		buttonActions.add(new ButtonAction(6, "MIDDLE_BLUE_BUTTON", "isLaunching"));
		buttonActions.add(new ButtonAction(7, "BOTTOM_BLUE_BUTTON", "isCollecting"));
	
		//buttonActions.add(new ButtonAction(8, "TOP_RED_BUTTON", "isSwitchingCamera"));
		//buttonActions.add(new ButtonAction(9, "MIDDLE_RED_BUTTON", "isResettingNavx"));
		//buttonActions.add(new ButtonAction(10, "BOTTOM_RED_BUTTON", "isStoppingCommands"));
	}
	
	@Override
	public boolean getRawButton(String action) {
		if (!configuration.isAuxPanelEnabled()) {
			return false;
		}
		
		return super.getRawButton(action);
	}
}
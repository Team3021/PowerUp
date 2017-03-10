package org.usfirst.frc.team3021.robot.controller;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.Joystick;

public abstract class BaseController implements Controller {
	
	List<ButtonAction> buttonActions = new ArrayList<ButtonAction>();
	
	// Member Attributes
	Joystick controller;
	
	public BaseController() {
		
	}
	
	public BaseController(int port) {
		controller = new Joystick(port);
	}

	@Override
	public double getMoveValue() {
		return 0;
	}
	
	@Override
	public double getTurnValue() {
		return 0;
	}

	@Override
	public boolean isXbox() {
		if (controller != null) {
			return controller.getIsXbox();
		}
		
		return false;
	}
	
	@Override
	public boolean isLaunching() {
		return getRawButton("isLaunching");
	}
	
	@Override
	public boolean isSwitchingCamera() {
		return getRawButton("isSwitchingCamera");
	}

	@Override
	public boolean isCollecting() {
		return getRawButton("isCollecting");
	}

	@Override
	public boolean isResettingNavx() {
		return getRawButton("isResettingNavx");
	}

	@Override
	public boolean isRotatingToNinety() {
		return getRawButton("isRotatingToNinety");
	}

	@Override
	public boolean isRotatingToNegativeNinety() {
		return getRawButton("isRotatingToNegativeNinety");
	}

	@Override
	public boolean isRotatingToOneHundredEighty() {
		return getRawButton("isRotatingToOneHundredEighty");
	}

	@Override
	public boolean isRotatingRight45() {
		return getRawButton("isRotatingRight45");
	}

	@Override
	public boolean isRotatingLeft45() {
		return getRawButton("isRotatingLeft45");
	}

	@Override
	public boolean isClimberSafteyOn() {
		return getRawButton("isClimberSafteyOn");
	}

	@Override
	public boolean isClimbing() {
		return getRawButton("isClimbing");
	}

	@Override
	public boolean isScopeEnabled() {
		return getRawButton("isScopeEnabled");
	}

	@Override
	public boolean isTargetLocatorEnabled() {
		return getRawButton("isTargetLocatorEnabled");
	}
	
	@Override
	public boolean isStoppingCommands() {
		return getRawButton("isStoppingCommands");
	}
	
	public boolean getRawButton(String action) {
		
		ButtonAction foundButtonAction = null;
		
		for (ButtonAction buttonAction : buttonActions) {
			if (buttonAction.getAction().equals(action)) {
				foundButtonAction = buttonAction;
				break;
			}
		}
		
		if (foundButtonAction == null) {
			System.err.println("Failed to find button for action " + action);
			return false;
		}
		
		int number = foundButtonAction.getNumber();
		
		if (controller == null) {
			return false;
		}
		
		if (controller.getButtonCount() < number) {
			return false;
		}
		
		boolean buttonOn = controller.getRawButton(number);
		
		if (buttonOn) {
			return true;
		}
		
		return false;
	}

	@Override
	public void printButtonActions(String controller) {
		System.out.println("******************* " + controller + " *******************");

		for (ButtonAction buttonAction : buttonActions) {
			System.out.println(buttonAction);
		}

		System.out.println("");
	}
}


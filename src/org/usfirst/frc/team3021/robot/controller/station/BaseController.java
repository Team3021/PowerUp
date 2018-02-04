package org.usfirst.frc.team3021.robot.controller.station;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.Configuration;
import org.usfirst.frc.team3021.robot.QBert;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public abstract class BaseController implements Controller {

	// Member Attributes

	protected List<ButtonAction> buttonActions = new ArrayList<ButtonAction>();
	
	protected Joystick controller;
	
	protected Configuration configuration;
	
	public BaseController() {
		this.configuration = QBert.getConfiguration();
	}
	
	public BaseController(int port) {
		controller = new Joystick(port);
		this.configuration = QBert.getConfiguration();
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
			return DriverStation.getInstance().getJoystickIsXbox(controller.getPort());
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
	public boolean isZeroGyro() {
		return getRawButton("isZeroGyro");
	}

	@Override
	public boolean isZeroEncoders() {
		return getRawButton("isZeroEncoders");
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


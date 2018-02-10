package org.usfirst.frc.team3021.robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.usfirst.frc.team3021.robot.commands.auto.*;
import org.usfirst.frc.team3021.robot.controller.station.AttackThreeController;
import org.usfirst.frc.team3021.robot.controller.station.AuxController;
import org.usfirst.frc.team3021.robot.controller.station.Controller;
import org.usfirst.frc.team3021.robot.controller.station.DefaultController;
import org.usfirst.frc.team3021.robot.controller.station.Xbox360Controller;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Configuration {
	
	private static final String NO_AUTONOMOUS = "No Command";
	
	private static final String NO_CONTROLLER = "No Controller";
	private static final String ATTACK_THREE = "Attack Three";
	private static final String XBOX360 = "Xbox360";

	private final String PREF_MAIN_CONTROLLER_PORT = "Controller.main.port";
	private final int MAIN_CONTROLLER_PORT_DEFAULT = 0;

	private  final String PREF_CONTROLLER_XBOX_ENABLED = "Controller.xbox.enabled";
	private  final boolean CONTROLLER_XBOX_ENABLED_DEFAULT = false;

	private  final String PREF_AUX_PANEL_ENABLED = "Controller.aux.enabled";
	private  final boolean AUX_PANEL_ENABLED_DEFAULT = false;
	
	private final String PREF_AUX_PANEL_PORT = "Controller.aux.port";
	private final int AUX_PANEL_PORT_DEFAULT = 1;

	private  final String PREF_DASHBOARD_COMMANDS_ENABLED = "Config.dashboard.commands.enabled";
	private  final boolean DASHBOARD_COMMANDS_ENABLED_DEFAULT = false;
	
	private  final String PREF_DASHBAORD_SUBSYSTEMS_ENABLED = "Config.dashboard.subsystems.enabled";
	private  final boolean DASHBAORD_SUBSYSTEMS_ENABLED_DEFAULT = false;
	
	private SendableChooser<String> autonomousChooser = new SendableChooser<>();
	
	private List<Command> autoCommands = new ArrayList<Command>();

	public void addAutonmousChoices() {
		autonomousChooser.addDefault("[Red] [Left] to [Left Gear]", "[Red] [Left] to [Left Gear]");
		
		autonomousChooser.addObject(NO_AUTONOMOUS, NO_AUTONOMOUS);
		
		for (Command command : autoCommands) {
			autonomousChooser.addObject(command.getName(), command.getName());
		}
		
		SmartDashboard.putData("Autonomous Mode", autonomousChooser);
	}

	public void addSubsystemsToSmartDashboard(List<Subsystem> subsystems) {
		boolean isDashboardSubsystemsEnabled = Preferences.getInstance().getBoolean(PREF_DASHBAORD_SUBSYSTEMS_ENABLED, DASHBAORD_SUBSYSTEMS_ENABLED_DEFAULT);
		
		if (!isDashboardSubsystemsEnabled) {
			return;
		}
		
		for (Subsystem subsystem : subsystems) {
			SmartDashboard.putData(subsystem);
		}
	}
	
	public void addCommandsToDashboard() {
		SmartDashboard.putData(Scheduler.getInstance());

		// ****************************************************************************
		// **********************      AUTO COMMANDS RED         **********************
		// ****************************************************************************
		
		autoCommands.add(new RedStartLeftToLeftSwitchPlate());

		// ****************************************************************************
		// **********************      AUTO COMMANDS BLUE        **********************
		// ****************************************************************************
		
		autoCommands.add(new BlueStartLeftToLeftSwitchPlate());

		// Add commands to dashboard
		addCommandsToSmartDashboard("Autonomous", autoCommands);
	}

	private void addCommandsToSmartDashboard(String commandType, List<Command> commands) {
		boolean isDashboardCommandsEnabled = Preferences.getInstance().getBoolean(PREF_DASHBOARD_COMMANDS_ENABLED, DASHBOARD_COMMANDS_ENABLED_DEFAULT);
		
		if (!isDashboardCommandsEnabled) {
			return;
		}

		for (Command command : commands) {
			SmartDashboard.putData(command);
		}
	}
	
	public String getAutonomousMode() {
		return autonomousChooser.getSelected();
	}

	public Command getAutonomousCommand() {
		
		String name = getAutonomousMode();
		
		for (Command command : autoCommands) {
			if (command.getName().equals(name)) {
				return command;
			}
		}
		
		return null;
	}

	public String getMainControllerMode() {
		String selected = ATTACK_THREE;

		boolean xboxEnabled = Preferences.getInstance().getBoolean(PREF_CONTROLLER_XBOX_ENABLED, CONTROLLER_XBOX_ENABLED_DEFAULT);

		if (xboxEnabled) {
			selected = XBOX360;
		}
		
		SmartDashboard.putString("Configuration : joystick mode",  selected);
		
		return selected;
	}
	
	public int getMainControllerPort() {
		return Preferences.getInstance().getInt(PREF_MAIN_CONTROLLER_PORT, MAIN_CONTROLLER_PORT_DEFAULT);
	}
	
	public int getAuxPanelPort() {
		return Preferences.getInstance().getInt(PREF_AUX_PANEL_PORT, AUX_PANEL_PORT_DEFAULT);
	}
	
	public boolean isAuxPanelEnabled() {
		return Preferences.getInstance().getBoolean(PREF_AUX_PANEL_ENABLED, AUX_PANEL_ENABLED_DEFAULT);
	}

	public static void printButtonActions() {
		new AttackThreeController().printButtonActions("Attack Three");
		new Xbox360Controller().printButtonActions("Xbox360");
		new AuxController().printButtonActions("Aux Panel");
	}

	public void printPreferences() {
		Preferences prefs = Preferences.getInstance();
		
		@SuppressWarnings("rawtypes")
		Vector keys = prefs.getKeys();
		
		System.out.println("******************* Prefernces *******************");
		
		for (Object obj : keys) {
			String key = null;
			
			// cast from object to String
			if (obj instanceof String) {
				key = (String) obj;
			}
			
			if (key != null) {
				String value = prefs.getString(key, "");
			
				System.out.println(key + " : " + value);
			}
		}
	}

	public Controller initializeMainController() {
		int mainControllerPort = getMainControllerPort();

		Controller mainController = new AttackThreeController(mainControllerPort);
		
		String selectedController = getMainControllerMode();

		if (selectedController.equals(Configuration.ATTACK_THREE)) {
			System.out.println("*************** ATTACK THREE ***************");
			
			if (mainController.isXbox()) {
				System.out.println("*************** WARNING !!! ***************");
				System.out.println("Dahboard choice is not an XBOX controller, but this is an XBOX CONTROLLER on port " + getMainControllerPort());
			}
		}
		else if (selectedController.equals(Configuration.XBOX360)) {
			System.out.println("*************** XBOX ***************");
			mainController = new Xbox360Controller(mainControllerPort);
			
			if (!mainController.isXbox()) {
				System.out.println("*************** WARNING !!! ***************");
				System.out.println("Dahboard choice is XBOX controller, but this is NOT an XBOX CONTROLLER on port " + getMainControllerPort());
			}
		} else if (selectedController.equals(Configuration.NO_CONTROLLER)) {
			System.out.println("*************** NO CONTROLLER ***************");
			mainController = new DefaultController(mainControllerPort);
		}
		
		return mainController;
	}

	public Controller initializeAuxController() {
		System.out.println("*************** AUX ***************");
		
		int auxControllerPort = getAuxPanelPort();

		Controller auxController = new AuxController(auxControllerPort);
		
		return auxController;
	}
	
	public static void main(String[] args) {
		Configuration.printButtonActions();
	}
}

package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.auto.BlueStartLeftToLeftSwitchPlate;
import org.usfirst.frc.team3021.robot.commands.auto.RedStartLeftToLeftSwitchPlate;

import org.usfirst.frc.team3021.robot.configuration.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousConfiguration extends Configuration {
	
	private final String COMMAND_GROUP = "Autonomous";
	
	private static final String NO_AUTONOMOUS = "No Command";
	
	private  final String PREF_AUTO_COMMANDS_ENABLED = "Config.auto.commands.enabled";
	private  final boolean AUTO_COMMANDS_ENABLED_DEFAULT = false;

	private SendableChooser<String> autonomousChooser = new SendableChooser<>();
	
	private List<Command> commands = new ArrayList<Command>();
	
	private boolean enabled = true;
	
	public AutonomousConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_AUTO_COMMANDS_ENABLED, AUTO_COMMANDS_ENABLED_DEFAULT);
		
		addCommandsToDashboard();
		
		addAutonmousChoices();
	}
	
	// ****************************************************************************
	// **********************             CHOICES            **********************
	// ****************************************************************************

	private void addAutonmousChoices() {
		autonomousChooser.addDefault("[Red] [Left] to [Left Gear]", "[Red] [Left] to [Left Gear]");
		
		autonomousChooser.addObject(NO_AUTONOMOUS, NO_AUTONOMOUS);
		
		for (Command command : commands) {
			autonomousChooser.addObject(command.getName(), command.getName());
		}
		
		SmartDashboard.putData("Autonomous Mode", autonomousChooser);
	}

	// ****************************************************************************
	// **********************            COMMANDS            **********************
	// ****************************************************************************
	
	private void addCommandsToDashboard() {
		SmartDashboard.putData(Scheduler.getInstance());

		// RED ALLIANCE COMMANDS
		commands.add(new RedStartLeftToLeftSwitchPlate());

		// BLUE ALLIANCE COMMANDS
		commands.add(new BlueStartLeftToLeftSwitchPlate());

		// Add commands to dashboard
		addCommandsToDashboard(COMMAND_GROUP, commands, enabled);
	}
	
	public String getAutonomousMode() {
		return autonomousChooser.getSelected();
	}

	public Command getAutonomousCommand() {
		
		String name = getAutonomousMode();
		
		for (Command command : commands) {
			if (command.getName().equals(name)) {
				return command;
			}
		}
		
		return null;
	}
}

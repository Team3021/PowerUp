package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.auto.LeftToSCALE;
import org.usfirst.frc.team3021.robot.commands.auto.LeftToSWITCH;
import org.usfirst.frc.team3021.robot.commands.auto.MiddleToLeftSWITCH;
import org.usfirst.frc.team3021.robot.commands.auto.MiddleToRightSWITCH;
import org.usfirst.frc.team3021.robot.commands.auto.RightToSCALE;
import org.usfirst.frc.team3021.robot.commands.auto.RightToSWITCH;
import org.usfirst.frc.team3021.robot.commands.auto.Straight;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousConfiguration extends BaseConfiguration {
	
	private  final String PREF_AUTO_COMMANDS_ENABLED = "Config.auto.commands.enabled";
	private  final boolean AUTO_COMMANDS_ENABLED_DEFAULT = true;
	
	private boolean enabled = AUTO_COMMANDS_ENABLED_DEFAULT;
	
	private static final String NO_AUTONOMOUS = "No Command";
	
	public AutonomousConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_AUTO_COMMANDS_ENABLED, AUTO_COMMANDS_ENABLED_DEFAULT);

		if (enabled) {
			Dashboard.putData(Scheduler.getInstance());
		}
		
		addCommandsToDashboard();
		addAutonmousChoices();
		
	}

	private SendableChooser<String> autonomousChooser = new SendableChooser<>();
	
	private List<Command> autoCommands = new ArrayList<Command>();

	// ****************************************************************************
	// **********************             CHOICES            **********************
	// ****************************************************************************

	public void addAutonmousChoices() {
		autonomousChooser.addDefault("[Straight]", "[Straight]");
		
		autonomousChooser.addObject(NO_AUTONOMOUS, NO_AUTONOMOUS);
		
		for (Command command : autoCommands) {
			autonomousChooser.addObject(command.getName(), command.getName());
		}
		
		SmartDashboard.putData("Autonomous Mode", autonomousChooser);
	}

	// ****************************************************************************
	// **********************            COMMANDS            **********************
	// ****************************************************************************
	
	public void addCommandsToDashboard() {
		SmartDashboard.putData(Scheduler.getInstance());

		// AUTONOMOUS COMMANDS
		autoCommands.add(new LeftToSCALE());
		autoCommands.add(new LeftToSWITCH());
		
		autoCommands.add(new MiddleToLeftSWITCH());
		autoCommands.add(new MiddleToRightSWITCH());
		
		autoCommands.add(new Straight());
		
		autoCommands.add(new RightToSCALE());
		autoCommands.add(new RightToSWITCH());

		// Add commands to dashboard
		addCommandsToDashboard("Autonomous", autoCommands, enabled);
	}
	
	public String getAutonomousMode() {
		return autonomousChooser.getSelected();
	}

	public Command getAutonomousCommand(String gameData) {
		
		System.out.println("Game Data: " + gameData);

		Command autoCommand = null;
		
		String autoMode = getAutonomousMode();
		System.out.println("Auto command selected by DriverStation: " + autoMode);

		// Autonomous Modes: To Switch
		if (gameData.charAt(0) == 'L' && autoMode.equals("[Left] to [SWITCH]")) {
			autoCommand = new LeftToSWITCH();
		}
		else if (gameData.charAt(0) == 'R' && autoMode.equals("[Right] to [SWITCH]")) {
			autoCommand = new RightToSWITCH();
		}
		// Autonomous Modes: Middle To Left Switch
		else if (autoMode.startsWith("[Middle]")) {
			if (gameData.charAt(0) == 'L') {
				autoCommand = new MiddleToLeftSWITCH();
			} 
			else if (gameData.charAt(0) == 'R') {
				autoCommand = new MiddleToRightSWITCH();
			}
		}
		else if (gameData.charAt(0) == 'R' && autoMode.equals("[Middle] to [Right SWITCH]")) {
			
		}
		// Autonomous Modes: To Scale; first priority
		else if (gameData.charAt(1) == 'L' && autoMode.equals("[Left] to [SCALE]")) {
			autoCommand = new LeftToSCALE();
		}
		else if (gameData.charAt(1) == 'R' && autoMode.equals("[Right] to [SCALE]")) {
			autoCommand = new RightToSCALE();
		}
		// Autonomous Mode: Straight (Default)
		else {
			autoCommand = new Straight();
			System.out.println("Scale was on opposite side, unknown command, or [Straight] was chosen; initiating [Straight]");
		}
		
		return autoCommand;
	}
}

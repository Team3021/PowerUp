package org.usfirst.frc.team3021.robot.configuration;

import java.util.List;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class Configuration {

	protected void addCommandsToDashboard(String commandType, List<Command> commands, boolean enabled) {
		
		// Dont add commands to dashboard if the preferences is not enabled
		if (!enabled) {
			System.out.println("Commands not enabled for the group: " + commandType);
			
			return;
		}

		System.out.println("Commands enabled for the group: " + commandType);

		for (Command command : commands) {
			SmartDashboard.putData(command);
		}
	}
}

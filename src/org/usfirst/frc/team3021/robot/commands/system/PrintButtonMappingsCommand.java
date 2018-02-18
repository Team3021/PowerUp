package org.usfirst.frc.team3021.robot.commands.system;

import org.usfirst.frc.team3021.robot.configuration.Preferences;

public class PrintButtonMappingsCommand extends SystemCommand {
	
	public PrintButtonMappingsCommand() {
		super();
	}
	
	protected void execute() {
		Preferences.getInstance().printPreferences();
		
		Preferences.getInstance().printPreferencesOnRobot();
	}
	
	protected boolean isFinished() {
		return true;
	}
}

package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.system.PrintPreferencesCommand;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

public class SystemCommandConfiguration extends Configuration {
	
	private final String COMMAND_GROUP = "System";

	private  final String PREF_SYSTEM_COMMANDS_ENABLED = "Config.system.commands.enabled";
	private  final boolean SYSTEM_COMMANDS_ENABLED_DEFAULT = false;
	
	private List<Command> commands = new ArrayList<Command>();
	
	private boolean enabled = true;
	
	public SystemCommandConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_SYSTEM_COMMANDS_ENABLED, SYSTEM_COMMANDS_ENABLED_DEFAULT);

		addCommandsToDashboard();
	}
	
	private void addCommandsToDashboard() {
		commands.add(new PrintPreferencesCommand());

		addCommandsToDashboard(COMMAND_GROUP, commands, enabled);
	}
}

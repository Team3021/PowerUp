package org.usfirst.frc.team3021.robot.configuration;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team3021.robot.commands.device.CollectTote;
import org.usfirst.frc.team3021.robot.commands.device.DeliverTote;
import org.usfirst.frc.team3021.robot.commands.device.DeployTote;
import org.usfirst.frc.team3021.robot.commands.device.ExtendClimber;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeScale;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeSwitch;
import org.usfirst.frc.team3021.robot.commands.device.StowTote;

import edu.wpi.first.wpilibj.command.Command;

public class DeviceCommandConfiguration extends BaseConfiguration {
	
	private final String COMMAND_GROUP = "Device";

	private  final String PREF_SYSTEM_COMMANDS_ENABLED = "Config.device.commands.enabled";
	private  final boolean SYSTEM_COMMANDS_ENABLED_DEFAULT = false;
	
	private List<Command> commands = new ArrayList<Command>();
	
	private boolean enabled = true;
	
	public DeviceCommandConfiguration() {
		enabled = Preferences.getInstance().getBoolean(PREF_SYSTEM_COMMANDS_ENABLED, SYSTEM_COMMANDS_ENABLED_DEFAULT);

		addCommandsToDashboard();
	}
	
	private void addCommandsToDashboard() {
		commands.add(new ExtendClimber());
		commands.add(new DeployTote());
		commands.add(new CollectTote());
		commands.add(new DeliverTote());
		commands.add(new StowTote());
		
		commands.add(new OuttakeSwitch());
		commands.add(new OuttakeScale());

		addCommandsToDashboard(COMMAND_GROUP, commands, enabled);
	}
}

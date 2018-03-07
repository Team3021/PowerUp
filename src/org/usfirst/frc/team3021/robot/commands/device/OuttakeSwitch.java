package org.usfirst.frc.team3021.robot.commands.device;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OuttakeSwitch extends CommandGroup {
	
	public OuttakeSwitch() {
		super();
		
		addSequential(new ExtendClimber());
		addSequential(new DeployTote());
		addSequential(new DeliverTote());
	}
}

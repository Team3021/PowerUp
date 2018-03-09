package org.usfirst.frc.team3021.robot.commands.device;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OuttakeScale extends CommandGroup {
	
	public OuttakeScale() {
		super();
		
		addSequential(new ExtendClimber(3.5)); //5 seconds?  We really need limit switches.
		addSequential(new DeployTote(1));
		addSequential(new DeliverTote(3));
		addSequential(new StowTote(1));
		addSequential(new RetractClimber(1));
	}
}

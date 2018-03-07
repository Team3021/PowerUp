package org.usfirst.frc.team3021.robot.commands.device;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OuttakeSwitch extends CommandGroup{
	public OuttakeSwitch(){
		super();
		addSequential(new RaiseClimberDuration(3)); //5 seconds?  We really need limit switches.
		addSequential(new DeployTote());
		addSequential(new DeliverTote(2));
	}
}

package org.usfirst.frc.team3021.robot.commands.test;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OuttakeSwitch extends CommandGroup{
	public OuttakeSwitch(){
		super();
		addSequential(new RaiseClimberDuration(2)); //5 seconds?  We really need limit switches.
		addSequential(new DeliverTote(2));
	}
}

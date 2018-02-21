package org.usfirst.frc.team3021.robot.commands.test;

import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class OuttakeScale extends CommandGroup{
	public OuttakeScale(){
		super();
		addSequential(new RaiseClimberDuration(3)); //5 seconds?  We really need limit switches.
		addSequential(new DeliverTote(2));
	}
}

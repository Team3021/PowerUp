package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightToSCALE extends CommandGroup {
	public RightToSCALE() {
		super("[] [Right] to [SCALE]");
		
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		addSequential(new MoveForwardForDistance(speed, 6.9));
		addSequential(new TurnRightToAngle(62));
		addSequential(new MoveForwardForDistance(speed, 1.75));
	}
}

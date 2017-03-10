package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.Stanley;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RedStartRightToRightGear extends CommandGroup {
	
	public RedStartRightToRightGear() {
		super("[Red] [Right] to [Right Gear]");
		
		requires(Stanley.robotDrive);
		
		addSequential(new MoveForwardForDistance(0.3, 2));
		addSequential(new TurnRightToAngle(20));
		addSequential(new MoveForwardForDistance(0.3, 5));
		addSequential(new TurnLeftToAngle(90));
		addSequential(new MoveForwardForDistance(0.3, 4.5));
	}
}

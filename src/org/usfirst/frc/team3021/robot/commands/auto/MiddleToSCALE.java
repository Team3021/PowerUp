package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleToSCALE extends CommandGroup {
	public MiddleToSCALE() {
		super("[Middle] to [SCALE]");
		
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.length() > 0)
		{
			if(gameData.charAt(1)== 'L')
			{
				//Middle to Left Switch
				addSequential(new TurnLeftToAngle(45));
				addSequential(new MoveForwardForDistance(speed, 6.9));
				addSequential(new TurnRightToAngle(90));
				
			}else if(gameData.charAt(1) == 'R') {
				//Middle to Right Switch
				
				addSequential(new TurnRightToAngle(45));
				addSequential(new MoveForwardForDistance(speed, 15.2));
				addSequential(new TurnLeftToAngle(45));
				
				addSequential(new MoveForwardForDistance(speed, 6.9));
				
			}else {
				//Straight 
				addSequential(new MoveForwardForDistance(speed, 10));
				
			}
			
		}

	}
}
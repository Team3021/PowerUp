package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToSWITCH extends CommandGroup {
	public LeftToSWITCH() {
		super("[Left] to [SWITCH]");
		
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		
		if(gameData.length() > 0)
		{
			if(gameData.charAt(0)== 'L')
			{
				//Left to Left Switch // PRIORITY
				addSequential(new MoveForwardForDistance(speed, 13.125));
				addSequential(new TurnRightToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 0.83));
				
				
			} else if (gameData.charAt(1) == 'L'){
				
				
				//Left to Left Scale //Second Priority
				addSequential(new MoveForwardForDistance(speed, 26.75));
				addSequential(new TurnRightToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 6.9));
				
			}else {
				//Nothing on left side, just go straight
				addSequential(new MoveForwardForDistance(speed, 10));
				
			}
			
		}

		
	}
}

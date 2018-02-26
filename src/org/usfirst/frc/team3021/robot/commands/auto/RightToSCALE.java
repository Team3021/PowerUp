package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle;
import org.usfirst.frc.team3021.robot.commands.test.OuttakeScale;
import org.usfirst.frc.team3021.robot.commands.test.OuttakeSwitch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightToSCALE extends CommandGroup {
	public RightToSCALE() {
		super("[Right] to [SCALE]");
		
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.length() > 0)
		{
			if(gameData.charAt(1)== 'R')
			{
				//Right to Right Scale //PRIORITY
				addSequential(new MoveForwardForDistance(speed, 26.75));
				addSequential(new TurnLeftToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 6.9));
				addSequential(new OuttakeScale());
				
			} else if (gameData.charAt(0) == 'R'){
				//Right to Right Switch //Second priority
				addSequential(new MoveForwardForDistance(speed, 13.125));
				addSequential(new TurnLeftToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 0.83));
				addSequential(new OuttakeSwitch());
				
			}else {
				//Nothing on right side, go forward
				addSequential(new MoveForwardForDistance(speed, 10));
				
			}
			
		}
		
	}
}
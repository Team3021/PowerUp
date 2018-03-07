package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeScale;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeSwitch;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToSCALE extends CommandGroup {
	public LeftToSCALE() {
		super("[Left] to [SCALE]");
		
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.length() > 0)
		{
			if(gameData.charAt(1)== 'L')
			{
				//Left to Left Scale //PRIORITY
				addSequential(new MoveForwardForDistance(speed, 26.75));
				addSequential(new TurnRightToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 6.9));
				addSequential(new OuttakeScale());
				
			} else if (gameData.charAt(0) == 'L'){
				//Left to Left Switch //Second priority
				addSequential(new MoveForwardForDistance(speed, 13.125));
				addSequential(new TurnRightToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 0.83));
				addSequential(new OuttakeSwitch());
				
			}else {
				//Nothing, go forward
				addSequential(new MoveForwardForDistance(speed, 10));
				
			}
			
		}
		
		
	
	}
}
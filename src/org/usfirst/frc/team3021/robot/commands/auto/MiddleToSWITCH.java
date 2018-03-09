package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.QBert;
import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeSwitch;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MiddleToSWITCH extends CommandGroup {
	public MiddleToSWITCH() {
		super("[Middle] to [SWITCH]");
	}
	
	@Override
	protected void initialize() {
		System.out.println("Entering Initialize MiddleToSwitchs");

	
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		String gameData = QBert.getGameData();

		if(gameData.length() > 0)
		{
			if(gameData.charAt(1)== 'L')
			{
				//Middle to Left Switch
				addSequential(new MoveForwardForDistance(speed, 2.25));
				addSequential(new TurnLeftToAngle(90.0));
				addSequential(new MoveForwardForDistance(speed, 5 ));
				addSequential(new TurnRightToAngle(90.0));
				addSequential(new MoveForwardForDistance(speed, 4.583));
				addSequential(new OuttakeSwitch());
			}else if(gameData.charAt(1) == 'R') {
				//Middle to Right Switch
				addSequential(new MoveForwardForDistance(speed, 8.25));
				addSequential(new OuttakeSwitch());
			}else {
				//Straight 
				addSequential(new MoveForwardForDistance(speed, 8.25));
				
			}
			
		} else {
			System.out.println("Failed to get game data" + getName());
			addSequential(new MoveForwardForDistance(speed, 10));
			
		}

	}
}
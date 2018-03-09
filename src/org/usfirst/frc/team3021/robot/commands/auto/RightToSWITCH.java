package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.QBert;
import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.device.DeliverTote;
import org.usfirst.frc.team3021.robot.commands.device.DeployTote;
import org.usfirst.frc.team3021.robot.commands.device.ExtendClimber;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeScale;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeSwitch;
import org.usfirst.frc.team3021.robot.commands.device.RetractClimber;
import org.usfirst.frc.team3021.robot.commands.device.StowTote;
import org.usfirst.frc.team3021.robot.commands.driving.MoveBackwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class RightToSWITCH extends CommandGroup {
	public RightToSWITCH() {
		super("[Right] to [SWITCH]");
	}
	
	@Override
	protected void initialize() {
		
		System.out.println("Entering Initialize RightToSwitch");

	
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		String gameData = QBert.getGameData();

		
		if(gameData.length() > 0)
		{
			if(gameData.charAt(0)== 'R')
			{
				//Right to Right Switch // PRIORITY
				addSequential(new MoveForwardForDistance(speed, 10));
				addSequential(new TurnLeftToAngle(100));
				addSequential(new MoveForwardForDistance(speed, 1.1));
				addSequential(new ExtendClimber());
				addSequential(new DeployTote(1));
				addSequential(new DeliverTote(2));
				addSequential(new StowTote(1));
				addSequential(new MoveBackwardForDistance(speed - .2, 0.83));
				addSequential(new RetractClimber(1));
			}
//			else if (gameData.charAt(1) == 'R'){
//				
//				
//				//Right to Right Scale //Second Priority
//				addSequential(new MoveForwardForDistance(speed, 26.75));
//				addSequential(new TurnLeftToAngle(90));
//				addSequential(new OuttakeScale());
				//}
			else {
				//Nothing on Right side, just go straight
				addSequential(new MoveForwardForDistance(speed, 10));
				
			}
			
		} else {
			System.out.println("Failed to get game data" + getName());
			addSequential(new MoveForwardForDistance(speed, 10));
			
		}
	}
}
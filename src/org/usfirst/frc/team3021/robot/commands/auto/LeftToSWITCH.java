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
import org.usfirst.frc.team3021.robot.commands.driving.HoldPosition;
import org.usfirst.frc.team3021.robot.commands.driving.MoveBackwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToSWITCH extends CommandGroup {
	public LeftToSWITCH() {
		super("[Left] to [SWITCH]");
	}
	
	@Override
	protected void initialize() {
		System.out.println("Entering Initialize LeftToSwitch");

	
		double speed = DriveCommand.getAutonomousMoveSpeed();
		
		String gameData = QBert.getGameData();
		
		Timer timer = new Timer();

		if (gameData.length() > 0) {
			
			if (gameData.charAt(0) == 'L') {
				// Left to Left Switch // PRIORITY
				addSequential(new MoveForwardForDistance(speed, 10));
				addSequential(new TurnRightToAngle(80));
				addSequential(new MoveForwardForDistance(speed, .2));
				addSequential(new ExtendClimber());
				addSequential(new DeployTote(1));
				addSequential(new DeliverTote(2));
				addSequential(new StowTote(1));
				addSequential(new MoveBackwardForDistance(speed - .2, 0.83));
				addSequential(new RetractClimber(1));
			}
//			} else if (gameData.charAt(1) == 'L') {
//
//				// Right to Right Scale //Second Priority
//				addSequential(new MoveForwardForDistance(speed, 26.75));
//				addSequential(new TurnRightToAngle(90));
//				addSequential(new OuttakeScale());
//}
			 else {
				// Nothing on Right side, just go straight
				addSequential(new MoveForwardForDistance(speed, 10));

			}

		} else {
			System.out.println("Failed to get game data" + getName());
			addSequential(new MoveForwardForDistance(speed, 10));
			
		}
	}
}

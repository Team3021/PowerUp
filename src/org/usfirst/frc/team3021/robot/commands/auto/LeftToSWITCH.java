package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.QBert;
import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeScale;
import org.usfirst.frc.team3021.robot.commands.device.OuttakeSwitch;
import org.usfirst.frc.team3021.robot.commands.driving.HoldPosition;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;

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

		if (gameData.length() > 0) {
			if (gameData.charAt(0) == 'L') {
				// Left to Left Switch // PRIORITY
				addSequential(new MoveForwardForDistance(speed, 13.125));
				addSequential(new TurnRightToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 0.83));
				
				addParallel(new HoldPosition());
				addParallel(new OuttakeSwitch());

			} else if (gameData.charAt(1) == 'L') {

				// Right to Right Scale //Second Priority
				addSequential(new MoveForwardForDistance(speed, 26.75));
				addSequential(new TurnRightToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 6.9));
				addSequential(new OuttakeScale());

			} else {
				// Nothing on Right side, just go straight
				addSequential(new MoveForwardForDistance(speed, 10));

			}

		} else {
			System.out.println("Failed to get game data" + getName());
			addSequential(new MoveForwardForDistance(speed, 10));
			
		}
	}
}

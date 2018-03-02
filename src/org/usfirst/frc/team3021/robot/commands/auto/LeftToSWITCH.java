package org.usfirst.frc.team3021.robot.commands.auto;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.MoveForwardForDistance;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle;
import org.usfirst.frc.team3021.robot.commands.test.OuttakeScale;
import org.usfirst.frc.team3021.robot.commands.test.OuttakeSwitch;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftToSWITCH extends CommandGroup {
	public LeftToSWITCH() {
		super("[Left] to [SWITCH]");

		double speed = DriveCommand.getAutonomousMoveSpeed();

		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (gameData.length() > 0) {
			if (gameData.charAt(0) == 'L') {
				// Left to Left Switch // PRIORITY
				addSequential(new MoveForwardForDistance(speed, 13.125));
				addSequential(new TurnRightToAngle(90));
				addSequential(new MoveForwardForDistance(speed, 0.83));
				addSequential(new OuttakeSwitch());

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

		}
	}
}

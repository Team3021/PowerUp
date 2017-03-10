package org.usfirst.frc.team3021.robot.commands.driving;

import org.usfirst.frc.team3021.robot.Stanley;
import org.usfirst.frc.team3021.robot.commands.DriveCommand;

public class DriveWithJoystick extends DriveCommand {

	@Override
	protected void execute() {
		Stanley.robotDrive.drive(getMoveValue(), getTurnValue());
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

	private double getMoveValue() {
		double moveValue = Stanley.mainController.getMoveValue();
		
		// Determines whether the output of expSpeed should be positive or negative.
		int verticalDirection = getDirection(Stanley.mainController.getMoveValue());

		// These equations are modified versions of the standard exponential form, ab^x. 
		// The equations here are of the form d(ab^|x| - a) where d is the respective direction value (note above). 
		// If you wish to alter these equations, adjust b, then solve for a at (1, 1).
		// Greater values of b make the controls less sensitive; smaller values are more sensitive.
		double expSpeed = -1 * verticalDirection * (0.2 * Math.pow(6.0, Math.abs(moveValue)) - 0.2);

		return expSpeed;
	}

	private double getTurnValue() {
		double turnValue = Stanley.mainController.getTurnValue();

		int turnDirection = getDirection(turnValue);

		// These equations are modified versions of the standard exponential form, ab^x. 
		// The equations here are of the form d(ab^|x| - a) where d is the respective direction value (note above). 
		// If you wish to alter these equations, adjust b, then solve for a at (1, 1).
		// Greater values of b make the controls less sensitive; smaller values are more sensitive.
		// a = 0.25, b = 5
		// a = 0.2, b = 6
		// a = 0.166, b = 7
		// a = 0.1428, b = 8
		// a = 0.125, b = 9
		
		turnValue = turnDirection * (0.166 * Math.pow(7.0, Math.abs(turnValue)) - 0.166);

		return turnValue;
	}

	private int getDirection(double n) {
		// Returns either 1, -1, or 0 depending on whether the argument is 
		// positive, negative, or neutral respectively.
		// Returns 0 when given -0 as an argument.

		int result = 0;

		if (n > 0) {
			result = 1;
		}
		else if (n < -0) {
			result = -1;
		}
		else {
			result = 0;
		}

		return result;
	}
}

package org.usfirst.frc.team3021.robot.commands.driving;

import org.usfirst.frc.team3021.robot.QBert;
import org.usfirst.frc.team3021.robot.commands.DriveCommand;

public abstract class TurnToAngle extends DriveCommand {
	
	protected double desiredAngle;

	public TurnToAngle(double desiredAngle) {
		super();
		
		this.desiredAngle = desiredAngle;
	}
	
	@Override
	protected void initialize() {
		System.out.println("Start TurnToAngle at: " + timeSinceInitialized());
		
		super.initialize();
	}
	
	@Override
	protected void execute() {
		QBert.robotDrive.turnToAngle(desiredAngle);
	}
	
	@Override
	protected void end() {
		System.out.println("Finished TurnToAngle for : " + desiredAngle + " degrees; turned : " + QBert.robotDrive.getGyroRotation());
	}
	
	@Override
	protected boolean isFinished() {
		return (Math.abs(QBert.robotDrive.getGyroRotation()) >= Math.abs(desiredAngle));
	}
}

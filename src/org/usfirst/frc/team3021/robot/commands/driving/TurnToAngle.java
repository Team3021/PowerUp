package org.usfirst.frc.team3021.robot.commands.driving;

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
		
		driveSystem.enableGyro();
	}
	
	@Override
	protected void execute() {
		driveSystem.turnToAngle(desiredAngle);
	}
	
	@Override
	protected void end() {
		System.out.println("Finished TurnToAngle for : " + desiredAngle + " degrees; turned : " + driveSystem.getGyroRotation());
		
		driveSystem.resetGyro();
	}
	
	@Override
	protected boolean isFinished() {
		return driveSystem.isGyroOnTarget();
	}
}

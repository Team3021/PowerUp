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
		
		driveSystem.setGyroDesiredAngle(desiredAngle);
		
		driveSystem.enableGyro();
		
		driveSystem.printHeaderData();
	}
	
	@Override
	protected void execute() {
		// Command does not perform action as the GyroController is executing drive actions in a PID loop
		
		// print some data
		driveSystem.printData();
	}
	
	@Override
	protected void end() {
		System.out.println("Finished TurnToAngle for : " + desiredAngle + " degrees; turned : " + driveSystem.getGyroRotation());
		
		driveSystem.stop();
		
		driveSystem.resetGyro();
	}
	
	@Override
	protected boolean isFinished() {
		return driveSystem.isGyroOnTarget() || timeSinceInitialized() >= 5.0;
	}
}

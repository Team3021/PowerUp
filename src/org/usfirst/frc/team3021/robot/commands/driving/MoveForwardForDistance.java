package org.usfirst.frc.team3021.robot.commands.driving;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;
import org.usfirst.frc.team3021.robot.inputs.DriveInput;

public class MoveForwardForDistance extends DriveCommand {
	
	protected double desiredSpeed = 0;
	protected double desiredDistance = 0;
	protected String direction = FORWARD;
	
	public MoveForwardForDistance(double speed, double distance) {
		super();
		
		this.desiredSpeed = speed;
		this.desiredDistance = distance;
	}

	@Override
	protected void initialize() {
		System.out.println("Start moving " + direction + " for distance : " + desiredDistance);
		
		driveSystem.zeroEncoders();
		
		super.initialize();
	}
	
	@Override
	protected void execute() {
		double moveValue = desiredSpeed;
		
		if (direction.equals(BACKWARD)) {
			moveValue = -1.0 * moveValue;
		}
		
		DriveInput driveInput = getDriveInput(moveValue);
		
		driveSystem.drive(driveInput);
	}
	
	@Override
	protected void end() {
		System.out.println("End moving " + direction + " for distance : " + desiredDistance + " and moved distance : " + driveSystem.getDistance());
	}
	
	@Override
	protected boolean isFinished() {
		return (Math.abs(driveSystem.getDistance()) >= Math.abs(desiredDistance));
	}

	// Drive forward using the gyro to maintain course
	// This assumes that forward is set to zero degrees
	// and thus the gyro offset is is a deviation from going straight forward
	private DriveInput getDriveInput(double moveValue) {
		
		double turnValue = 0;
		
		double currentHeading = driveSystem.getGyroRotation();
		
		// current heading is correct
		if (currentHeading == 0.0) {
			turnValue = 0;
		} 
		// current heading is drifting left,
		// then adjust to the right
		else if (currentHeading < 0.0) {
			turnValue = currentHeading * 0.05; // TODO need to determine the best value to adjust the heading
		}
		// current heading is drifting right
		// then adjust to the left
		else if (currentHeading > 0.0) {
			turnValue = -1.0 * currentHeading * 0.05; // TODO need to determine the best value to adjust the heading
		}
		
		ArcadeDriveInput input = new ArcadeDriveInput(moveValue, turnValue);
		
		return input;
	}
}

package org.usfirst.frc.team3021.robot.commands.driving;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;

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
		
		super.initialize();
	}
	
	@Override
	protected void execute() {
		double moveValue = desiredSpeed;
		
		if (direction.equals(BACKWARD)) {
			moveValue = -1.0 * moveValue;
		}
		
		driveSystem.moveWithGyro(moveValue);
	}
	
	@Override
	protected void end() {
		System.out.println("End moving " + direction + " for distance : " + desiredDistance + " and moved distance : " + driveSystem.getDistance());
	}
	
	@Override
	protected boolean isFinished() {
		return (Math.abs(driveSystem.getDistance()) >= Math.abs(desiredDistance));
	}
}

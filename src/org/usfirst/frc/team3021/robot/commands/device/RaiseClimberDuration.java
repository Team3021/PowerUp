package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.ClimberCommand;

public class RaiseClimberDuration extends ClimberCommand{

	int duration; // seconds

	public RaiseClimberDuration(int duration) {
		super();
		this.duration = duration;
	}

	@Override
	protected void execute() {
		climberSystem.startMotor();
	}

	@Override
	protected void end() {
		System.out.println("Done with the elevator.");
		climberSystem.stopMotor();
	}

	@Override
	protected boolean isFinished() {
		
		return (timeSinceInitialized() >= duration);
	}

}

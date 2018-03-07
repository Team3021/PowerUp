package org.usfirst.frc.team3021.robot.commands.test;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

public class CollectTote extends CollectorCommand {
	public CollectTote() {
		super();
	}
	
	@Override
	protected void execute() {
		collectorSystem.startMotor();
	}

	@Override
	protected void end() {
		System.out.println("Done with the intake.");
		collectorSystem.stopMotor();
	}

	@Override
	protected boolean isFinished() {
		return (collectorSystem.hasTote());
	}

}

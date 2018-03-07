package org.usfirst.frc.team3021.robot.commands.device;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

public class DeliverTote extends CollectorCommand {

	int time; // seconds
	
	public DeliverTote() {
		this(2);
	}
	
	public DeliverTote(int time) {
		super();

		this.time = time;
	}
	
	@Override
	protected void execute() {
		collectorSystem.deliver();
	}

	@Override
	protected void end() {
		System.out.println("Done with the outtake.");
		collectorSystem.stop();
	}

	@Override
	protected boolean isFinished() {
		return (timeSinceInitialized() >= time);
	}

}
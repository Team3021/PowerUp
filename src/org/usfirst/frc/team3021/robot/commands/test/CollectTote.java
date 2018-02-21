//IS THIS CORRECT LOL

package org.usfirst.frc.team3021.robot.commands.test;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;
import org.usfirst.frc.team3021.robot.subsystem.CollectorSystem;

import edu.wpi.first.wpilibj.Timer;

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

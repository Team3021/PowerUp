package org.usfirst.frc.team3021.robot.commands.driving;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;

public class MoveForwardEscalateSpeed extends DriveCommand{
	
	private double currentMoveValue = 0;
	
	public MoveForwardEscalateSpeed() {
		super();
	}
	
	protected void initialize() {
		System.out.println("Beginning Experiment 1; driving forward at increasing rate");
		
		super.initialize();
		
		System.out.println("(Input Voltage, Encoder Distance)");
	}
	
	protected void execute() {
		driveSystem.drive(new ArcadeDriveInput(currentMoveValue, 0));
		System.out.println(currentMoveValue + ", " + driveSystem.getDistance());
		currentMoveValue += 0.01;
	}
	
	protected boolean isFinished() {
		return (currentMoveValue == 1);
	}
	
	protected void end() {
		System.out.println("End of Experiment 1");
	}
}

package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.DriveCommand;
import org.usfirst.frc.team3021.robot.commands.driving.DriveWithJoystick;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle45;
import org.usfirst.frc.team3021.robot.commands.driving.TurnLeftToAngle90;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle45;
import org.usfirst.frc.team3021.robot.commands.driving.TurnRightToAngle90;
import org.usfirst.frc.team3021.robot.commands.driving.TurnToAngle180;
import org.usfirst.frc.team3021.robot.controller.onboard.DriveController;
import org.usfirst.frc.team3021.robot.controller.onboard.GyroController;
import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;
import org.usfirst.frc.team3021.robot.inputs.DriveInput;

import edu.wpi.first.wpilibj.command.Scheduler;

public class DriveSystem extends Subsystem {

	private DriveController driveController;
	
	private GyroController gyroController;
	
	private DriveCommand defaultCommand;

	private DriveCommand autonomousCommand;
	
	public DriveSystem() {
		driveController = new DriveController();
		
		gyroController = new GyroController();
	}
	
	@Override
	protected void initDefaultCommand() {
		defaultCommand = new DriveWithJoystick(mainController);
		
		setDefaultCommand(defaultCommand);
	}

	// ****************************************************************************
	// **********************              MOVE              **********************
	// ****************************************************************************

	public void zeroEncoders() {
		driveController.zeroDistance();
	}

	public double getDistance() {
		return driveController.getEncoderDistance();
	}

	public void drive(DriveInput input) {
		driveController.drive(input);
	}
	
	public double getMotorOutput() {
		return Math.abs(driveController.getMotorOutput());
	}
	
	// Drive forward using the gyro to maintain course
	// This assumes that forward is set to zero degrees
	// and thus the gyro offset is is a deviation from going straight forward
	public void moveWithGyro(double moveValue) {
		ArcadeDriveInput input = new ArcadeDriveInput(moveValue, gyroController.getTurnValue());
		drive(input);
	}

	public void stop() {
		driveController.stop();
	}

	// ****************************************************************************
	// **********************             TURNING            **********************
	// ****************************************************************************

	public void turnToAngle(double desiredAngle) {
		setGyroDesiredAngle(desiredAngle);
		
		ArcadeDriveInput input = new ArcadeDriveInput(0, getGyroTurnValue());
		
		drive(input);
	}

	public void turnToTarget(double desiredPosition) {
		setDesiredTargetPostion(desiredPosition);
		
		ArcadeDriveInput input = new ArcadeDriveInput(0, getTargetTurnValue());
		
		drive(input);
	}

	// ****************************************************************************
	// **********************              GYRO              **********************
	// ****************************************************************************

	public void zeroGyro() {
		gyroController.zeroGyro();
	}

	public void enableGyro() {
		gyroController.enable();
	}

	public void resetGyro() {
		gyroController.reset();
	}

	private void setGyroDesiredAngle(double angle) {
		gyroController.setDesiredAngle(angle);
	}

	private double getGyroTurnValue() {
		return gyroController.getTurnValue();
	}

	public double getGyroRotation() {
		return gyroController.getGyroRotation();
	}
	
	public boolean isGyroMoving() {
		return gyroController.isMoving();
	}

	public boolean isGyroOnTarget() {
		return gyroController.isOnTarget();
	}
	
	// ****************************************************************************
	// **********************             TARGET             **********************
	// ****************************************************************************

	public double getDesiredTargetPosition() {
		return 0;
	}
	
	private void setDesiredTargetPostion(double value) {
		return;
	}
	
	private double getTargetTurnValue() {
		return 0;
	}
	
	// ****************************************************************************
	// **********************             TELEOP            **********************
	// ****************************************************************************

	@Override
	public void teleopPeriodic() {
		
		gyroController.getGyroRotation();
		
		driveController.getEncoderDistance();
		driveController.getEncoderRate();
		
		driveController.getMotorOutput();
		
        if (mainController.isStoppingCommands() || auxController.isStoppingCommands()) {
        	Scheduler.getInstance().removeAll();
        }
		
        if (mainController.isZeroGyro() || auxController.isZeroGyro()) {
        	zeroGyro();
        }
		
        if (mainController.isZeroEncoders() || auxController.isZeroEncoders()) {
        	zeroEncoders();
        }
		
        // Lets the current autonomous command continue to run.
        if (autonomousCommand != null && autonomousCommand.isRunning()) {
        	return;
        }
        // Clears the current autonomous command if finished.
        else if (autonomousCommand != null && !autonomousCommand.isRunning()) {
        	autonomousCommand = null;
        }
        
        if (mainController.isRotatingToNinety()) {
        	autonomousCommand = new TurnRightToAngle90();
        }
        else if (mainController.isRotatingToNegativeNinety()) {
        	autonomousCommand = new TurnLeftToAngle90();
        }
        else if (mainController.isRotatingToOneHundredEighty()) {
        	autonomousCommand = new TurnToAngle180();
        }
        else if (mainController.isRotatingRight45()) {
        	autonomousCommand = new TurnRightToAngle45();
        }
        else if (mainController.isRotatingLeft45()) {
        	autonomousCommand = new TurnLeftToAngle45();
        }
        
        // Updates the scheduler to the selected autonomous command. 
        // If none is chosen, the scheduler runs the default command, driving with the joystick.
        if (autonomousCommand != null) {
        	Scheduler.getInstance().removeAll();
        	Scheduler.getInstance().add(autonomousCommand);
        }
	}
}

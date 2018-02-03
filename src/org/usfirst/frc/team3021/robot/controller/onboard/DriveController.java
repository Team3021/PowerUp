package org.usfirst.frc.team3021.robot.controller.onboard;

import org.usfirst.frc.team3021.robot.inputs.ArcadeDriveInput;
import org.usfirst.frc.team3021.robot.inputs.DriveInput;
import org.usfirst.frc.team3021.robot.inputs.VoltageDriveInput;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveController {
	
	private static final String PREF_DRIVE_WHEEL_SIZE = "DriveController.wheel.diameter";

	// DRIVE SYSTEM
	private RobotDrive robotDrive;
	
	// TALON PORTS
	private static final int RIGHT_FRONT_PORT = 22;
	private static final int RIGHT_REAR_PORT = 23;
	private static final int LEFT_REAR_PORT = 24;
	private static final int LEFT_FRONT_PORT = 25;

	// TALONS
	private CANTalon rightRearTalon;
	private CANTalon rightFrontTalon;
	private CANTalon leftRearTalon;
	private CANTalon leftFrontTalon;
	
	// ENCODER CHANNELS
	private static final int RIGHT_ENCODER_CHANNEL_A = 0;
	private static final int RIGHT_ENCODER_CHANNEL_B = 1;

	private static final int LEFT_ENCODER_CHANNEL_A = 2;
	private static final int LEFT_ENCODER_CHANNEL_B = 3;
	
	// ENCODERS
	private Encoder leftEncoder;
	private Encoder rightEncoder;
	
	// DISTANCE
	private static final int PULSE_PER_ROTATION = 256;
	private static final double INCHES_PER_FOOT = 12;
	
	// RATE
	private double maxRateAchieved = 0.0;

	public DriveController() {
		// TALONS
		leftFrontTalon = new CANTalon(LEFT_FRONT_PORT);
		leftRearTalon = new CANTalon(LEFT_REAR_PORT);
		rightFrontTalon = new CANTalon(RIGHT_FRONT_PORT);
		rightRearTalon = new CANTalon(RIGHT_REAR_PORT);
		
		// DRIVE DECLARATION
		robotDrive = new RobotDrive(leftFrontTalon, leftRearTalon, rightFrontTalon, rightRearTalon);
		robotDrive.setExpiration(0.1);

		// MOTOR INVERSIONS
		robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		robotDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		// Calculate encoder distance
		double wheelDiameter = Preferences.getInstance().getDouble(PREF_DRIVE_WHEEL_SIZE, 6.0);
		
		final double wheelCircumerence = wheelDiameter * Math.PI;
		final double distancePerPulse = (wheelCircumerence / PULSE_PER_ROTATION) / INCHES_PER_FOOT;
		
		// ENCODERS
		leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B, false, Encoder.EncodingType.k4X);
		leftEncoder.setMinRate(10);
		leftEncoder.setDistancePerPulse(distancePerPulse);
		
		rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B, true, Encoder.EncodingType.k4X);
		rightEncoder.setMinRate(10);
		rightEncoder.setDistancePerPulse(distancePerPulse);

		LiveWindow.addActuator("Drive", "Front Left Talon", leftFrontTalon);
		LiveWindow.addActuator("Drive", "Front Right Talon", rightFrontTalon);
		LiveWindow.addActuator("Drive", "Rear Left Talon", leftRearTalon);
		LiveWindow.addActuator("Drive", "Rear Right Talon", rightRearTalon);

		LiveWindow.addSensor("Drive Encoders", "Left Encoder", leftEncoder);
		LiveWindow.addSensor("Drive Encoders", "Right Encoder", rightEncoder);
	}

	// ****************************************************************************
	// **********************            ENCODERS            **********************
	// ****************************************************************************
	
	public void printEncoderValues() {
		SmartDashboard.putNumber("Drive : Encoder Speed Left", leftEncoder.getRate());
		SmartDashboard.putNumber("Drive : Encoder Speed Right", rightEncoder.getRate());
		SmartDashboard.putNumber("Drive : Encoder Pulses Left: ", leftEncoder.get());
		SmartDashboard.putNumber("Drive : Encoder Pulses Right: ", rightEncoder.get());
	}

	public void printEncoderDistance(double distance) {
		SmartDashboard.putNumber("Drive : Encoder Distance", distance);
	}

	public double getEncoderRate() {
		double rateLeftSide = leftEncoder.getRate();
		
		SmartDashboard.putNumber("Drive : Encoder Rate : Left", rateLeftSide);

		double rateRightSide = rightEncoder.getRate();
		
		SmartDashboard.putNumber("Drive : Encoder Rate : Right", rateRightSide);
		
		double rateAverage = (rateLeftSide + rateRightSide) / 2;
		
		SmartDashboard.putNumber("Drive : Encoder Rate", rateAverage);
		
		if (rateAverage > maxRateAchieved) {
			maxRateAchieved = rateAverage;
			
			SmartDashboard.putNumber("Drive : Encoder Rate Max", maxRateAchieved);
		}
		
		return rateAverage;
	}
	
	public double getEncoderDistance() {

		double distanceTraveledLeftSide = getDistanceTraveled(leftEncoder);
		
		SmartDashboard.putNumber("Drive : Encoder Distance : Left", distanceTraveledLeftSide);

		double distanceTraveledRightSide = getDistanceTraveled(rightEncoder);
		
		SmartDashboard.putNumber("Drive : Encoder Distance : Right", distanceTraveledRightSide);
		
		double distanceTraveledAverage = (distanceTraveledLeftSide + distanceTraveledRightSide) / 2;
		
		SmartDashboard.putNumber("Drive : Encoder Distance", distanceTraveledAverage);
		
		return distanceTraveledAverage;
	}

	public void zeroDistance() {
		System.out.println("Zero encoders");
		
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public double getDistanceTraveled(Encoder driveEncoder) {
		return driveEncoder.getDistance();
	}

	// ****************************************************************************
	// **********************            ACTIONS             **********************
	// ****************************************************************************

	public void drive(DriveInput input) {
		if (input instanceof ArcadeDriveInput) {
			ArcadeDriveInput arcadeInput = (ArcadeDriveInput) input;
			robotDrive.arcadeDrive(arcadeInput.getMoveValue(), arcadeInput.getTurnValue(), false);
		}
		else if (input instanceof VoltageDriveInput) {
			// Not yet implemented
		}
		
	}
	
	public void stop() {
		robotDrive.arcadeDrive(0, 0, false);
	}

	public double getMotorOutput() {
		
		SmartDashboard.putNumber("Drive : Motor Voltage : Left Front", leftFrontTalon.getOutputVoltage());
		SmartDashboard.putNumber("Drive : Motor Voltage : Left Rear", leftRearTalon.getOutputVoltage());
		SmartDashboard.putNumber("Drive : Motor Voltage : Right Front", rightFrontTalon.getOutputVoltage());
		SmartDashboard.putNumber("Drive : Motor Voltage : Right Rear", rightRearTalon.getOutputVoltage());
		
		return (leftFrontTalon.getOutputVoltage() + leftRearTalon.getOutputVoltage() + rightFrontTalon.getOutputVoltage() + rightRearTalon.getOutputVoltage()) / 4;
	}
}

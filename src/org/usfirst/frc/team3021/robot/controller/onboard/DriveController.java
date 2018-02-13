package org.usfirst.frc.team3021.robot.controller.onboard;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveController {
	
	private static final String PREF_DRIVE_WHEEL_SIZE = "DriveController.wheel.diameter";
	private static final double DRIVE_WHEEL_SIZE_DEFAULT = 6.0;

	// DRIVE SYSTEM
	private DifferentialDrive robotDrive;
	
	// TALON PORTS
	private static final int RIGHT_FRONT_PORT = 24;
	private static final int RIGHT_REAR_PORT = 25;
	private static final int LEFT_REAR_PORT = 23;
	private static final int LEFT_FRONT_PORT = 22;

	// TALONS
	private WPI_TalonSRX rightRearTalon;
	private WPI_TalonSRX rightFrontTalon;
	private WPI_TalonSRX leftRearTalon;
	private WPI_TalonSRX leftFrontTalon;
	
	// SPEED CONTROLLERS
	private SpeedControllerGroup leftSpeedController;
	private SpeedControllerGroup rightSpeedController;
	
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
		leftFrontTalon = new WPI_TalonSRX(LEFT_FRONT_PORT);
		leftRearTalon = new WPI_TalonSRX(LEFT_REAR_PORT);
		rightFrontTalon = new WPI_TalonSRX(RIGHT_FRONT_PORT);
		rightRearTalon = new WPI_TalonSRX(RIGHT_REAR_PORT);
		
		// DRIVE DECLARATION
		leftSpeedController = new SpeedControllerGroup(leftFrontTalon, leftRearTalon);
		rightSpeedController = new SpeedControllerGroup(rightFrontTalon, rightRearTalon);
		
		robotDrive = new DifferentialDrive(leftSpeedController, rightSpeedController);
		robotDrive.setExpiration(0.1);

		// Calculate encoder distance
		double wheelDiameter = Preferences.getInstance().getDouble(PREF_DRIVE_WHEEL_SIZE, DRIVE_WHEEL_SIZE_DEFAULT);
		
		final double wheelCircumerence = wheelDiameter * Math.PI;
		final double distancePerPulse = (wheelCircumerence / PULSE_PER_ROTATION) / INCHES_PER_FOOT;
		
		// ENCODERS
		leftEncoder = new Encoder(LEFT_ENCODER_CHANNEL_A, LEFT_ENCODER_CHANNEL_B, false, Encoder.EncodingType.k4X);
		leftEncoder.setMinRate(10);
		leftEncoder.setDistancePerPulse(distancePerPulse);
		
		rightEncoder = new Encoder(RIGHT_ENCODER_CHANNEL_A, RIGHT_ENCODER_CHANNEL_B, true, Encoder.EncodingType.k4X);
		rightEncoder.setMinRate(10);
		rightEncoder.setDistancePerPulse(distancePerPulse);
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

	public void drive(double moveValue, double turnValue) {
		robotDrive.arcadeDrive(moveValue, turnValue, false);
	}
	
	public void stop() {
		robotDrive.arcadeDrive(0, 0, false);
	}

	public double getMotorOutput() {
		
		SmartDashboard.putNumber("Drive : Motor Voltage : Left Front", leftFrontTalon.getMotorOutputVoltage());
		SmartDashboard.putNumber("Drive : Motor Voltage : Left Rear", leftRearTalon.getMotorOutputVoltage());
		SmartDashboard.putNumber("Drive : Motor Voltage : Right Front", rightFrontTalon.getMotorOutputVoltage());
		SmartDashboard.putNumber("Drive : Motor Voltage : Right Rear", rightRearTalon.getMotorOutputVoltage());
		
		return (leftFrontTalon.getMotorOutputVoltage() + leftRearTalon.getMotorOutputVoltage() + rightFrontTalon.getMotorOutputVoltage() + rightRearTalon.getMotorOutputVoltage()) / 4;
	}
}

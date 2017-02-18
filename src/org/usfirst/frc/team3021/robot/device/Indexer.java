package org.usfirst.frc.team3021.robot.device;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Indexer extends RunnableDevice {
	
	private static final String TIME_BEFORE_FIRST_PERIODIC = "Indexer : Time Before First Periodic";
	private static final double DEFAULT_TIME_BEFORE_FIRST_PERIODIC = 1000;
	
	private static final String TIME_BETWEEN_PERIODIC = "Indexer : Time Between Periodic";
	private static final double DEFAULT_TIME_BETWEEN_PERIODIC = 100;
	
	private static final String TIME_FOR_MOTOR = "Indexer : Time for Motor";
	private static final double DEFAULT_TIME_FOR_MOTOR = 30;
	
	private static final String VOLTAGE = "Indexer : Voltage";
	private static final double DEFAULT_VOLTAGE = 0.3;
	
	private CANTalon talon;
	
	public Indexer(int port) {
		super();
		
		talon = new CANTalon(port);
		
		SmartDashboard.putNumber(VOLTAGE, DEFAULT_VOLTAGE);
		
		SmartDashboard.putNumber(TIME_BEFORE_FIRST_PERIODIC, DEFAULT_TIME_BEFORE_FIRST_PERIODIC);
		SmartDashboard.putNumber(TIME_BETWEEN_PERIODIC, DEFAULT_TIME_BETWEEN_PERIODIC);
		SmartDashboard.putNumber(TIME_FOR_MOTOR, DEFAULT_TIME_FOR_MOTOR);
	}

	@Override
	protected void runPeriodic() {
		// delay the first periodic
		if (isFirstPeriodic()) {
			long pulseTime = (long) SmartDashboard.getNumber(TIME_BEFORE_FIRST_PERIODIC, DEFAULT_TIME_BEFORE_FIRST_PERIODIC);
			RunnableDevice.delay(pulseTime);
		}
		
		// start the motor
		double voltage = SmartDashboard.getNumber(VOLTAGE, DEFAULT_VOLTAGE);
		talon.set(voltage);

		// run the motor for a time
		long motorRunTime = (long) SmartDashboard.getNumber(TIME_FOR_MOTOR, DEFAULT_TIME_FOR_MOTOR);
		RunnableDevice.delay(motorRunTime);
		
		// stop the motor
		talon.set(0);
		
		// wait before next periodic
		long timeBetweenPeriodic = (int) SmartDashboard.getNumber(TIME_BETWEEN_PERIODIC, DEFAULT_TIME_BETWEEN_PERIODIC);
		RunnableDevice.delay(timeBetweenPeriodic);
	}
}
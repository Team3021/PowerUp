package org.usfirst.frc.team3021.robot.subsystem;

//import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Preferences;

public class Collector extends Subsystem {
	
	private static final String PREF_VOLTAGE = "Collector.motor.voltage";
	private static final double DEFAULT_VOLTAGE = 0.55;
	
	private CANTalon right_motor;
	private CANTalon left_motor;
	
	public Collector() {		
		right_motor = new CANTalon(21);
		left_motor = new CANTalon(27);

	}
	
	@Override
	public void teleopPeriodic() {
		// Control the motor
		if (mainController.isCollecting() || auxController.isCollecting()) {
			startMotor();
		}
		else if (mainController.isLaunching() || auxController.isLaunching()) {
			reverseMotor();
		}
		else {
			stopMotor();
		}
		
	}

	public void startMotor() {
		right_motor.set(getVoltage());
		left_motor.set(-getVoltage());

	}
	public void reverseMotor() {
		right_motor.set(-getVoltage());
		left_motor.set(getVoltage());

	}
	public void stopMotor() {
		right_motor.set(0);
		left_motor.set(0);

	}
	
	private double getVoltage() {
		double voltage = Preferences.getInstance().getDouble(PREF_VOLTAGE, DEFAULT_VOLTAGE);
		
		// reverse the polarity
		voltage = voltage * -1;
		
		return voltage;
	}

	@Override
	protected void initDefaultCommand() {
		//setDefaultCommand(new CollectorCommand());
	}	
}

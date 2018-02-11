package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Preferences;

public class CollectorSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Collector.enabled";
	private static final boolean ENABLED_DEFAULT = true;
	
	private boolean isEnabled = ENABLED_DEFAULT;
	
	private static final String PREF_VOLTAGE = "Collector.motor.voltage";
	private static final double VOLTAGE_DEFAULT = 0.55;
	
	private double voltage = VOLTAGE_DEFAULT;
	
	private WPI_TalonSRX right_motor;
	private WPI_TalonSRX left_motor;
	
	public CollectorSystem() {		
		 isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		 voltage = Preferences.getInstance().getDouble(PREF_VOLTAGE, VOLTAGE_DEFAULT);

		 if (isEnabled) {
			 right_motor = new WPI_TalonSRX(21);
			 left_motor = new WPI_TalonSRX(27);
		 }
	}
	
	@Override
	public void teleopPeriodic() {
		
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		
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
		// reverse the polarity
		voltage = voltage * -1;
		
		return voltage;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CollectorCommand());
	}	
}

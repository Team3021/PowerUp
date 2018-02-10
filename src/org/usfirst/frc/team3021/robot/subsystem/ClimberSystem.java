package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Spark;

public class ClimberSystem extends Subsystem {
	
	private static final String PREF_VOLTAGE = "Climber.motor.voltage";
	private static final double DEFAULT_VOLTAGE = 0.55;
	
	private Spark right_motor;
	private Spark left_motor;
	
	public ClimberSystem() {		
		right_motor = new Spark(1);
		left_motor = new Spark(2);
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
		setDefaultCommand(new CollectorCommand());
	}	
}

package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.ClimberCommand;
import org.usfirst.frc.team3021.robot.configuration.Preferences;
import edu.wpi.first.wpilibj.Spark;

public class ClimberSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Climber.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;
	
	private static final String PREF_VOLTAGE = "Climber.motor.voltage";
	private static final double VOLTAGE_DEFAULT = 0.5;
	
	private double voltage = VOLTAGE_DEFAULT;
	
	private static final double REVERSE_MULTIPLIER = -1.0;
	
	private Spark motor;
	
	public ClimberSystem() {		
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		voltage = Preferences.getInstance().getDouble(PREF_VOLTAGE, VOLTAGE_DEFAULT);

		motor = new Spark(0);
		motor.setInverted(true);
	}
	
	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		if (auxController.isClimberExtending()) {
			extend();
		}
		else if (auxController.isClimberContracting()) {
			contract();
		}
		else {
			stop();
		}
	}

	public void extend() {
		motor.set(getVoltage());
	}
	
	public void contract() {
		motor.set(REVERSE_MULTIPLIER * getVoltage());
	}
	
	public void stop() {
		motor.set(0);
	}
	
	private double getVoltage() {
		return voltage;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ClimberCommand());
	}	
}

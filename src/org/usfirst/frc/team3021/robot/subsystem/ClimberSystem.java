package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.ClimberCommand;
import org.usfirst.frc.team3021.robot.configuration.Preferences;
import edu.wpi.first.wpilibj.Spark;

public class ClimberSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Climber.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;
	
	private static final String PREF_VOLTAGE = "Climber.motor.voltage";
	private static final double VOLTAGE_DEFAULT = 0.55;

	private double voltage = VOLTAGE_DEFAULT;
	
	private Spark motor;
	
	public ClimberSystem() {		
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		voltage = Preferences.getInstance().getDouble(PREF_VOLTAGE, VOLTAGE_DEFAULT);

		motor = new Spark(0);
	}
	
	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}

		if (auxController.isClimberSafteyOn())  {
			startMotor();
		}
		else if (auxController.isClimberExtending()) {//!auxController.isClimberSafteyOn() && auxController.isClimberExtending()) { //mainController.isCollecting() || 
			startMotor();
		}
		else if (auxController.isClimberContracting()) { //!auxController.isClimberSafteyOn() &&  auxController.isClimberContracting()) { //mainController.isLaunching() ||
			reverseMotor();
		}
		else {
			stopMotor();
		}
	}

	public void startMotor() {
		motor.set(getVoltage());
	}
	
	public void reverseMotor() {
		motor.set(-getVoltage());

	}
	
	public void stopMotor() {
		motor.set(0);
	}
	
	private double getVoltage() {
		// reverse the polarity
		voltage = voltage * -1;
		
		return voltage;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ClimberCommand());
	}	
}

package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import org.usfirst.frc.team3021.robot.configuration.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

public class CollectorSystem extends Subsystem {
	
	private static final String PREF_ENABLED = "Collector.enabled";
	private static final boolean ENABLED_DEFAULT = true;

	private boolean isEnabled = ENABLED_DEFAULT;

	private static final String PREF_VOLTAGE = "Collector.motor.voltage";
	private static final double VOLTAGE_DEFAULT = 0.65;

	private double voltage = VOLTAGE_DEFAULT;
	
	private static final double REVERSE_MULTIPLIER = -1.0;
	
	private static DigitalInput tote_switch;
	private static Relay        tote_light;
	
	private static Compressor compressor;

	private WPI_TalonSRX right_motor;
	private WPI_TalonSRX left_motor;
	
	private Solenoid collector_deploy;
	private Solenoid collector_stow;
	
	boolean collecter_deployed = false;

	public CollectorSystem() {	
		isEnabled =  Preferences.getInstance().getBoolean(PREF_ENABLED, ENABLED_DEFAULT);
		voltage = Preferences.getInstance().getDouble(PREF_VOLTAGE, VOLTAGE_DEFAULT);

		if (isEnabled) {
			collector_deploy = new Solenoid(1);
			collector_stow = new Solenoid(2);
			
			right_motor = new WPI_TalonSRX(26);
			left_motor = new WPI_TalonSRX(27);
			left_motor.setInverted(true);
			
			tote_switch = new DigitalInput(4);
			tote_light = new Relay(0);        ;

			compressor = new Compressor(0);
			compressor.setClosedLoopControl(true);
		}
	}

	@Override
	public void teleopPeriodic() {
		// don't do any actions as the sub system is not enabled
		if (!isEnabled) {
			return;
		}
		else{
			
		}
		
		if (tote_switch.get()) {
			tote_light.set(Relay.Value.kOff);
		}
		else {
			tote_light.set(Relay.Value.kForward);
	    }			

		if (auxController.isClimberSafteyOn())  {
			collector_deploy.set(false);
			collector_stow.set(true);	
		}
		else {
			if (auxController.isCollectorDeploying()) {
				collector_deploy.set(true);
				collector_stow.set(false);
				collecter_deployed = true;
			}
			
			if (auxController.isCollectorStowing()) {
				collector_deploy.set(false);
				collector_stow.set(true);	
				collecter_deployed = false;
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
	}

	public void startMotor() {
		right_motor.set(REVERSE_MULTIPLIER * getVoltage());
		left_motor.set(getVoltage());
	}
	
	public void reverseMotor() {
		right_motor.set(getVoltage());
		left_motor.set(REVERSE_MULTIPLIER * getVoltage());
	}
	
	public void stopMotor() {
		right_motor.set(0);
		left_motor.set(0);
	}

	private double getVoltage() {
		return voltage;
	}
	
	public boolean hasTote(){
		return !(tote_switch.get());
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CollectorCommand());
	}	
}

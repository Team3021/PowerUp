package org.usfirst.frc.team3021.robot.subsystem;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;

public class CollectorSystem extends Subsystem {
	
	private static final String PREF_VOLTAGE = "Collector.motor.voltage";
	private static final double DEFAULT_VOLTAGE = 0.55;
	
	private WPI_TalonSRX right_motor;
	private WPI_TalonSRX left_motor;
	private Solenoid     climber_lift;
	
	public CollectorSystem() {	
		climber_lift = new Solenoid(1);
		right_motor = new WPI_TalonSRX(21);
		left_motor = new WPI_TalonSRX(27);
	}
	
	@Override
	public void teleopPeriodic() {
		
		if (auxController.isClimberSafteyOn())  {
			climber_lift.set(false);
		}
		else {
			// Control the motor
			if (auxController.isCollectorDeploying() && (mainController.isCollecting() || auxController.isCollecting())) {
				startMotor();
			}
			else if (auxController.isCollectorDeploying() && (mainController.isLaunching() || auxController.isLaunching())) {
				reverseMotor();
			}
			else {
				stopMotor();
			}
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

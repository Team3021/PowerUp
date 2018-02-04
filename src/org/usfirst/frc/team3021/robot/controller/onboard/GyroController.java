package org.usfirst.frc.team3021.robot.controller.onboard;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroController implements PIDOutput {
	
	private final String PREF_GYRO_USB_ENABLED = "Gyro.usb.enabled";
	private final String PREF_GYRO_MXP_ENABLED = "Gyro.mxp.enabled";
	
	private final String PREF_GYRO_TURN_VALUE_MIN = "Gyro.turn.rate.min";
	
	private final String PREF_GYRO_TURN_RATE_MAX = "Gyro.turn.rate.max";
	
	private final double GYRO_TURN_RATE_DEFAULT = 0.375;
	
	private double turnRateMin = 0.0;
	private double turnRateMax = GYRO_TURN_RATE_DEFAULT;

	// Member Attributes
	private AHRS navx;
	private PIDController pidController;
    double rotateToAngleRate;
    
    /* The following PID Controller coefficients will need to be tuned */
    /* to match the dynamics of your drive system.  Note that the      */
    /* SmartDashboard in Test mode has support for helping you tune    */
    /* controllers by displaying a form where you can enter new P, I,  */
    /* and D constants and test the mechanism.                         */
    
    // Motor tuning values
    static final double kP = 0.01; // Proportion
    static final double kI = 0.00; // Integral 
    static final double kD = 0.02; // Derivative
    static final double kF = 0.00; // Feedback
    
    public static final double kToleranceDegrees = 4.0f; // Tolerance--Precision of turning with the Navx
	
	public GyroController() {
		if (!isGyroEnabled()) {
			System.out.println("WARNING !!! NO GYRO PORT ENABLED");
			return;  
		}
		else {
			System.out.println("gyro port enabled");
		}
		
        try {
        	
        	if (isUSBEnabled()) {
        		// The Navx--connected by USB port
        		System.out.println("Using NavX on USB port");
        		navx = new AHRS(Port.kUSB); 
        	} else {
        		// The Navx--connected by MXP port
        		System.out.println("Using NavX on MXP port");
        		navx = new AHRS(SPI.Port.kMXP); 
        	}
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        
        recalibrateGyro();

        pidController = new PIDController(kP, kI, kD, kF, navx, this, 0.01); 
        
        pidController.setInputRange(-180.0f, 180.0f);
        pidController.setAbsoluteTolerance(kToleranceDegrees);
        pidController.setContinuous(true);

		turnRateMin = Preferences.getInstance().getDouble(PREF_GYRO_TURN_VALUE_MIN, 0.0);
        turnRateMax = Preferences.getInstance().getDouble(PREF_GYRO_TURN_RATE_MAX, GYRO_TURN_RATE_DEFAULT);
        
        pidController.setOutputRange(-turnRateMax, turnRateMax);
        
        enable();
        
	}
	
	private boolean isUSBEnabled() {
		return Preferences.getInstance().getBoolean(PREF_GYRO_USB_ENABLED, false);
	}
	
	private boolean isMXPEnabled() {
		return Preferences.getInstance().getBoolean(PREF_GYRO_MXP_ENABLED, true);
	}
	
	private boolean isGyroEnabled() {
		return isUSBEnabled() || isMXPEnabled();
	}

	public boolean isMoving() {
		return navx.isMoving();
	}

	// Input is the range of -180 to 180 to control the PID Controller
	public void setDesiredAngle(double setpoint) {
		pidController.setSetpoint(setpoint);
	}

	public double getGyroRotation() {
		
		double rotation = navx.getYaw();
		
		SmartDashboard.putNumber("GyroController : Navx : Rotation", rotation);

		return rotation;
	}

	public double getTurnValue() {

		int turnDirection = getDirection(rotateToAngleRate);
		
		double turnValue = rotateToAngleRate;
		
		if (Math.abs(turnValue) > turnRateMax) {
			turnValue = turnRateMax * turnDirection;
		}
		
		if (Math.abs(turnValue) < turnRateMin) {
			turnValue = turnRateMin * turnDirection;
		}
		
		SmartDashboard.putNumber("GyroController : currentRotationRate",  turnValue);

		return turnValue;
	}

	private void recalibrateGyro() {
		System.out.println("Recalibrating gyro");
		
		navx.reset();
	}
	
	public void zeroGyro() {
		System.out.println("Zero the gyro");
		
		navx.zeroYaw();
	}
	
	public void enable() {
		System.out.println("Enable the gyro");
		
		pidController.enable();
	}
	
	public void reset() {
		System.out.println("Reset the gyro");
		
		pidController.reset();
	}
	
	public boolean isOnTarget() {
		return pidController.onTarget();
	}
	
    @Override
    /* This function is invoked periodically by the PID Controller, */
    /* based upon navX MXP yaw angle input and PID Coefficients.    */
    public void pidWrite(double rotateToAngleRate) {
        this.rotateToAngleRate = rotateToAngleRate;
    }
    
	private int getDirection(double n) {
		// Returns either 1, -1, or 0 depending on whether the argument is 
		// positive, negative, or neutral respectively.
		// Returns 0 when given -0 as an argument.

		int result = 0;

		if (n > 0) {
			result = 1;
		}
		else if (n < -0) {
			result = -1;
		}
		else {
			result = 0;
		}

		return result;
	}
   
}
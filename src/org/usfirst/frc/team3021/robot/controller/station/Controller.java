package org.usfirst.frc.team3021.robot.controller.station;

public interface Controller {
	
	double getMoveValue();

	double getTurnValue();

	boolean isLaunching();

	boolean isSwitchingCamera();
	
	boolean isCollecting();

	boolean isResettingNavx();

	boolean isRotatingToNinety();
	
	boolean isRotatingToNegativeNinety();
	
	boolean isRotatingToOneHundredEighty();

	boolean isRotatingRight45();

	boolean isRotatingLeft45();

	boolean isXbox();

	boolean isClimberSafteyOn();

	boolean isClimberExtending();
	
	boolean isClimberContracting();
	
	boolean isCollectorDeploying();

	boolean isCollectorStowing();
	
	void printButtonActions(String controller);
 
	boolean isScopeEnabled();

	boolean isTargetLocatorEnabled();

	boolean isStoppingCommands();

	boolean isZeroGyro();

	boolean isZeroEncoders();
}
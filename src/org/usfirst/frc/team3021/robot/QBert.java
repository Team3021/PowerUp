package org.usfirst.frc.team3021.robot;

import org.usfirst.frc.team3021.robot.controller.station.Controller;
import org.usfirst.frc.team3021.robot.subsystem.CollectorSystem;
import org.usfirst.frc.team3021.robot.subsystem.DriveSystem;
import org.usfirst.frc.team3021.robot.subsystem.VisionSystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class QBert extends IterativeRobot {
	
	// Member Attributes
	public static Configuration configuration;
	
	private static DriveSystem driveSystem;
	public static CollectorSystem collectorSystem;	
	private static VisionSystem visionSystem;
	
	public static Controller mainController;
	public static Controller auxController;

	public QBert() {
		super();

		// Create the sub systems
		visionSystem = new VisionSystem();
		
		driveSystem = new DriveSystem();
		
		collectorSystem = new CollectorSystem();
		
		// Create the configuration and initialize
		configuration = new Configuration();
		
		configuration.addSubsystemsToDashboard();
		
		configuration.addCommandsToDashboard();
		
		configuration.addControllerChoices();
		
		configuration.addAutonmousChoices();
	}

	// ****************************************************************************
	// **********************              INIT              **********************
	// ****************************************************************************
	
	@Override
	public void robotInit() {
		System.out.println("Robot initializing...");
		
		mainController = configuration.initializeMainController();

		auxController = configuration.initializeAuxController();

		driveSystem.setControllers(mainController, auxController);
		visionSystem.setControllers(mainController, auxController);
		collectorSystem.setControllers(mainController, auxController);
	}

	// ****************************************************************************
	// **********************          AUTONOMOUS            **********************
	// ****************************************************************************
	
	@Override
	public void autonomousInit() {
		// Stop any commands that might be left running from another mode
		Scheduler.getInstance().removeAll();
		
		String autoMode = configuration.getAutonomousMode();
		
		System.out.println("Autonomous mode: " + autoMode);
		
		Command autoCommand = configuration.getAutonomousCommand();
		
		Scheduler.getInstance().add(autoCommand);
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	// ****************************************************************************
	// **********************            TELEOP              **********************
	// ****************************************************************************

	@Override
	public void teleopInit() {
		// Stop any commands that might be left running from another mode
		Scheduler.getInstance().removeAll();
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		driveSystem.teleopPeriodic();
		visionSystem.teleopPeriodic();
		collectorSystem.teleopPeriodic();
		
	}

	// ****************************************************************************
	// **********************             TEST               **********************
	// ****************************************************************************

	@Override
	public void testInit() {
		// Stop any commands that might be left running from another mode
		Scheduler.getInstance().removeAll();
	}

	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}

	// ****************************************************************************
	// **********************       OTHER BASE MEHTODS       **********************
	// ****************************************************************************

	@Override
	public void disabledInit() {
		// Do nothing to prevent warnings
	}

	@Override
	public void robotPeriodic() {
		// Do nothing to prevent warnings
	}

	@Override
	public void disabledPeriodic() {
		// Do nothing to prevent warnings
	}

	// ****************************************************************************
	// **********************        ACCESSOR METHODS        **********************
	// ****************************************************************************

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static VisionSystem getVisionSystem() {
		return visionSystem;
	}

	public static DriveSystem getDriveSystem() {
		return driveSystem;
	}

	public static CollectorSystem getCollectorSystem() {
		return collectorSystem;
	}
}


package org.usfirst.frc.team3021.robot;

import org.usfirst.frc.team3021.robot.configuration.AutonomousConfiguration;
import org.usfirst.frc.team3021.robot.configuration.ControllerConfiguration;
import org.usfirst.frc.team3021.robot.configuration.DriveCommandConfiguration;
import org.usfirst.frc.team3021.robot.configuration.TestCommandConfiguration;
import org.usfirst.frc.team3021.robot.controller.station.Controller;
import org.usfirst.frc.team3021.robot.subsystem.ClimberSystem;
import org.usfirst.frc.team3021.robot.subsystem.CollectorSystem;
import org.usfirst.frc.team3021.robot.subsystem.DriveSystem;
import org.usfirst.frc.team3021.robot.subsystem.VisionSystem;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class QBert extends IterativeRobot {
	
	// Member Attributes
	private static ControllerConfiguration controllerConfiguration;
	private static AutonomousConfiguration autonomousConfiguration;
	
	private static DriveSystem driveSystem;
	private static CollectorSystem collectorSystem;	
	private static ClimberSystem climberSystem;
	private static VisionSystem visionSystem;
	
	private Controller mainController;
	private Controller auxController;

	public QBert() {
		super();

		// Create the sub systems
		visionSystem = new VisionSystem();
		driveSystem = new DriveSystem();
		collectorSystem = new CollectorSystem();
		climberSystem = new ClimberSystem();
		
		// Create the main configuration
		controllerConfiguration = new ControllerConfiguration();
		autonomousConfiguration = new AutonomousConfiguration();
		
		// Create the command configuration
		new DriveCommandConfiguration();
		new TestCommandConfiguration();
	}

	// ****************************************************************************
	// **********************              INIT              **********************
	// ****************************************************************************
	
	@Override
	public void robotInit() {
		System.out.println("Robot initializing...");
		
		mainController = controllerConfiguration.getMainController();
		auxController = controllerConfiguration.getAuxController();

		driveSystem.setControllers(mainController, auxController);
		visionSystem.setControllers(mainController, auxController);
		collectorSystem.setControllers(mainController, auxController);
		climberSystem.setControllers(mainController, auxController);
	}

	// ****************************************************************************
	// **********************          AUTONOMOUS            **********************
	// ****************************************************************************
	
	@Override
	public void autonomousInit() {
		// Stop any commands that might be left running from another mode
		Scheduler.getInstance().removeAll();
		
		Command autoCommand = autonomousConfiguration.getAutonomousCommand();
		
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
		climberSystem.teleopPeriodic();
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

	public static VisionSystem getVisionSystem() {
		return visionSystem;
	}

	public static DriveSystem getDriveSystem() {
		return driveSystem;
	}

	public static CollectorSystem getCollectorSystem() {
		return collectorSystem;
	}
	
	public static ClimberSystem getClimberSystem() {
		return climberSystem;
	}
}


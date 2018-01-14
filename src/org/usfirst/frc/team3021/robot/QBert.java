package org.usfirst.frc.team3021.robot;

import org.usfirst.frc.team3021.robot.controller.Controller;
import org.usfirst.frc.team3021.robot.subsystem.Drive;
import org.usfirst.frc.team3021.robot.subsystem.Vision;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class QBert extends IterativeRobot {
	
	// Member Attributes
	public static Configuration configuration;
	
	public static Drive robotDrive;
	
	public static Vision vision;
	
	public static Controller mainController;
	public static Controller auxController;

	public QBert() {
		super();
		
		configuration = new Configuration();
		
		vision = new Vision();
		
		robotDrive = new Drive();
		
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

		robotDrive.setControllers(mainController, auxController);
		vision.setControllers(mainController, auxController);
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
		
		robotDrive.teleopPeriodic();
		vision.teleopPeriodic();
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

	public static Vision getVisionSubSystem() {
		return vision;
	}

	public static Drive getDriveSubSystem() {
		return robotDrive;
	}
}


package org.usfirst.frc.team3021.robot.commands.system;

import org.usfirst.frc.team3021.robot.controller.station.AttackThreeController;
import org.usfirst.frc.team3021.robot.controller.station.AuxController;
import org.usfirst.frc.team3021.robot.controller.station.Xbox360Controller;

public class PrintPreferencesCommand extends SystemCommand {
	
	public PrintPreferencesCommand() {
		super();
	}
	
	protected void execute() {
		new AttackThreeController().printButtonActions("Attack Three");
		new Xbox360Controller().printButtonActions("Xbox360");
		new AuxController().printButtonActions("Aux Panel");
	}
	
	protected boolean isFinished() {
		return true;
	}
}

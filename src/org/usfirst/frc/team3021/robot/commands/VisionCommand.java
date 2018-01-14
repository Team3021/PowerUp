package org.usfirst.frc.team3021.robot.commands;

import org.usfirst.frc.team3021.robot.QBert;

public class VisionCommand extends Command {
	
	public VisionCommand() {
		super();
		
		requires(QBert.getVisionSubSystem());
	}
}
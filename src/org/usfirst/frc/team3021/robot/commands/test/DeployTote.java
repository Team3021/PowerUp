package org.usfirst.frc.team3021.robot.commands.test;

import org.usfirst.frc.team3021.robot.commands.CollectorCommand;

public class DeployTote extends CollectorCommand {
		public DeployTote() {
			super();
		}
		
		@Override
		protected void execute() {
			collectorSystem.deploy();
		}

		@Override
		protected void end() {
			System.out.println("Done deploying.");
		}

		@Override
		protected boolean isFinished() {
			return (true);
		}

	}
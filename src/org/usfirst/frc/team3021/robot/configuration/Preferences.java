package org.usfirst.frc.team3021.robot.configuration;

import java.util.Vector;

import org.usfirst.frc.team3021.robot.controller.station.AttackThreeController;
import org.usfirst.frc.team3021.robot.controller.station.AuxController;
import org.usfirst.frc.team3021.robot.controller.station.Xbox360Controller;

public class Preferences {
	
	private static Preferences preferences;
	
	private Preferences() {
		
	}

	public static Preferences getInstance() {
		if (preferences == null) {
			preferences = new Preferences();
		}
		
		return preferences;
	}
	
	public static void printButtonActions() {
		new AttackThreeController().printButtonActions("Attack Three");
		new Xbox360Controller().printButtonActions("Xbox360");
		new AuxController().printButtonActions("Aux Panel");
	}

	public void printPreferences() {
		edu.wpi.first.wpilibj.Preferences prefs = edu.wpi.first.wpilibj.Preferences.getInstance();
		
		@SuppressWarnings("rawtypes")
		Vector keys = prefs.getKeys();
		
		System.out.println("******************* Prefernces *******************");
		
		for (Object obj : keys) {
			String key = null;
			
			// cast from object to String
			if (obj instanceof String) {
				key = (String) obj;
			}
			
			if (key != null) {
				String value = prefs.getString(key, "");
			
				System.out.println(key + " : " + value);
			}
		}
	}
}

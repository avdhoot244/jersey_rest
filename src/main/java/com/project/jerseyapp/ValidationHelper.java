package com.project.jerseyapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public final class ValidationHelper {
	final static Logger logger = Logger.getLogger(ValidationHelper.class);

	public static boolean validateFirstName(String firstName) {
		logger.info("Validating firstName : " + firstName);
		if ("".equals(firstName.trim())) {
			logger.error("Error in Validating firstName : " + firstName);
			return true;
		}
		return false;

	}

	public static boolean validateLastName(String lastName) {
		logger.info("Validating lastName : " + lastName);
		if ("".equals(lastName.trim())) {
			logger.error("Error in Validating lastName : " + lastName);
			return true;
		}
		return false;
	}

	public static boolean validateDate(String date) {
		logger.info("Validating dob : " + date);
		String[] patterns = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd" };
		for (String pattern : patterns) {
			try {
				Date dob = new SimpleDateFormat(pattern).parse(date.trim());
				Date today = new Date();
				if (dob.after(today)) {
					logger.error("dob should not be greater than today's date");
					return true;
				}
				return false;
			} catch (ParseException e) {
				logger.info(date + " is not in " + pattern + " format");
			}
		}
		logger.error("Error in Validating dob : " + date);
		return true;
	}
}

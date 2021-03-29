package com.project.jerseyapp;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

@Path("/humans")
public class HumanResource {
	final static Logger logger = Logger.getLogger(HumanResource.class);

	HumanRepository repo = new HumanRepository();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHuman() {
		logger.info("Request is made to get all Humans");
		return repo.getHumans();

	}

	@Path("createHuman")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createHuman(JsonObject job) {
		if (job.containsKey("string")) {
			Human human = new Human();
			String requestString = job.getString("string");
			logger.info("Requested string is : " + requestString);
			Map<String, String> errorMap = new HashMap<String, String>();
			if (requestString.matches(".*\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2}.*")) {
				Pattern pattern = Pattern.compile("(.*)(\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}:\\d{2})(.*)");
				Matcher matcher = pattern.matcher(requestString);
				if (matcher.matches()) {
					requestString = requestString.replaceAll(matcher.group(2), "");
					String[] stringParts = requestString.split(":");
					if (stringParts.length == 5) {
						if (ValidationHelper.validateFirstName(stringParts[0].trim())) {
							errorMap.put("message", "Error in Validating firstName : " + stringParts[0].trim());
							return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
						}
						human.setFirstName(stringParts[0].trim());
						if (ValidationHelper.validateLastName(stringParts[1].trim())) {
							errorMap.put("message", "Error in Validating lastName : " + stringParts[1].trim());
							return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
						}
						human.setLastName(stringParts[1].trim());
						if (ValidationHelper.validateDate(matcher.group(2).trim())) {
							errorMap.put("message", "Error in Validating date : " + matcher.group(2).trim());
							return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
						}
						human.setDob(matcher.group(2).trim());
						human.setCity(stringParts[3].trim());
						human.setState(stringParts[4].trim());
						logger.info("Parsed Human Object is : " + human);
						return repo.createHuman(human);
					} else {
						logger.error("cannot able to parse request String into Human Object");
						errorMap.put("message",
								"requestString should be in Format : 'firstName:lastName:dob:city:state'");
						return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
					}

				}
			} else if (requestString.matches(".*\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2}.*")) {
				Pattern pattern = Pattern.compile("(.*)(\\d{4}-\\d{2}-\\d{2}\\s*\\d{2}:\\d{2})(.*)");
				Matcher matcher = pattern.matcher(requestString);
				if (matcher.matches()) {
					requestString = requestString.replaceAll(matcher.group(2), "");
					String[] stringParts = requestString.split(":");
					if (stringParts.length == 5) {
						if (ValidationHelper.validateFirstName(stringParts[0].trim())) {
							errorMap.put("message", "Error in Validating firstName : " + stringParts[0].trim());
							return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
						}
						human.setFirstName(stringParts[0].trim());
						if (ValidationHelper.validateLastName(stringParts[1].trim())) {
							errorMap.put("message", "Error in Validating lastName : " + stringParts[1].trim());
							return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
						}
						human.setLastName(stringParts[1].trim());
						if (ValidationHelper.validateDate(matcher.group(2).trim())) {
							errorMap.put("message", "Error in Validating date : " + matcher.group(2).trim());
							return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
						}
						human.setDob(matcher.group(2).trim());
						human.setCity(stringParts[3].trim());
						human.setState(stringParts[4].trim());
						logger.info("Parsed Human Object is : " + human);
						return repo.createHuman(human);
					} else {
						logger.error("cannot able to parse request String into Human Object");
						errorMap.put("message",
								"requestString should be in Format : 'firstName:lastName:dob:city:state'");
						return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();

					}
				}
			} else {
				String[] stringParts = requestString.split(":");
				if (stringParts.length == 5) {
					if (ValidationHelper.validateFirstName(stringParts[0].trim())) {
						errorMap.put("message", "Error in Validating firstName : " + stringParts[0].trim());
						return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
					}
					human.setFirstName(stringParts[0].trim());
					if (ValidationHelper.validateLastName(stringParts[1].trim())) {
						errorMap.put("message", "Error in Validating lastName : " + stringParts[1].trim());
						return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
					}
					human.setLastName(stringParts[1].trim());
					if (ValidationHelper.validateDate(stringParts[2].trim())) {
						errorMap.put("message", "Error in Validating date : " + stringParts[2].trim());
						return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
					}
					human.setDob(stringParts[2].trim());
					human.setCity(stringParts[3].trim());
					human.setState(stringParts[4].trim());
					logger.info("Parsed Human Object is : " + human);
					return repo.createHuman(human);
				} else {
					logger.error("cannot able to parse request String into Human Object");
					errorMap.put("message", "requestString should be in Format : 'firstName:lastName:dob:city:state'");
					return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();

				}
			}
		} else {
			logger.error("cannot able to find requset param 'string'");
			Map<String, String> errorMap = new HashMap<String, String>();
			errorMap.put("message", "cannot able to find request param 'string' in request params : " + job.toString());
			return Response.status(Response.Status.BAD_REQUEST).entity(errorMap).build();
		}
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("message", "Internal Server Error");
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMap).build();
	}

}

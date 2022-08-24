package tests;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import utils.ConfigReader;

public class StravaActivity{
	
	ConfigReader properties = new ConfigReader();
	StravaLogin sl = new StravaLogin();
	Response response;
	RequestSpecification httpRequest;
	ResponseBody body;
	String authorizationCode;
	String accessToken;

	
	@Test
	public void authorize() throws Throwable {
		
		// Hitting the base url
		RestAssured.baseURI = properties.getProperty("baseUrl");

		// Instance of StravaLogin
		authorizationCode = sl.makeUserAuthorize();

		// Request specifications
		httpRequest = RestAssured.given();
		httpRequest.header("ContentType","application/json");

		// String to be hit for the authorization
		String payload = "https://www.strava.com/oauth/token?client_id=" + properties.getProperty("client_id")
		+ "&client_secret=" + properties.getProperty("client_secret") + "&code=" + authorizationCode
		+ "&grant_type=authorization_code";

		// post request
		response = httpRequest.post(payload);

		// Response Body of Authorization
		body = response.getBody();
		System.out.println("Response Body Of Authorized :- "+ body.asPrettyString());
		accessToken = body.jsonPath().getString("access_token");
		System.out.println("Access Token = "+ accessToken);

		// get request for user's activity
		response = httpRequest.queryParam("access_token", accessToken).get(properties.getProperty("getUrl"));
		String activity = response.getBody().asPrettyString();
		System.out.println("Activity:- "+ activity);
		Assert.assertEquals(response.statusCode(), 200);
	}
}

package testcases.apiTest.bookStore.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.framework.api.ApiRequestHandler;
import com.framework.api.ApiVariableManager;
import com.framework.api.SessionManager;
import com.framework.base.BaseTest;
import com.framework.base.DataProvider;
import com.framework.utils.api.ApiUtils;
import com.framework.utils.validation.AssertionUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTest extends BaseTest {

    @Test(description = "Test to create a new user")
    public void createUserTest() {
        ApiRequestHandler api = new ApiRequestHandler();

        // Set the header values for the API call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        // Get data from the JSON file.
        List<Map<String, String>> testData = DataProvider.getData("src/test/resources/testdata/api/json/user_data.json", "JSON");
        for (Map<String, String> data : testData) {
            String userName = data.get("userName"); // Get the user name
            String password = data.get("password");  // Get the user password.

            // Create the request body.
            String requestBody = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, password);

            // Make the API call
            Response response = api.post("/Account/v1/User", requestBody, "application/json", headers);

            // Validate the response
            AssertionUtils.assertEquals(ApiUtils.getStatusCode(response), 201, "Validates the status code"); // validates status code
            String responseBody = ApiUtils.getResponseBody(response); // Capture the response body.
            ApiUtils.validateJsonResponseContains(responseBody, "userID", "Validate if the response contains userID");  // validates the key

            JsonNode responseNode = ApiUtils.getJsonNodeFromString(responseBody); //Get response as JsonNode
            ApiVariableManager.setVariable("id", responseNode.get("userID").asText()); // set the user id as a variable.

            SessionManager.setSessionValue("userName", userName); // set the user name as session variable.
            SessionManager.setSessionValue("password", password); // set the password as session variable.

            ApiVariableManager.setVariable("USER", requestBody); // set the request body in the variable.
        }
    }

    @Test(description = "Test user login", dependsOnMethods = "createUserTest")
    public void userLoginTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        String requestBody = ApiVariableManager.getVariable("USER"); // Get the user object.

        // set the header values for the api call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        // make the api call
        Response response = api.post("/Account/v1/GenerateToken", requestBody, "application/json", headers); // make the post request.
        String responseBody = ApiUtils.getResponseBody(response); // capture the response body.

        ApiUtils.validateJsonResponseContains(responseBody, "token", "Validate if the response contains token");  // validates if response contains the token.

        JsonNode responseNode = ApiUtils.getJsonNodeFromString(responseBody);
        ApiVariableManager.setVariable("token",responseNode.get("token").asText()); // set the token as a variable.

        SessionManager.setSessionValue("token", responseNode.get("token").asText());
        SessionManager.setSessionValue("Genratedtoken",  responseBody);
    }

    @Test(description = "Test user authorization", dependsOnMethods = "userLoginTest")
    public void userAuthorizationTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        String requestBody = ApiVariableManager.getVariable("USER"); // Get the user object.

        // set the header values for the api call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        // make the api call
        Response response =  api.post("/Account/v1/Authorized", requestBody, "application/json", headers); // make the post request.

        AssertionUtils.assertEquals(ApiUtils.getStatusCode(response),200, "Validates the status code");
    }
}
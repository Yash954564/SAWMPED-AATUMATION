package testcases.apiTest.bookStore.user;

import com.framework.api.ApiRequestHandler;
import com.framework.api.ApiVariableManager;
import com.framework.api.SessionManager;
import com.framework.base.BaseTest;
import com.framework.base.DataProvider;
import com.framework.utils.api.ApiUtils;
import com.framework.utils.validation.AssertionUtils;
import okhttp3.Response;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.qameta.allure.Description;

public class UserTest extends BaseTest {

    @Test(description = "Test to create a new user")
    @Description("Test to create a new user")
    public void createUserTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        // set the header values for the api call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");
        // get data from json file
        List<Map<String, String>> testData = DataProvider.getData("src/test/resources/testdata/api/json/user_data.json", "JSON");
        for (Map<String, String> data : testData) {
            String userName = data.get("userName"); // Get the user name
            String password = data.get("password");  // Get the user password.

            // create the request body.
            String requestBody = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}",userName,password);
            ApiVariableManager.setVariable("USER", requestBody); // set the request body in the variable.

            // make the api call
            Response response = api.post("/Account/v1/User", requestBody, "JSON", headers); // make the post request.
            String responseBody = ApiUtils.getResponseBody(response); // Capture the response body.


            // validate the response
            ApiUtils.validateJsonResponseContains(responseBody, "userID", "Validate if the response contains userID");  // validates the key
            AssertionUtils.assertEquals(ApiUtils.getStatusCode(response), 201, "Validates the status code"); // validates status code
            ApiVariableManager.setVariable("id", ApiUtils.getJsonNodeFromString(responseBody).get("userID").asText()); // set the user id as a variable.
            SessionManager.setSessionValue("userName", userName); // set the user name as session variable.
            SessionManager.setSessionValue("password", password); // set the password as session variable.
        }
    }

    @Test(description = "Test user login", dependsOnMethods = "createUserTest")
    @Description("Test user login")
    public void userLoginTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        String requestBody = ApiVariableManager.getVariable("USER"); // Get the user object.

        // set the header values for the api call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");


        // make the api call
        Response response = api.post("/Account/v1/GenerateToken", requestBody, "JSON", headers); // make the post request.
        String responseBody = ApiUtils.getResponseBody(response); // capture the response body.


        ApiUtils.validateJsonResponseContains(responseBody, "token", "Validate if the response contains token");  // validates if response contains the token.
        ApiVariableManager.setVariable("token",ApiUtils.getJsonNodeFromString(responseBody).get("token").asText()); // set the token as a variable.
        SessionManager.setSessionValue("token", responseBody);
        SessionManager.setSessionValue("Genratedtoken",  responseBody);
    }

    @Test(description = "Test user authorization", dependsOnMethods = "userLoginTest")
    @Description("Test user authorization")
    public void userAuthorizationTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        String requestBody = ApiVariableManager.getVariable("USER"); // Get the user object.

        // set the header values for the api call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");


        // make the api call
        Response response =  api.post("/Account/v1/Authorized", requestBody, "JSON", headers); // make the post request.

        AssertionUtils.assertEquals(ApiUtils.getStatusCode(response),200, "Validates the status code");
    }



}

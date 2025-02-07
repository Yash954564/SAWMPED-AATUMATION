package testcases.apiTest.bookStore.delete;

import com.framework.api.ApiRequestHandler;
import com.framework.api.ApiVariableManager;
import com.framework.api.SessionManager;
import com.framework.base.BaseTest;
import com.framework.utils.api.ApiUtils;
import com.framework.utils.validation.AssertionUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class DeleteTest extends BaseTest {
    @Test(description = "Test to delete all books for a user", enabled = true)
    public void deleteAllBooksTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        String userId = ApiVariableManager.getVariable("id"); // get the user id from the session.
        // set the header values for the api call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Authorization", "Bearer " + ApiVariableManager.getVariable("token"));// Add Authorization header.

        // make the api call
        Response response =  api.delete(String.format("/BookStore/v1/Books?UserId=%s", userId), headers); // make the delete request.

        // validate the response
        AssertionUtils.assertEquals(ApiUtils.getStatusCode(response),204, "Validate the status code for delete all books."); // validate the status code.
    }

    @Test(description = "Test to delete a user", dependsOnMethods = {"deleteAllBooksTest"}, enabled = true)
    public void deleteUserTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        String userId = ApiVariableManager.getVariable("id");
        // set the header values for the api call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Authorization", "Bearer " + ApiVariableManager.getVariable("token"));// Add Authorization header.

        // make the api call
        Response response = api.delete(String.format("/Account/v1/User/%s", userId), headers); // make the delete request.

        // validate the response
        AssertionUtils.assertEquals(ApiUtils.getStatusCode(response), 204, "Validate the status code for delete user."); // validate the status code.
    }
}
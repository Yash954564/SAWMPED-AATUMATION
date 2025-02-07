package testcases.apiTest.bookStore.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.framework.api.ApiRequestHandler;
import com.framework.api.ApiVariableManager;
import com.framework.api.SessionManager;
import com.framework.base.BaseTest;
import com.framework.utils.api.ApiUtils;
import com.framework.utils.validation.AssertionUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookTest extends BaseTest {

    @Test(description = "Test to get all books")
    public void getAllBooksTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");

        Response response = api.get("/BookStore/v1/Books", headers);
        String responseBody = ApiUtils.getResponseBody(response);

        AssertionUtils.assertEquals(ApiUtils.getStatusCode(response), 200, "Validates the status code");
        ApiUtils.validateJsonResponseContains(responseBody, "books", "Validates if the response contains books array.");
        ApiUtils.validateJsonResponseValue(responseBody, "books[0].title", "Git Pocket Guide");

        JsonPath jsonPath = response.jsonPath();  //Use JsonPath for easier data extraction
        List<String> isbns = jsonPath.getList("books.isbn");  //Get list of all ISBNs

        //Check if there are enough ISBNs to extract and assign
        if (isbns.size() > 5) {
            SessionManager.setSessionValue("isbn", isbns.get(5));
        }
        if (isbns.size() > 1) {
            SessionManager.setSessionValue("isbn1", isbns.get(1));
        }
        if (isbns.size() > 2) {
            SessionManager.setSessionValue("isbn2", isbns.get(2));
        }
        if (isbns.size() > 3) {
            SessionManager.setSessionValue("isbn3", isbns.get(3));
        }
        if (isbns.size() > 4) {
            SessionManager.setSessionValue("isbn4", isbns.get(4));
        }

    }

    @Test(description = "Test to get a book by ISBN", dependsOnMethods = "getAllBooksTest")
    public void getBookByIsbnTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        String isbn = SessionManager.getSessionValue("isbn");
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");

        Response response = api.get(String.format("/BookStore/v1/Book?ISBN=%s", isbn), headers);
        String responseBody = ApiUtils.getResponseBody(response);

        AssertionUtils.assertEquals(ApiUtils.getStatusCode(response), 200, "Validates the status code");
        ApiUtils.validateJsonResponseContains(responseBody, "isbn", "Validates if the response contains isbn key");
    }

    @Test(description = "Test add books to user", dependsOnMethods = {"getBookByIsbnTest"}, enabled = true)
    public void addBooksToUserTest(){
        ApiRequestHandler api = new ApiRequestHandler();
        String userId = ApiVariableManager.getVariable("id"); // get id from variable manager.
        String isbn = SessionManager.getSessionValue("isbn");
        String isbn1 = SessionManager.getSessionValue("isbn1");
        String isbn2 = SessionManager.getSessionValue("isbn2");
        String isbn3 = SessionManager.getSessionValue("isbn3");
        String isbn4 = SessionManager.getSessionValue("isbn4");

        String requestBody = String.format("{\n  \"userId\": \"%s\",\n  \"collectionOfIsbns\": [\n    {\n      \"isbn\": \"%s\"\n    },\n    {\n      \"isbn\": \"%s\"\n    },\n    {\n      \"isbn\": \"%s\"\n    },\n    {\n      \"isbn\": \"%s\"\n    },\n    {\n      \"isbn\": \"%s\"\n    }\n  ]\n}",userId,isbn,isbn1,isbn2,isbn3,isbn4);
        // set the header values for the api call.
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + ApiVariableManager.getVariable("token"));// Add Authorization header.

        Response response = api.post("/BookStore/v1/Books", requestBody, "application/json", headers);
        AssertionUtils.assertEquals(ApiUtils.getStatusCode(response),201, "Validate the status code for add books.");
    }

    @Test(description = "Test get user details", dependsOnMethods = {"addBooksToUserTest"}, enabled = true)
    public void getUserDetailsTest() {
        ApiRequestHandler api = new ApiRequestHandler();
        String userId = ApiVariableManager.getVariable("id");  // get id from ApiVariableManager

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Authorization", "Bearer " + ApiVariableManager.getVariable("token"));// Add Authorization header.

        Response response = api.get(String.format("/Account/v1/User/%s", userId), headers);
        AssertionUtils.assertEquals(ApiUtils.getStatusCode(response), 200, "Validate the status code for get user details.");
    }
}
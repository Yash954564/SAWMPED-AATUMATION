package stepDefinitions.apiSteps.bookStore.book;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.api.*;
import com.framework.base.LogManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Response;
import org.testng.Assert;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BookSteps {
    private ApiRequestHandler apiRequestHandler = new ApiRequestHandler();
    private Response response;

    @When("the get all books api is called")
    public void theGetAllBooksApiIsCalled() {
        LogManager.info("Calling get all books api");
        GetRequest getRequest = new GetRequest("/BookStore/v1/Books");
        response = getRequest.send();
        try {
            new PostRequestScript(){
                @Override
                public void execute(Response response) {
                    setVariables(response);
                }
            }.execute(response);
        }catch (Exception e){
            LogManager.error("Error while setting the variables "+e.getMessage());
        }
    }

    @And("the response should contain list of books")
    public void theResponseShouldContainListOfBooks() {
        LogManager.info("Verifying the response contains the list of books");
        String responseBody = null;
        try {
            responseBody = Objects.requireNonNull(response.body()).string();
        }catch (Exception e){
            LogManager.error("Error while reading response body "+e.getMessage());
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            Assert.assertTrue(rootNode.has("books"),"Response does not contains books key");
            Assert.assertTrue(rootNode.get("books").isArray(), "Books is not an array");

            if (rootNode.get("books").isArray()){
                List<JsonNode> bookList = rootNode.findValues("isbn");
                if (bookList.size()>0){
                    ApiVariableManager.setVariable("isbn",bookList.get(5).asText());
                }
            }
        }catch (Exception e){
            LogManager.error("Error while reading the book from the response "+e.getMessage());
        }
    }

    @Given("a book isbn is fetched")
    public void aBookIsbnIsFetched() {
        LogManager.info("Fetching book isbn");
        if (ApiVariableManager.getVariable("isbn")==null){
            theGetAllBooksApiIsCalled();
            theResponseShouldContainListOfBooks();
        }
        LogManager.info("Fetched book isbn is "+ApiVariableManager.getVariable("isbn"));
    }

    @When("the get book by isbn api is called")
    public void theGetBookByIsbnApiIsCalled() {
        LogManager.info("Calling get book by isbn api");
        GetRequest getRequest = new GetRequest("/BookStore/v1/Book?ISBN="+ApiVariableManager.getVariable("isbn"));
        response = getRequest.send();
        try {
            new PostRequestScript(){
                @Override
                public void execute(Response response) {
                    setVariables(response);
                }
            }.execute(response);
        }catch (Exception e){
            LogManager.error("Error while setting the variables "+e.getMessage());
        }
    }

    @And("the response should contain the book with isbn")
    public void theResponseShouldContainTheBookWithIsbn() {
        LogManager.info("Verifying the response contains the book with isbn");
        String responseBody = null;
        try {
            responseBody = Objects.requireNonNull(response.body()).string();
            Assert.assertTrue(responseBody.contains(ApiVariableManager.getVariable("isbn")),
                    "Response body does not contains isbn "+ApiVariableManager.getVariable("isbn"));
        }catch (Exception e){
            LogManager.error("Error while reading response body "+e.getMessage());
        }
    }

    @When("the add books to user api is called")
    public void theAddBooksToUserApiIsCalled() {
        LogManager.info("Calling add books to user api");
        String requestBody = "{\n" +
                "  \"userId\": \""+ApiVariableManager.getVariable("id")+"\",\n" +
                "  \"collectionOfIsbns\": [\n" +
                "    {\n" +
                "      \"isbn\": \""+ApiVariableManager.getVariable("isbn")+"\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        PostRequest postRequest = new PostRequest("/BookStore/v1/Books", requestBody, "application/json",
                Map.of("Authorization","Bearer "+ ApiVariableManager.getVariable("token")));
        response = postRequest.send();
    }

    @When("the get user api is called")
    public void theGetUserApiIsCalled() {
        LogManager.info("Calling get user api");
        GetRequest getRequest = new GetRequest("/Account/v1/User/"+ApiVariableManager.getVariable("id"),
                Map.of("Authorization","Bearer "+ ApiVariableManager.getVariable("token")));
        response = getRequest.send();
        try {
            new PostRequestScript(){
                @Override
                public void execute(Response response) {
                    setVariables(response);
                }
            }.execute(response);
        }catch (Exception e){
            LogManager.error("Error while setting the variables "+e.getMessage());
        }

    }

    @And("the response should contain user details")
    public void theResponseShouldContainUserDetails() {
        LogManager.info("Verifying the response contains user details");
        String responseBody = null;
        try {
            responseBody = Objects.requireNonNull(response.body()).string();
            Assert.assertTrue(responseBody.contains(ApiVariableManager.getVariable("id")),
                    "Response body does not contains id "+ApiVariableManager.getVariable("id"));
        }catch (Exception e){
            LogManager.error("Error while reading response body "+e.getMessage());
        }
    }

    @When("the delete all books for a user api is called")
    public void theDeleteAllBooksForAUserApiIsCalled() {
        LogManager.info("Calling delete all books api");
        DeleteRequest deleteRequest = new DeleteRequest("/BookStore/v1/Books?UserId="+ApiVariableManager.getVariable("id"),
                Map.of("Authorization","Bearer "+ ApiVariableManager.getVariable("token")));
        response = deleteRequest.send();
    }
    @Then("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        LogManager.info("Verifying response status code is "+statusCode);
        Assert.assertEquals(response.code(), Integer.parseInt(statusCode));
    }
}
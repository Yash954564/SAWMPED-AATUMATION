package stepDefinitions.apiSteps.bookStore.user;

import com.framework.api.ApiRequestHandler;
import com.framework.api.ApiVariableManager;
import com.framework.base.LogManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Response;
import org.testng.Assert;

import java.util.Map;
import java.util.Objects;
import com.framework.api.PostRequestScript;
import com.framework.api.PostRequest;
import com.framework.api.DeleteRequest;

public class UserSteps {
    private ApiRequestHandler apiRequestHandler = new ApiRequestHandler();
    private Response response;

    @Given("a user payload is created with {string} and {string}")
    public void aUserPayloadIsCreatedWithAnd(String username, String password) {
        LogManager.info("Creating user payload");
        String userPayload = "{\"userName\":\"" + username + "\", \"password\":\"" + password + "\"}";
        ApiVariableManager.setVariable("userPayload", userPayload);
        LogManager.info("User payload is " + userPayload);
    }

    @When("the user create account api is called")
    public void theUserCreateAccountApiIsCalled() {
        LogManager.info("calling user create account api");
        PostRequest postRequest = new PostRequest("/Account/v1/User", ApiVariableManager.getVariable("userPayload"));
        response= postRequest.send();
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

    @Then("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        LogManager.info("Verifying response status code is "+statusCode);
        Assert.assertEquals(response.code(),Integer.parseInt(statusCode));
    }

    @And("the response should contain the key {string}")
    public void theResponseShouldContainTheKey(String key) {
        LogManager.info("Verifying response contains key " + key);
        String responseBody = null;
        try {
            responseBody = Objects.requireNonNull(response.body()).string();
            LogManager.info("response body is " + responseBody);
        } catch (Exception e) {
            LogManager.error("Error while reading response body " + e.getMessage());
        }
        Assert.assertTrue(Objects.requireNonNull(responseBody).contains(key));

        if (key.equalsIgnoreCase("userID")){
            try {
                String userId = new PostRequestScript(){
                    @Override
                    public void execute(Response response) {
                        setVariables(response);
                    }
                }.getResponse(response);
                String extractedUserId = userId.substring(userId.indexOf(":") + 2, userId.indexOf(",")-1);
                ApiVariableManager.setVariable("id",extractedUserId);
            }catch (Exception e){
                LogManager.error("Error while extracting userId from the response "+e.getMessage());
            }
        }else if (key.equalsIgnoreCase("token")){
            try {
                String tokenValue =  new PostRequestScript(){
                    @Override
                    public void execute(Response response) {
                        setVariables(response);
                    }
                }.getResponse(response);
                String extractedTokenValue = tokenValue.substring(tokenValue.indexOf(":")+2, tokenValue.length()-2);
                ApiVariableManager.setVariable("token",extractedTokenValue);
            }catch (Exception e){
                LogManager.error("Error while extracting token from the response "+e.getMessage());
            }
        }
    }

    @When("the user login api is called")
    public void theUserLoginApiIsCalled() {
        LogManager.info("Calling user login api");
        PostRequest postRequest = new PostRequest("/Account/v1/GenerateToken", ApiVariableManager.getVariable("userPayload"));
        response= postRequest.send();
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

    @When("the user auth api is called")
    public void theUserAuthApiIsCalled() {
        LogManager.info("calling user auth api");
        PostRequest postRequest = new PostRequest("/Account/v1/Authorized", ApiVariableManager.getVariable("userPayload"));
        response= postRequest.send();
    }

    @When("the user delete account api is called")
    public void theUserDeleteAccountApiIsCalled() {
        LogManager.info("Calling user delete account api");
        DeleteRequest deleteRequest = new DeleteRequest("/Account/v1/User/" + ApiVariableManager.getVariable("id"),
                Map.of("Authorization", "Bearer " + ApiVariableManager.getVariable("token")));
        response = deleteRequest.send();

    }
}
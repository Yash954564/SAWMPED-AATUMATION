package testcases.apiTest.bookStore.user;

import com.framework.api.ApiVariableManager;
import com.framework.base.BaseTest;
import com.framework.base.DataProvider;
import com.framework.base.LogManager;
import stepDefinitions.apiSteps.bookStore.user.UserSteps;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class UserTest extends BaseTest {
    UserSteps userSteps = new UserSteps();
    String testDataFile = "src/main/resources/testdata/api/json/user_data.json";
    String fileType = "JSON";
    List<Map<String, String>> testData;


    @BeforeMethod
    public void setup() {
        try {
            testData = DataProvider.getData(testDataFile, fileType);
            LogManager.info("Test data is " + testData);
        }catch (Exception e){
            LogManager.error("Error while reading data from file "+testDataFile+e.getMessage());
            throw new RuntimeException("Error while reading the data "+testDataFile+e.getMessage());
        }

    }

    @Test(dataProvider = "testData")
    public void createUserTest(Map<String, String> data) {
        userSteps.aUserPayloadIsCreatedWithAnd(data.get("userName"), data.get("password"));
        userSteps.theUserCreateAccountApiIsCalled();
        userSteps.theResponseStatusCodeShouldBe(data.get("create_status_code"));
        userSteps.theResponseShouldContainTheKey(data.get("create_key"));
    }

    @Test(dataProvider = "testData")
    public void loginUserTest(Map<String, String> data) {
        userSteps.aUserPayloadIsCreatedWithAnd(data.get("userName"), data.get("password"));
        userSteps.theUserLoginApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("login_key"));
    }

    @Test(dataProvider = "testData")
    public void authUserTest(Map<String, String> data) {
        userSteps.aUserPayloadIsCreatedWithAnd(data.get("userName"), data.get("password"));
        userSteps.theUserAuthApiIsCalled();
        userSteps.theResponseStatusCodeShouldBe(data.get("auth_status_code"));
    }

    @Test(dataProvider = "testData")
    public void deleteUserTest(Map<String, String> data) {
        userSteps.aUserPayloadIsCreatedWithAnd(data.get("userName"), data.get("password"));
        userSteps.theUserCreateAccountApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("create_key"));
        userSteps.theUserLoginApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("login_key"));
        userSteps.theUserDeleteAccountApiIsCalled();
        userSteps.theResponseStatusCodeShouldBe(data.get("delete_status_code"));
    }

    @org.testng.annotations.DataProvider(name = "testData")
    public Object[][] testData() {
        if (testData == null) {
            LogManager.error("Test data is null, cannot execute tests");
            throw new RuntimeException("Test data is null, cannot execute tests");

        }
        Object[][] array = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            array[i][0] = testData.get(i);
        }
        return array;
    }

    @AfterMethod
    public void tearDown() {
        ApiVariableManager.clearVariables();
    }
}
package testcases.apiTest.bookStore.book;

import com.framework.api.ApiVariableManager;
import com.framework.base.BaseTest;
import com.framework.base.DataProvider;
import com.framework.base.LogManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import stepDefinitions.apiSteps.bookStore.book.BookSteps;
import stepDefinitions.apiSteps.bookStore.user.UserSteps;

import java.util.List;
import java.util.Map;

public class BookTest extends BaseTest {
    BookSteps bookSteps = new BookSteps();
    UserSteps userSteps = new UserSteps();
    String testDataFile = "src/main/resources/testdata/api/json/book_data.json";
    String fileType = "JSON";
    private static List<Map<String, String>> testData;

    @BeforeSuite(alwaysRun = true)
    public void loadTestData(){
        try {
            if (testData==null){
                testData = DataProvider.getData(testDataFile, fileType);
                LogManager.info("Test data is " + testData);
            }

        }catch (Exception e){
            LogManager.error("Error while reading data from file "+testDataFile+e.getMessage());
            throw new RuntimeException("Error while reading the data "+testDataFile+e.getMessage());
        }
    }
    @Test(dataProvider = "testData")
    public void getAllBooksTest(Map<String, String> data) {
        bookSteps.theGetAllBooksApiIsCalled();
        bookSteps.theResponseStatusCodeShouldBe(data.get("getallbooks_status_code"));
        bookSteps.theResponseShouldContainListOfBooks();
    }

    @Test(dataProvider = "testData")
    public void getBookByIsbnTest(Map<String, String> data) {
        bookSteps.aBookIsbnIsFetched();
        bookSteps.theGetBookByIsbnApiIsCalled();
        bookSteps.theResponseStatusCodeShouldBe(data.get("getbookbyisbn_status_code"));
        bookSteps.theResponseShouldContainTheBookWithIsbn();
    }

    @Test(dataProvider = "testData")
    public void addBookToUserTest(Map<String, String> data) {
        userSteps.aUserPayloadIsCreatedWithAnd(data.get("userName"), data.get("password"));
        userSteps.theUserCreateAccountApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("create_key"));
        userSteps.theUserLoginApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("login_key"));
        bookSteps.aBookIsbnIsFetched();
        bookSteps.theAddBooksToUserApiIsCalled();
        bookSteps.theResponseStatusCodeShouldBe(data.get("addbook_status_code"));
    }

    @Test(dataProvider = "testData")
    public void getUserTest(Map<String,String> data){
        userSteps.aUserPayloadIsCreatedWithAnd(data.get("userName"),data.get("password"));
        userSteps.theUserCreateAccountApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("create_key"));
        userSteps.theUserLoginApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("login_key"));
        bookSteps.theGetUserApiIsCalled();
        bookSteps.theResponseStatusCodeShouldBe(data.get("getuser_status_code"));
        bookSteps.theResponseShouldContainUserDetails();
    }

    @Test(dataProvider = "testData")
    public void deleteBooksForUserTest(Map<String, String> data){
        userSteps.aUserPayloadIsCreatedWithAnd(data.get("userName"),data.get("password"));
        userSteps.theUserCreateAccountApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("create_key"));
        userSteps.theUserLoginApiIsCalled();
        userSteps.theResponseShouldContainTheKey(data.get("login_key"));
        bookSteps.aBookIsbnIsFetched();
        bookSteps.theAddBooksToUserApiIsCalled();
        bookSteps.theDeleteAllBooksForAUserApiIsCalled();
        bookSteps.theResponseStatusCodeShouldBe(data.get("deletebook_status_code"));
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
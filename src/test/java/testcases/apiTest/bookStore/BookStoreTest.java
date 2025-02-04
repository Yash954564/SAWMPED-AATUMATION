//package testcases.apiTest.bookStore;
//
//import com.framework.api.ApiVariableManager;
//import org.testng.annotations.Test;
//import testcases.apiTest.bookStore.f2_book.BookTest;
//import testcases.apiTest.bookStore.f3_delete.DeleteTest;
//import testcases.apiTest.bookStore.f1_user.UserTest;
//
//import java.util.Map;
//
//public class BookStoreTest {
//    String userId;
//    String generatedToken;
//    String token;
//    Map<String, String> testData;
//
//
//    public BookStoreTest(Map<String, String> data) {
//        this.testData = data;
//    }
//    @Test(description = "Test to create a new f1_user")
//    public void createUserTest() {
//        UserTest userTest = new UserTest();
//        userTest.createUserTest(testData);
//        userId =  ApiVariableManager.getVariable("id");
//    }
//    @Test(description = "Test f1_user login", dependsOnMethods = "createUserTest")
//    public void userLoginTest() {
//        UserTest userTest = new UserTest();
//        userTest.userLoginTest();
//        generatedToken = ApiVariableManager.getVariable("Genratedtoken");
//        token = ApiVariableManager.getVariable("token");
//
//    }
//
//    @Test(description = "Test f1_user authorization", dependsOnMethods = "userLoginTest")
//    public void userAuthorizationTest() {
//        UserTest userTest = new UserTest();
//        userTest.userAuthorizationTest();
//    }
//
//
//    @Test(description = "Test to get all books", dependsOnMethods = "userAuthorizationTest")
//    public void getAllBooksTest(){
//        BookTest bookTest = new BookTest();
//        bookTest.getAllBooksTest();
//    }
//    @Test(description = "Test to get a f2_book by ISBN", dependsOnMethods = "getAllBooksTest")
//    public void getBookByIsbnTest(){
//        BookTest bookTest = new BookTest();
//        bookTest.getBookByIsbnTest();
//    }
//
//    @Test(description = "Test add books to f1_user", dependsOnMethods = {"getBookByIsbnTest"}, enabled = true)
//    public void addBooksToUserTest(){
//        BookTest bookTest = new BookTest();
//        bookTest.addBooksToUserTest();
//    }
//
//    @Test(description = "Test get f1_user details", dependsOnMethods = {"addBooksToUserTest"}, enabled = true)
//    public void getUserDetailsTest(){
//        BookTest bookTest = new BookTest();
//        bookTest.getUserDetailsTest();
//    }
//
//
//    @Test(description = "Test to f3_delete all books for a f1_user", dependsOnMethods = {"getUserDetailsTest"}, enabled = true)
//    public void deleteAllBooksTest() {
//        DeleteTest deleteTest = new DeleteTest();
//        deleteTest.deleteAllBooksTest();
//    }
//
//    @Test(description = "Test to f3_delete a f1_user", dependsOnMethods = {"deleteAllBooksTest"}, enabled = true)
//    public void deleteUserTest() {
//        DeleteTest deleteTest = new DeleteTest();
//        deleteTest.deleteUserTest();
//    }
//}
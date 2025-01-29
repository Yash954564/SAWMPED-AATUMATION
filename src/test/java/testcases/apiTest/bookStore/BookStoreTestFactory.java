//package testcases.apiTest.bookStore;
//
//import com.framework.base.DataProvider;
//import org.testng.annotations.Factory;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class BookStoreTestFactory {
//    @Factory
//    public Object[] createInstances() {
//        List<Map<String, String>> testData = DataProvider.getData("src/main/resources/testdata/api/json/user_data.json", "JSON");
//        List<BookStoreTest> tests = new ArrayList<>();
//        for (Map<String, String> data : testData) {
//            tests.add(new BookStoreTest(data));
//        }
//        return tests.toArray();
//    }
//}
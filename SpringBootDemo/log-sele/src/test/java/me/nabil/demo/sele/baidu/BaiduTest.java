package me.nabil.demo.sele.baidu;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.screenshot;

/**
 * @author zhangbi
 */
public class BaiduTest {

    @BeforeClass
    public static void beforeClass() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);
    }

    @Test
    public void userCanSearch() {
        BaiduPage page = open("https://www.baidu.com/", BaiduPage.class);
        SearchResultsPage results = page.searchFor("selenide");
        screenshot("baidu_search_selenide.jpg");
        results.getResults().shouldHave(size(9));
        results.getResult(0).shouldHave(text("Selenide:Selenium WebDriver"));
    }


}

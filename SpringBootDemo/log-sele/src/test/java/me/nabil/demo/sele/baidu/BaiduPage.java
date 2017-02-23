package me.nabil.demo.sele.baidu;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * @author zhangbi
 */
public class BaiduPage {
    public SearchResultsPage searchFor(String text) {

        $(By.name("wd")).val(text).pressEnter();
        return page(SearchResultsPage.class);
    }

}

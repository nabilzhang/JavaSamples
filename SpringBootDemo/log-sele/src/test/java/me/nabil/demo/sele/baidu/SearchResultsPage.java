package me.nabil.demo.sele.baidu;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * @author zhangbi
 */
public class SearchResultsPage {
    public ElementsCollection getResults() {
        return $$("#content_left .result");
    }

    public SelenideElement getResult(int index) {
        return $("#content_left .result", index);
    }
}

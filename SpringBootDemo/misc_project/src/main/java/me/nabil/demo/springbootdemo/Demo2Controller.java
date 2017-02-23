package me.nabil.demo.springbootdemo;

import me.nabil.demo.springbootdemo.form.DemoForm;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangbi
 */
@Controller
@EnableAutoConfiguration
@RequestMapping(value = "/xxx")
public class Demo2Controller {

    @RequestMapping(value = "/a", method = RequestMethod.DELETE)
    @ResponseBody
    String home(@RequestParam(required = false) String a) {
        return "ahahaljkl";
    }

    /**
     * 创建
     *
     * @param form 请求
     * @return
     */
    @RequestMapping(value = "/b", method = RequestMethod.POST)
    DemoForm post(@RequestBody DemoForm form) {

        return form;
    }
}

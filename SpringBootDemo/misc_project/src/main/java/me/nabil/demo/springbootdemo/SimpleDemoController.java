package me.nabil.demo.springbootdemo;

import io.swagger.annotations.Api;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 简单的Demo Controller
 */
@Api(value = " 简单的Demo Controller")
@Controller
@EnableAutoConfiguration
@RequestMapping(value = {"/", "/simple"}, method = {RequestMethod.GET, RequestMethod.DELETE})
public class SimpleDemoController {

    Logger LOGGER = LoggerFactory.getLogger(SimpleDemoController.class);

    //    @Value("${test.a.name}")
    private String name = "world";

    /**
     * 信息
     *
     * @param a
     * @return
     */
    @RequestMapping(value = {"/", "/a"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    String home(@RequestParam(required = false) String a) {
        MDC.put("U", "AAA");

        if ("1".equals(a)) {
            LOGGER.warn("hello world");
        } else {
            LOGGER.info("hello world");
        }


        MDC.remove("U");
        return "Hello World!" + name;

    }


    /**
     * 启动
     *
     * @param args
     */
    public static void main(String args[]) {
        SpringApplication.run(SimpleDemoController.class, args);
    }
}

package me.nabil.demo.springbootdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 超时测试
 *
 * @author nabilzhang
 */
@Controller
@EnableAutoConfiguration
@RequestMapping(value = {"/test"}, method = {RequestMethod.GET})
public class TimeoutTestController {

    private static final Logger logger = LoggerFactory.getLogger(TimeoutTestController.class);

    /**
     * main方法
     *
     * @param args 参数数组
     */
    public static void main(String args[]) {
        SpringApplication.run(TimeoutTestController.class, args);
    }

    /**
     * 1.测试socketOutTimeout，三秒后返回数据
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = {"/socket_timeout"}, method = {RequestMethod.GET})
    @ResponseBody
    String socketTimeout() throws InterruptedException {
        logger.info("socket_timeout");
        Thread.sleep(3000);
        return "socket_timeout";
    }

    /**
     * 2.测试socketOutTimeout，每隔0.8秒返回数据
     *
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = {"/socket_timeout_2"}, method = {RequestMethod.GET})
    void socketTimeout2(HttpServletResponse response) throws InterruptedException, IOException {
        logger.info("socket_timeout_2");
        for (int i = 0; i < 10; i++) {
            logger.info("{}", i);
            response.getWriter().println("" + i);
            response.flushBuffer();
            Thread.sleep(800);
        }
    }

    /**
     * 3.测试connectionRequestTimeout用的服务，三秒后返回数据
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = {"/connection_request_timeout"}, method = {RequestMethod.GET})
    @ResponseBody
    String connectionRequestTimeout(HttpServletRequest request) throws InterruptedException {
        logger.info("{}", request.getRequestURI());
        Thread.sleep(3000);
        return "connectionRequestTimeout";
    }

    /**
     * 4.直接返回
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @RequestMapping(value = {"/return"}, method = {RequestMethod.POST})
    @ResponseBody
    String returnImediately(HttpServletRequest request) throws InterruptedException {
        logger.info("{}", request.getRequestURI());
        return "return";
    }
}

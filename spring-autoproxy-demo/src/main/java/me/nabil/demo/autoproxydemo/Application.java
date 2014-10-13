package me.nabil.demo.autoproxydemo;

import me.nabil.demo.autoproxydemo.service.MessagePrinter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * 
 * @author zhangbi
 * @email zhangbi@baidu.com
 * @date 2014年10月13日上午11:48:13
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        MessagePrinter printer = context.getBean(MessagePrinter.class);
        printer.printMessage();
    }
}
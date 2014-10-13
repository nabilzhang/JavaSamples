package me.nabil.demo.autoproxydemo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author zhangbi
 * @email zhangbi@baidu.com
 * @date 2014年10月13日上午11:47:20
 */
@Component
public class MessagePrinter {

    @Autowired
    private MessageService messageService;

    public void printMessage() {
        System.out.println(messageService.getMessage());
    }
}

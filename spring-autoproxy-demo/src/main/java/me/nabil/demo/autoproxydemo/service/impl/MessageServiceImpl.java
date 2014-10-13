package me.nabil.demo.autoproxydemo.service.impl;

import me.nabil.demo.autoproxydemo.service.Inner1Service;
import me.nabil.demo.autoproxydemo.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @author zhangbi
 * @date 2014年10月13日下午12:13:55
 */

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private Inner1Service inner1Service;

    public String getMessage() {
        inner1Service.say();

        System.out.println();

        return "hello" + this;
    }

}

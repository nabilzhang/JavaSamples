package me.nabil.demo.autoproxydemo.service.impl;

import me.nabil.demo.autoproxydemo.service.Inner1Service;
import me.nabil.demo.autoproxydemo.service.Inner2Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author zhangbi
 * @email zhangbi@baidu.com
 * @date 2014年10月13日下午2:43:17
 */
@Service
public class Inner1ServiceImpl implements Inner1Service {

    @Autowired
    private Inner2Service inner2Service;

    public void say() {
        System.out.println(this.getClass().getCanonicalName() + this);
        inner2Service.say();

    }

}

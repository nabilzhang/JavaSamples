package me.nabil.demo.autoproxydemo.service.impl;

import me.nabil.demo.autoproxydemo.service.Inner2Service;

import org.springframework.stereotype.Service;

/**
 *
 *
 * @author zhangbi
 * @date 2014年10月13日下午2:43:17
 */
@Service
public class Inner2ServiceImpl implements Inner2Service {

    public void say() {
        System.out.println(this.getClass().getCanonicalName() + this);
    }

}

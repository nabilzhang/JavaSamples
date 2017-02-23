package me.nabil.mixed;

import java.util.List;

/**
 * Created by baidu on 15/9/2.
 */
public class TestClass {
    private List<Long> list;

    public void sayhello() {
        list.add(1L);
    }

    @Override
    public String toString() {
        return "TestClass{" +
                "list=" + list +
                '}';
    }


    public void callSayHello(){
        this.sayhello();
    }

}

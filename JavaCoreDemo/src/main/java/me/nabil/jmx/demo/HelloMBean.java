package me.nabil.jmx.demo;

/**
 * Created by baidu on 15/9/14.
 */
public interface HelloMBean {
    public void sayHello();
    public int add(int x, int y);

    public String getName();

    public int getCacheSize();
    public void setCacheSize(int size);

}

package me.nabil.demo.nettydemo;

import java.util.Date;

/**
 * UnixTime
 *
 * @author nabil
 * @date 2019/1/3
 */
public class UnixTime {
    private long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}

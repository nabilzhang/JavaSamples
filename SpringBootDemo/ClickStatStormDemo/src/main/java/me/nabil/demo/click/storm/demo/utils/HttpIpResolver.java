package me.nabil.demo.click.storm.demo.utils;

/**
 * @author zhangbi
 */
public class HttpIpResolver {

    static String url = "";

    public static GeoCountry resolveIp(String ip) {
        GeoCountry geoCountry = new GeoCountry();
        geoCountry.setCity("上海");
        geoCountry.setCountry("中国");
        return geoCountry;
    }


}

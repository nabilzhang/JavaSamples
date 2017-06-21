package com.pingan.haofang.interpreter;


import org.apache.zeppelin.interpreter.InterpreterContext;
import org.apache.zeppelin.interpreter.InterpreterResult;
import org.apache.zeppelin.user.AuthenticationInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @author zhangbi617
 * @date 2017-06-21
 */
public class SearchCloudTest {

    InterpreterContext interpreterContext;

    public static Properties getProperties() {
        Properties p = new Properties();
        p.setProperty("searchcloud.url", "http://sc.ci.anhouse.com.cn/api/v1/sql/select");
        p.setProperty("searchcloud.app_code", "hft");
        p.setProperty("searchcloud.token", "vbyptxey");
        return p;
    }


    @Before
    public void setUp() throws Exception {
        interpreterContext = new InterpreterContext("", "1",
                null, "", "", new AuthenticationInfo("testUser"),
                null, null, null, null,
                null, null);
    }

    @Test
    public void interpret() throws Exception {
        SearchCloud searchCloud = new SearchCloud(getProperties());
        searchCloud.open();
        InterpreterResult result = searchCloud.interpret("{\n" +
                "  \"indexName\": \"danke_comm\",\n" +
                "  \"sql\": \"select * from danke_comm;\"\n" +
                "}", interpreterContext);
        Assert.assertEquals(result.code(), InterpreterResult.Code.SUCCESS);
    }

}

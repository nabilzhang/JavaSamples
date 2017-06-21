package com.pingan.haofang.interpreter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.zeppelin.interpreter.Interpreter;
import org.apache.zeppelin.interpreter.InterpreterContext;
import org.apache.zeppelin.interpreter.InterpreterResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * 搜索云
 *
 * @author zhangbi617
 * @date 2017-06-21
 */
public class SearchCloud extends Interpreter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchCloud.class);

    private static final String URL_KEY = "searchcloud.url";

    private static final String APP_CODE_KEY = "searchcloud.app_code";

    private static final String TOKEN_KEY = "searchcloud.token";

    private Gson gson = new Gson();


    private static final char WHITESPACE = ' ';
    private static final char NEWLINE = '\n';
    private static final char TAB = '\t';
    private static final String TABLE_MAGIC_TAG = "%table ";

    public SearchCloud(Properties property) {
        super(property);
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public InterpreterResult interpret(String s, InterpreterContext interpreterContext) {
        String url = property.getProperty(URL_KEY);
        String appCode = property.getProperty(APP_CODE_KEY);
        String token = property.getProperty(TOKEN_KEY);
        try {

            String content = Request.Post(url)
                    .addHeader("appCode", appCode)
                    .addHeader("token", token)
                    .addHeader("traceId", interpreterContext.getParagraphId())
//                    .addHeader("Content-Typ", "application/json")
//                    .addHeader("Accept", "application / json")
                    .bodyString(s, ContentType.APPLICATION_JSON).execute().returnContent().asString();


            System.out.println(content);
            SearchCloudJson json = gson.fromJson(content, SearchCloudJson.class);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(TABLE_MAGIC_TAG);
            stringBuilder.append(NEWLINE);
            for (String column : json.getResultColumns()) {
                stringBuilder.append(column).append(TAB);
            }
            stringBuilder.append(NEWLINE);

            for (HashMap<String, JsonElement> line : json.getResult()) {
                for (String column : json.getResultColumns()) {
                    stringBuilder.append(line.get(column).getAsString()).append(TAB);
                }
                stringBuilder.append(NEWLINE);
            }

            InterpreterResult interpreterResult = new InterpreterResult(InterpreterResult.Code.SUCCESS);
            interpreterResult.add(InterpreterResult.Type.TABLE, stringBuilder.toString());
            return interpreterResult;

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            InterpreterResult result = new InterpreterResult(InterpreterResult.Code.ERROR, e.getMessage());
            return result;
        }

    }

    @Override
    public void cancel(InterpreterContext interpreterContext) {

    }

    @Override
    public FormType getFormType() {
        return FormType.SIMPLE;
    }

    public int getProgress(InterpreterContext interpreterContext) {
        return 0;
    }
}

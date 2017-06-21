package com.pingan.haofang.interpreter;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.List;

/**
 * Json
 *
 * @author zhangbi617
 * @date 2017-06-21
 */
public class SearchCloudJson {

    private List<HashMap<String, JsonElement>> result;

    private List<String> resultColumns;

    public List<HashMap<String, JsonElement>> getResult() {
        return result;
    }

    public void setResult(List<HashMap<String, JsonElement>> result) {
        this.result = result;
    }

    public List<String> getResultColumns() {
        return resultColumns;
    }

    public void setResultColumns(List<String> resultColumns) {
        this.resultColumns = resultColumns;
    }

    @Override
    public String toString() {
        return "SearchCloudJson{" +
                "result=" + result +
                ", resultColumns=" + resultColumns +
                '}';
    }
}

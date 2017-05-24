package me.nabil.samples.search.lucence;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * GsonTest
 *
 * @author zhangbi617
 * @date 24/05/2017
 */
public class GsonTest {
    public static void main(String[] args) {

        String a = "a";

        JsonParser jsonParser = new JsonParser();
        JsonElement element1 = jsonParser.parse(a);
        printType(element1);

        a = "{\"a\":1}";
        JsonElement element2 = jsonParser.parse(a);
        printType(element2);

        a = "[{\"b\":1}]";
        JsonElement element3 = jsonParser.parse(a);
        printType(element3);

        a = "";
        JsonElement element4 = jsonParser.parse(a);
        printType(element4);

    }

    private static void printType(JsonElement jsonElement) {
        System.out.println(jsonElement);
        System.out.println(jsonElement.getClass().getName());
        System.out.println("-----------");

//        if (jsonElement instanceof JsonObject) {
//            System.out.println("JsonObject");
//        } else if (jsonElement instanceof JsonArray) {
//            System.out.println("JsonArray");
//        } else if (jsonElement instanceof JsonPrimitive) {
//            System.out.println(JsonPrimitive.class);
//        } else if (jsonElement instanceof JsonNull) {
//            System.out.println(JsonNull.class);
//        }

    }
}

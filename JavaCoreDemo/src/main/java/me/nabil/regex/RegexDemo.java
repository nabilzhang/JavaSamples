package me.nabil.regex;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexDemo
 *
 * @author nabilzhang
 * @date 2019/1/24
 */
public class RegexDemo {
    public static void main(String[] args) throws IOException {
        Pattern pattern = Pattern.compile("\"uid\":(.+?),\"charge_no\"");
        List<String> lines =
                FileUtils.readLines(new File(""), "UTF-8");
        Set<String> users = new HashSet<>();
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                users.add(matcher.group(1));
                System.out.println(matcher.group(1));
            }
        }

        System.out.println(users.size());
    }
}

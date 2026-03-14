package de.stevee.Utils;



import com.googlecode.lanterna.gui2.Label;
import de.stevee.Logic.Craft.Craft;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Inventory {
    public static ArrayList<String> getMatches(HashMap<String, ?> list, String match) {
        if (match == null) match = "";

        StringBuilder regex = new StringBuilder();
        for (char c : match.toCharArray()) {
            regex.append(Pattern.quote(Character.toString(c))).append(".*");
        }

        Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

        ArrayList<String> possible = new ArrayList<>();
        for (String s : list.keySet()) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) possible.add(s);
            if (s.equalsIgnoreCase(match)) {
                possible.clear();
                possible.add(s);
                break;
            }
        }

        possible.sort(Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));

        return possible;
    }

    public static ArrayList<String> getMatches(List<?> list, String match) {
        if (match == null) match = "";

        StringBuilder regex = new StringBuilder();
        for (char c : match.toCharArray()) {
            regex.append(Pattern.quote(Character.toString(c))).append(".*");
        }
        ArrayList<String> all = new ArrayList<>();
        list.forEach(o -> {
            all.add(o.toString());
        });

        Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

        ArrayList<String> possible = new ArrayList<>();
        for (String s : all) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) possible.add(s);
            if (s.equalsIgnoreCase(match)) {
                possible.clear();
                possible.add(s);
                break;
            }
        }

        possible.sort(Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));

        return possible;
    }
}

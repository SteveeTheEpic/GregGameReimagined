package de.stevee.Utils;



import com.googlecode.lanterna.gui2.Label;
import de.stevee.Logic.Items.Tool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static de.stevee.Utils.Items.Items_List;


public class Inventory {

    public static void init() {
        Items_List.forEach((item) -> {
            if (item.showing && (item instanceof Tool tool)) {
                System.out.printf("%s's Tier: %d\n", tool.name, tool.tier);
            } else {
                if (item.showing) {
                    System.out.println(item.name + ": " + item.quantity);
                }
            }
        });
    }


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
    public static ArrayList<String> getMatchesInList(List<Label> list, String match) {
        if (match == null) match = "";

        StringBuilder regex = new StringBuilder();
        for (char c : match.toCharArray()) {
            regex.append(Pattern.quote(Character.toString(c))).append(".*");
        }

        Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

        ArrayList<String> possible = new ArrayList<>();
        for (Label s : list) {
            Matcher matcher = pattern.matcher(s.getText());
            if (matcher.find()) possible.add(s.getText());
            if (s.getText().equalsIgnoreCase(match)) {
                possible.clear();
                possible.add(s.getText());
                break;
            }
        }

        possible.sort(Comparator.comparingInt(String::length).thenComparing(String.CASE_INSENSITIVE_ORDER));

        return possible;
    }
}

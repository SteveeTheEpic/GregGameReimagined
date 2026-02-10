package de.stevee.Utils;



import de.stevee.Logic.Items.Item;
import de.stevee.Logic.Items.Tool;

import java.util.ArrayList;
import java.util.HashMap;
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
        StringBuilder regex = new StringBuilder();

        for (char c : match.toLowerCase().toCharArray()) {
            regex.append(Pattern.quote(Character.toString(c))).append(".*");
        }

        String finalRegex = regex.toString();
        Pattern pattern = Pattern.compile(finalRegex);

        ArrayList<String> possible = new ArrayList<>();
        for (String s : list.keySet()) {
            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) possible.add(s);
            if (s == match) {
                possible.clear();
                possible.add(s);
                break;
            }
        }

        return possible;
    }

    public static void showChanged() {
        for (Item item : Items_List) {
            if (item.prev_quantity - item.quantity != 0) {
                System.out.printf("%s:  %d -> %d\n", item.name, item.prev_quantity, item.quantity);
            }
        }

        for (Item item : Items_List) {
            if (!(item instanceof Tool tool)) continue;
            if (tool.prev_tier - tool.tier != 0) {
                System.out.printf("%s's Tier:  %d -> %d\n", tool.name, tool.prev_tier, tool.tier);
            }
        }
    }
}

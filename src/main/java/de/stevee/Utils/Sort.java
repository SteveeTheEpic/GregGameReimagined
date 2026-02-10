package de.stevee.Utils;

import java.util.Comparator;

public class Sort {

    public static class SortByNameLength implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            String item = (String) o1;
            String item2 = (String) o2;
            if (item.length() < item2.length()) return -1;
            if (item.length() > item2.length()) return 1;

            return 0;
        }
    }
}

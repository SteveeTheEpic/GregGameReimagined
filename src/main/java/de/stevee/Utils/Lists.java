package de.stevee.Utils;

import de.stevee.ui.Component.ProgressLabel;
import de.stevee.ui.Component.ProgressList;
import de.stevee.ui.Component.ScrollingLabel;

import java.util.ArrayList;

public class Lists {
    public static ArrayList<ScrollingLabel> scrolling_labels = new ArrayList<>();
    public static ArrayList<ProgressLabel> progressLabelList = new ArrayList<>();

    public static void updateLabel() {
        scrolling_labels.forEach(ScrollingLabel::tick);
    }

    public static void updateProgress() {
        progressLabelList.forEach(ProgressLabel::update);
    }
}

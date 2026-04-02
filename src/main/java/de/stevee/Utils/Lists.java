package de.stevee.Utils;

import de.stevee.Ui.Component.ProgressLabel;
import de.stevee.Ui.Component.ScrollingLabel;

import java.util.ArrayList;

public class Lists {
    public static ArrayList<ScrollingLabel> scrolling_labels = new ArrayList<>();
    public static ArrayList<ProgressLabel> progressLabelList = new ArrayList<>();

    public static void updateProgress() {
        progressLabelList.forEach(ProgressLabel::update);
    }
}

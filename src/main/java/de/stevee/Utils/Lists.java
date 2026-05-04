package de.stevee.Utils;

import de.stevee.API.Render.UI.Component.ProgressLabel;
import de.stevee.API.Render.UI.Component.ScrollingLabel;

import java.util.ArrayList;

public class Lists {
    public static ArrayList<ScrollingLabel> scrolling_labels = new ArrayList<>();
    public static ArrayList<ProgressLabel> progressLabelList = new ArrayList<>();

    public static void updateProgress() {
        progressLabelList.forEach(ProgressLabel::update);
    }
}

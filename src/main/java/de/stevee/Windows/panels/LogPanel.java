package de.stevee.Windows.panels;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.TextBox;

import java.util.ArrayList;
import java.util.List;

public class LogPanel extends TextBox {
    private final List<String> lines = new ArrayList<>();
    private final int height;
    private int scroll = 0;

    public LogPanel(int height) {
        super(new TerminalSize(10, height), "", Style.MULTI_LINE);
        this.height = height;

        setReadOnly(true);
        setVerticalFocusSwitching(false);
        setHorizontalFocusSwitching(false);
    }

    public void append(String msg) {
        lines.add(msg);
        // Auto-follow bottom unless user has scrolled up
        if (scroll == 0) {
            refreshText();
        } else {
            refreshText();
        }
    }

    public void scroll(int deltaLines) {
        int maxScroll = Math.max(0, lines.size() - height);
        scroll = clamp(scroll + deltaLines, 0, maxScroll);
        refreshText();
    }

    private void refreshText() {
        int maxScroll = Math.max(0, lines.size() - height);
        scroll = clamp(scroll, 0, maxScroll);

        int start = Math.max(0, lines.size() - height - scroll);
        int end = Math.min(lines.size(), start + height);

        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(lines.get(i));
            if (i + 1 < end) sb.append('\n');
        }
        setText(sb.toString());
    }

    private static int clamp(int v, int lo, int hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}

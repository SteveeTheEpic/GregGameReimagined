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

    public void append(String log) {
        lines.add(log);
        if (scroll == 0) {
            refreshText();
        } else {
            scroll(1);
            refreshText();
        }
    }

    /**
     * Scroll {@code lines} up <p>
     * if {@code lines} is positive it scrolls up, if its negative it scrolls down
     * </p>

     * @param lines
     */
    public void scroll(int lines) {
        int maxScroll = Math.max(0, this.lines.size() - height);
        scroll = Math.clamp(scroll + lines, 0, maxScroll);
        refreshText();
    }

    private void refreshText() {
        int maxScroll = Math.max(0, lines.size() - height);
        scroll = Math.clamp(scroll, 0, maxScroll);

        int start = Math.max(0, lines.size() - height - scroll);
        int end = Math.min(lines.size(), start + height);

        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(lines.get(i));
            if (i + 1 < end) sb.append('\n');
        }
        setText(sb.toString());
    }
}

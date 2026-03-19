package de.stevee.ui.Component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;

import java.util.List;

public class ProgressList extends AbstractListBox<ProgressLabel, ProgressList>{
    private final ProgressLabelRenderer renderer = new ProgressLabelRenderer();

    public ProgressList() {
        super();
        setListItemRenderer(renderer);
    }

    public void update() {
        invalidate();
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i).shouldBeRemoved()) {
                removeItem(i);
                return;
            }
        }
    }

    private static class ProgressLabelRenderer extends ListItemRenderer<ProgressLabel, ProgressList> {
        @Override
        public void drawItem(TextGUIGraphics graphics, ProgressList listBox, int index, ProgressLabel item, boolean selected, boolean focused) {
            TerminalSize area = graphics.getSize();
            graphics.fillRectangle(TerminalPosition.TOP_LEFT_CORNER, area, ' ');

            // Use ScrollingLabel for name rendering
            String name = item.nameLabel.getText();
            ScrollingLabel nameScroller = new ScrollingLabel(name, area.getColumns() / 2);
            String scrollingName = nameScroller.getVisibleText();
            graphics.putString(0, 0, scrollingName);

            // Progress bar WITH number inside
            long value = item.getValue();
            long max = item.bar.getMax();
            double pct = max > 0 ? (100.0 * value / max) : 0;
            String percent = String.format("%3d%%", (int)pct);

            int barStart = area.getColumns() / 2 + 2;
            int barWidth = area.getColumns() - barStart;
            int filled = max > 0 ? (int)((value * barWidth) / max) : 0;

            // Number position inside bar
            int numberPos = barStart + barWidth / 2 - 1;
            boolean numberOverlapsFilled = numberPos + percent.length() > barStart + filled;

            TextColor fgColor, bgColor;
            if (numberOverlapsFilled) {
                fgColor = TextColor.ANSI.BLACK;  // Dark on filled
                bgColor = TextColor.ANSI.WHITE;
            } else {
                fgColor = TextColor.ANSI.WHITE;  // Light on empty
                bgColor = TextColor.ANSI.BLACK;
            }

            // Draw bar (filled first, then empty)
            graphics.setForegroundColor(bgColor);
            graphics.setBackgroundColor(bgColor);
            for (int i = 0; i < barWidth; i++) {
                char barChar = (i < filled) ? '█' : ' ';
                graphics.putString(barStart + i, 0, String.valueOf(barChar));
            }

            // Draw percentage INSIDE bar with inversion
            graphics.setForegroundColor(fgColor);
            graphics.setBackgroundColor(bgColor);
            if (numberPos >= barStart && numberPos + percent.length() <= barStart + barWidth) {
                graphics.putString(numberPos, 0, percent);
            }

            // Selection highlight (whole row)
            if (selected) {
                graphics.enableModifiers(SGR.BOLD, SGR.BLINK);
            }
        }
    }
}

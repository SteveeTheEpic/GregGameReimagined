package de.stevee.API.Render.UI.Component;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.gui2.*;

import static com.googlecode.lanterna.TextColor.ANSI.BLACK;
import static com.googlecode.lanterna.TextColor.ANSI.WHITE;

public class ProgressList extends AbstractListBox<ProgressLabel, ProgressList>{

    private int time = 1;

    public ProgressList() {
        super();
        ProgressLabelRenderer renderer = new ProgressLabelRenderer();
        setListItemRenderer(renderer);
    }

    public void update() {
        invalidate();
        for (int i = 0; i < getItems().size(); i++) {
            if (time % 250 == 0) {
                tickLabel();

                // needed for integer overflow protection
                time = 1;
            }

            if (getItems().get(i).shouldBeRemoved()) {
                removeItem(i);
                return;
            }
        }
        time++;
    }

    private void tickLabel() {
        for (ProgressLabel label : getItems()) {
            label.getLabel().tick();
        }
    }

    private static class ProgressLabelRenderer extends ListItemRenderer<ProgressLabel, ProgressList> {
        @Override
        public void drawItem(TextGUIGraphics graphics, ProgressList listBox, int index, ProgressLabel item, boolean selected, boolean focused) {
            TerminalSize area = graphics.getSize();
            graphics.fillRectangle(TerminalPosition.TOP_LEFT_CORNER, area, ' ');

            ScrollingLabel nameScroller = item.getLabel();
            nameScroller.setLabelWidth(area.getColumns() / 3);
            String scrollingName = nameScroller.getVisibleText();
            graphics.putString(0, 0, scrollingName);

            long currProgress = item.getValue();
            long maxProgress = item.bar.getMax();
            double pct = maxProgress > 0 ? (100.0 * currProgress / maxProgress) : 0;
            String percent = String.format("%3d%%", (int)pct);

            int barStart = area.getColumns() / 3 + 2;
            int barWidth = area.getColumns() - barStart;
            int filled = maxProgress > 0 ? (int)((currProgress * barWidth) / maxProgress) : 0;

            int numberPos = barStart + barWidth / 2 - 2;

            TextColor fgColor = BLACK;
            TextColor bgColor = WHITE;

            for (int i = 0; i < barWidth; i++) {
                if (i < filled) {
                    graphics.setBackgroundColor(bgColor);
                } else {
                    graphics.setBackgroundColor(fgColor);
                }
                graphics.putString(barStart + i, 0, " ");
            }

            if (numberPos >= barStart && numberPos + percent.length() <= barStart + barWidth) {
                for (int i = 0; i < percent.length(); i++) {
                    if (numberPos + i > barStart + filled - 1) {
                        graphics.setForegroundColor(bgColor);
                        graphics.setBackgroundColor(fgColor);
                    } else {
                        graphics.setForegroundColor(fgColor);
                        graphics.setBackgroundColor(bgColor);
                    }
                    graphics.putString(numberPos + i, 0, String.valueOf(percent.toCharArray()[i]));
                }
            }

            // TODO: Fix this and that
            if (selected) {
                graphics.enableModifiers(SGR.BOLD, SGR.BLINK);
            }
        }
    }
}

package de.stevee.ui.Component;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import de.stevee.Utils.Lists;

import java.util.EnumSet;
import java.util.List;

public class ScrollingLabel extends AbstractComponent<ScrollingLabel> {
    private String[] lines;
    private int scrollOffset = 0;
    private int pauseCounter = 0;
    private boolean scrolling = false;
    private boolean inPause = false;
    private int PAUSE_FRAMES = 8;
    private Integer labelWidth;
    private TerminalSize labelSize;
    private TextColor foregroundColor;
    private TextColor backgroundColor;
    private final EnumSet<SGR> additionalStyles;

    public ScrollingLabel(String text, int fixedWidth) {
        this.labelWidth = fixedWidth;
        this.foregroundColor = null;
        this.backgroundColor = null;
        this.additionalStyles = EnumSet.noneOf(SGR.class);
        Lists.scrolling_labels.add(this);
        setText(text);
    }

    protected void setLines(String[] lines) {
        this.lines = lines;
    }

    protected String[] splitIntoMultipleLines(String text) {
        return text.replace("\r", "").split("\n");
    }

    protected TerminalSize getBounds(String[] lines, TerminalSize currentBounds) {
        if(currentBounds == null) {
            currentBounds = TerminalSize.ZERO;
        }
        currentBounds = currentBounds.withRows(lines.length);
        if(labelWidth == null || labelWidth == 0) {
            int preferredWidth = 0;
            for(String line : lines) {
                int lineWidth = TerminalTextUtils.getColumnWidth(line);
                if(preferredWidth < lineWidth) {
                    preferredWidth = lineWidth;
                }
            }
            currentBounds = currentBounds.withColumns(preferredWidth);
        }
        else {
            List<String> wordWrapped = TerminalTextUtils.getWordWrappedText(labelWidth, lines);
            currentBounds = currentBounds.withColumns(labelWidth).withRows(wordWrapped.size());
        }
        return currentBounds;
    }

    public void setText(String text) {
        setLines(splitIntoMultipleLines(text));
        scrollOffset = 0;
        pauseCounter = 0;
        inPause = false;
        scrolling = lines.length > 0 && TerminalTextUtils.getColumnWidth(lines[0]) > labelWidth;
        labelSize = getBounds(lines, labelSize);
        invalidate();
    }

    @Override
    protected ComponentRenderer<ScrollingLabel> createDefaultRenderer() {
        return new ComponentRenderer<>() {
            @Override
            public TerminalSize getPreferredSize(ScrollingLabel component) {
                return new TerminalSize(labelWidth, 1);  // Fixed single line height
            }

            @Override
            public void drawComponent(TextGUIGraphics graphics, ScrollingLabel component) {
                ThemeDefinition themeDefinition = component.getThemeDefinition();
                graphics.applyThemeStyle(themeDefinition.getNormal());
                if (component.getForegroundColor() != null) {
                    graphics.setForegroundColor(component.getForegroundColor());
                }
                if (component.getBackgroundColor() != null) {
                    graphics.setBackgroundColor(component.getBackgroundColor());
                }
                for (SGR sgr : component.additionalStyles) {
                    graphics.enableModifiers(sgr);
                }

                if (component.lines == null || component.lines.length == 0) return;


                String line = component.lines[0];
                int textWidth = TerminalTextUtils.getColumnWidth(line);
                int areaWidth = graphics.getSize().getColumns();

                if (!component.scrolling || textWidth <= areaWidth) {
                    graphics.putString(0, 0, line);
                    return;
                }

                if (component.inPause) {
                    return;  // Blank pause
                }

                // Smooth scroll-in from right (negative offset = offscreen right)
                int displayStart = component.scrollOffset;

                for (int i = 0; i < areaWidth; i++) {
                    int charIndex = displayStart + i;
                    if (charIndex >= 0 && charIndex < textWidth) {
                        graphics.putString(i, 0, String.valueOf(line.charAt(charIndex)));
                    }
                    // else: empty space (left side during scroll-in, right side during scroll-out)
                }
            }
        };
    }

    public void tick() {
        if (!scrolling) return;

        int textWidth = TerminalTextUtils.getColumnWidth(lines[0]);

        if (inPause) {
            pauseCounter++;
            if (pauseCounter >= PAUSE_FRAMES) {
                // Start scroll-in from OFFSCREEN RIGHT
                inPause = false;
                scrollOffset = -textWidth;  // Start left of visible area
                pauseCounter = 0;
            }
        } else {
            scrollOffset++;

            // Check if fully scrolled off left side
            if (scrollOffset >= textWidth) {
                // Enter pause phase
                inPause = true;
                pauseCounter = 0;
            }
        }
        invalidate();
    }

    public void setPauseDuration(int frames) {
        this.PAUSE_FRAMES = Math.max(1, frames);
    }

    public Integer getLabelWidth() {
        return labelWidth;
    }
    public void setLabelWidth(Integer labelWidth) {
        this.labelWidth = labelWidth;
    }
    public TextColor getForegroundColor() {
        return foregroundColor;
    }
    public void setForegroundColor(TextColor foregroundColor) {
        this.foregroundColor = foregroundColor;
    }
    public TextColor getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(TextColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
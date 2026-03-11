package de.stevee.ui.Component;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.gui2.*;

public class CustomProgressBar extends AbstractComponent<CustomProgressBar> {

    private  long min;
    private  long max;
    private  long value;
    private  int preferredWidth;
    private  String labelFormat;

    /**
     * Creates a new progress bar initially defined with a range from 0 to 100. The
     */
    public CustomProgressBar() {
        this(0, 100);
    }

    /**
     * Creates a new progress bar with a defined range of minimum to maximum
     *
     * @param min The minimum value of this progress bar
     * @param max The maximum value of this progress bar
     */
    public CustomProgressBar(long min, long max) {
        this(min, max, 0);
    }

    /**
     * Creates a new progress bar with a defined range of minimum to maximum and also with a hint as to how wide the
     * progress bar should be drawn
     *
     * @param min            The minimum value of this progress bar
     * @param max            The maximum value of this progress bar
     * @param preferredWidth Width size hint, in number of columns, for this progress bar. The renderer may choose to
     *                       not use this hint. 0 or less means that there is no hint.
     */
    public CustomProgressBar(long min, long max, int preferredWidth) {
        if (min > max) {
            min = max;
        }
        this.min = min;
        this.max = max;
        this.value = min;
        this.labelFormat = "%2.0f%%";

        if (preferredWidth < 1) {
            preferredWidth = 1;
        }
        this.preferredWidth = preferredWidth;
    }

    /**
     * Returns the current <i>minimum</i> value for this progress bar
     *
     * @return The <i>minimum</i> value of this progress bar
     */
    public long getMin() {
        return min;
    }

    /**
     * Updates the <i>minimum</i> value of this progress bar. If the current <i>maximum</i> and/or <i>value</i> are
     * smaller than this new <i>minimum</i>, they are automatically adjusted so that the range is still valid.
     *
     * @param min New <i>minimum</i> value to assign to this progress bar
     * @return Itself
     */
    public synchronized CustomProgressBar setMin(int min) {
        if (min > max) {
            setMax(min);
        }
        if (min > value) {
            setValue(min);
        }
        if (this.min != min) {
            this.min = min;
            invalidate();
        }
        return this;
    }

    /**
     * Returns the current <i>maximum</i> value for this progress bar
     *
     * @return The <i>maximum</i> value of this progress bar
     */
    public  long getMax() {
        return max;
    }

    /**
     * Updates the <i>maximum</i> value of this progress bar. If the current <i>minimum</i> and/or <i>value</i> are
     * greater than this new <i>maximum</i>, they are automatically adjusted so that the range is still valid.
     *
     * @param max New <i>maximum</i> value to assign to this progress bar
     * @return Itself
     */
    public synchronized CustomProgressBar setMax(int max) {
        if (max < min) {
            setMin(max);
        }
        if (max < value) {
            setValue(max);
        }
        if (this.max != max) {
            this.max = max;
            invalidate();
        }
        return this;
    }

    /**
     * Returns the current <i>value</i> of this progress bar, which represents how complete the progress indication is.
     *
     * @return The progress value of this progress bar
     */
    public  long getValue() {
        return value;
    }

    /**
     * Updates the <i>value</i> of this progress bar, which will update the visual state. If the value passed in is
     * outside the <i>minimum-maximum</i> range, it is automatically adjusted.
     *
     * @param value New value of the progress bar
     * @return Itself
     */
    public synchronized CustomProgressBar setValue(long value) {
        if (value < min) {
            value = min;
        }
        if (value > max) {
            value = max;
        }
        if (this.value != value) {
            this.value = value;
            invalidate();
        }
        return this;
    }

    /**
     * Returns the preferred width of the progress bar component, in number of columns. If 0 or less, it should be
     * interpreted as no preference on width and it's up to the renderer to decide.
     *
     * @return Preferred width this progress bar would like to have, or 0 (or less) if no preference
     */
    public int getPreferredWidth() {
        return preferredWidth;
    }

    /**
     * Updated the preferred width hint, which tells the renderer how wide this progress bar would like to be. If called
     * with 0 (or less), it means no preference on width and the renderer will have to figure out on its own how wide
     * to make it.
     *
     * @param preferredWidth New preferred width in number of columns, or 0 if no preference
     */
    public void setPreferredWidth(int preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    /**
     * Returns the current label format string which is the template for what the progress bar would like to be the
     * label printed. Exactly how this label is printed depends on the renderer, but the default renderer will print
     * the label centered in the middle of the progress indication.
     *
     * @return The label format template string this progress bar is currently using
     */
    public String getLabelFormat() {
        return labelFormat;
    }

    /**
     * Sets the label format this progress bar should use when the component is drawn. The string would be compatible
     * with {@code String.format(..)}, the class will pass the string through that method and pass in the current
     * progress as a single vararg parameter (passed in as a {@code float} in the range of 0.0f to 100.0f). Setting this
     * format string to null or empty string will turn off the label rendering.
     *
     * @param labelFormat Label format to use when drawing the progress bar, or {@code null} to disable
     * @return Itself
     */
    public synchronized CustomProgressBar setLabelFormat(String labelFormat) {
        this.labelFormat = labelFormat;
        invalidate();
        return this;
    }

    /**
     * Returns the current progress of this progress bar's <i>value</i> from <i>minimum</i> to <i>maximum</i>, expressed
     * as a float from 0.0f to 1.0f.
     *
     * @return current progress of this progress bar expressed as a float from 0.0f to 1.0f.
     */
    public synchronized float getProgress() {
        return (float) (value - min) / (float) max;
    }

    /**
     * Returns the label of this progress bar formatted through {@code String.format(..)} with the current progress
     * value.
     *
     * @return The progress bar label formatted with the current progress
     */
    public synchronized String getFormattedLabel() {
        if (labelFormat == null) {
            return "";
        }
        return String.format(labelFormat, getProgress() * 100.0f);
    }

    @Override
    protected DefaultCustomProgressBarRenderer createDefaultRenderer() {
        return new DefaultCustomProgressBarRenderer();
    }

    /**
     * Default implementation of the progress bar GUI component renderer. This renderer will draw the progress bar
     * on a single line and gradually fill up the space with a different color as the progress is increasing.
     */
    public static class DefaultCustomProgressBarRenderer implements ComponentRenderer<CustomProgressBar> {
        @Override
        public TerminalSize getPreferredSize(CustomProgressBar component) {
            int preferredWidth = component.getPreferredWidth();
            if (preferredWidth > 0) {
                return new TerminalSize(preferredWidth, 1);
            } else if (component.getLabelFormat() != null && !component.getLabelFormat().trim().isEmpty()) {
                return new TerminalSize(TerminalTextUtils.getColumnWidth(String.format(component.getLabelFormat(), 100.0f)) + 2, 1);
            } else {
                return new TerminalSize(10, 1);
            }
        }

        @Override
        public void drawComponent(TextGUIGraphics graphics, CustomProgressBar component) {
            TerminalSize size = graphics.getSize();
            if (size.getRows() == 0 || size.getColumns() == 0) {
                return;
            }
            ThemeDefinition themeDefinition = component.getThemeDefinition();
            int columnOfProgress = (int) (component.getProgress() * size.getColumns());
            String label = component.getFormattedLabel();
            int labelRow = size.getRows() / 2;

            // Adjust label so it fits inside the component
            int labelWidth = TerminalTextUtils.getColumnWidth(label);

            // Can't be too smart about this, because of CJK characters
            if (labelWidth > size.getColumns()) {
                boolean tail = true;
                while (labelWidth > size.getColumns()) {
                    if (tail) {
                        label = label.substring(0, label.length() - 1);
                    } else {
                        label = label.substring(1);
                    }
                    tail = !tail;
                    labelWidth = TerminalTextUtils.getColumnWidth(label);
                }
            }
            int labelStartPosition = (size.getColumns() - labelWidth) / 2;

            for (int row = 0; row < size.getRows(); row++) {
                graphics.applyThemeStyle(themeDefinition.getActive());
                for (int column = 0; column < size.getColumns(); column++) {
                    if (column == columnOfProgress) {
                        graphics.applyThemeStyle(themeDefinition.getNormal());
                    }
                    if (row == labelRow && column >= labelStartPosition && column < labelStartPosition + labelWidth) {
                        char character = label.charAt(TerminalTextUtils.getStringCharacterIndex(label, column - labelStartPosition));
                        graphics.setCharacter(column, row, character);
                        if (TerminalTextUtils.isCharDoubleWidth(character)) {
                            column++;
                            if (column == columnOfProgress) {
                                graphics.applyThemeStyle(themeDefinition.getNormal());
                            }
                        }
                    } else {
                        graphics.setCharacter(column, row, themeDefinition.getCharacter("FILLER", ' '));
                    }
                }
            }

            String text = component.getValue() + "/" + component.getMax();
            int x = Math.max(0, (graphics.getSize().getColumns() - text.length()) / 2);
            int y = 0;

            graphics.putString(x, y, text);
        }
    }
}
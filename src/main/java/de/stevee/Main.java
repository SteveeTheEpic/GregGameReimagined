package de.stevee;

import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import de.stevee.Logic.Energy.Energy;
import de.stevee.Utils.Lists;
import de.stevee.Windows.GameWindow;
import de.stevee.Logic.Controller;
import de.stevee.Windows.panels.InfoPanel;

import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static de.stevee.Logic.Scheduler.Scheduler.scheduler;
import static de.stevee.ui.UI.gui;

public class Main {
    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory tf = new DefaultTerminalFactory();
        SwingTerminalFontConfiguration fontConfiguration = SwingTerminalFontConfiguration.getDefaultOfSize(24);
        tf.setTerminalEmulatorFontConfiguration(fontConfiguration);
        Screen screen = tf.createScreen();
        screen.startScreen();

        gui = new MultiWindowTextGUI(new SameTextGUIThread.Factory(), screen);
        gui.setTheme(createTheme());
        InfoPanel.init(gui);
        Controller controller = new Controller();
        gui.addWindow(new GameWindow(controller));
        gui.moveToTop(InfoPanel.getInfoPanelWindow());


        // Initialize scheduler tasks (runs in background)
        initializeScheduler();

        while (!controller.isEnd()) {
            boolean didWork = gui.getGUIThread().processEventsAndUpdate();
            gui.getScreen().doResizeIfNecessary();
            InfoPanel.updatePosition();

            if (!didWork) {
                Thread.sleep(10);
            }
        }

        scheduler.shutdown();
        screen.stopScreen();
    }

    private static void initializeScheduler() {
        scheduler.executeWithFixedDelay(
                Energy::tick,
                0,
                50,
                TimeUnit.MILLISECONDS
        );


        scheduler.executeWithFixedDelay(
                Lists::updateProgress,
                0,
                5,
                TimeUnit.MILLISECONDS
        );

        scheduler.executeWithFixedDelay(
                Lists::updateLabel,
                150,
                150,
                TimeUnit.MILLISECONDS
        );
    }

    private static Theme createTheme() throws Exception {
        String propsText = ""
                + "foreground = default\n"
                + "background = default\n"
                + "sgr =\n"
                + "\n"
                + "foreground[SELECTED] = black\n"
                + "background[SELECTED] = white\n"
                + "sgr[SELECTED] =\n"
                + "\n"
                + "foreground[ACTIVE] = black\n"
                + "background[ACTIVE] = white\n"
                + "sgr[ACTIVE] =\n"
                + "\n"
                + "foreground[PRELIGHT] = black\n"
                + "background[PRELIGHT] = white\n"
                + "sgr[PRELIGHT] =\n";

        Properties p = new Properties();
        p.load(new StringReader(propsText));
        return new PropertyTheme(p, false);
    }
}
package de.stevee;

import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import de.stevee.Logic.Energy.Energy;
import de.stevee.Logic.Scheduler.Scheduler;
import de.stevee.Windows.GameWindow;
import de.stevee.Logic.Controller;
import de.stevee.ui.Component.ProgressList;

import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final Scheduler scheduler = new Scheduler();

    public static void main(String[] args) throws Exception {
        DefaultTerminalFactory tf = new DefaultTerminalFactory();
        SwingTerminalFontConfiguration fontConfiguration = SwingTerminalFontConfiguration.getDefaultOfSize(24);
        tf.setTerminalEmulatorFontConfiguration(fontConfiguration);
        Screen screen = tf.createScreen();
        screen.startScreen();

        WindowBasedTextGUI gui = new MultiWindowTextGUI(new SameTextGUIThread.Factory(), screen);
        gui.setTheme(createTheme());
        Controller controller = new Controller();

        gui.addWindow(new GameWindow(controller));

        // Initialize scheduler tasks (runs in background)
        initializeScheduler();

        while (!controller.isEnd()) {
            boolean didWork = gui.getGUIThread().processEventsAndUpdate();

            if (!didWork) {
                Thread.sleep(10);
            }
        }

        scheduler.shutdown();
        screen.stopScreen();
    }

    private static void initializeScheduler() {
        // Energy tick - runs every 50ms (20 TPS)
        scheduler.executeWithFixedDelay(
                Energy::tick,
                0,
                50,
                TimeUnit.MILLISECONDS
        );

        scheduler.executeWithFixedDelay(
                ProgressList::update,
                0,
                50,
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
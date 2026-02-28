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
import de.stevee.Windows.panels.EnergyPanel;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Scheduler scheduler = new Scheduler();
    private static WindowBasedTextGUI gui;
    private static Screen screen;

    public static void main(String[] args) throws Exception {
        // Initialize terminal and GUI
        DefaultTerminalFactory tf = new DefaultTerminalFactory();
        SwingTerminalFontConfiguration fontConfiguration = SwingTerminalFontConfiguration.getDefaultOfSize(24);
        tf.setTerminalEmulatorFontConfiguration(fontConfiguration);
        screen = tf.createScreen();
        screen.startScreen();

        gui = new MultiWindowTextGUI(new SameTextGUIThread.Factory(), screen);
        gui.setTheme(createTheme());
        Controller controller = new Controller();

        gui.addWindow(new GameWindow(controller));

        // Start all systems
        initialize();

        // Graceful shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            try {
                screen.stopScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
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

    private static void initialize() {
        // Energy tick - runs every 50ms (20 TPS)
        scheduler.executeWithFixedDelay(
                Energy::tick,
                0,
                50,
                TimeUnit.MILLISECONDS
        );

        // GUI update - runs every 10ms on the GUI thread
        // IMPORTANT: Let Lanterna handle the GUI thread naturally
        scheduler.executeWithFixedDelay(
                () -> {
                    try {
                        // Direct call on the scheduler's thread
                        // Lanterna's MultiWindowTextGUI handles thread safety internally
                        gui.getGUIThread().processEventsAndUpdate();
                    } catch (IOException e) {
                        System.err.println("GUI update error: " + e.getMessage());
                    }
                },
                0,
                10,
                TimeUnit.MILLISECONDS
        );
    }
}
package de.stevee;

import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.graphics.Theme;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import de.stevee.Logic.Energy.Energy;
import de.stevee.Windows.GameWindow;
import de.stevee.Logic.Controller;
import de.stevee.Windows.panels.EnergyPanel;

import java.io.StringReader;
import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        DefaultTerminalFactory tf = new DefaultTerminalFactory();
        SwingTerminalFontConfiguration fontConfiguration = SwingTerminalFontConfiguration.getDefaultOfSize(24);
        tf.setTerminalEmulatorFontConfiguration(fontConfiguration);
        Screen screen = tf.createScreen();
        screen.startScreen();

        WindowBasedTextGUI gui = new MultiWindowTextGUI(new SameTextGUIThread.Factory(), screen);
        gui.setTheme(createTheme());
        Controller controller = new Controller();

        gui.addWindow(new GameWindow(controller));

        while (!controller.isEnd()) {
            // SameTextGUIThread lets you run your own loop; see processEventsAndUpdate usage pattern[cite:16]
            boolean didWork = gui.getGUIThread().processEventsAndUpdate();
            tick();
            if (!didWork) {
                Thread.sleep(10);
            }
        }

        screen.stopScreen();
    }

    private static Theme createTheme() throws Exception {
        // Lanterna's DefaultTheme is property-based; keys like foreground[SELECTED]/background[SELECTED] are used[cite:4]
        // TextColor parsing supports ANSI names like DEFAULT via TextColor.ANSI.valueOf(...) in fromString()[cite:78]
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

    private static void tick() {
        Energy.tick();
    }
}
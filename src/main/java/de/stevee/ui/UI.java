package de.stevee.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Items.Item;
import de.stevee.Windows.Section;
import de.stevee.Windows.panels.*;
import de.stevee.Logic.Controller;

import static de.stevee.Utils.Items.Items_List;

public class UI {
    private static UI INSTANCE = null;
    private final Controller controller;

    private final Panel root;
    private final Panel mainArea;
    private final Panel logHolder;

    private final ActionPanel actionPanel;
    private final FarmPanel farmPanel;
    private final CraftPanel craftPanel;
    private final InventoryPanel inventoryPanel;
    private final MachinePanel machinePanel;
    private final LogPanel logPanel;
    private final EnergyPanel energyPanel;

    private Section activeSection = Section.CRAFT;

    public UI(Controller controller) {
        INSTANCE = this;
        this.controller = controller;
        root = new Panel(new BorderLayout());

        actionPanel = new ActionPanel();
        actionPanel.setPreferredSize(new TerminalSize(18, 1));
        for (Section section : Section.values()) {
            actionPanel.addItem(section.title(), () -> controller.runSelected(section));
        }
        root.addComponent(actionPanel.withBorder(Borders.singleLine()), BorderLayout.Location.LEFT);


        this.mainArea = new Panel(new BorderLayout());
        root.addComponent(mainArea, BorderLayout.Location.CENTER);

        farmPanel = new FarmPanel("Farm");
        for (Item item : Items_List) {
            if (item.farmable) {
                farmPanel.getList().addItem(item.name, item::farm);
            }
        }

        inventoryPanel = new InventoryPanel("Inventory");

        machinePanel = new MachinePanel("Machines");

        energyPanel = new EnergyPanel();

        craftPanel = new CraftPanel();

        logPanel = new LogPanel(7);
        logHolder = new Panel(new BorderLayout());
        logHolder.addComponent(logPanel.withBorder(Borders.singleLine("Log")), BorderLayout.Location.CENTER);

        actionPanel.takeFocus();


    }

    public Panel getRoot() {
        return root;
    }

    public Section getActiveSection() {
        return activeSection;
    }

    public void setActiveSection(Section section) {
        this.activeSection = section;

        // Swap main content
        mainArea.removeAllComponents();
        switch (section) {
            case FARM -> mainArea.addComponent(farmPanel.getRoot(), BorderLayout.Location.CENTER);
            case INVENTORY -> mainArea.addComponent(inventoryPanel.getRoot(), BorderLayout.Location.CENTER);
            case CRAFT -> mainArea.addComponent(craftPanel.getRoot(), BorderLayout.Location.CENTER);
            case ENERGY -> mainArea.addComponent(energyPanel.getRoot(), BorderLayout.Location.CENTER);
            case MACHINES -> mainArea.addComponent(machinePanel.getRoot(), BorderLayout.Location.CENTER);
            case QUIT -> {} // handled in controller
        }

        // Show/hide log (hidden on Inventory)
        root.removeComponent(logHolder);
        if (section != Section.INVENTORY) {
            root.addComponent(logHolder, BorderLayout.Location.BOTTOM);
        }

        refreshAll();
        root.invalidate();
    }

    public void refreshAll() {
        inventoryPanel.refresh();
        machinePanel.refresh();
        energyPanel.refresh();
        craftPanel.refreshInventoryContext(); // optional: can show craftability later
    }


    public void logInfo(String msg) {
        logPanel.append(msg);
    }

    public void scrollLog(int deltaLines) {
        logPanel.scroll(deltaLines);
    }

    public void focusSidebar() {
        actionPanel.takeFocus();
    }

    public void focusMain() {
        switch (activeSection) {
            case FARM -> farmPanel.focusDefault();
            case INVENTORY -> inventoryPanel.focusDefault();
            case CRAFT -> craftPanel.focusDefault();
            case ENERGY -> energyPanel.focusDefault();
            case MACHINES -> machinePanel.focusDefault();
            default -> actionPanel.takeFocus();
        }
    }

    public boolean isCraftSearchFocused() {
        return craftPanel.isSearchFocused();
    }

    public void focusCraftResults() {
        craftPanel.focusResults();
    }

    public void toggleCraftFocus() {
        if (craftPanel.isSearchFocused()) {
            craftPanel.focusResults();
        } else {
            craftPanel.focusDefault();
        }
    }

    public static UI getINSTANCE() {
        return INSTANCE;
    }
}

package de.stevee.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import de.stevee.Logic.Items.Item;
import de.stevee.Windows.Section;
import de.stevee.Windows.panels.*;
import de.stevee.Logic.Controller;
import de.stevee.Windows.panels.basic.SearchPanel;

import java.io.IOException;

import static de.stevee.Utils.Items.Items_List;


public class UI {
    private static UI INSTANCE = null;
    private final Controller controller;

    private final Panel root;
    private final Panel mainArea;
    private final Panel logHolder;

    private final SidePanel sidePanel;
    private final FarmPanel farmPanel;
    private final CraftPanel craftPanel;
    private final InventoryPanel inventoryPanel;
    private final MachinePanel machinePanel;
    private final LogPanel logPanel;
    private final EnergyPanel energyPanel;
    private final ProcessPanel processPanel;

    private Section activeSection = Section.CRAFT;

    public UI(Controller controller) {
        INSTANCE = this;
        this.controller = controller;
        root = new Panel(new BorderLayout());

        sidePanel = new SidePanel();
        sidePanel.setPreferredSize(new TerminalSize(18, 1));
        for (Section section : Section.values()) {
            sidePanel.addItem(section.title(), () -> {
                try {
                    controller.runSelected(section);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        root.addComponent(sidePanel.withBorder(Borders.singleLine()), BorderLayout.Location.LEFT);


        this.mainArea = new Panel(new BorderLayout());
        root.addComponent(mainArea, BorderLayout.Location.CENTER);

        farmPanel = new FarmPanel("Farm");
        for (Item item : Items_List) {
            if (item.farmable) {
                farmPanel.getList().addItem(item.name, item::farm);
            }
        }

        inventoryPanel = new InventoryPanel();

        machinePanel = new MachinePanel("Machines");

        processPanel = new ProcessPanel("Processes");
        //processPanel.addProcess("test", 1000, () -> {});

        energyPanel = new EnergyPanel();

        craftPanel = new CraftPanel();

        logPanel = new LogPanel(4);
        logHolder = new Panel(new BorderLayout());
        logHolder.addComponent(logPanel.withBorder(Borders.singleLine("Log")), BorderLayout.Location.CENTER);

        sidePanel.takeFocus();
    }

    public ProcessPanel getProcessPanel() {
        return processPanel;
    }

    public Panel getRoot() {
        return root;
    }

    public Section getActiveSection() {
        return activeSection;
    }

    public void setActiveSection(Section section) throws IOException {
        this.activeSection = section;

        // Swap main content
        mainArea.removeAllComponents();
        switch (section) {
            case FARM -> mainArea.addComponent(farmPanel.getRoot(), BorderLayout.Location.CENTER);
            case INVENTORY -> mainArea.addComponent(inventoryPanel.getRoot(), BorderLayout.Location.CENTER);
            case CRAFT -> {
                mainArea.addComponent(craftPanel.getRoot(), BorderLayout.Location.CENTER);

            }
            case ENERGY -> mainArea.addComponent(energyPanel.getRoot(), BorderLayout.Location.CENTER);
            case MACHINES -> mainArea.addComponent(machinePanel.getRoot(), BorderLayout.Location.CENTER);
            case PROCESS -> mainArea.addComponent(processPanel.getRoot(), BorderLayout.Location.CENTER);
            case QUIT -> {}
        }

        root.removeComponent(logHolder);
        if (section != Section.INVENTORY) {
            root.addComponent(logHolder, BorderLayout.Location.BOTTOM);
        }

        if (section != Section.CRAFT) {
            InfoPanel.setVisibility(false);
        } else {
            InfoPanel.setVisibility(true);
        }

        refreshAll();
        root.invalidate();
    }

    public void refreshAll() {
        inventoryPanel.refresh();
        machinePanel.refresh();
        energyPanel.refresh();
        craftPanel.recalculateResult();
    }


    public void logInfo(String msg) {
        logPanel.append(msg);
    }

    public void scrollLog(int lines) {
        logPanel.scroll(lines);
    }

    public void focusSidebar() {
        sidePanel.takeFocus();
    }

    public void focusMain() {
        switch (activeSection) {
            case FARM -> farmPanel.focusDefault();
            case INVENTORY -> inventoryPanel.focusDefault();
            case PROCESS -> processPanel.focusDefault();
            case CRAFT -> craftPanel.focusDefault();
            case ENERGY -> energyPanel.focusDefault();
            case MACHINES -> machinePanel.focusDefault();
            default -> sidePanel.takeFocus();
        }
    }

    public SearchPanel<?> getSearchPanel() {
        switch (activeSection) {
            case INVENTORY -> {
                return inventoryPanel;
            }
            case CRAFT -> {
                return craftPanel;
            }
            default -> {
                return null;
            }
        }
    }

    public boolean isSearchFocused() {
        return craftPanel.isSearchFocused() || inventoryPanel.isSearchFocused();
    }

    public void focusResults() {
        getSearchPanel().focusResults();
    }

    public boolean isSearchGui() {
        return activeSection == Section.CRAFT || activeSection == Section.INVENTORY;
    }

    public void toggleSearchFocus() {
        if (isSearchGui()) {
            if (craftPanel.isSearchFocused()) {
                craftPanel.focusResults();
            } else {
                craftPanel.focusSearch();
            }
        }
    }

    public static UI getINSTANCE() {
        return INSTANCE;
    }


}

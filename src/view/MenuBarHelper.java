package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuBarHelper {
    public static JMenuBar getJMenuBar(ActionListener listener) {
        JMenuBar menuBar = new JMenuBar();

        addFileMenu(menuBar, listener);
        addViewMenu(menuBar, listener);

        return menuBar;
    }

    private static void addFileMenu(JMenuBar menuBar, ActionListener listener) {
        JMenu menu = new JMenu("File");

        addMenuItem(menu, "Open DataBase..", listener);
        addMenuItem(menu, "Extract to File..", listener);

        menuBar.add(menu);
    }

    private static void addViewMenu(JMenuBar menuBar, ActionListener listener) {
        JMenu menu = new JMenu("View");

        addCheckBoxMenuItem(menu, "Wrap Line", listener);
        addCheckBoxMenuItem(menu, "Dark Theme", listener);

        menuBar.add(menu);
    }

    private static void addMenuItem(JMenu menu, String title, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }

    private static void addCheckBoxMenuItem(JMenu menu, String title, ActionListener listener) {
        JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(title);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }
}

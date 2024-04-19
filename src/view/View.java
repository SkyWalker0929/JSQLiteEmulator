package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private JFrame frame = new JFrame();
    private Controller controller;

    private JNumberedTextArea commandArea = new JNumberedTextArea();
    private JNumberedTextArea resultArea = new JNumberedTextArea();

    public View(Controller controller) {
        this.controller = controller;
        initGUI();
    }

    private void initGUI() {
        initFrame();
        addTextAreas();

        frame.setJMenuBar(MenuBarHelper.getJMenuBar(controller.getMenuBarListener()));

        frame.pack();
        frame.setVisible(true);
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 750));
        frame.setLocationRelativeTo(null);
        frame.setTitle("SQLite Emulator");
        frame.setLayout(new GridLayout(1, 2));
    }

    private void addTextAreas() {
        addWestPanel();
        addEastPanel();
    }

    private void addWestPanel() {
        JPanel panelWest = new JPanel(new BorderLayout());

        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.LEFT);
        JPanel innerPanel = new JPanel(layout);
        innerPanel.add(new JLabel("Enter SQL request"));
        innerPanel.add(getExecuteButton());

        panelWest.add(innerPanel, BorderLayout.NORTH);
        panelWest.add(commandArea, BorderLayout.CENTER);

        frame.add(panelWest);
    }

    private void addEastPanel() {
        JPanel panelEast = new JPanel(new BorderLayout());
        panelEast.add(new JLabel("Result:"), BorderLayout.NORTH);
        panelEast.add(resultArea, BorderLayout.CENTER);

        frame.add(panelEast);
    }

    private JButton getExecuteButton() {
        JButton button = new JButton("Run");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.execute(commandArea.getText());
            }
        });
        return button;
    }

    public JNumberedTextArea getResultArea() {
        return resultArea;
    }

    public JNumberedTextArea getCommandArea() {
        return commandArea;
    }

    public JFrame getFrame() {
        return frame;
    }
}

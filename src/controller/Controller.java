package controller;

import database.DataBaseController;
import view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private View view;
    private DataBaseController DBController;
    private ActionListener menuBarListener;

    public Controller() {
        init();
    }

    private void init() {
        initJMenuBarListener();
    }

    private void initJMenuBarListener() {
        menuBarListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()) {
                    case "Open DataBase.." -> {}
                    case "Extract to File.." -> {}
                    case "Wrap Line" -> {
                        view.getResultArea().getCommandArea().setLineWrap(!view.getResultArea().getCommandArea().getLineWrap());
                        view.getCommandArea().getCommandArea().setLineWrap(!view.getCommandArea().getCommandArea().getLineWrap());
                        view.getCommandArea().updateLineNumbers();
                        view.getResultArea().updateLineNumbers();
                    }
                    case "Dark Theme" -> {
                        if(!((JCheckBoxMenuItem)view.getFrame().getJMenuBar().getMenu(1).getMenuComponent(1)).getState()) {
                            view.getCommandArea().getCommandArea().setBackground(Color.BLACK);
                            view.getCommandArea().getCommandArea().setForeground(Color.WHITE);
                            view.getCommandArea().getNumbersArea().setBackground(Color.BLACK);
                            view.getCommandArea().getNumbersArea().setForeground(Color.WHITE);

                            view.getResultArea().getCommandArea().setBackground(Color.BLACK);
                            view.getResultArea().getCommandArea().setForeground(Color.WHITE);
                            view.getResultArea().getNumbersArea().setBackground(Color.BLACK);
                            view.getResultArea().getNumbersArea().setForeground(Color.WHITE);
                        } else {
                            view.getCommandArea().getCommandArea().setBackground(Color.WHITE);
                            view.getCommandArea().getCommandArea().setForeground(Color.BLACK);
                            view.getCommandArea().getNumbersArea().setBackground(Color.WHITE);
                            view.getCommandArea().getNumbersArea().setForeground(Color.BLACK);

                            view.getResultArea().getCommandArea().setBackground(Color.WHITE);
                            view.getResultArea().getCommandArea().setForeground(Color.BLACK);
                            view.getResultArea().getNumbersArea().setBackground(Color.WHITE);
                            view.getResultArea().getNumbersArea().setForeground(Color.BLACK);
                        }
                    }
                }
            }
        };
    }

    private void initDB() {
        DBController = new DataBaseController(view.getResultArea().getCommandArea());
        DBController.connect();
    }

    public void execute(String request) {
        DBController.execute(request);
    }

    public ActionListener getMenuBarListener() {
        return menuBarListener;
    }

    public void setView(View view) {
        this.view = view;
        initDB();
    }
}

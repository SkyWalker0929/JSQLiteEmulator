package database;

import javax.swing.*;
import java.sql.*;

public class DataBaseController {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private JTextArea commandArea;

    public DataBaseController(JTextArea commandArea) {
        this.commandArea = commandArea;
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:TEST1.s3db");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Couldn't connect SQLite");
            throw new RuntimeException(e);
        }
    }

    public void execute(String request) {
        try {
            createStatement();
            statement.execute(request);

            resultSet = statement.getResultSet();
            if(resultSet != null)
                printResult();

        } catch (SQLException e) {
            System.out.println("Couldn't execute request");
        }
    }

    private void printResult() throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            print(metaData.getColumnName(i) + "\t");
        }
        println();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                print(resultSet.getString(i) + "\t");
            }
            println();
        }
        resultSet.close();
    }

    private void createStatement() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            println("Couldn't create a statement");
            throw new RuntimeException(e);
        }
    }

    private void print(String text) {
        commandArea.append(text);
    }

    private void println(String text) {
        commandArea.append(text + "\n");
    }

    private void println() {
        commandArea.append("\n");
    }
}

package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class JNumberedTextArea extends JPanel {
    private final JTextArea textArea = new JTextArea();
    private final JTextArea numbersArea = new JTextArea();
    private JScrollPane numbersAreaScrollPane;
    private JScrollPane textAreaScrollPane;

    public JNumberedTextArea() {
        setLayout(new BorderLayout());

        initCommandArea();
        initNumbersTextArea();
    }

    private void initCommandArea() {
        textAreaScrollPane = new JScrollPane(textArea);

        textAreaScrollPane.setHorizontalScrollBar(new JScrollBar(Adjustable.HORIZONTAL));
        textAreaScrollPane.setVerticalScrollBar(new JScrollBar());

        textAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textAreaScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        addDocumentListener();
        addScrollListener();
        addResizeListener();

        add(textAreaScrollPane, BorderLayout.CENTER);
    }

    private void addDocumentListener() {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineNumbers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineNumbers();
            }
        });
    }

    private void addScrollListener() {
        textAreaScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> numbersAreaScrollPane.getVerticalScrollBar().setValue(e.getValue()));
    }

    private void addResizeListener() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateLineNumbers();
            }
        });
    }

    private void initNumbersTextArea() {
        numbersAreaScrollPane = new JScrollPane(numbersArea);

        numbersAreaScrollPane.setWheelScrollingEnabled(false);
        numbersArea.setHighlighter(null);

        JScrollBar verticalScrollBar = new JScrollBar();
        numbersAreaScrollPane.setVerticalScrollBar(verticalScrollBar);
        numbersAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        numbersArea.setBackground(Color.WHITE);
        numbersArea.setCaretColor(numbersArea.getBackground());
        numbersArea.setEditable(false);
        numbersArea.setForeground(Color.BLACK);
        numbersArea.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));

        add(numbersAreaScrollPane, BorderLayout.WEST);
    }

    public void updateLineNumbers() {
        StringBuilder builder = new StringBuilder();
        if(textArea.getLineWrap()) {
            String[] lines = textArea.getText().splitWithDelimiters("\n", -1);
            Graphics graphics = textAreaScrollPane.getGraphics();
            FontMetrics metrics = graphics.getFontMetrics();
            int i = 1;
            for (String line: lines) {
                if(!line.equals("\n")) {
                    builder.append(i).append("\n");
                    i++;
                } else
                    continue;
                int lineSize = metrics.stringWidth(line);
                int lineCount = lineSize / textAreaScrollPane.getWidth();
                System.out.println(textAreaScrollPane.getWidth());
                if(lineCount != 0)
                    builder.append("\n".repeat(Math.max(0, lineCount)));
            }
        } else
            for(int i = 0; i < textArea.getLineCount(); i++)
                builder.append(i + 1).append("\n");

        numbersArea.setText(builder.toString());

        int numberCount = (textArea.getLineCount() + "").length();
        numbersAreaScrollPane.setSize(new Dimension(numberCount * numbersArea.getFont().getSize() + 2, 1));

        numbersAreaScrollPane.revalidate();
        numbersAreaScrollPane.repaint();
        revalidate();
        repaint();
    }

    public JTextArea getNumbersArea() {
        return numbersArea;
    }

    public JTextArea getCommandArea() {
        return textArea;
    }

    public String getText() {
        return textArea.getText();
    }
}

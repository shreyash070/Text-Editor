import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;
import javax.swing.JColorChooser;
import java.awt.Color;

public class Text implements KeyListener {

    private Stack<String> uStack;
    private Stack<String> rStack;
    private String curr;
    private JTextArea textArea;

    public Text() {

        JFrame frame = new JFrame("TextEditor 22BCE173");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setVisible(true);

        JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                undo();
            }
        });

        JButton redo = new JButton("Redo");
        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                redo();
            }
        });

        JMenuBar menuBar = new JMenuBar();
        JMenu fontMenu = new JMenu("Font");
        JMenu fontSizeMenu = new JMenu("Font Size");

        JMenuItem size12 = new JMenuItem("12");
        size12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(12);
            }
        });

        JMenuItem size16 = new JMenuItem("16");
        size16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(16);
            }
        });
        JMenuItem size18 = new JMenuItem("18");
        size18.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(18);
            }
        });

        JMenuItem size20 = new JMenuItem("20");
        size20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(20);
            }
        });
        JMenuItem size22 = new JMenuItem("22");
        size22.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(22);
            }
        });
        JMenuItem size24 = new JMenuItem("24");
        size24.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(24);
            }
        });
        JMenuItem size26 = new JMenuItem("26");
        size26.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(26);
            }
        });
        JMenuItem size28 = new JMenuItem("28");
        size28.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(28);
            }
        });
        JMenuItem size30 = new JMenuItem("30");
        size30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                size(30);
            }
        });

        // FIND
        JMenuItem search = new JMenuItem("Find");
        search.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String s2 = JOptionPane.showInputDialog(frame, "Enter word:");
                if (s2 != null) {
                    search(s2);
                }
            }
        });

        JButton color = new JButton("Color");
        color.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Col();
            }
        });

        JMenu fontFamilySubMenu = new JMenu("Font Family");
        String[] fontFamilies = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String fontFamily : fontFamilies) {
            JMenuItem fontFamilyItem = new JMenuItem(fontFamily);
            fontFamilyItem.addActionListener(e -> {
                textArea.setFont(new Font(fontFamily, textArea.getFont().getStyle(), textArea.getFont().getSize()));
            });
            fontFamilySubMenu.add(fontFamilyItem);
        }

        fontSizeMenu.add(size12);
        fontSizeMenu.add(size16);
        fontSizeMenu.add(size18);
        fontSizeMenu.add(size20);
        fontSizeMenu.add(size22);
        fontSizeMenu.add(size24);
        fontSizeMenu.add(size26);
        fontSizeMenu.add(size28);
        fontSizeMenu.add(size30);
        fontMenu.add(fontFamilySubMenu);

        menuBar.add(undo);
        menuBar.add(redo);
        menuBar.add(fontMenu);
        menuBar.add(fontSizeMenu);
        menuBar.add(search);
        frame.setJMenuBar(menuBar);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(color);
        buttonPanel.add(undo);
        buttonPanel.add(redo);
        // buttonPanel.add(Find);
        frame.add(buttonPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.addKeyListener(this);
        // frame.removeKeyListener(this);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        uStack = new Stack<>();
        rStack = new Stack<>();
        curr = "";
        uStack.push(curr);
    }

    public void type(String input) {
        uStack.push(curr);
        curr = input;
        rStack.clear();
        textArea.setText(curr);
    }

    public void undo() {
        if (uStack.size() > 1) {
            rStack.push(uStack.pop());
            curr = uStack.peek();
            updateTextArea();
        }
    }

    public void redo() {
        if (!rStack.isEmpty()) {
            uStack.push(rStack.pop());
            curr = uStack.peek();
            updateTextArea();
        }
    }

    public void font(String fontName) {
        Font currentFont = textArea.getFont();
        Font newFont = new Font(fontName, currentFont.getStyle(), currentFont.getSize());
        textArea.setFont(newFont);
    }

    public void size(int fontSize) {
        Font currentFont = textArea.getFont();
        Font newFont = new Font(currentFont.getName(), currentFont.getStyle(), fontSize);
        textArea.setFont(newFont);
    }

    public void keyTyped(KeyEvent e) {
        char inp = e.getKeyChar();

        if (Character.isLetterOrDigit(inp) || Character.isWhitespace(inp)) {
            int caretPosition = textArea.getCaretPosition();
            String text = textArea.getText();
            String curr = text.substring(0, caretPosition);
            textArea.setText(curr);
            textArea.setCaretPosition(caretPosition);
            uStack.push(curr);
            rStack.clear();
        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    private void search(String s2) {
        String text = textArea.getText();
        int index = text.indexOf(s2);

        if (index >= 0) {
            textArea.setSelectionStart(index);
            textArea.setSelectionEnd(index + s2.length());
        } else {
            JOptionPane.showMessageDialog(textArea, "Word not found: " + s2, "Find",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void Col() {
        Color selectedColor = JColorChooser.showDialog(
                textArea,
                "Choose Text Color",
                textArea.getForeground());

        if (selectedColor != null) {
            textArea.setForeground(selectedColor);
        }
    }

    private void updateTextArea() {
        textArea.setText(curr);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Text newframe = new Text();
            }
        });
    }
}

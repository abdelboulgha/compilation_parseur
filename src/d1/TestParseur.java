package d1;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class TestParseur {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TestParseur::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        ArrayList<String> valide = new ArrayList<>();
        ArrayList<String> noValide = new ArrayList<>();
        JFrame frame = new JFrame("Analyseur de Validité Syntaxique");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Analyseur de Validité Syntaxique");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(72, 61, 139));
        mainPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Saisissez des phrases ci-dessous et séparez-les par un tiret (-).");
        subtitleLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        mainPanel.add(subtitleLabel);

        JTextArea textArea = new JTextArea(7, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

                    g2.setColor(Color.GRAY);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                }
                super.paintComponent(g);
            }
        };
        textArea.setFont(new Font("Poppins", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
            }
        };
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setMaximumSize(new Dimension(600, 200));
        scrollPane.setPreferredSize(new Dimension(600, 50));

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.X_AXIS));
        textAreaPanel.add(Box.createHorizontalGlue());
        textAreaPanel.add(scrollPane);
        textAreaPanel.add(Box.createHorizontalGlue());
        textAreaPanel.setBackground(new Color(240, 248, 255));
        mainPanel.add(textAreaPanel);

        JButton selectButton = new JButton("vérifier les phrases") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(new Color(123, 104, 238));
                } else {
                    g.setColor(getBackground());
                }
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getForeground());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
            }

            @Override
            public boolean contains(int x, int y) {
                if (shape == null || !shape.getBounds().equals(getBounds())) {
                    shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 50, 50);
                }
                return shape.contains(x, y);
            }

            private Shape shape;
        };

        selectButton.setFont(new Font("Poppins", Font.PLAIN, 16));
        selectButton.setBackground(new Color(72, 61, 139));
        selectButton.setForeground(Color.WHITE);
        selectButton.setFocusPainted(false);
        selectButton.setContentAreaFilled(false);
        selectButton.setBorderPainted(false);
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.setPreferredSize(new Dimension(150, 50));
        selectButton.setMaximumSize(new Dimension(400, 50));
        selectButton.setMinimumSize(new Dimension(300, 50));

        selectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                selectButton.setBackground(new Color(123, 104, 238));
                selectButton.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                selectButton.setBackground(new Color(72, 61, 139));
                selectButton.repaint();
            }
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(selectButton);

        JLabel resultLabel = new JLabel();
        resultLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setForeground(new Color(72, 61, 139));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(resultLabel);

        selectButton.addActionListener(e -> {
            String input = textArea.getText();
            if (input.isEmpty()) {

                JLabel messageLabel = new JLabel("s'il vous plaît entrer des phrases!");
                messageLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
                messageLabel.setForeground(new Color(72, 61, 139));

                JOptionPane.showMessageDialog(
                        frame,
                        messageLabel,
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                String[] choices = input.split("-");

                for (String test : choices) {
                    TokenManager tm = new TokenManager(test);
                    Parseur parseur = new Parseur(tm);

                    try {
                        parseur.parse();
                        System.out.println("Entrée : \"" + test + "\" => valide");
                        valide.add(test);
                    } catch (RuntimeException ex) {
                        System.out.println("Entrée : \"" + test + "\" => invalide (" + ex.getMessage() + ")");
                        noValide.add(test + " : " + ex.getMessage());
                    }
                }

                JPanel dialogPanel = new JPanel(new BorderLayout(10, 10));
                dialogPanel.setBackground(new Color(240, 248, 255));
                dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JLabel labelCenter = new JLabel("Resultat des phrases", SwingConstants.CENTER);
                labelCenter.setFont(new Font("Poppins", Font.BOLD, 20));
                labelCenter.setForeground(new Color(72, 61, 139));
                labelCenter.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
                dialogPanel.add(labelCenter, BorderLayout.NORTH);

                JPanel textAreasPanel = new JPanel(new GridLayout(1, 2, 15, 0));
                textAreasPanel.setBackground(new Color(240, 248, 255));

                Font textFont = new Font("Poppins", Font.PLAIN, 14);
                Dimension textAreaSize = new Dimension(250, 200);

                JPanel leftPanel = createStyledTextPanel(
                        "Phrases valides",
                        valide,
                        textFont,
                        textAreaSize,
                        new Color(240, 248, 255),
                        new Color(72, 61, 139));

                JPanel rightPanel = createStyledTextPanel(
                        "Phrases invalides",
                        noValide,
                        textFont,
                        textAreaSize,
                        new Color(240, 248, 255),
                        new Color(72, 61, 139));

                textAreasPanel.add(leftPanel);
                textAreasPanel.add(rightPanel);
                dialogPanel.add(textAreasPanel, BorderLayout.CENTER);

                JDialog dialog = new JDialog(frame, "Parseur", true);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setContentPane(dialogPanel);
                dialog.setSize(600, 400);
                dialog.setLocationRelativeTo(frame);
                dialog.setResizable(false);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        valide.clear();
                        noValide.clear();
                        System.out.println("The lists have been cleared on dialog close.");
                    }
                });
                dialog.setVisible(true);
            }
        });

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createStyledTextPanel(
            String title,
            ArrayList<String> items,
            Font font,
            Dimension size,
            Color backgroundColor,
            Color foregroundColor) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(backgroundColor);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        titleLabel.setForeground(foregroundColor);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);

        StringBuilder textBuilder = new StringBuilder();
        for (String item : items) {
            textBuilder.append(item.trim())
                    .append(title.contains("invalides") ? "❌" : "✅")
                    .append("\n");
        }

        JTextArea textArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                }
                super.paintComponent(g);
            }
        };

        textArea.setText(textBuilder.toString());
        textArea.setFont(font);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBackground(new Color(255, 255, 255));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(size);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(72, 61, 139), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        scrollPane.setBackground(new Color(255, 255, 255));
        scrollPane.getViewport().setBackground(new Color(255, 255, 255));

        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}

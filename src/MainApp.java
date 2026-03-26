import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class MainApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookFileFrame());
    }
}

class BookFileFrame extends JFrame {

    // =========================
    // GUI Components
    // =========================
    private JTextField titleField, authorField, priceField, bookIdField;
    private JTextArea descField;
    private JRadioButton availableRadio, notAvailableRadio;

    public BookFileFrame() {
        setTitle("Book File");
        setSize(600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel (Centered)
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(244, 231, 215));
        setLayout(new BorderLayout());

        add(createHeader(), BorderLayout.NORTH); //  HEADER 
        add(mainPanel, BorderLayout.CENTER); //  FORM

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        formPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    
        mainPanel.add(formPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // =========================
        // Form Fields
        // =========================

        titleField = new JTextField(18);
        authorField = new JTextField(18);
        priceField = new JTextField(18);
        bookIdField = new JTextField(18);

        titleField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        authorField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        priceField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        bookIdField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        addField(formPanel, gbc, 0, "Title:", titleField);
        addField(formPanel, gbc, 1, "Author:", authorField);

        // Description field
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(descLabel, gbc);

        descField = new JTextArea(6, 18);
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        descField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(descField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weighty = 0.3;
        formPanel.add(scrollPane, gbc);

        addField(formPanel, gbc, 3, "Price:", priceField);
        addField(formPanel, gbc, 4, "Book ID:", bookIdField);

        // =========================
        // Radio Buttons
        // =========================
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weighty = 0;
        JLabel availLabel = new JLabel("Availability:");
        availLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        formPanel.add(availLabel, gbc);

        gbc.gridx = 1;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setBackground(Color.WHITE);

        availableRadio = new JRadioButton("Available");
        notAvailableRadio = new JRadioButton("Not Available");
        
        availableRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        notAvailableRadio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        availableRadio.setBackground(Color.WHITE);
        notAvailableRadio.setBackground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();
        group.add(availableRadio);
        group.add(notAvailableRadio);

        radioPanel.add(availableRadio);
        radioPanel.add(notAvailableRadio);

        formPanel.add(radioPanel, gbc);

        // =========================
        // Buttons
        // =========================
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);
                
        JButton saveBtn = new RoundedButton("Save");
        JButton resetBtn = new RoundedButton("Reset");
        JButton exitBtn = new RoundedButton("Exit");

        // Button font size
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        resetBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // Button preferred size
        saveBtn.setPreferredSize(new Dimension(100, 45));
        resetBtn.setPreferredSize(new Dimension(100, 45));
        exitBtn.setPreferredSize(new Dimension(100, 45));

        // Button colors 
        saveBtn.setBackground(new Color(144, 238, 144));
        resetBtn.setBackground(new Color(255, 239, 150));
        exitBtn.setBackground(new Color(255, 182, 193));

        buttonPanel.add(saveBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(exitBtn);

        formPanel.add(buttonPanel, gbc);

        // =========================
        // Button Actions
        // =========================

        // SAVE
        saveBtn.addActionListener(e -> saveData());
        saveBtn.setFocusPainted(false);
        saveBtn.setBorderPainted(false);

        // RESET
        resetBtn.addActionListener(e -> resetFields());
        resetBtn.setFocusPainted(false);
        resetBtn.setBorderPainted(false);

        // EXIT
        exitBtn.addActionListener(e -> System.exit(0));
        exitBtn.setFocusPainted(false);
        exitBtn.setBorderPainted(false);

        setVisible(true);
    }
    
    class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}
            
        // =========================
        // Header (Logo + Title Centered)
        // =========================
        private JPanel createHeader() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(183, 156, 126));
                

            ImageIcon logo = new ImageIcon("logo.png");
            Image scaled = logo.getImage().getScaledInstance(220, 60, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaled));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel title = new JLabel("LRC Inventory System");
            title.setFont(new Font("SansSerif", Font.BOLD, 20));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            panel.add(Box.createVerticalStrut(10));
            panel.add(logoLabel);
            panel.add(Box.createVerticalStrut(5));
            panel.add(title);
            panel.add(Box.createVerticalStrut(10));

            return panel;
        }
    

    // Helper method for fields
    private void addField(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weighty = 0;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // =========================
    // Database Connection
    // =========================
    private void saveData() {
        String title = titleField.getText();
        String author = authorField.getText();
        String desc = descField.getText();
        String priceText = priceField.getText();
        String bookIdText = bookIdField.getText();

        String availability = availableRadio.isSelected() ? "Available"
                : notAvailableRadio.isSelected() ? "Not Available" : "";

        if (title.isEmpty() || author.isEmpty() || priceText.isEmpty() || bookIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "HEY! Fill all required fields!");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int bookId = Integer.parseInt(bookIdText);

            String url = "jdbc:postgresql://localhost:5432/Book_db";
            String user = "postgres";
            String password = "010825";

            Connection conn = DriverManager.getConnection(url, user, password);

            String sql = "INSERT INTO book_file (title, author, description, price, book_id, avail_notavail) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, title);
            pst.setString(2, author);
            pst.setString(3, desc);
            pst.setDouble(4, price);
            pst.setInt(5, bookId);
            pst.setString(6, availability);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Saved Successfully!");
            conn.close();

            resetFields();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be number & Book ID must be integer!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // =========================
    // Reset Function
    // =========================
    private void resetFields() {
        titleField.setText("");
        authorField.setText("");
        descField.setText("");
        priceField.setText("");
        bookIdField.setText("");
        availableRadio.setSelected(false);
        notAvailableRadio.setSelected(false);
    }
}
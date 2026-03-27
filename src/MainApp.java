import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
        JButton viewRecordsBtn = new RoundedButton("View Records");
        JButton exitBtn = new RoundedButton("Exit");

        // Button font size
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        resetBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        viewRecordsBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        // Button preferred size
        saveBtn.setPreferredSize(new Dimension(110, 45));
        resetBtn.setPreferredSize(new Dimension(110, 45));
        viewRecordsBtn.setPreferredSize(new Dimension(130, 45));
        exitBtn.setPreferredSize(new Dimension(110, 45));

        // Button colors 
        saveBtn.setBackground(new Color(144, 238, 144));
        resetBtn.setBackground(new Color(255, 239, 150));
        viewRecordsBtn.setBackground(new Color(173, 216, 230));
        exitBtn.setBackground(new Color(255, 182, 193));

        buttonPanel.add(saveBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(viewRecordsBtn);
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

        // VIEW RECORDS
        viewRecordsBtn.addActionListener(e -> openViewRecordsWindow());
        viewRecordsBtn.setFocusPainted(false);
        viewRecordsBtn.setBorderPainted(false);

        // EXIT
        exitBtn.addActionListener(e -> System.exit(0));
        exitBtn.setFocusPainted(false);
        exitBtn.setBorderPainted(false);

        setVisible(true);
    }
    
    // =========================
    // View Records Window
    // =========================
    private void openViewRecordsWindow() {
        JFrame recordsFrame = new JFrame("View Records");
        recordsFrame.setSize(1100, 600);
        recordsFrame.setLocationRelativeTo(this);
        recordsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(244, 231, 215));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(183, 156, 126));
        JLabel headerLabel = new JLabel("Book Records");
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerPanel.add(headerLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JLabel searchLabel = new JLabel("Search (Title/Author):");
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        
        JTextField searchField = new JTextField(25);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton searchBtn = new RoundedButton("Search");
        searchBtn.setPreferredSize(new Dimension(100, 35));
        searchBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        searchBtn.setBackground(new Color(100, 200, 255));
        searchBtn.setFocusPainted(false);
        searchBtn.setBorderPainted(false);

        JButton resetSearchBtn = new RoundedButton("Reset");
        resetSearchBtn.setPreferredSize(new Dimension(100, 35));
        resetSearchBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        resetSearchBtn.setBackground(new Color(255, 239, 150));
        resetSearchBtn.setFocusPainted(false);
        resetSearchBtn.setBorderPainted(false);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(resetSearchBtn);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] columns = {"Title", "Author", "Description", "Price", "Book ID", "Availability"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable recordsTable = new JTable(tableModel);
        recordsTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        recordsTable.setRowHeight(30);
        recordsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        recordsTable.getTableHeader().setBackground(new Color(183, 156, 126));
        recordsTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(recordsTable);
        scrollPane.setBackground(Color.WHITE);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Button Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(Color.WHITE);

        JButton addBtn = new RoundedButton("Add");
        addBtn.setPreferredSize(new Dimension(100, 40));
        addBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        addBtn.setBackground(new Color(144, 238, 144));
        addBtn.setFocusPainted(false);
        addBtn.setBorderPainted(false);

        JButton deleteBtn = new RoundedButton("Delete");
        deleteBtn.setPreferredSize(new Dimension(100, 40));
        deleteBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        deleteBtn.setBackground(new Color(255, 182, 193));
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorderPainted(false);

        bottomPanel.add(addBtn);
        bottomPanel.add(deleteBtn);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        recordsFrame.add(mainPanel);

        // Load initial data
        loadAllRecords(tableModel);

        // Search Button Action
        searchBtn.addActionListener(e -> {
            String searchTerm = searchField.getText().trim();
            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(recordsFrame, "Please enter a search term!");
                return;
            }
            searchRecords(tableModel, searchTerm);
        });

        // Reset Search Button Action
        resetSearchBtn.addActionListener(e -> {
            searchField.setText("");
            loadAllRecords(tableModel);
        });

        // Delete Button Action
        deleteBtn.addActionListener(e -> {
            int selectedRow = recordsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(recordsFrame, "Please select a record to delete!");
                return;
            }

            int bookId = (Integer) tableModel.getValueAt(selectedRow, 4);
            int confirm = JOptionPane.showConfirmDialog(recordsFrame,
                    "Are you sure you want to delete this record?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                deleteRecord(bookId, tableModel);
            }
        });

        // Add Button Action
        addBtn.addActionListener(e -> openAddRecordDialog(recordsFrame, tableModel));

        recordsFrame.setVisible(true);
    }

    // =========================
    // Load All Records
    // =========================
    private void loadAllRecords(DefaultTableModel tableModel) {
        tableModel.setRowCount(0);

        try {
            Connection conn = getDBConnection();
            String sql = "SELECT title, author, description, price, book_id, avail_notavail FROM book_file ORDER BY book_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Object[] row = {
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("book_id"),
                        rs.getString("avail_notavail")
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading records: " + ex.getMessage());
        }
    }

    // =========================
    // Search Records
    // =========================
    private void searchRecords(DefaultTableModel tableModel, String searchTerm) {
        tableModel.setRowCount(0);

        try {
            Connection conn = getDBConnection();
            String sql = "SELECT title, author, description, price, book_id, avail_notavail FROM book_file " +
                    "WHERE title ILIKE ? OR author ILIKE ? ORDER BY book_id";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + searchTerm + "%");
            pst.setString(2, "%" + searchTerm + "%");

            ResultSet rs = pst.executeQuery();

            // Counter to track if any results found
            int rowCount = 0;

            while (rs.next()) {
                Object[] row = {
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("book_id"),
                        rs.getString("avail_notavail")
                };
                tableModel.addRow(row);
                rowCount++;
            }

            // Show message if no books found
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, "No records found.");
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error searching records: " + ex.getMessage());
        }
    }

    // =========================
    // Delete Record
    // =========================
    private void deleteRecord(int bookId, DefaultTableModel tableModel) {
        try {
            Connection conn = getDBConnection();
            String sql = "DELETE FROM book_file WHERE book_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, bookId);

            int result = pst.executeUpdate();
            pst.close();
            conn.close();

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Record deleted successfully!");
                loadAllRecords(tableModel);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error deleting record: " + ex.getMessage());
        }
    }

    // =========================
    // Add Record Dialog
    // =========================
    private void openAddRecordDialog(JFrame parent, DefaultTableModel tableModel) {
        // Create a modal dialog for adding new book records
        JDialog addDialog = new JDialog(parent, "Add New Book Record", true);
        addDialog.setSize(450, 500);
        addDialog.setLocationRelativeTo(parent);

        // Main panel with padding and white background
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        dialogPanel.setBackground(Color.WHITE);
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Layout constraints for components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // === TITLE FIELD ===
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dialogPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        JTextField addTitleField = new JTextField(18);
        addTitleField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dialogPanel.add(addTitleField, gbc);

        // === AUTHOR FIELD ===
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dialogPanel.add(authorLabel, gbc);

        gbc.gridx = 1;
        JTextField addAuthorField = new JTextField(18);
        addAuthorField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dialogPanel.add(addAuthorField, gbc);

        // === DESCRIPTION FIELD ===
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dialogPanel.add(descLabel, gbc);

        gbc.gridx = 1;
        gbc.weighty = 0.3;
        // Text area with scroll pane for multi-line description
        JTextArea addDescField = new JTextArea(4, 18);
        addDescField.setLineWrap(true);
        addDescField.setWrapStyleWord(true);
        addDescField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JScrollPane descScrollPane = new JScrollPane(addDescField);
        dialogPanel.add(descScrollPane, gbc);

        // === PRICE FIELD ===
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weighty = 0;
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dialogPanel.add(priceLabel, gbc);

        gbc.gridx = 1;
        JTextField addPriceField = new JTextField(18);
        addPriceField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dialogPanel.add(addPriceField, gbc);

        // === BOOK ID FIELD ===
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel bookIdLabel = new JLabel("Book ID:");
        bookIdLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dialogPanel.add(bookIdLabel, gbc);

        gbc.gridx = 1;
        JTextField addBookIdField = new JTextField(18);
        addBookIdField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        dialogPanel.add(addBookIdField, gbc);

        // === AVAILABILITY RADIO BUTTONS ===
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel availLabel = new JLabel("Availability:");
        availLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dialogPanel.add(availLabel, gbc);

        gbc.gridx = 1;
        JPanel availPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        availPanel.setBackground(Color.WHITE);
        JRadioButton addAvailRadio = new JRadioButton("Available");
        JRadioButton addNotAvailRadio = new JRadioButton("Not Available");
        addAvailRadio.setBackground(Color.WHITE);
        addNotAvailRadio.setBackground(Color.WHITE);
        addAvailRadio.setFont(new Font("SansSerif", Font.PLAIN, 12));
        addNotAvailRadio.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // Group radio buttons so only one can be selected
        ButtonGroup availGroup = new ButtonGroup();
        availGroup.add(addAvailRadio);
        availGroup.add(addNotAvailRadio);

        availPanel.add(addAvailRadio);
        availPanel.add(addNotAvailRadio);
        dialogPanel.add(availPanel, gbc);

        // === DIALOG BUTTONS (Save & Cancel) ===
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        btnPanel.setBackground(Color.WHITE);

        // Save button - green color
        JButton saveAddBtn = new RoundedButton("Save");
        saveAddBtn.setPreferredSize(new Dimension(100, 40));
        saveAddBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        saveAddBtn.setBackground(new Color(144, 238, 144));
        saveAddBtn.setFocusPainted(false);
        saveAddBtn.setBorderPainted(false);

        // Cancel button - red color
        JButton cancelAddBtn = new RoundedButton("Cancel");
        cancelAddBtn.setPreferredSize(new Dimension(100, 40));
        cancelAddBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        cancelAddBtn.setBackground(new Color(255, 182, 193));
        cancelAddBtn.setFocusPainted(false);
        cancelAddBtn.setBorderPainted(false);

        btnPanel.add(saveAddBtn);
        btnPanel.add(cancelAddBtn);
        dialogPanel.add(btnPanel, gbc);

        // === SAVE BUTTON ACTION ===
        saveAddBtn.addActionListener(e -> {
            // Get all field values
            String title = addTitleField.getText().trim();
            String author = addAuthorField.getText().trim();
            String desc = addDescField.getText().trim();
            String priceStr = addPriceField.getText().trim();
            String bookIdStr = addBookIdField.getText().trim();
            String availability = addAvailRadio.isSelected() ? "Available" :
                    addNotAvailRadio.isSelected() ? "Not Available" : "";

            // Validate all fields are filled
            if (title.isEmpty() || author.isEmpty() || priceStr.isEmpty() || bookIdStr.isEmpty() || availability.isEmpty()) {
                JOptionPane.showMessageDialog(addDialog, "Please fill all fields!");
                return;
            }

            try {
                // Convert price to double and book ID to integer
                double price = Double.parseDouble(priceStr);
                int bookId = Integer.parseInt(bookIdStr);

                // Get database connection
                Connection conn = getDBConnection();
                
                // SQL insert query
                String sql = "INSERT INTO book_file (title, author, description, price, book_id, avail_notavail) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = conn.prepareStatement(sql);

                // Set parameters for the SQL query
                pst.setString(1, title);
                pst.setString(2, author);
                pst.setString(3, desc);
                pst.setDouble(4, price);
                pst.setInt(5, bookId);
                pst.setString(6, availability);

                // Execute the insert query
                pst.executeUpdate();
                pst.close();
                conn.close();

                // Show success message
                JOptionPane.showMessageDialog(addDialog, "Record added successfully!");
                
                // Refresh the table to show new record
                loadAllRecords(tableModel);
                
                // Close the dialog
                addDialog.dispose();

            } catch (NumberFormatException ex) {
                // Show error if price is not a number or book ID is not an integer
                JOptionPane.showMessageDialog(addDialog, "Price must be a number and Book ID must be an integer!");
            } catch (SQLException ex) {
                // Show database error
                JOptionPane.showMessageDialog(addDialog, "Database Error: " + ex.getMessage());
            }
        });

        // === CANCEL BUTTON ACTION ===
        cancelAddBtn.addActionListener(e -> addDialog.dispose());

        // Add panel to dialog and display
        addDialog.add(dialogPanel);
        addDialog.setVisible(true);
    }

    // =========================
    // Database Helper
    // =========================
    private Connection getDBConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/Book_db";
        String user = "postgres";
        String password = "010825";
        return DriverManager.getConnection(url, user, password);
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

            Connection conn = getDBConnection();

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
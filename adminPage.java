package CourseWork;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.sql.*;
import java.util.List;

public class adminPage extends JFrame {

    private JPanel contentPane;
    private JTable questionTable;
    private DefaultTableModel tableModel;

    private static final String URL = "jdbc:mysql://localhost:3306/QuizDB"; // Database URL
    private static final String USER = "root"; // MySQL Username
    private static final String PASSWORD = ""; // MySQL Password

    public adminPage() {
        initialize();
    }

    // Initialize the admin page frame
    private void initialize() {
        setTitle("Admin Control Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600); // Increasing window size to fit more content
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        // Title Label
        JLabel lblTitle = new JLabel("Admin Control Panel");
        lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 25));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblTitle, BorderLayout.NORTH);

        // Table to display all questions
        tableModel = new DefaultTableModel(new Object[]{"ID", "Category", "Question", "Option 1", "Option 2", "Option 3", "Option 4", "Correct Option", "Created At", "Actions"}, 0);
        questionTable = new JTable(tableModel);
        questionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Disable auto resizing

        // Set specific column widths (increase question column width)
        questionTable.getColumnModel().getColumn(2).setPreferredWidth(400); // Increase width of the Question column

        // Make the table scrollable
        JScrollPane scrollPane = new JScrollPane(questionTable);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Load the questions from the database
        loadQuestionsFromDatabase();

        // Action Buttons (Add, Edit, Copy, Delete)
        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Add button to add a new question
        JButton btnAdd = new JButton("Add Question");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddQuestionDialog(); // Opens the dialog to add a question
            }
        });
        buttonPanel.add(btnAdd);
    }

    // Method to load questions from the database into the table
    private void loadQuestionsFromDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM quiz_questions")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String category = rs.getString("category");
                String question = rs.getString("question");
                String option1 = rs.getString("option1");
                String option2 = rs.getString("option2");
                String option3 = rs.getString("option3");
                String option4 = rs.getString("option4");
                int correctOption = rs.getInt("correct_option");
                String createdAt = rs.getString("created_at");

                // Add a row in the table with the question data
                tableModel.addRow(new Object[]{id, category, question, option1, option2, option3, option4, correctOption, createdAt, createActionButtons(id)});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage());
        }
    }

    // Method to create the action buttons for Edit, Copy, Delete for each row
    private JPanel createActionButtons(int questionId) {
        JPanel panel = new JPanel();

        // Edit button
        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openEditQuestionDialog(questionId); // Open the edit dialog
            }
        });
        panel.add(btnEdit);

        // Copy button
        JButton btnCopy = new JButton("Copy");
        btnCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyQuestion(questionId); // Copy the selected question
            }
        });
        panel.add(btnCopy);

        // Delete button
        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteQuestion(questionId); // Delete the selected question
            }
        });
        panel.add(btnDelete);

        return panel;
    }

    // Method to open a dialog to add a new question
    private void openAddQuestionDialog() {
        JTextField questionField = new JTextField(20);
        JTextField option1Field = new JTextField(20);
        JTextField option2Field = new JTextField(20);
        JTextField option3Field = new JTextField(20);
        JTextField option4Field = new JTextField(20);
        JTextField correctOptionField = new JTextField(20);
        JTextField categoryField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Question:"));
        panel.add(questionField);
        panel.add(new JLabel("Option 1:"));
        panel.add(option1Field);
        panel.add(new JLabel("Option 2:"));
        panel.add(option2Field);
        panel.add(new JLabel("Option 3:"));
        panel.add(option3Field);
        panel.add(new JLabel("Option 4:"));
        panel.add(option4Field);
        panel.add(new JLabel("Correct Option (1-4):"));
        panel.add(correctOptionField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Question", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            addNewQuestion(categoryField.getText(), questionField.getText(), option1Field.getText(), option2Field.getText(), option3Field.getText(), option4Field.getText(), Integer.parseInt(correctOptionField.getText()));
        }
    }

    // Method to add a new question to the database
    private void addNewQuestion(String category, String question, String option1, String option2, String option3, String option4, int correctOption) {
        String query = "INSERT INTO quiz_questions (category, question, option1, option2, option3, option4, correct_option, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);
            stmt.setString(2, question); 
            stmt.setString(3, option1);
            stmt.setString(4, option2);
            stmt.setString(5, option3);
            stmt.setString(6, option4);
            stmt.setInt(7, correctOption);
            stmt.setString(8, new Timestamp(System.currentTimeMillis()).toString());

            stmt.executeUpdate();
            loadQuestionsFromDatabase(); // Reload the table with updated data
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding question: " + e.getMessage());
        }
    }

    // Method to open the edit dialog for a question
    private void openEditQuestionDialog(int questionId) {
    }

    // Method to copy a question
    private void copyQuestion(int questionId) {
    }

    // Method to delete a question
    private void deleteQuestion(int questionId) {
        String query = "DELETE FROM quiz_questions WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, questionId);
            stmt.executeUpdate();
            loadQuestionsFromDatabase(); // Reload the table after deletion
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting question: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    adminPage frame = new adminPage();
                    frame.setVisible(true); // Display the admin page
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

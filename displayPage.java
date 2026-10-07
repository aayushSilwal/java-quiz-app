package CourseWork;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class displayPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;

    // Constructor to set up the frame
    public displayPage() {
        setTitle("Quiz Results");  // Set the title of the window
        setSize(600, 400);  // Set the size of the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close the window when disposed
        setLocationRelativeTo(null);  // Center the window on the screen

        // Create a panel to hold the table and other components
        JPanel panel = new JPanel(new BorderLayout());

        // Create a title label and set its font and alignment
        JLabel titleLabel = new JLabel("Quiz Results (Descending Order)");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);  // Add the label to the top of the panel

        // Create a table to display the results
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);  // Make the table scrollable
        panel.add(scrollPane, BorderLayout.CENTER);  // Add the scrollable table to the center of the panel

        add(panel);  // Add the panel to the frame
        loadResults();  // Call method to load results from the database
    }

    // Method to load quiz results from the database
    private void loadResults() {
        String url = "jdbc:mysql://localhost:3306/QuizDB";  // Database connection URL
        String user = "root";  // MySQL username
        String password = "";  // MySQL password (empty in this case)

        // SQL query to fetch results from the competitors table, ordered by TotalScore in descending order
        String query = "SELECT Name, Level, TotalScore FROM competitors ORDER BY TotalScore DESC";

        try (Connection conn = DriverManager.getConnection(url, user, password);  // Establish connection to the database
             PreparedStatement pstmt = conn.prepareStatement(query);  // Prepare the SQL statement
             ResultSet rs = pstmt.executeQuery()) {  // Execute the query and get the result set

            // Create a table model and add columns
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Name");  // Column for competitor's name
            model.addColumn("Level");  // Column for quiz level (difficulty)
            model.addColumn("Total Score");  // Column for total score

            // Iterate through the result set and populate the table model
            while (rs.next()) {
                String name = rs.getString("Name");
                String level = rs.getString("Level");
                int score = rs.getInt("TotalScore");

                // Add each row of results to the table model
                model.addRow(new Object[]{name, level, score});
            }

            // Set the table model to display the data
            table.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQLException to the console
        }
    }
}

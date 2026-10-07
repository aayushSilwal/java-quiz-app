package CourseWork;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CompetitiorList extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;  // Table to display competitors' data
    private JLabel topPerformerLabel;  // Label to display the top performer
    private JTextArea statsTextArea;  // Text area to display score frequency statistics

    // Constructor to set up the GUI
    public CompetitiorList() {
        setTitle("Competitor Results");  // Set the window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set the default close operation
        setBounds(100, 100, 800, 500);  // Set the size and position of the window
        getContentPane().setLayout(new BorderLayout());  // Use a BorderLayout for the content pane

        // Table for displaying competitors' data
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);  // Make the table scrollable
        getContentPane().add(scrollPane, BorderLayout.CENTER);  // Add the table to the center of the window

        // Panel for displaying top performer and statistics
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        // Label to display the top performer
        topPerformerLabel = new JLabel("Top Performer: ");
        topPerformerLabel.setFont(new Font("Arial", Font.BOLD, 16));  // Set the font style and size
        bottomPanel.add(topPerformerLabel, BorderLayout.NORTH);  // Add label to the top of the bottom panel

        // Text area for displaying score frequency statistics
        statsTextArea = new JTextArea();
        statsTextArea.setEditable(false);  // Make the text area read-only
        bottomPanel.add(new JScrollPane(statsTextArea), BorderLayout.CENTER);  // Add text area to the center of the bottom panel

        // Add the bottom panel to the south section of the window
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        loadCompetitorData();  // Load data when the frame opens
    }

    // Method to load competitor data from the database
    private void loadCompetitorData() {
        String url = "jdbc:mysql://localhost:3306/QuizDB";  // Database connection URL
        String user = "root";  // MySQL username
        String password = "";  // MySQL password (empty in this case)

        // SQL query to fetch competitors' results ordered by total score in ascending order
        String query = "SELECT * FROM competitors ORDER BY TotalScore ASC";

        try (Connection conn = DriverManager.getConnection(url, user, password);  // Establish connection to the database
             PreparedStatement stmt = conn.prepareStatement(query);  // Prepare the SQL statement
             ResultSet rs = stmt.executeQuery()) {  // Execute the query and get the result set

            DefaultTableModel model = new DefaultTableModel();  // Create a table model
            model.addColumn("CompetitorID");  // Add columns to the model
            model.addColumn("Name");
            model.addColumn("Level");
            model.addColumn("Score1");
            model.addColumn("Score2");
            model.addColumn("Score3");
            model.addColumn("Score4");
            model.addColumn("Total Score");

            int highestScore = -1;  // Variable to track the highest score
            String topPerformer = "";  // String to store the top performer's name and details
            int[] scoreFrequencies = new int[6];  // Array to count occurrences of each score (0-5)

            // Iterate through each row in the result set and populate the table model
            while (rs.next()) {
                int competitorID = rs.getInt("CompetitorID");
                String name = rs.getString("Name");
                String level = rs.getString("Level");
                int score1 = rs.getInt("Score1");
                int score2 = rs.getInt("Score2");
                int score3 = rs.getInt("Score3");
                int score4 = rs.getInt("Score4");
                int totalScore = rs.getInt("TotalScore");

                model.addRow(new Object[]{competitorID, name, level, score1, score2, score3, score4, totalScore});  // Add row to the table model

                // Update the top performer if this competitor has the highest score
                if (totalScore > highestScore) {
                    highestScore = totalScore;
                    topPerformer = name + " (" + level + ") - Score: " + highestScore;
                }

                // Update the frequency counts for the scores (0-5)
                if (score1 >= 0 && score1 <= 5) scoreFrequencies[score1]++;
                if (score2 >= 0 && score2 <= 5) scoreFrequencies[score2]++;
                if (score3 >= 0 && score3 <= 5) scoreFrequencies[score3]++;
                if (score4 >= 0 && score4 <= 5) scoreFrequencies[score4]++;
            }

            table.setModel(model);  // Set the table model with the populated data

            // Display the top performer
            topPerformerLabel.setText("Top Performer: " + topPerformer);

            // Build the score frequency report and display it
            StringBuilder stats = new StringBuilder("Score Frequency Report:\n");
            for (int i = 0; i < scoreFrequencies.length; i++) {
                stats.append("Score ").append(i).append(": ").append(scoreFrequencies[i]).append("\n");
            }
            statsTextArea.setText(stats.toString());  // Set the stats text area with the generated report

        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQLException to the console
        }
    }

    // Main method to launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CompetitiorList frame = new CompetitiorList();  // Create the frame
            frame.setVisible(true);  // Make the frame visible
        });
    }
}

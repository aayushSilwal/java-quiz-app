package CourseWork;

import java.sql.*;
import java.util.Scanner;

public class manager {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/QuizDB";  // Update with your DB URL
    private static final String USER = "root";  // Update with your DB username
    private static final String PASSWORD = "";  // Update with your DB password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        // Main loop for menu interaction
        while (!exit) {
            // Display the menu options to the user
            System.out.println("1. Generate Full Report");
            System.out.println("2. Display Top Performer");
            System.out.println("3. Generate Statistics");
            System.out.println("4. Search Competitor by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();  // Read the user's choice

            // Handle the user's choice with a switch statement
            switch (choice) {
                case 1:
                    generateFullReport();  // Call method to generate the full report
                    break;
                case 2:
                    displayTopPerformer();  // Call method to display the top performer
                    break;
                case 3:
                    generateStatistics();  // Call method to generate statistics
                    break;
                case 4:
                    searchCompetitorByID(scanner);  // Call method to search for a competitor by ID
                    break;
                case 5:
                    exit = true;  // Exit the program
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");  // Handle invalid input
            }
        }

        scanner.close();  // Close the scanner resource when done
    }

    // Database connection method
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);  // Establish a connection to the database
    }

    // 1. Generate Full Report: Retrieves and displays all competitors' data
    public static void generateFullReport() {
        String query = "SELECT * FROM competitors";  // SQL query to fetch all competitors

        try (Connection connection = getConnection();  // Try-with-resources to automatically close the connection
             Statement statement = connection.createStatement();  // Create a statement object
             ResultSet resultSet = statement.executeQuery(query)) {  // Execute the query

            System.out.println("Full Report of Competitors:");
            // Iterate over the result set and print each competitor's data
            while (resultSet.next()) {
                int competitorID = resultSet.getInt("CompetitorID");
                String name = resultSet.getString("Name");
                String level = resultSet.getString("Level");
                int score1 = resultSet.getInt("Score1");
                int score2 = resultSet.getInt("Score2");
                int score3 = resultSet.getInt("Score3");
                int score4 = resultSet.getInt("Score4");
                int totalScore = resultSet.getInt("TotalScore");

                // Print each competitor's details
                System.out.printf("ID: %d, Name: %s, Level: %s, Scores: [%d, %d, %d, %d], TotalScore: %d\n",
                        competitorID, name, level, score1, score2, score3, score4, totalScore);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print SQL exceptions if any
        }
    }

    // 2. Display Top Performer: Displays the competitor with the highest total score
    public static void displayTopPerformer() {
        String query = "SELECT * FROM competitors ORDER BY TotalScore DESC LIMIT 1";  // SQL query to fetch the top performer

        try (Connection connection = getConnection();  // Try-with-resources to automatically close the connection
             Statement statement = connection.createStatement();  // Create a statement object
             ResultSet resultSet = statement.executeQuery(query)) {  // Execute the query

            if (resultSet.next()) {  // If the result set contains data
                int competitorID = resultSet.getInt("CompetitorID");
                String name = resultSet.getString("Name");
                String level = resultSet.getString("Level");
                int totalScore = resultSet.getInt("TotalScore");

                // Print the top performer's details
                System.out.printf("Top Performer: ID: %d, Name: %s, Level: %s, TotalScore: %d\n",
                        competitorID, name, level, totalScore);
            } else {
                System.out.println("No data available.");  // In case no data is found
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print SQL exceptions if any
        }
    }

    // 3. Generate Statistics: Displays total competitors and the average score
    public static void generateStatistics() {
        String query = "SELECT COUNT(*) AS totalCompetitors, AVG(TotalScore) AS avgScore FROM competitors";  // SQL query for statistics

        try (Connection connection = getConnection();  // Try-with-resources to automatically close the connection
             Statement statement = connection.createStatement();  // Create a statement object
             ResultSet resultSet = statement.executeQuery(query)) {  // Execute the query

            if (resultSet.next()) {  // If the result set contains data
                int totalCompetitors = resultSet.getInt("totalCompetitors");
                double avgScore = resultSet.getDouble("avgScore");

                // Print the statistics
                System.out.printf("Total Competitors: %d, Average Score: %.2f\n", totalCompetitors, avgScore);
            } else {
                System.out.println("No data available.");  // In case no data is found
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print SQL exceptions if any
        }
    }

    // 4. Search Competitor by ID: Searches for a specific competitor using their ID
    public static void searchCompetitorByID(Scanner scanner) {
        System.out.print("Enter Competitor ID to search: ");
        int competitorID = scanner.nextInt();  // Read competitor ID input from the user

        String query = "SELECT * FROM competitors WHERE CompetitorID = ?";  // SQL query to search by ID

        try (Connection connection = getConnection();  // Try-with-resources to automatically close the connection
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  // Prepare the SQL statement

            preparedStatement.setInt(1, competitorID);  // Set the competitor ID in the query
            ResultSet resultSet = preparedStatement.executeQuery();  // Execute the query

            if (resultSet.next()) {  // If the competitor is found
                String name = resultSet.getString("Name");
                String level = resultSet.getString("Level");
                int score1 = resultSet.getInt("Score1");
                int score2 = resultSet.getInt("Score2");
                int score3 = resultSet.getInt("Score3");
                int score4 = resultSet.getInt("Score4");
                int totalScore = resultSet.getInt("TotalScore");

                // Print the competitor's details
                System.out.printf("Competitor Found: ID: %d, Name: %s, Level: %s, Scores: [%d, %d, %d, %d], TotalScore: %d\n",
                        competitorID, name, level, score1, score2, score3, score4, totalScore);
            } else {
                System.out.println("Competitor not found.");  // If the competitor is not found
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print SQL exceptions if any
        }
    }
}

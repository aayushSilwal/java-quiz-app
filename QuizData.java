package CourseWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizData {

    private static final String URL = "jdbc:mysql://localhost:3306/QuizDB"; // Database URL
    private static final String USER = "root"; // MySQL Username
    private static final String PASSWORD = ""; // MySQL Password

    // A simple class to hold the details of each question
    public static class Question {
        private String question;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private int correctOption;

        // Constructor
        public Question(String question, String option1, String option2, String option3, String option4, int correctOption) {
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctOption = correctOption;
        }

        // Getters for the question and options
        public String getQuestion() {
            return question;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public int getCorrectOption() {
            return correctOption;
        }
    }

    // Method to connect to the database
    private static Connection connect() throws SQLException {
        try {
            // Ensure MySQL driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found");
        }

        // Return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to fetch questions for a specific category
    public static List<Question> getQuestions(String category) {
        List<Question> questions = new ArrayList<>();

        // SQL query to fetch questions by category (limit to 10 questions per category)
        String query = "SELECT * FROM quiz_questions WHERE category = ? LIMIT 10";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the category in the query
            stmt.setString(1, category);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                // Loop through the results and create Question objects
                while (rs.next()) {
                    String questionText = rs.getString("question");
                    String option1 = rs.getString("option1");
                    String option2 = rs.getString("option2");
                    String option3 = rs.getString("option3");
                    String option4 = rs.getString("option4");
                    int correctOption = rs.getInt("correct_option");

                    // Create a Question object and add it to the list
                    Question question = new Question(questionText, option1, option2, option3, option4, correctOption);
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the list of questions
        return questions;
    }
}

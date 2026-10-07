package CourseWork;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class quizPage extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int count = 0;
    private int score = 0;
    private int[] individualScores; // This will be dynamically sized
    private List<QuizData.Question> questions;
    private String currentCategory;
    private String userName;
    private ButtonGroup optionsGroup;
    private JButton submitButton;

    public quizPage(String category, String userName) {
        this.userName = userName;  // Set the username
        this.currentCategory = category;  // Set the selected category (difficulty)
        loadQuestionsForCategory();  // Fetch questions based on difficulty
        initialize();  // Initialize the GUI components
    }

    private void loadQuestionsForCategory() {
        questions = QuizData.getQuestions(currentCategory);  // Fetch questions based on selected category

        // Check if questions list is empty
        if (questions.isEmpty()) {
            // Handle case where there are no questions for this category
            System.out.println("No questions found for category: " + currentCategory);
            return;  // Prevent further execution in this case
        }

        individualScores = new int[questions.size()];  // Dynamically size the array based on the number of questions
    }

    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel questionCounter = new JLabel("Question no 1");
        questionCounter.setFont(new Font("Tahoma", Font.PLAIN, 25));
        questionCounter.setHorizontalAlignment(SwingConstants.CENTER);
        questionCounter.setBounds(38, 10, 395, 38);
        contentPane.add(questionCounter);

        JTextArea questionTextArea = new JTextArea("What does the c in e = mc^2 stand for?");
        questionTextArea.setFont(new Font("Arial", Font.PLAIN, 20));
        questionTextArea.setEditable(false);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        JScrollPane questionScrollPane = new JScrollPane(questionTextArea);
        questionScrollPane.setBounds(10, 58, 466, 100);
        contentPane.add(questionScrollPane);

        JRadioButton optionOne = new JRadioButton("energy");
        optionOne.setActionCommand("energy");
        optionOne.setFont(new Font("Tahoma", Font.PLAIN, 18));
        optionOne.setBounds(10, 173, 466, 38);
        contentPane.add(optionOne);

        JRadioButton optionTwo = new JRadioButton("mass");
        optionTwo.setActionCommand("mass");
        optionTwo.setFont(new Font("Tahoma", Font.PLAIN, 18));
        optionTwo.setBounds(10, 215, 466, 38);
        contentPane.add(optionTwo);

        JRadioButton optionThree = new JRadioButton("speed of light");
        optionThree.setActionCommand("speed of light");
        optionThree.setFont(new Font("Tahoma", Font.PLAIN, 18));
        optionThree.setBounds(10, 255, 466, 38);
        contentPane.add(optionThree);

        JRadioButton optionFour = new JRadioButton("nuclear fusion");
        optionFour.setActionCommand("nuclear fusion");
        optionFour.setFont(new Font("Tahoma", Font.PLAIN, 18));
        optionFour.setBounds(10, 295, 466, 38);
        contentPane.add(optionFour);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionOne);
        optionsGroup.add(optionTwo);
        optionsGroup.add(optionThree);
        optionsGroup.add(optionFour);

        submitButton = new JButton("Next");
        submitButton.setEnabled(false);  
        submitButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        submitButton.setBounds(10, 356, 170, 38);
        contentPane.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (optionsGroup.getSelection() == null) {
                    return;
                }

                QuizData.Question currentQuestion = questions.get(count);

                if (isAnswerCorrect(currentQuestion)) {
                    score++;
                    individualScores[count] = 1;  // Store individual score for this question
                } else {
                    individualScores[count] = 0;
                }

                count++;

                if (count < questions.size()) {
                    updateQuestionDisplay(questionTextArea, optionOne, optionTwo, optionThree, optionFour, questionCounter);
                } else {
                    saveScoreToDatabase(); // Save score to the database
                    displayScore();
                }
            }
        });

        ActionListener radioButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.setEnabled(true); // Enable submit when an option is selected
            }
        };

        optionOne.addActionListener(radioButtonListener);
        optionTwo.addActionListener(radioButtonListener);
        optionThree.addActionListener(radioButtonListener);
        optionFour.addActionListener(radioButtonListener);

        updateQuestionDisplay(questionTextArea, optionOne, optionTwo, optionThree, optionFour, questionCounter);
    }

    private boolean isAnswerCorrect(QuizData.Question currentQuestion) {
        if (optionsGroup.getSelection() == null) {
            return false;
        }

        String selectedOption = optionsGroup.getSelection().getActionCommand();

        switch (selectedOption) {
            case "energy":
                return currentQuestion.getCorrectOption() == 1;
            case "mass":
                return currentQuestion.getCorrectOption() == 2;
            case "speed of light":
                return currentQuestion.getCorrectOption() == 3;
            case "nuclear fusion":
                return currentQuestion.getCorrectOption() == 4;
            default:
                return false;
        }
    }

    private void updateQuestionDisplay(JTextArea questionTextArea, JRadioButton optionOne, JRadioButton optionTwo,
                                       JRadioButton optionThree, JRadioButton optionFour, JLabel questionCounter) {
        if (questions.isEmpty()) {
            return;
        }

        QuizData.Question currentQuestion = questions.get(count);
        questionTextArea.setText(currentQuestion.getQuestion());
        optionOne.setText(currentQuestion.getOption1());
        optionTwo.setText(currentQuestion.getOption2());
        optionThree.setText(currentQuestion.getOption3());
        optionFour.setText(currentQuestion.getOption4());
        questionCounter.setText("Question no " + (count + 1));
        optionsGroup.clearSelection();
        submitButton.setEnabled(false);
    }

    private void displayScore() {
        contentPane.removeAll(); // Clear the panel

        // Label to display the final score
        JLabel scoreLabel = new JLabel("Your Score: " + score + " / " + questions.size());
        scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setBounds(38, 10, 395, 38);
        contentPane.add(scoreLabel);

        // Label to confirm score saving
        JLabel savedLabel = new JLabel("Your Score has been saved!");
        savedLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        savedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        savedLabel.setBounds(38, 60, 395, 38);
        contentPane.add(savedLabel);
        
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        playAgainButton.setBounds(150, 150, 200, 50);
        contentPane.add(playAgainButton);

        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();  // Close the current quiz window
            }
        });

        contentPane.revalidate();
        contentPane.repaint();
    }

    private void saveScoreToDatabase() {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/QuizDB";
        String user = "root";
        String password = "";

        // SQL query to insert score into the database
        String sql = "INSERT INTO competitors (Name, Level, Score1, Score2, Score3, Score4, TotalScore) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // Execute the insert query
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userName);  // Set the username
            pstmt.setString(2, currentCategory);  // Set the difficulty level
            pstmt.setInt(3, individualScores[0]);  // Set Score1
            pstmt.setInt(4, individualScores[1]);  // Set Score2
            pstmt.setInt(5, individualScores[2]);  // Set Score3
            pstmt.setInt(6, individualScores[3]);  // Set Score4
            pstmt.setInt(7, score);  // Set TotalScore

            pstmt.executeUpdate();  // Execute the insert statement

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

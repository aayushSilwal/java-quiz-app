package CourseWork;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class difficultyPage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private String user;  // Store username here

    // Constructor that accepts username
    public difficultyPage(String user) {
        this.user = user;
        setUpPage();  // Set up the page with the username
    }

    // Method to set up the page layout and components
    public void setUpPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title Label
        JLabel lblNewLabel = new JLabel("Choose difficulty");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        lblNewLabel.setBounds(104, 10, 280, 44);
        contentPane.add(lblNewLabel);

        // Beginner Button
        JButton beginner = new JButton("Beginner");
        beginner.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quizPage quiz = new quizPage("Beginner", user);  // Pass username here
                quiz.setVisible(true);
                dispose();  // Close current window
            }
        });
        beginner.setFont(new Font("Tahoma", Font.PLAIN, 20));
        beginner.setBounds(23, 64, 453, 55);
        contentPane.add(beginner);

        // Intermediate Button
        JButton intermediate = new JButton("Intermediate");
        intermediate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quizPage quiz = new quizPage("Intermediate", user);  // Pass username here
                quiz.setVisible(true);
                dispose();  // Close current window
            }
        });
        intermediate.setFont(new Font("Tahoma", Font.PLAIN, 20));
        intermediate.setBounds(23, 135, 453, 55);
        contentPane.add(intermediate);

        // Advanced Button
        JButton advanced = new JButton("Advanced");
        advanced.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quizPage quiz = new quizPage("Advanced", user);  // Pass username here
                quiz.setVisible(true);
                dispose();  // Close current window
            }
        });
        advanced.setFont(new Font("Tahoma", Font.PLAIN, 20));
        advanced.setBounds(23, 212, 453, 55);
        contentPane.add(advanced);
    }
}

package CourseWork;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class Homepage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel startLabel; // Label to display the quiz title or welcome message
    private String username; // Stores the logged-in username

    /**
     * Launching the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Homepage frame = new Homepage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Homepage() {
    	/**
    	 * jframe for home page
    	 */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        /**
         * Start Label displaying the quiz title
         */
        startLabel = new JLabel("Quiz Game");
        startLabel.setHorizontalAlignment(SwingConstants.CENTER);
        startLabel.setBounds(83, 12, 336, 38);
        startLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        contentPane.add(startLabel);

        /**
         * Login Button - Opens the UsernamePage to enter a username
         */
        JButton login = new JButton("Login");
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UsernamePage usernamepage = new UsernamePage();
                usernamepage.setVisible(true);

                // Set up event listener for when UsernamePage closes
                usernamepage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                usernamepage.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        // Retrieve the username after the UsernamePage is closed
                        username = usernamepage.getUser();
                        if (!username.isEmpty()) {
                            startLabel.setText("Welcome " + username);
                        } else {
                            startLabel.setText("Quiz Game");
                        }
                    }
                });
            }
        });
        login.setFont(new Font("Tahoma", Font.PLAIN, 20));
        login.setBounds(10, 60, 466, 66);
        contentPane.add(login);

        /**
         * Start Button - Starts the quiz if a username is set
         */
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /**
                 * Check if a username is set before starting the quiz
                 */
                if (username != null && !username.isEmpty()) {
                    difficultyPage diff = new difficultyPage(username);
                    diff.setVisible(true);
                } else {
                    // Show an error message if no username is set
                    JOptionPane.showMessageDialog(null, "Please log in to start the game.");
                }
            }
        });
        start.setFont(new Font("Tahoma", Font.PLAIN, 20));
        start.setBounds(10, 136, 466, 66);
        contentPane.add(start);

        /**
         *  See Players Details Button - Opens CompetitorList to display player details
         */
        JButton details = new JButton("See Players Details");
        details.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CompetitiorList cl = new CompetitiorList();
                cl.setVisible(true);
            }
        });
        details.setFont(new Font("Tahoma", Font.PLAIN, 20));
        details.setBounds(10, 214, 466, 66);
        contentPane.add(details);

        /**
         * High Scores Button - Opens displayPage to show high scores
         */
        JButton highScores = new JButton("High Scores");
        highScores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayPage dp = new displayPage();
                dp.setVisible(true);
            }
        });
        highScores.setFont(new Font("Tahoma", Font.PLAIN, 20));
        highScores.setBounds(10, 293, 466, 66);
        contentPane.add(highScores);

        /**
         * Logout/Exit Button - Closes the application
         */
        JButton logout = new JButton("Exit/Logout");
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the application
            }
        });
        logout.setFont(new Font("Tahoma", Font.PLAIN, 20));
        logout.setBounds(10, 376, 466, 66);
        contentPane.add(logout);
    }
}

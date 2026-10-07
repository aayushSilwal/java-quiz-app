package CourseWork;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UsernamePage extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField userName; // Text field for username input
    public String user; // Stores the entered username

    // Getter for the username
    public String getUser() {
        return user;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UsernamePage frame = new UsernamePage();
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
    public UsernamePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Title Label
        JLabel lblNewLabel = new JLabel("Set Username");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 25));
        lblNewLabel.setBounds(59, 10, 365, 59);
        contentPane.add(lblNewLabel);

        // TextField for username input
        userName = new JTextField();
        userName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        userName.setBounds(10, 86, 466, 48);
        contentPane.add(userName);
        userName.setColumns(10);
        
        // Focus the text field when the window opens
        userName.requestFocus();

        // Submit Button - Sets the username and proceeds
        JButton submitUserName = new JButton("Submit");
        submitUserName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user = userName.getText().trim();
                if (!user.equals("")) {  
                    // If username is not empty, proceed
                    JOptionPane.showMessageDialog(null, "Username set to: " + user);

                    // Proceed to difficulty page
                    difficultyPage difficulty = new difficultyPage(user);  // Pass username here
                    dispose(); // Close the window
                } else {
                    // If username is empty, show a message
                    JOptionPane.showMessageDialog(null, "Please fill all fields.");
                }
            }
        });
        submitUserName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        submitUserName.setBounds(157, 163, 175, 42);
        contentPane.add(submitUserName);
        
        // Admin Button - Grants access if the correct password is entered
        JButton btnAdmin = new JButton("Admin");
        btnAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminPage ap = new adminPage();
                user = userName.getText().trim();
                if(user.equals("12345")) { // Check for correct admin password
                    ap.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Type in the correct admin password in the username box");
                }
            }
        });
        btnAdmin.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnAdmin.setBounds(157, 215, 175, 42);
        contentPane.add(btnAdmin);
    }
}

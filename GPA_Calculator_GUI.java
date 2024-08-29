
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Course {
    String courseName;
    int creditHours;
    String grade;

    Course(String courseName, int creditHours, String grade) {
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.grade = grade;
    }

    public double getGradePoint() {
        switch (grade) {
            case "A":
                return 4.0;
            case "B":
                return 3.0;
            case "C":
                return 2.0;
            case "D":
                return 1.0;
            case "F":
                return 0.0;
            default:
                return 0.0;
        }
    }
}

public class GPA_Calculator_GUI extends JFrame {
    private ArrayList<Course> courses = new ArrayList<>();
    private JTextField courseNameField, creditHoursField, gradeField;
    private JTextArea courseListArea;
    private JLabel gpaLabel;

    public GPA_Calculator_GUI() {
        setTitle("GPA Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        courseNameField = new JTextField();
        creditHoursField = new JTextField();
        gradeField = new JTextField();

        inputPanel.add(new JLabel("Course Name:"));
        inputPanel.add(courseNameField);
        inputPanel.add(new JLabel("Credit Hours:"));
        inputPanel.add(creditHoursField);
        inputPanel.add(new JLabel("Grade (A, B, C, D, F):"));
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Course");
        addButton.addActionListener(new AddCourseAction());
        inputPanel.add(addButton);

        JButton calculateButton = new JButton("Calculate GPA");
        calculateButton.addActionListener(new CalculateGPAAction());
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        courseListArea = new JTextArea();
        courseListArea.setEditable(false);
        add(new JScrollPane(courseListArea), BorderLayout.CENTER);

        gpaLabel = new JLabel("Your GPA will be displayed here.");
        gpaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(gpaLabel, BorderLayout.SOUTH);
    }

    private class AddCourseAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String courseName = courseNameField.getText();
            int creditHours = Integer.parseInt(creditHoursField.getText());
            String grade = gradeField.getText().toUpperCase();

            courses.add(new Course(courseName, creditHours, grade));
            courseListArea.append(courseName + " (" + creditHours + " hours) - " + grade + "\n");

            // Clear input fields
            courseNameField.setText("");
            creditHoursField.setText("");
            gradeField.setText("");
        }
    }

    private class CalculateGPAAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double totalGradePoints = 0;
            int totalCreditHours = 0;

            for (Course course : courses) {
                totalGradePoints += course.getGradePoint() * course.creditHours;
                totalCreditHours += course.creditHours;
            }

            double gpa = totalCreditHours == 0 ? 0.0 : totalGradePoints / totalCreditHours;
            gpaLabel.setText(String.format("Your GPA is: %.2f", gpa));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GPA_Calculator_GUI calculator = new GPA_Calculator_GUI();
            calculator.setVisible(true);
        });
    }
}

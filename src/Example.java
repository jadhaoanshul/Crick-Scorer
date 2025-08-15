import javax.swing.*;

public class Example {
    public static void main(String[] args) {
        // Create a JFrame (main window)
        JFrame frame = new JFrame("Swing UI Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel to hold components
        JPanel panel = new JPanel();
        frame.add(panel);

        // Add components to the panel
        JLabel label = new JLabel("Enter your name:");
        JTextField textField = new JTextField(20);
        JButton button = new JButton("Submit");

        panel.add(label);
        panel.add(textField);
        panel.add(button);

        // Add action to the button
        button.addActionListener(e -> {
            String name = textField.getText();
            JOptionPane.showMessageDialog(frame, "Hello, " + name + "!");
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class SimpleGUI extends JFrame {
    private JButton button = new JButton("Press");
    private JTextField input = new JTextField();
    private JTextField input3 = new JTextField();

    private JLabel label = new JLabel("Input:");
    public SimpleGUI () {
        super("Simple Example");
        this.setBounds(400, 200, 400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 1, 5, 5));

        container.setBackground(Color.lightGray);
        container.add(label);
        container.add(input);
        container.add(input3);
        button.addActionListener(new ButtonEventListener ());
        container.add(button);

    }
    class ButtonEventListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {

            String message = "";
            String input2 = input.getText();

            String textReverse = "";

            for (int i = input2.length()/2; i < input2.length(); i+=3)
            textReverse += input2.substring(i, i+2);

            message += "Text is: " + textReverse + " \n";
            input3.setText(message);
        }
    }

}
class Main {
    public static void main(String[] args) {
        SimpleGUI app = new SimpleGUI();
        app.setVisible(true);
    }
}





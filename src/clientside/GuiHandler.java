package clientside;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuiHandler extends JFrame {

    JPanel jPanel = new JPanel();
    JLabel jLabel = new JLabel();

    JTextField jTextField = new JTextField(20);

    public GuiHandler(InputHandler inputHandler, List<Message> msgSendQueue) {

        setTitle("Chat App");
        setSize(300, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        jPanel.add(jTextField);

        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.handleInput(jTextField.getText());
                jTextField.setText("");
            }
        });

        add(jPanel);
    }
}
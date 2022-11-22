package clientside;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GuiHandler extends JFrame {

    JPanel jPanelMain = new JPanel();

    JTextArea jTextArea = new JTextArea(10, 20);
    JScrollPane jScrollPane = new JScrollPane(jTextArea);

    JTextField jTextField = new JTextField(20);

    BoxLayout boxLayout = new BoxLayout(jPanelMain, BoxLayout.Y_AXIS);

    private final ChatTextContainer chatTextContainer;

    public GuiHandler(InputHandler inputHandler, List<Message> msgSendQueue, ChatTextContainer chatTextContainer) {

        this.chatTextContainer = chatTextContainer;

        jPanelMain.setLayout(boxLayout);

        jTextArea.setEditable(false);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputHandler.handleInput(jTextField.getText());
                updateChatText(jTextField.getText(), true);
                jTextField.setText("");
            }
        });

        jPanelMain.add(jScrollPane);
        jPanelMain.add(Box.createVerticalGlue());
        jPanelMain.add(Box.createRigidArea(new Dimension(0, 5)));
        jPanelMain.add(jTextField);

        Border borderMargin = new EmptyBorder(10,10,10,10);
        jPanelMain.setBorder(new CompoundBorder(BorderFactory.createEtchedBorder(), borderMargin));

        add(jPanelMain);
        setTitle("Chat App");
        setSize(400, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    public void updateChatText(String newText, boolean localMessage) {
        synchronized (chatTextContainer) {
            String prefix = localMessage ? "You: " : "";
            chatTextContainer.chatText += prefix + newText + "\n";
            jTextArea.setText(chatTextContainer.chatText);
        }
    }
}
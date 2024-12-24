package main.java.com.project_app;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InviteFriendFrame extends JFrame {
    private JComboBox<String> blindTestComboBox;
    private JComboBox<String> friendComboBox; // New dropdown for friends
    private List<BlindTest> blindTests;
    private Member currentUser; // To hold the current user

    public InviteFriendFrame(List<BlindTest> blindTests, Member currentUser) {
        this.blindTests = blindTests;
        this.currentUser = currentUser;
        initComponents();
        setTitle("Invite a Friend to a Blind Test");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridLayout(4, 2));

        JLabel selectBlindTestLabel = new JLabel("Select Blind Test: ");
        blindTestComboBox = new JComboBox<>();
        for (BlindTest blindTest : blindTests) {
            blindTestComboBox.addItem(blindTest.getTitle());
        }

        JLabel selectFriendLabel = new JLabel("Select Friend: ");
        friendComboBox = new JComboBox<>();
        for (String friend : currentUser.getFriends()) {
            friendComboBox.addItem(friend);
        }

        JButton sendInviteButton = new JButton("Send Invite");

        add(selectBlindTestLabel);
        add(blindTestComboBox);
        add(selectFriendLabel);
        add(friendComboBox);
        add(new JLabel()); // Empty cell for spacing
        add(sendInviteButton);

        sendInviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBlindTest = (String) blindTestComboBox.getSelectedItem();
                String selectedFriend = (String) friendComboBox.getSelectedItem();

                if (selectedBlindTest != null && selectedFriend != null) {
                    BlindTest blindTest = blindTests.stream()
                            .filter(bt -> bt.getTitle().equals(selectedBlindTest))
                            .findFirst()
                            .orElse(null);

                    if (blindTest != null) {
                        String message = "Lyrics: " + blindTest.getLyrics();
                        NotificationBlindTest notificationBlindTest = new NotificationBlindTest(currentUser.getUsername(), message);

                        Member recipient = MemberManager.getMember(selectedFriend); // Assuming a method to retrieve member by username
                        if (recipient != null) {
                            recipient.addNotificationBlindTest(notificationBlindTest);
                            JOptionPane.showMessageDialog(InviteFriendFrame.this, "Invite sent to " + selectedFriend);
                        } else {
                            JOptionPane.showMessageDialog(InviteFriendFrame.this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(InviteFriendFrame.this, "Please select a blind test and a friend.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

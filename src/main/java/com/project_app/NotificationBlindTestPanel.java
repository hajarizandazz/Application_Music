package main.java.com.project_app;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class NotificationBlindTestPanel extends JPanel {
    private Member currentUser;
    private List<NotificationBlindTest> notificationBlindTests;

    public NotificationBlindTestPanel(Member currentUser) {
        this.currentUser = currentUser;
        this.notificationBlindTests = currentUser.getNotificationsBlindTests();

        setLayout(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("From");
        tableModel.addColumn("Message");
        tableModel.addColumn("Response");

        for (NotificationBlindTest notificationBlindTest :notificationBlindTests) {
            Object[] rowData = new Object[3];
            rowData[0] = notificationBlindTest.getSender();
            rowData[1] = notificationBlindTest.getMessage();

            JButton responseButton = new JButton("Response");
            responseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Open FriendResponseFrame with the notification
                    FriendResponseFrame responseFrame = new FriendResponseFrame(notificationBlindTest);
                    responseFrame.setVisible(true);
                }
            });

            rowData[2] = responseButton;
            tableModel.addRow(rowData);
        }

        JTable notificationsBlindTestTable = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return (column == 2) ? JButton.class : Object.class;
            }
        };

        notificationsBlindTestTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        notificationsBlindTestTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));

        add(new JScrollPane(notificationsBlindTestTable), BorderLayout.CENTER);
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof JButton) {
                JButton button = (JButton) value;
                return button;
            }
            return new JButton("Response");
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value instanceof JButton) {
                button = (JButton) value;
            }
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return button;
        }
    }
}


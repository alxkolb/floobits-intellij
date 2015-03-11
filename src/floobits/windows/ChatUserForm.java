package floobits.windows;

import com.intellij.ui.components.JBList;
import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ChatUserForm {
    private JList clientList;
    private JPanel gravatarContainer;
    private JPanel containerPanel;
    private DefaultListModel clientModel;


    private static class ClientCellRenderer extends JLabel implements ListCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setText((String) value);
            setOpaque(false);
            return this;
        }
    }

    private void createUIComponents() {
        clientModel = new DefaultListModel();
        clientList = new JBList();
        clientList.setOpaque(false);
        clientList.setModel(clientModel);
        clientList.setCellRenderer(new ClientCellRenderer());
    }

    public void setUsername(String username) {
        TitledBorder border = (TitledBorder) containerPanel.getBorder();
        border.setTitle(username);
    }

    public void addGravatar(Image gravatar) {
        JLabel iconlabel = new JLabel(new ImageIcon(gravatar));
        gravatarContainer.add(iconlabel, new GridConstraints());

    }

    public void addClient(String client, String platform) {
        clientModel.addElement(String.format("%s %s", client, platform));
    }

    public JPanel getContainerPanel() {
        return containerPanel;
    }

}

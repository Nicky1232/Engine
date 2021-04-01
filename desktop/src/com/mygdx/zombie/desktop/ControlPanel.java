package com.mygdx.zombie.desktop;

import com.mygdx.zombie.actors.ColorBall;
import com.mygdx.zombie.actors.Pipe;
import com.mygdx.zombie.actors.Player;
import com.mygdx.zombie.actors.Spike;
import com.mygdx.zombie.swingterface.Buffer;
import com.mygdx.zombie.swingterface.Status;
import com.mygdx.zombie.swingterface.Swingterface;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;

public class ControlPanel extends JPanel {

    private static final String X = "X";
    private static final String Y = "Y";
    private static final String WIDTH = "Width";
    private static final String HEIGHT = "Height";


    public static final String[] selectors = new String[] {
            "Select", "Pipe", "Spike", "ColorBall", "Player"
    };

    public static final String[] common = new String[] {
            X, Y, WIDTH, HEIGHT
    };

    public static final String[] labels = new String[] {
            "X_1", "Y_1", "Width_1", "Height_1"
    };

    private static final int PANEL_WIDTH = 150;

    // Used for libgdx communication
    private static final HashMap<String, JTextField> textFields = new HashMap<>(16);
    private static final HashMap<String, JToggleButton> selectionButtons = new HashMap<>(8);
    private static String currentSelection = "Select";

    private static final GridBagConstraints gridBagConstraints = new GridBagConstraints();

    ControlPanel(int width) {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(width, 300));
//        setBackground(Color.BLUE);

        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.anchor = GridBagConstraints.NORTH;

        createSelectionPanel();
        createMainPanel();
        createPipePanel();
        createList();
    }

    void signalSelection() {

        if (Status.selection == null) {
            selectionButtons.get("Select").setSelected(true);
            clearAllFields();
        } else if (Pipe.class.equals(Status.selection)) {
            selectionButtons.get("Pipe").setSelected(true);
        } else if (Spike.class.equals(Status.selection)) {
            selectionButtons.get("Spike").setSelected(true);
        } else if (Player.class.equals(Status.selection)) {
            selectionButtons.get("Player").setSelected(true);
        } else if (ColorBall.class.equals(Status.selection)) {
            selectionButtons.get("ColorBall").setSelected(true);
        }

        updateCommonData();

    }

    private void updateCommonData() {
        Buffer buffer = Swingterface.getBuffer();
        if(!buffer.isFlushed()) {
            textFields.forEach((s, jTextField) -> {
                switch (jTextField.getName()) {
                    case X:
                        jTextField.setText(String.valueOf(buffer.getCommon().x));
                        break;
                    case Y:
                        jTextField.setText(String.valueOf(buffer.getCommon().y));
                        break;
                    case WIDTH:
                        jTextField.setText(String.valueOf(buffer.getCommon().width));
                        break;
                    case HEIGHT:
                        jTextField.setText(String.valueOf(buffer.getCommon().height));
                        break;
                }
            });
            Swingterface.flush();
        }
    }

    private void clearAllFields() {
        textFields.forEach((s, jTextField) -> jTextField.setText(""));
    }

    private void createSelectionPanel() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_WIDTH));

        final ButtonGroup group = new ButtonGroup();

        for(int i = 0; i < selectors.length; i++) {

            final JToggleButton button = new JToggleButton(selectors[i]);
            button.setName(selectors[i]);
            button.addActionListener(actionEvent -> {
                System.out.println(currentSelection);
                currentSelection = button.getName();
                Swingterface.triggerSelectionUpdate(currentSelection);
            });

            setGrid(0, i, GridBagConstraints.HORIZONTAL, 1f);
            selectionButtons.put(selectors[i], button);
            group.add(button);
            wrapper.add(button, gridBagConstraints);

        }

        selectionButtons.get("Select").setSelected(true);
        add(wrapper);
    }

    private void createMainPanel() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBorder(BorderFactory.createTitledBorder("Common Options"));
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_WIDTH));

        for(int i = 0; i < common.length; i++) {

            String text = common[i];

            setGrid(0, i, GridBagConstraints.HORIZONTAL, 0.25f);
            JLabel label = new JLabel(text + ":");
            label.setName(common[i]);
            wrapper.add(label, gridBagConstraints);

            setGrid(1, i, GridBagConstraints.HORIZONTAL, 0.75f);
            JTextField input = new JTextField();
            input.setName(common[i]);
            input.addActionListener(actionEvent -> {
                Buffer buffer = Swingterface.getBuffer();
                buffer.setCommon(
                        Float.parseFloat(textFields.get(X).getText()),
                        Float.parseFloat(textFields.get(Y).getText()),
                        Float.parseFloat(textFields.get(WIDTH).getText()),
                        Float.parseFloat(textFields.get(HEIGHT).getText()));
                Swingterface.triggerDataUpdate(buffer);
            });
            wrapper.add(input, gridBagConstraints);

            textFields.put(common[i], input);

        }

//        optionPanels.put("Main", wrapper);
        add(wrapper);

    }

    private void createPipePanel() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBorder(BorderFactory.createTitledBorder("Specific Options"));
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_WIDTH));

        for(int i = 0; i < labels.length; i++) {

            String text = labels[i];

            setGrid(0, i, GridBagConstraints.HORIZONTAL, 0.25f);
            JLabel label = new JLabel(text + ":");
            label.setName(labels[i]);
            wrapper.add(label, gridBagConstraints);

            setGrid(1, i, GridBagConstraints.HORIZONTAL, 0.75f);
            JTextField input = new JTextField();
            input.setName(labels[i]);
            wrapper.add(input, gridBagConstraints);

            textFields.put(labels[i], input);

        }

//        optionPanels.put("Pipe", wrapper);
        add(wrapper);

    }

    private void createList() {

        JPanel wrapper = new JPanel(new GridBagLayout());
//        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_WIDTH));

        //GridBagConstraints.VERTICAL + weighty=1f
        setGrid(0, 0, GridBagConstraints.BOTH, 1f);
        gridBagConstraints.weighty = 0.99f;

        final JList<String> sampleList = new JList<>(labels);
        sampleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sampleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                System.out.println(sampleList.getSelectedValue());
            }
        });

        JScrollPane scrollPane = new JScrollPane(sampleList);
        wrapper.add(scrollPane, gridBagConstraints);

        JButton deleteButton = new JButton("Delete");
        setGrid(0, 1, GridBagConstraints.HORIZONTAL, 1f);
        gridBagConstraints.weighty = 0.01f;
        wrapper.add(deleteButton, gridBagConstraints);

//        optionPanels.put("List", wrapper);
        add(wrapper);
    }

    private void setGrid(int gridx, int gridy, int fill, float weigthx) {
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.fill = fill;
        gridBagConstraints.weightx = weigthx;
    }
}

//public static void main(String[] args) {
//    NumberFormat format = NumberFormat.getInstance();
//    NumberFormatter formatter = new NumberFormatter(format);
//    formatter.setValueClass(Integer.class);
//    formatter.setMinimum(0);
//    formatter.setMaximum(Integer.MAX_VALUE);
//    formatter.setAllowsInvalid(false);
//    // If you want the value to be committed on each keystroke instead of focus lost
//    formatter.setCommitsOnValidEdit(true);
//    JFormattedTextField field = new JFormattedTextField(formatter);
//
//    JOptionPane.showMessageDialog(null, field);
//
//    // getValue() always returns something valid
//    System.out.println(field.getValue());
//}

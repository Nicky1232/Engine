package com.mygdx.zombie.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.text.NumberFormatter;

public class ControlPanel extends JPanel {

    private static final int PANEL_WIDTH = 150;

    // Used for libgdx communication
    private static final HashMap<String, JTextField> textFields = new HashMap<>(16);
    // Used for hide/show
    private static final HashMap<String, JPanel> optionPanels = new HashMap<>(8);
    private static String currentSelection = "";

    private static final String[] selectors = new String[] {
            "Select", "Pipe", "Spike", "ColorBall", "Player"
    };

    private static final String[] labels = new String[] {
            "X", "Y", "Width", "Height"
    };

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

    private void createSelectionPanel() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_WIDTH));

        final ButtonGroup group = new ButtonGroup();

        for(int i = 0; i < selectors.length; i++) {

            final JToggleButton button = new JToggleButton(selectors[i]);
            button.setName(selectors[i]);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    currentSelection = button.getName();
                    System.out.println(currentSelection);
                }
            });

            if(i == 0)
                button.setSelected(true);

            setGrid(0, i, GridBagConstraints.HORIZONTAL, 1f);
            group.add(button);
            wrapper.add(button, gridBagConstraints);

        }

        add(wrapper);
    }

    private void createMainPanel() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBorder(BorderFactory.createTitledBorder("Common Options"));
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, PANEL_WIDTH));

        for(int i = 0; i < labels.length; i++) {

            String text = labels[i];

            setGrid(0, i, GridBagConstraints.HORIZONTAL, 0.25f);
            JLabel label = new JLabel(text + ":");
            wrapper.add(label, gridBagConstraints);

            setGrid(1, i, GridBagConstraints.HORIZONTAL, 0.75f);
            JTextField input = new JTextField();
            wrapper.add(input, gridBagConstraints);

            textFields.put(labels[i], input);

        }

        optionPanels.put("Main", wrapper);
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
            wrapper.add(label, gridBagConstraints);

            setGrid(1, i, GridBagConstraints.HORIZONTAL, 0.75f);
            JTextField input = new JTextField();
            wrapper.add(input, gridBagConstraints);

            textFields.put(labels[i], input);

        }

        optionPanels.put("Pipe", wrapper);
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

        optionPanels.put("List", wrapper);
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

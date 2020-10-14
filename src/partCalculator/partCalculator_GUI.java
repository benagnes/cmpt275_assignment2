/*  Author: Ben Agnes
    Student ID: 301277322
    email: bagnes@sfu.ca
    date created: Oct 5 2020
    - displays GUI for user to configure part dimensions
      height of the cylinder (h), the radius of the hole (d1), and the thickness of the hollow cylinder (d2)
    - creates and displays visual of part given user specified inputs using makePart class
    - calculates and displays volume of liquid required to fill the part
*/

package partCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Insets;

// Creates a GUI for user to calculate volume of a hollow cylinder based on values entered
// and displays 'scaled' (if values entered too small / big to accurately represent on screen) drawing on GUI
public class partCalculator_GUI extends JFrame implements ActionListener{
    // GUI labels and text fields
    private JLabel labelHeight = new JLabel("Enter height (m):");
    private JLabel labelThickness = new JLabel("Enter thickness (m):");
    private JLabel labelHoleRadius = new JLabel("Enter hole radius (m): ");
    private JLabel labelNeededLiquid = new JLabel("Needed liquid (m\u00B3):");
    private JTextField textHeight = new JTextField(10);
    private JTextField textThickness = new JTextField(10);
    private JTextField textHoleRadius = new JTextField(10);
    private JTextField textDisplayLiquid = new JTextField(12);
    private JButton calcButton = new JButton("calculate");

    // Variables to save GUI inputs
    private double height;
    private double thickness;
    private double holeRadius;

    // need to edit part panel when users presses 'calculate', make it available to all methods
    private JPanel partPanel;

    // Class constructor. Creates and displays GUI with a default example part
    public partCalculator_GUI() {

        // Create panels
        JPanel dataPanel = new JPanel(new GridBagLayout());
        partPanel = new JPanel(new FlowLayout());
        JPanel topBlankPanel = new JPanel();
        JPanel bottomBlankPanel = new JPanel();
        JPanel westPanel = new JPanel();

        // add panel 'borders' to frame
        topBlankPanel.setBackground(Color.LIGHT_GRAY);
        topBlankPanel.setPreferredSize(new Dimension(625,50));
        getContentPane().add(topBlankPanel, BorderLayout.NORTH);

        bottomBlankPanel.setBackground(Color.LIGHT_GRAY);
        bottomBlankPanel.setPreferredSize(new Dimension(625,50));
        getContentPane().add(bottomBlankPanel, BorderLayout.SOUTH);

        westPanel.setBackground(Color.LIGHT_GRAY);
        westPanel.setPreferredSize(new Dimension(25, 300));
        getContentPane().add(westPanel, BorderLayout.WEST);

        // add labels and text fields
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10,0,10,0);

        constraints.gridx = 0;
        constraints.gridy = 0;
        dataPanel.add(labelHeight, constraints);

        constraints.gridx = 1;
        dataPanel.add(textHeight, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        dataPanel.add(labelThickness, constraints);

        constraints.gridx = 1;
        dataPanel.add(textThickness, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        dataPanel.add(labelHoleRadius, constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(10,0,0,0);
        dataPanel.add(textHoleRadius, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.insets = new Insets(10, 0, 0, 5);
        dataPanel.add(labelNeededLiquid, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.insets = new Insets(0, 0, 5, 5);
        textDisplayLiquid.setEditable(false);
        textDisplayLiquid.setBackground(Color.WHITE);
        dataPanel.add(textDisplayLiquid, constraints);

        // add RUN button, runs circuit when user clicks button
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(0,20,0,0);
        calcButton.addActionListener(this);
        dataPanel.add(calcButton, constraints);

        // add data panel to frame
        dataPanel.setBackground(Color.YELLOW);
        setUndecorated(true);
        dataPanel.setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        setUndecorated(false);
        dataPanel.setPreferredSize(new Dimension(300, 200));
        getContentPane().add(dataPanel, BorderLayout.CENTER);

        // add part panel to frame
        partPanel.add(new makePart());
        partPanel.setBackground(Color.LIGHT_GRAY);
        partPanel.setPreferredSize(new Dimension(300, 300));
        getContentPane().add(partPanel, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Part Calculator");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // When 'calculate' button clicked calculate volume of part and display on GUI
    public void actionPerformed(ActionEvent e) {
        // try to read in parameters, if user didn't enter a value in one or more fields display error and return
        try {
            height = Double.parseDouble(textHeight.getText());
            thickness = Double.parseDouble(textThickness.getText());
            holeRadius = Double.parseDouble(textHoleRadius.getText());
        }
        catch (Exception exception) {
            displayExceptionasMsg("Must fill out all fields",
                    exception.getMessage());
            return;
        }

        // verify inputs in valid range
        try {

            if (height <= 0) {
                throw new Exception("Invalid height: height > 0");
            }

            if (thickness < 0) {
                throw new Exception("Invalid thickness: thickness > 0");
            }

            if ((holeRadius < 0)) { //|| (holeRadius >= thickness)) {
                throw new Exception("Invalid hole radius: 0 <= hole radius");
            }
        }
        catch (Exception exception) {
            displayExceptionasMsg("Invalid Input", exception.getMessage());
            return;
        }

        try {
            // remove existing drawing
            Component partComponent = partPanel.getComponent(0);
            partPanel.remove(partComponent);
            partPanel.revalidate();
            // add new part with user-entered values
            partPanel.add(new makePart(height, thickness, holeRadius));
            getVolume_and_Display();
        }
        catch (Exception exception) {
            displayExceptionasMsg("ERROR", exception.getMessage());
        }
    }

    // Calculates liquid needed and display
    private void getVolume_and_Display() {
        // formula is volume = pi * (R^2 - r^2) * h where R = thickness + holeRadius, r = holeRadius
        double neededLiquid = Math.PI * (Math.pow(thickness + holeRadius, 2)
                - Math.pow(holeRadius, 2)) * height;
        String SneededLiquid = String.format("%.5f", neededLiquid);
        textDisplayLiquid.setText(SneededLiquid);
    }

    private void displayExceptionasMsg(String title, String message) {
        JOptionPane.showMessageDialog(null, message,
                title, JOptionPane.WARNING_MESSAGE);
    }
}
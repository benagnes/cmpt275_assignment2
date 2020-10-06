/*  Author: Ben Agnes
    Student ID: 301277322
    email: bagnes@sfu.ca
    date created: Oct 5 2020
    - displays GUI for user to configure part dimensions
      height of the cylinder (h), the radius of the hole (d1), and the thickness of the hollow cylinder (d2)
    - creates and displays visual of part given user specified inputs
    - calculates and displays volume of liquid required to fill the part

*/

package partCalculatorGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class partCalculator_GUI extends JFrame implements ActionListener{
    // GUI label and text fields
    private JLabel labelHeight = new JLabel("Enter height (m):");
    private JLabel labelThickness = new JLabel("File name: ");
    private JLabel labelHoleRadius = new JLabel("Voltage: ");
    private JLabel labelNeededLiquid = new JLabel("Needed liquid (m^3):");
    private JTextField textHeight = new JTextField(10);
    private JTextField textThickness = new JTextField(10);
    private JTextField textHoleRadius = new JTextField(10);
    private JTextField textNeededLiquid = new JTextField(10);
    private JButton calcButton = new JButton("calculate");

    // Variables to save GUI inputs
    private double height;
    private double thickness;
    private double holeRadius;
    private double neededLiquid;

    // Class constructor. Creates and displays GUI for user to enter parameters
    public partCalculator_GUI() {

        // Create panel
        JPanel newPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        // add labels and text fields
        constraints.gridx = 0;
        constraints.gridy = 0;
        newPanel.add(labelHeight, constraints);

        constraints.gridx = 1;
        newPanel.add(textHeight, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        newPanel.add(labelThickness, constraints);

        constraints.gridx = 1;
        newPanel.add(textThickness, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        newPanel.add(labelHoleRadius, constraints);

        constraints.gridx = 1;
        newPanel.add(textHoleRadius, constraints);

        // add RUN button, runs circuit when user clicks button
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        calcButton.addActionListener(this);
        newPanel.add(calcButton, constraints);

        // add panel to frame
        add(newPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Create part and calculate volume");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // When 'run' button clicked read in values then run circuit if all within valid ranges
    public void actionPerformed(ActionEvent e) {
        // read in parameters
        height = Double.parseDouble(textHeight.getText());
        thickness = Double.parseDouble(textThickness.getText());
        holeRadius = Double.parseDouble(textHoleRadius.getText());

        // verify inputs in range and filepath valid
        try {

            if (height <= 0) {
                throw new Exception("Invalid height. height > 0");
            }

            if (thickness <= 0) {
                throw new Exception("Invalid thickness. thickness > 0");
            }

            if ((holeRadius <= 0) || (holeRadius >= thickness)) {
                throw new Exception("Invalid hole radius. 0 < hole radius < thickness");
            }
        }
        catch (Exception exception) {
            displayExceptionasMsg("Invalid Input", exception.getMessage());
            return;
        }

        try {
            calculate();
            displayPart();
        }
        catch (Exception exception) {
            displayExceptionasMsg("ERROR", exception.getMessage());
        }
    }

    // Calculates liquid needed
    private void calculate() throws Exception {

    }

    // display part on frame
    private void displayPart() {

    }

    private void displayExceptionasMsg(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }
}
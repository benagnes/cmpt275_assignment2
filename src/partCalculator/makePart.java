/*  Author: Ben Agnes
    Student ID: 301277322
    email: bagnes@sfu.ca
    date created: Oct 5 2020
    - creates and displays visual of part given user specified inputs
*/

package partCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

// Draws Part on GUI based on input parameters at object creation
public class makePart extends JComponent {
    private int partHeight;
    private int partThickness;
    private int partHoleRadius;
    private int partTotalRadius;

    // default constructor, draws pre-set sized part at program start
    makePart() {
        setPreferredSize(new Dimension(300, 300));
        // default sizes for part
        partHeight = 150;
        partThickness = 50;
        partHoleRadius = 30;
        partTotalRadius = partThickness + partHoleRadius;
    }

    // constructor used when user clicks 'calculate' -> takes in arguments and re-draws part on GUI
    makePart(double height, double thickness, double holeRadius) {
        setPreferredSize(new Dimension(300, 300));

        // scale inputs so the part will always fit on 300x300 panel
        int iHeight;
        int iThickness;
        int iHoleRadius;

        double totalDiameter = (thickness + holeRadius) * 2;
        double heightToWidthRatio = height / totalDiameter;
        double thicknessToHoleRadiusRatio = thickness / holeRadius;

        // scale parameters so that part always fits on 300x300 panel and sizing ratios are preserved
        if (heightToWidthRatio < 1) {
            iHeight = (int) (100 * heightToWidthRatio);
            iThickness = (int) (12.5 * thicknessToHoleRadiusRatio);
            iHoleRadius = (int) (12.5 / thicknessToHoleRadiusRatio);
        } else if ((heightToWidthRatio >= 1) && (heightToWidthRatio < 4)) {
            iHeight = (int) (50 * heightToWidthRatio);
            iThickness = (int) (25 * thicknessToHoleRadiusRatio);
            iHoleRadius = (int) (25 / thicknessToHoleRadiusRatio);
        } else if ((heightToWidthRatio >= 4) && (heightToWidthRatio < 10)) {
            iHeight = (int) (25 * heightToWidthRatio);
            iThickness = (int) (50 * thicknessToHoleRadiusRatio);
            iHoleRadius = (int) (50 / thicknessToHoleRadiusRatio);
        } else {
            iHeight = (int) (12.5 * heightToWidthRatio);
            iThickness = (int) (50 * thicknessToHoleRadiusRatio);
            iHoleRadius = (int) (50 / thicknessToHoleRadiusRatio);
        }

        partHeight = iHeight;
        partThickness = iThickness;
        partHoleRadius = iHoleRadius;
        partTotalRadius = partThickness + partHoleRadius;
    }

    @Override // paints component to panel when constructor called
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2.setRenderingHints(rh);

        // center part on panel
        int topPadding = 150 - (partHeight / 2);
        int botPadding = 150 + (partHeight / 2);
        int leftPadding = 150 - partTotalRadius;
        int rightPadding = 150 + partTotalRadius;

        g2.setColor(Color.BLUE);

        // body
        g2.fillRect(leftPadding, topPadding, partTotalRadius * 2, partHeight);

        // top ellipse
        g2.fill(new Ellipse2D.Double(leftPadding, topPadding - (double)(partTotalRadius / 2),
                partTotalRadius * 2, partTotalRadius)); // last param (h) was 30
        // bottom ellipse
        g2.fill(new Ellipse2D.Double(leftPadding, botPadding - (double)(partTotalRadius / 2),
                partTotalRadius * 2, partTotalRadius));

        // draw hole at top of cylinder
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Double(leftPadding + partThickness,
                topPadding - (double)(partHoleRadius / 2), partHoleRadius * 2, partHoleRadius));

        // outline edges of cylinder
        g2.setColor(Color.BLACK);
        g2.draw(new Ellipse2D.Double(leftPadding, topPadding - (double)(partTotalRadius / 2),
                partTotalRadius * 2, partTotalRadius));

        g2.drawArc(leftPadding, botPadding - (partTotalRadius / 2),
                partTotalRadius * 2, partTotalRadius, 0, -180);

        g2.drawLine(leftPadding, topPadding, leftPadding, botPadding);
        g2.drawLine(rightPadding, topPadding, rightPadding, botPadding);


        // outline hole
        g2.setColor(Color.BLACK);
        g2.draw(new Ellipse2D.Double(leftPadding + partThickness,
                topPadding - (double)(partHoleRadius / 2), partHoleRadius * 2, partHoleRadius));
    }
}

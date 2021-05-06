package Entities;

import GUI.ColorScheme;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class LaneBorderPane extends Pane {
    private Rectangle laneBorder;
    private final int LEFT_MARGIN = 10;

    public LaneBorderPane(String laneType, int xPos, int laneWidth)
    {
        laneBorder = new Rectangle();
        laneType.toLowerCase();
        if (laneType.equals("left"))
        {
            //White line on left edge of the road

            //TODO current problem is that these lines are hard coded
            laneBorder.setX(xPos + LEFT_MARGIN);
            laneBorder.setY(0);
            laneBorder.setHeight(800);
            laneBorder.setWidth(5);
            laneBorder.setFill(ColorScheme.colorGameWhite);
            getChildren().add(laneBorder);

            Line line1 = new Line(xPos + laneWidth, 45, xPos + laneWidth, 845);
            line1.setStroke(ColorScheme.colorGameWhite);
            line1.getStrokeDashArray().addAll(40d, 40d);
            getChildren().add(line1);

            //line2 is for the center-line animation that wasn't implemented
//            Line line2 = new Line(490, 0, 490, 800);
//            line2.getStrokeDashArray().addAll(40d, 40d);
//            line2.setStroke(ColorScheme.colorGameWhite);
//            getChildren().add(line2);
        }
        else if(laneType.equals("middle"))
         {
//            //TODO: Allow N lanes. Later iterations.
//            //values are incorrect, should be a function of numLanes    // great...
            Line line1 = new Line(xPos, 45, xPos, 845);
            line1.setStroke(ColorScheme.colorGameWhite);
            line1.getStrokeDashArray().addAll(40d, 40d);
            getChildren().add(line1);

             Line line2 = new Line(xPos + laneWidth, 45, xPos + laneWidth, 845);
             line2.setStroke(ColorScheme.colorGameWhite);
             line2.getStrokeDashArray().addAll(40d, 40d);
             getChildren().add(line2);
            //line2 is for the center-line animation that wasn't implemented
            //Line line2 = new Line(490, 0, 490, 800);
            //line2.getStrokeDashArray().addAll(40d, 40d);
            //line2.setStroke(ColorScheme.colorGameWhite);
            //getChildren().add(line2);
        }
        else if(laneType.equals("right"))
        {
            //White line on the right edge of the road
            laneBorder.setX(xPos + laneWidth - LEFT_MARGIN);
            laneBorder.setY(0);
            laneBorder.setHeight(800);
            laneBorder.setWidth(5);
            laneBorder.setFill(ColorScheme.colorGameWhite);
            getChildren().add(laneBorder);

            Line line1 = new Line(xPos, 45, xPos , 845);
            line1.setStroke(ColorScheme.colorGameWhite);
            line1.getStrokeDashArray().addAll(40d, 40d);
            getChildren().add(line1);
        }
    }
}
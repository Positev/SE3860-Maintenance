package Entities;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class RightLane implements Lane {
    private LanePane lane;
    private LaneBorderPane border;

    private int startX;
    private int width;


    public RightLane(int startX, int startY, double width, double height){
        this.lane = new LanePane(startX, startY, width, height);
        this.border = new LaneBorderPane("right", startX, (int) width);

        this.startX = startX;
        this.width = (int) width;

    }

    @Override
    public List<Node> getUIChildren() {
        List nodes = new ArrayList();
        nodes.add(this.lane);
        nodes.add(this.border);
        return nodes;
    }

    @Override
    public int getX() {
        return startX;
    }

    @Override
    public int getWidth() {
        return width;
    }

}

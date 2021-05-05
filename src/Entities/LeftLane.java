package Entities;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class LeftLane implements Lane {

    private LanePane lane;
    private LaneBorderPane border;

    public LeftLane(int startX, int startY, double width, double height){
        this.lane = new LanePane(startX, startY, width, height);
        this.border = new LaneBorderPane("left");
    }

    @Override
    public List<Node> getUIChildren() {
        List nodes = new ArrayList();
        nodes.add(this.lane);
        nodes.add(this.border);
        return nodes;
    }
}

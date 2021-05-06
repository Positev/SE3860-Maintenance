package Control;

import Entities.Lane;
import Entities.LeftLane;
import Entities.MiddleLane;
import Entities.RightLane;

import java.util.ArrayList;
import java.util.List;

public class LaneGenerator {

    private int laneWidth;
    private int laneHeight;
    public static final int LANE_MARGIN = 300;
    public static final int MIN_LANES = 2;
    public static final int MAX_LANES = 4;

    public LaneGenerator(int laneWidth, int laneHeight) {
        this.laneWidth = laneWidth;
        this.laneHeight = laneHeight;
    }



    public List<Lane> makeLanes(int n){
        List<Lane> lanes = new ArrayList<>();

        // enforce global range on n
        n = Math.max(MIN_LANES, n);
        n = Math.min(MAX_LANES, n);

        // always expect a right and a left lane so n should be the number of middle lanes
        n -= 2;

        lanes.add(new LeftLane(LANE_MARGIN, 0, this.laneWidth,  this.laneHeight));
        for (int i = 0; i < n; i++) {
            lanes.add(new MiddleLane((int) (LANE_MARGIN +  this.laneWidth * (i + 1)), 0,  this.laneWidth, this.laneHeight));
        }
        lanes.add(new RightLane((int) (LANE_MARGIN +  this.laneWidth * (n + 1)), 0,  this.laneWidth, this.laneHeight));


        return lanes;
    }
}

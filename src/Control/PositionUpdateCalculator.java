package Control;

import Entities.Lane;
import IO.Operator;
import Utils.Tuple;

import java.util.List;
import java.util.Random;

public class PositionUpdateCalculator {

    public static final int STARTING_LANE = 0;


    private Tuple<Double, Double> carImageSize = new Tuple<>(0.0,0.0);
    private Tuple<Double, Double> donkeyImageSize = new Tuple<>(0.0,0.0);
    private Tuple<Double, Double> laneSize = new Tuple<>(0.0,0.0);

    private List<Lane> lanes;
    private int currentCarLane;
    private int currentDonkeyLane;
    private int originalDonkeyLane;
    private Operator moveFromMiddleThree;
    private Random randomCalc = new Random();

    public PositionUpdateCalculator(List<Lane> lanes){
        setLanes(lanes);
        setCurrentCarLane(STARTING_LANE);
    }

    public Tuple<Double, Double> calculateDonkeyInitialPosition(){
        double x = -(getDonkeyImageSize().getX()/2);
        return new Tuple<>(x, 0.0);
    }

    public Tuple<Double, Double> calculateDonkeyLaneChangePosition(Operator direction, Tuple<Double,Double> current){
        double deltaX = 0.0;

        if (direction == Operator.LEFT){
            if (currentDonkeyLane > 0){

                currentDonkeyLane -= 1;
            }
        }
        else if (direction == Operator.RIGHT){
            if (currentDonkeyLane < this.lanes.size() - 1){

                currentDonkeyLane += 1;
            }
        }

        Lane occupiedLane = this.lanes.get(currentDonkeyLane);
        deltaX = (- occupiedLane.getWidth() - donkeyImageSize.getX() / 2 ) + currentDonkeyLane * occupiedLane.getWidth();
        return new Tuple<>(deltaX, current.getY());
    }


    public Tuple<Double, Double> calculateDonkeyForwardAdvance(double distance, Tuple<Double,Double> current){
        double deltaY =  distance;
        return new Tuple<>(current.getX(), current.getY() + deltaY);
    }

    public Tuple<Double, Double> calculateDonkeyDiagonalAdvance(double distance, Tuple<Double,Double> current){
        if(current.getY() == 0){
            originalDonkeyLane = currentDonkeyLane;
            if(this.lanes.size() == 3){
                int decideDirection = randomCalc.nextInt(2);
                if(decideDirection == 0){
                    moveFromMiddleThree = Operator.LEFT;
                }
                else{
                    moveFromMiddleThree = Operator.RIGHT;
                }
            }
        }
        double deltaY = distance;
        double deltaX;

        if(this.lanes.size() == 3){
            deltaX = calculateThreeLaneMovement();
            return new Tuple<>(deltaX, current.getY() + deltaY);
        }
        else if(this.lanes.size() == 4){
            deltaX = calculateFourLaneMovement();
            return new Tuple<>(deltaX, current.getY() + deltaY);
        }
        else{ //This should not be run at any point, but this is for security against errors.
            return new Tuple<>(current.getX(), current.getY() + deltaY); //Essentially this would run if there were two lanes
        } //But this method shouldn't be run if there are two lanes
    }

    //I moved this into its own method so that the main diagonal method was less clustered
    private Double calculateThreeLaneMovement(){
        if(currentDonkeyLane == 0){
            currentDonkeyLane += 1;
        }
        else if(currentDonkeyLane == 2){
            currentDonkeyLane -= 1;
        }
        else{
            if(currentDonkeyLane == originalDonkeyLane){
                if(moveFromMiddleThree == Operator.LEFT){
                    currentDonkeyLane -= 1;
                }
                else{
                    currentDonkeyLane += 1;
                }
            }
            else{
                currentDonkeyLane = originalDonkeyLane;
            }
        }

        Lane occupiedLane = this.lanes.get(currentDonkeyLane);
        double deltaX = (- occupiedLane.getWidth() - donkeyImageSize.getX() / 2 ) + currentDonkeyLane * occupiedLane.getWidth();
        return deltaX;
    }
    //I also moved this into its own method so the main diagonal movement is less clustered
    private Double calculateFourLaneMovement(){
        if(currentDonkeyLane == 0){
            currentDonkeyLane += 1;
        }
        else if(currentDonkeyLane == 3){
            currentDonkeyLane -= 1;
        }
        else{
            if(currentDonkeyLane == originalDonkeyLane){
                int determineMovement = randomCalc.nextInt(2);
                if(determineMovement == 0){
                    currentDonkeyLane -= 1;
                }
                else{
                    currentDonkeyLane += 1;
                }
            }
            else{
                currentDonkeyLane = originalDonkeyLane;
            }
        }

        Lane occupiedLane = this.lanes.get(currentDonkeyLane);
        double deltaX = (- occupiedLane.getWidth() - donkeyImageSize.getX() / 2 ) + currentDonkeyLane * occupiedLane.getWidth();
        return deltaX;
    }

    public Tuple<Double, Double>  calculateLaneChangePosition(Operator direction, Tuple<Double,Double> current){
        double deltaX = 0.0;

        if (direction == Operator.LEFT){
            if (currentCarLane > 0){

                currentCarLane -= 1;
            }
        }
        else if (direction == Operator.RIGHT){
            if (currentCarLane < this.lanes.size() - 1){

                currentCarLane += 1;
            }
        }


        Lane occupiedLane = this.lanes.get(currentCarLane);
        deltaX = (- occupiedLane.getWidth() - carImageSize.getX() / 2 ) + currentCarLane * occupiedLane.getWidth();


        return new Tuple<>(deltaX,current.getY());
    }

    public Tuple<Double, Double>  calculateCarInitialPosition(){
        double x = -(getCarImageSize().getX() / 2);
        double y = getLaneSize().getY() - (getLaneSize().getY() / 4) - 50;

        return new Tuple<>(x,y);
    }

    public Tuple<Double, Double>  calculateCarMoveForwardPosition(double distance, Tuple<Double,Double> current){

        double deltaY = getCarImageSize().getY() - distance;
        return new Tuple<>(current.getX(),current.getY() - deltaY);
    }

    public Tuple<Double, Double> getCarImageSize() {
        return carImageSize;
    }

    public void setCarImageSize(Double x, Double y) {
        this.carImageSize.setX(x);
        this.carImageSize.setY(y);
    }

    public Tuple<Double, Double> getDonkeyImageSize() {
        return donkeyImageSize;
    }

    public void setDonkeyImageSize(Double x, Double y) {
        this.donkeyImageSize.setX(x);
        this.donkeyImageSize.setY(y);

    }

    public Tuple<Double, Double> getLaneSize() {
        return laneSize;

    }

    public void setLaneSize(Double x, Double y) {
        this.laneSize.setX(x);
        this.laneSize.setY(y);
    }

    public void setLanes(List<Lane> lanes) {
        this.lanes = lanes;
    }

    public void setCurrentCarLane(int currentCarLane) {
        this.currentCarLane = currentCarLane;
    }
    public void setCurrentDonkeyLane(int currentDonkeyLane) {
        this.currentDonkeyLane = currentDonkeyLane;
    }
}

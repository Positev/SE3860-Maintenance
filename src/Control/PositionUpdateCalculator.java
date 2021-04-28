package Control;

import IO.Operator;
import Utils.Tuple;

public class PositionUpdateCalculator {

    private Tuple<Double, Double> carImageSize = new Tuple<>(0.0,0.0);
    private Tuple<Double, Double> donkeyImageSize = new Tuple<>(0.0,0.0);
    private Tuple<Double, Double> laneSize = new Tuple<>(0.0,0.0);

    public Tuple<Double, Double> calculateDonkeyInitialPosition(){
        double x = -(getDonkeyImageSize().getX()/2);
        return new Tuple<>(x, 0.0);
    }

    public Tuple<Double, Double> calculateDonkeyLaneChangePosition(Operator direction, Tuple<Double,Double> current){
        double deltaX = 0.0;

        if (direction == Operator.LEFT){
            deltaX = -getLaneSize().getX() - (getDonkeyImageSize().getX() / 2);
        }
        else if (direction == Operator.RIGHT){
            deltaX = -getDonkeyImageSize().getX() / 2;
        }
        else{}

        return new Tuple<>(deltaX, current.getY());
    }

    public Tuple<Double, Double> calculateDonkeyForwardAdvance(double distance, Tuple<Double,Double> current){
        double deltaY =  distance;
        return new Tuple<>(current.getX(), current.getY() + deltaY);
    }


    public Tuple<Double, Double>  calculateLaneChangePosition(Operator direction, Tuple<Double,Double> current){
        double deltaX = 0.0;

        if (direction == Operator.LEFT){
            deltaX = -getLaneSize().getX() - (getCarImageSize().getX() / 2);
        }
        else if (direction == Operator.RIGHT){
            deltaX = -getCarImageSize().getX() / 2;
        }
        else{}

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
}

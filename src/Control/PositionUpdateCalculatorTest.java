package Control;


import IO.Operator;
import Utils.Tuple;
import com.sun.javafx.collections.SourceAdapterChange;

import static org.junit.jupiter.api.Assertions.*;

class PositionUpdateCalculatorTest {
    private PositionUpdateCalculator calculator;

    @org.junit.jupiter.api.BeforeEach
    void BeforeEach(){
        LaneGenerator laneGenerator = new LaneGenerator(250, 800);
        calculator = new PositionUpdateCalculator(laneGenerator.makeLanes(2));
        calculator.setDonkeyImageSize(200d,200d);
        calculator.setLaneSize(250d,800d);
        calculator.setCarImageSize(200d,400d);
    }

    @org.junit.jupiter.api.Test
    void calculateDonkeyInitialPosition() {

        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        Tuple<Double, Double> expected = new Tuple(-100d,0d);

        assertEquals(expected,actual);

    }

    @org.junit.jupiter.api.Test
    void calculateDonkeyLaneChangePosition() {

        Tuple<Double,Double> position = calculator.calculateDonkeyInitialPosition();
        Tuple<Double, Double> actual = calculator.calculateDonkeyLaneChangePosition(Operator.LEFT, position);
        Tuple<Double, Double> expected = new Tuple(-350.0,0.0);

        assertEquals(expected, actual);

        expected = calculator.calculateDonkeyInitialPosition();
        actual = calculator.calculateDonkeyLaneChangePosition(Operator.RIGHT, position);


        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void calculateDonkeyForwardAdvance() {
        Tuple<Double,Double> position = calculator.calculateDonkeyInitialPosition();
        Tuple<Double, Double> actual = calculator.calculateDonkeyForwardAdvance(50, position);
        Tuple<Double, Double> expected = new Tuple(-100.0,50.0);

        assertEquals(expected, actual);

        actual = calculator.calculateDonkeyForwardAdvance(50, actual);
        expected = new Tuple(-100.0,100.0);

        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void calculateLaneChangePosition() {

        Tuple<Double,Double> position = calculator.calculateCarInitialPosition();
        Tuple<Double, Double> actual = calculator.calculateLaneChangePosition(Operator.LEFT, position);
        Tuple<Double, Double> expected = new Tuple(-350.0,550.0);

        assertEquals(expected, actual);

        expected = calculator.calculateCarInitialPosition();
        actual = calculator.calculateLaneChangePosition(Operator.RIGHT, position);


        assertEquals(expected, actual);
    }

    @org.junit.jupiter.api.Test
    void calculateCarInitialPosition() {

        Tuple<Double, Double> actual = calculator.calculateCarInitialPosition();
        Tuple<Double, Double> expected = new Tuple(-100d,550d);

        assertEquals(expected,actual);

    }

    @org.junit.jupiter.api.Test
    void calculateCarMoveForwardPosition() {
        Tuple<Double,Double> position = calculator.calculateCarInitialPosition();
        Tuple<Double, Double> actual = calculator.calculateCarMoveForwardPosition(50, position);
        Tuple<Double, Double> expected = new Tuple(-100.0,500.0);

        assertEquals(expected, actual);

        actual = calculator.calculateCarMoveForwardPosition(50, actual);
        expected = new Tuple(-100.0,450.0);

        assertEquals(expected, actual);
    }
}
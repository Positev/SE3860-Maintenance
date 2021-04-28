package Control;


import IO.Operator;
import Utils.Tuple;
import com.sun.javafx.collections.SourceAdapterChange;

import static org.junit.jupiter.api.Assertions.*;

public class DonkeyMovementTest {
    private PositionUpdateCalculator calculator;

    @org.junit.jupiter.api.BeforeEach
    void BeforeEach(){
        calculator = new PositionUpdateCalculator();
        calculator.setDonkeyImageSize(200d,200d);
        calculator.setLaneSize(250d,800d);
        calculator.setCarImageSize(200d,400d);
    }

    @org.junit.jupiter.api.Test
    void CanMoveLeftFalse(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        actual = calculator.calculateDonkeyLaneChangePosition(Operator.LEFT, actual);
        Tuple<Double, Double> unexpected = new Tuple(-350.0,0.0);

        assertFalse(!actual.equals(unexpected));
    }

    @org.junit.jupiter.api.Test
    void CanMoveRightFalse(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        Tuple<Double, Double> unexpected = new Tuple(-100.0, 0.0);

        assertFalse(!actual.equals(unexpected));
    }

    @org.junit.jupiter.api.Test
    void CanMoveOriginalLaneTrue(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        actual = calculator.calculateDonkeyLaneChangePosition(Operator.LEFT, actual);
        Tuple<Double, Double> unexpected = new Tuple(-100.0, 0.0);

        assertTrue(!actual.equals(unexpected));
    }

    @org.junit.jupiter.api.Test
    void CanMoveLeftTrue(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        Tuple<Double, Double> unexpected = new Tuple(-350.0,0.0);

        assertTrue(!actual.equals(unexpected));
    }

    @org.junit.jupiter.api.Test
    void CanMoveRightTrue(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        actual = calculator.calculateDonkeyLaneChangePosition(Operator.LEFT, actual);
        Tuple<Double, Double> unexpected = new Tuple(-100.0, 0.0);

        assertTrue(!actual.equals(unexpected));
    }
}

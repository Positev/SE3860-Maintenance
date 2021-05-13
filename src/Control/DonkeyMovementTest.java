package Control;


import IO.Operator;
import Utils.Tuple;
import com.sun.javafx.collections.SourceAdapterChange;

import static org.junit.jupiter.api.Assertions.*;

public class DonkeyMovementTest {
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
    void CanMoveLeftFalse(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        actual = calculator.calculateDonkeyLaneChangePosition(Operator.LEFT, actual);
        Tuple<Double, Double> unexpected = calculator.calculateDonkeyInitialPosition();
        unexpected = calculator.calculateDonkeyLaneChangePosition(Operator.LEFT, unexpected);

        assertFalse(!actual.equals(unexpected));
    }

    @org.junit.jupiter.api.Test
    void CanMoveRightFalse(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        actual = calculator.calculateDonkeyLaneChangePosition(Operator.RIGHT, actual);
        Tuple<Double, Double> unexpected = new Tuple(-100.0, 0.0);
        unexpected = calculator.calculateDonkeyLaneChangePosition(Operator.RIGHT, unexpected);

        assertFalse(!actual.equals(unexpected));
    }

    @org.junit.jupiter.api.Test
    void CanMoveOriginalLaneTrue(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        actual = calculator.calculateDonkeyLaneChangePosition(Operator.LEFT, actual);
        Tuple<Double, Double> unexpected = calculator.calculateCarInitialPosition();

        assertTrue(!actual.equals(unexpected));
    }

    @org.junit.jupiter.api.Test
    void CanMoveLeftTrue(){
        Tuple<Double, Double> actual = calculator.calculateDonkeyInitialPosition();
        Tuple<Double, Double> unexpected = calculator.calculateDonkeyInitialPosition();
        unexpected = calculator.calculateDonkeyLaneChangePosition(Operator.LEFT, unexpected);

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

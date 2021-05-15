package Control;

import Entities.Donkey;
import IO.Operator;
import SoundFX.Sounds;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Arrays;
import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class ResetDonkeyTest {

    private Game mockGame;

    @Start
    private void start(Stage stage) throws Exception {
        mockGame = Mockito.spy(Game.class);
        mockGame.start(stage);
    }

    @Test
    void testHitBoxMovedLane2() {
        int ExpectedXPos = -70;
        int ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        int laneCount = 2;
        mockGame.laneCount = laneCount;
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.positionCalculator = Mockito.mock(PositionUpdateCalculator.class);;
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(1).when(mockRand).nextInt(laneCount);
        //Unable to mock final methods for Stack Pane
        mockPane.setTranslateY(ExpectedYPos);
        mockPane.setTranslateX(ExpectedXPos);

        mockGame.resetDonkey();
        Mockito.verify(mockGame.donkey, times(1)).movedonkeyHitBox(ExpectedXPos, ExpectedYPos);

        }

    @Test
    void testHitBoxMovedLane1() {
        int ExpectedXPos = -200;
        int ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        int laneCount = 2;
        mockGame.laneCount = laneCount;
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.positionCalculator = Mockito.mock(PositionUpdateCalculator.class);;
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(0).when(mockRand).nextInt(laneCount);
        //Unable to mock final methods for Stack Pane
        mockPane.setTranslateY(ExpectedYPos);
        mockPane.setTranslateX(ExpectedXPos);

        mockGame.resetDonkey();
        Mockito.verify(mockGame.donkey, times(1)).movedonkeyHitBox(ExpectedXPos, ExpectedYPos);

    }

    @Test
    void testHitBoxMovedLane3() {
        int ExpectedXPos = -200;
        int ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        int laneCount = 3;
        mockGame.laneCount = laneCount;
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.positionCalculator = Mockito.mock(PositionUpdateCalculator.class);;
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(2).when(mockRand).nextInt(laneCount);
        //Unable to mock final methods for Stack Pane
        mockPane.setTranslateY(ExpectedYPos);
        mockPane.setTranslateX(ExpectedXPos);

        mockGame.resetDonkey();
        Mockito.verify(mockGame.donkey, times(1)).movedonkeyHitBox(ExpectedXPos, ExpectedYPos);

    }

    @Test
    void testHitBoxMovedLane4() {
        int ExpectedXPos = -200;
        int ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        int laneCount = 3;
        mockGame.laneCount = laneCount;
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.positionCalculator = Mockito.mock(PositionUpdateCalculator.class);;
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(3).when(mockRand).nextInt(laneCount);
        //Unable to mock final methods for Stack Pane
        mockPane.setTranslateY(ExpectedYPos);
        mockPane.setTranslateX(ExpectedXPos);

        mockGame.resetDonkey();
        Mockito.verify(mockGame.donkey, times(1)).movedonkeyHitBox(ExpectedXPos, ExpectedYPos);

    }

    @Test
    void testDonkeyPaneMovedLane1() {
        double ExpectedXPos = -200;
        double ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        int laneCount = 2;
        mockGame.laneCount = laneCount;
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.positionCalculator = Mockito.mock(PositionUpdateCalculator.class);;
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(0).when(mockRand).nextInt(laneCount);
        Mockito.doAnswer(invocationOnMock -> {
            mockPane.setTranslateX(ExpectedXPos);
            return null;
        }).when(mockGame).moveDonkey(Operator.LEFT);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, never()).moveDonkey(Operator.RIGHT);
        Assertions.assertEquals(ExpectedXPos, mockPane.translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedYPos, mockPane.translateYProperty().doubleValue(), 0.001);

    }

    @Test
    void testDonkeyPaneMovedLane2() {
        double ExpectedXPos = -70;
        double ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        int laneCount = 2;
        mockGame.laneCount = laneCount;
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.positionCalculator = Mockito.mock(PositionUpdateCalculator.class);;
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(1).when(mockRand).nextInt(laneCount);
        Mockito.doAnswer(invocationOnMock -> {
            mockPane.setTranslateX(ExpectedXPos);
            return null;
        }).when(mockGame).moveDonkey(Operator.RIGHT);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, times(1)).moveDonkey(Operator.RIGHT);
        Assertions.assertEquals(ExpectedXPos, mockPane.translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedYPos, mockPane.translateYProperty().doubleValue(), 0.001);

    }

    @Test
    void testDonkeyPaneMovedLane3() {
        double ExpectedXPos = -70;
        double ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        int laneCount = 3;
        mockGame.laneCount = laneCount;
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.positionCalculator = Mockito.mock(PositionUpdateCalculator.class);;
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(2).when(mockRand).nextInt(laneCount);
        Mockito.doAnswer(invocationOnMock -> {
            mockPane.setTranslateX(ExpectedXPos);
            return null;
        }).when(mockGame).moveDonkey(Operator.RIGHT);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, times(2)).moveDonkey(Operator.RIGHT);
        Assertions.assertEquals(ExpectedXPos, mockPane.translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedYPos, mockPane.translateYProperty().doubleValue(), 0.001);

    }

    @Test
    void testDonkeyPaneMovedLane4() {
        double ExpectedXPos = -70;
        double ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        int laneCount = 3;
        mockGame.laneCount = laneCount;
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.positionCalculator = Mockito.mock(PositionUpdateCalculator.class);;
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(3).when(mockRand).nextInt(laneCount);
        Mockito.doAnswer(invocationOnMock -> {
            mockPane.setTranslateX(ExpectedXPos);
            return null;
        }).when(mockGame).moveDonkey(Operator.RIGHT);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, times(3)).moveDonkey(Operator.RIGHT);
        Assertions.assertEquals(ExpectedXPos, mockPane.translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedYPos, mockPane.translateYProperty().doubleValue(), 0.001);

    }


    @Test
    void testResetDonkeyIntegrationLane2() {
        double expectedX = 0;
        double expectedY = 0;

        mockGame.sounds = Mockito.spy(Sounds.class);
        mockGame.setRandom(new Random(0));
        mockGame.laneCount = 2;
        mockGame.lanes =  mockGame.laneGenerator.makeLanes( 2);
        mockGame.positionCalculator = new PositionUpdateCalculator(mockGame.lanes);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, atLeastOnce()).moveDonkey(Mockito.any(Operator.class));
        Mockito.verify(mockGame.sounds, times(1)).playDonkeySounds();

        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorX(), mockGame.getDonkeyPaneBox().translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorY(), mockGame.getDonkeyPaneBox().translateYProperty().doubleValue(), 0.001);
        Assertions.assertEquals(expectedX, mockGame.donkey.getHitbox().getBoxAnchorX());
        Assertions.assertEquals(expectedY, mockGame.donkey.getHitbox().getBoxAnchorY());
    }

    @Test
    void testResetDonkeyIntegrationLane1() {
        double expectedX = -190;
        double expectedY = 0;

        mockGame.sounds = Mockito.spy(Sounds.class);
        mockGame.setRandom(new Random(1565254165));
        mockGame.laneCount = 2;
        mockGame.lanes =  mockGame.laneGenerator.makeLanes( 2);
        mockGame.positionCalculator = new PositionUpdateCalculator(mockGame.lanes);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, atLeastOnce()).moveDonkey(Mockito.any(Operator.class));
        Mockito.verify(mockGame.sounds, times(1)).playDonkeySounds();

        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorX(), mockGame.getDonkeyPaneBox().translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorY(), mockGame.getDonkeyPaneBox().translateYProperty().doubleValue(), 0.001);
        Assertions.assertEquals(expectedX, mockGame.donkey.getHitbox().getBoxAnchorX());
        Assertions.assertEquals(expectedY, mockGame.donkey.getHitbox().getBoxAnchorY());
    }

    @Test
    void testResetDonkeyIntegrationLane3() {
        double expectedX = 190;
        double expectedY = 0;

        mockGame.sounds = Mockito.spy(Sounds.class);
        mockGame.setRandom(new Random(298498195));
        mockGame.laneCount = 3;
        mockGame.lanes =  mockGame.laneGenerator.makeLanes( 3);
        mockGame.positionCalculator = new PositionUpdateCalculator(mockGame.lanes);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, atLeastOnce()).moveDonkey(Mockito.any(Operator.class));
        Mockito.verify(mockGame.sounds, times(1)).playDonkeySounds();

        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorX(), mockGame.getDonkeyPaneBox().translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorY(), mockGame.getDonkeyPaneBox().translateYProperty().doubleValue(), 0.001);
        Assertions.assertEquals(expectedX, mockGame.donkey.getHitbox().getBoxAnchorX());
        Assertions.assertEquals(expectedY, mockGame.donkey.getHitbox().getBoxAnchorY());
    }

    @Test
    void testResetDonkeyIntegrationLane4() {
        double expectedX = 380;
        double expectedY = 0;

        mockGame.sounds = Mockito.spy(Sounds.class);
        mockGame.setRandom(new Random(9000));
        mockGame.laneCount = 4;
        mockGame.lanes =  mockGame.laneGenerator.makeLanes( 4);
        mockGame.positionCalculator = new PositionUpdateCalculator(mockGame.lanes);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, atLeastOnce()).moveDonkey(Mockito.any(Operator.class));
        Mockito.verify(mockGame.sounds, times(1)).playDonkeySounds();

        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorX(), mockGame.getDonkeyPaneBox().translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorY(), mockGame.getDonkeyPaneBox().translateYProperty().doubleValue(), 0.001);
        Assertions.assertEquals(expectedX, mockGame.donkey.getHitbox().getBoxAnchorX());
        Assertions.assertEquals(expectedY, mockGame.donkey.getHitbox().getBoxAnchorY());
    }


    @RepeatedTest(30)
    void testResetDonkeyIntegrationRandom() {
        double[] expectedX = {-259, -69, 121, 311};
        double expectedY = 0;

        mockGame.sounds = Mockito.spy(Sounds.class);
        mockGame.setRandom(new Random());

        mockGame.resetDonkey();
        Mockito.verify(mockGame, atLeastOnce()).moveDonkey(Mockito.any(Operator.class));
        Mockito.verify(mockGame.sounds, times(1)).playDonkeySounds();

        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorX(), mockGame.getDonkeyPaneBox().translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorY(), mockGame.getDonkeyPaneBox().translateYProperty().doubleValue(), 0.001);
        Assertions.assertTrue(Arrays.stream(expectedX).anyMatch(i -> i == mockGame.donkey.getHitbox().getBoxAnchorX()));
        Assertions.assertEquals(expectedY, mockGame.donkey.getHitbox().getBoxAnchorY());
    }
}
package Control;

import IO.Operator;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.Random;

import static org.mockito.Mockito.times;

@ExtendWith(ApplicationExtension.class)
class ResetDonkeyTest {

    private Game mockGame;

    @Start
    private void start(Stage stage) throws Exception {
        mockGame = Mockito.spy(Game.class);
        mockGame.start(stage);
    }

    @Test
    void testIsResetLeft() {

        Random mockRand = Mockito.mock(Random.class);
        Mockito.doReturn(1).when(mockRand).nextInt(2);
        mockGame.setRandom(mockRand);
        double ExpectedXPosition = -259;
        double ExpectedYPosition = 0;

        Mockito.doAnswer(invocation -> {
            mockGame.getDonkeyPaneBox().setTranslateX(ExpectedXPosition);
            return null;
        }).when(mockGame).moveDonkey(Operator.LEFT);
        mockGame.resetDonkey();
        Mockito.verify(mockGame, times(1)).moveDonkey(Operator.LEFT);
        Mockito.verify(mockGame, times(0)).moveDonkey(Operator.RIGHT);
        Assertions.assertEquals(ExpectedXPosition, mockGame.getDonkeyPaneBox().translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedYPosition, mockGame.getDonkeyPaneBox().translateYProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedXPosition, mockGame.donkey.getHitbox().getBoxAnchorX(), 0.001);
        Assertions.assertEquals(ExpectedYPosition, mockGame.donkey.getHitbox().getBoxAnchorY(), 0.001);
    }

    @Test
    void testIsResetRight() {

        Random mockRand = Mockito.mock(Random.class);
        Mockito.doReturn(0).when(mockRand).nextInt(2);
        mockGame.setRandom(mockRand);
        double ExpectedXPosition = -69;
        double ExpectedYPosition = 0;

        Mockito.doAnswer(invocation -> {
            mockGame.getDonkeyPaneBox().setTranslateX(ExpectedXPosition);
            return null;
        }).when(mockGame).moveDonkey(Operator.RIGHT);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, times(0)).moveDonkey(Operator.LEFT);
        Mockito.verify(mockGame, times(1)).moveDonkey(Operator.RIGHT);
        Assertions.assertEquals(ExpectedXPosition, mockGame.getDonkeyPaneBox().translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedYPosition, mockGame.getDonkeyPaneBox().translateYProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedXPosition, mockGame.donkey.getHitbox().getBoxAnchorX(), 0.001);
        Assertions.assertEquals(ExpectedYPosition, mockGame.donkey.getHitbox().getBoxAnchorY(), 0.001);
    }

    @Test
    void testRandOfTwo() {

        Random mockRand = Mockito.mock(Random.class);
        Mockito.doReturn(0).when(mockRand).nextInt(2);
        mockGame.setRandom(mockRand);
        mockGame.resetDonkey();
        Mockito.verify(mockRand, times(1)).nextInt(2);
    }
}
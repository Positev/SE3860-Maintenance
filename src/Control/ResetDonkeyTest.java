package Control;

import Entities.Donkey;
import IO.Operator;
import SoundFX.Sounds;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

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
    void testHitBoxMovedRight() {
        int ExpectedXPos = -70;
        int ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(0).when(mockRand).nextInt(2);
        //Unable to mock final methods for Stack Pane
        mockPane.setTranslateY(ExpectedYPos);
        mockPane.setTranslateX(ExpectedXPos);

        mockGame.resetDonkey();
        Mockito.verify(mockGame.donkey, times(1)).movedonkeyHitBox(ExpectedXPos, ExpectedYPos);

        }

    @Test
    void testHitBoxMovedLeft() {
        int ExpectedXPos = -200;
        int ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(1).when(mockRand).nextInt(2);
        //Unable to mock final methods for Stack Pane
        mockPane.setTranslateY(ExpectedYPos);
        mockPane.setTranslateX(ExpectedXPos);

        mockGame.resetDonkey();
        Mockito.verify(mockGame.donkey, times(1)).movedonkeyHitBox(ExpectedXPos, ExpectedYPos);

    }

    @Test
    void testDonkeyPaneMovedLeft() {
        double ExpectedXPos = -200;
        double ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(1).when(mockRand).nextInt(2);
        Mockito.doAnswer(invocationOnMock -> {
            mockPane.setTranslateX(ExpectedXPos);
            return null;
        }).when(mockGame).moveDonkey(Operator.LEFT);

        mockGame.resetDonkey();
        Assertions.assertEquals(ExpectedXPos, mockPane.translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedYPos, mockPane.translateYProperty().doubleValue(), 0.001);

    }

    @Test
    void testDonkeyPaneMovedRight() {
        double ExpectedXPos = -70;
        double ExpectedYPos = 0;

        mockGame = Mockito.mock(Game.class);
        Mockito.doCallRealMethod().when(mockGame).resetDonkey();
        Mockito.doCallRealMethod().when(mockGame).setRandom(Mockito.any(Random.class));
        Mockito.doCallRealMethod().when(mockGame).setDonkeyPaneBox(Mockito.any(StackPane.class));
        Random mockRand = Mockito.mock(Random.class);
        StackPane mockPane = Mockito.mock(StackPane.class);
        mockGame.setRandom(mockRand);
        mockGame.setDonkeyPaneBox(mockPane);
        mockGame.sounds = Mockito.mock(Sounds.class);
        mockGame.donkey = Mockito.mock(Donkey.class);

        Mockito.doReturn(0).when(mockRand).nextInt(2);
        Mockito.doAnswer(invocationOnMock -> {
            mockPane.setTranslateX(ExpectedXPos);
            return null;
        }).when(mockGame).moveDonkey(Operator.RIGHT);

        mockGame.resetDonkey();
        Assertions.assertEquals(ExpectedXPos, mockPane.translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(ExpectedYPos, mockPane.translateYProperty().doubleValue(), 0.001);

    }


    @Test
    void SystemTest() {

        mockGame.sounds = Mockito.spy(Sounds.class);
        Random mockRand = Mockito.spy(Random.class);
        mockGame.setRandom(mockRand);

        mockGame.resetDonkey();
        Mockito.verify(mockGame, atLeastOnce()).moveDonkey(Mockito.any(Operator.class));
        Mockito.verify(mockGame.sounds, times(1)).playDonkeySounds();
        Mockito.verify(mockRand, times(1)).nextInt(2);

        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorX(), mockGame.getDonkeyPaneBox().translateXProperty().doubleValue(), 0.001);
        Assertions.assertEquals(mockGame.donkey.getHitbox().getBoxAnchorY(), mockGame.getDonkeyPaneBox().translateYProperty().doubleValue(), 0.001);
    }
}
package Control;

import Entities.Car;
import Entities.Donkey;
import Entities.Lane;
import GUI.ColorScheme;
import IO.InputParser;
import IO.Operator;
import SoundFX.Sounds;
import Utils.Tuple;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class Game extends Application {


    Sounds sounds = new Sounds();

    //Stage and stage dimensions
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 800;
    private static final int MAX_LANES = 4;
    private static final int MIN_LANES = 2;

    private float gameSpeed = 5;
    private float MAX_GAME_SPEED = 1; // Used as delay so lower is higher
    private float MIN_GAME_SPEED = 10;

    private Stage stage;


    //Title screen Scene and Labels
    private Scene TitleScreen;
    private Label LabelIBM, LabelDonkey, LabelPC, LabelRemaster, LabelAuthor, LabelContinue;

    //Game screen Scene, Panes, ImageViews, label VBoxes, and Labels
    private Scene GameScreen;
    //regular car / donkey panes and views
    private StackPane carPaneBox;
    private StackPane donkeyPaneBox;
    private ImageView carImageView;
    private ImageView donkeyImageView;
    //explosion car / donkey panes and views
    private StackPane carRightPaneBox;
    private StackPane carLeftPaneBox;
    private StackPane donkeyRightPaneBox;
    private StackPane donkeyLeftPaneBox;
    private ImageView carRightImageView;
    private ImageView carLeftImageView;
    private ImageView donkeyRightImageView;
    private ImageView donkeyLeftImageView;
    private boolean crashAnimationActive = false;
    //score-keeping label VBoxes and Labels
    private VBox labelPane1;
    private VBox labelPane2;
    private VBox labelPane3;
    private Label LabelDonkeyScore, LabelDonkeyNum, LabelDriverScore, LabelDriverNum, LabelInstructions;
    private int donkeyPassedCount = 0;//starting label counters at 0
    private int carScore = 0;
    private int donkeyScore = 0;
    private int DonkeyNum = 0;
    private int DriverNum = 0;

    //Prepare lanes to be drawn on the screen
    private static final int LANELEFTMARGIN = 300;//Distance of left edge from left of the screen
    private final double xLaneWidth = 190, yLaneHeight = HEIGHT;

    private double randomVariable;//Determines donkey spawn lane after a reset
    private int wiggleRoom = 150;//distance from bottom of donkey spawn to win
    private int DIAGONAL_NUM = 2;//Constant to help randomly decide when donkey can move diagonally

    private InputParser inputParser;
    private Timeline loop;
    private Timeline crashLoop;
    private Timeline diagonalLoop;
    private boolean hasGoneDiagonal = false;
    private Random random = new Random();

    //TODO: make the images and whatnot in Car and Donkey static so we don't need to make "new" here
    Car car = new Car(0, 0);
    Donkey donkey = new Donkey(0, 0);
    int laneCount = random.nextInt(MAX_LANES + 1 - MIN_LANES) + MIN_LANES;

    LaneGenerator laneGenerator = new LaneGenerator((int) getxLaneWidth(), (int) getyLaneHeight());
    List<Lane> lanes = laneGenerator.makeLanes( laneCount);
    PositionUpdateCalculator positionCalculator = new PositionUpdateCalculator(lanes);



    public void start( Stage stage1 ) throws Exception {
        ////////////////////////////////////////////////////////////////
        //                   Title Page UI                            //
        ////////////////////////////////////////////////////////////////

        createStage(stage1);

        Rectangle backgroundTitle = new Rectangle(WIDTH, HEIGHT);
        Rectangle backgroundGame = new Rectangle(WIDTH, HEIGHT);

        Rectangle greenTitleBox1 = new Rectangle(450, 125);
        Rectangle greenTitleBox2 = new Rectangle(447, 122);

        backgroundTitle.setFill(ColorScheme.colorTitleBlack);
        backgroundGame.setFill(ColorScheme.colorGameTeal);
        greenTitleBox1.setStroke(ColorScheme.colorTitleGreen);
        greenTitleBox2.setStroke(ColorScheme.colorTitleGreen);

        StackPane rootPaneTitle = new StackPane();
        StackPane titlePaneBox = new StackPane();
        VBox titlePaneInformation = new VBox();

        titlePaneBox.getChildren().addAll(greenTitleBox1,greenTitleBox2);

        rootPaneTitle.getChildren().addAll(backgroundTitle, titlePaneBox, titlePaneInformation);

        TitleScreen = new Scene(rootPaneTitle, WIDTH, HEIGHT);

        createLabels();

        titlePaneBox.setPadding(new Insets(0,0,110, 0));

        sounds.playMusic();

        titlePaneInformation.getChildren().addAll(LabelIBM, LabelPC,LabelDonkey,
                LabelRemaster, LabelAuthor, LabelContinue);

        stage.setTitle("DONKEYBAS-REMASTERED");
        stage.setScene(TitleScreen);


        inputParser = new InputParser(this, stage, true);
        inputParser.addKeyHandler();


        ////////////////////////////////////////////////////////////////
        //                   Game Screen UI                           //
        ////////////////////////////////////////////////////////////////
        StackPane rootPaneGame = new StackPane();
        StackPane gameRoadPaneBox = new StackPane();

        carPaneBox = new StackPane();
        donkeyPaneBox = new StackPane();

        carRightPaneBox = new StackPane();
        carLeftPaneBox = new StackPane();
        donkeyRightPaneBox = new StackPane();
        donkeyLeftPaneBox = new StackPane();

        labelPane1 = new VBox();
        labelPane2 = new VBox();
        labelPane3 = new VBox();
        carDonkeyImages();

        crashCarSetup();
        crashDonkeySetup();

        this.positionCalculator.setCarImageSize(car.getCarImg().getWidth(), car.getCarImg().getHeight());
        this.positionCalculator.setLaneSize(xLaneWidth, yLaneHeight);
        this.positionCalculator.setDonkeyImageSize(donkey.getDonkeyImg().getWidth(), donkey.getDonkeyImg().getHeight());

        moveCar(Operator.INITIAL);
        moveDonkey(Operator.INITIAL);

        lanes.forEach((lane -> gameRoadPaneBox.getChildren().addAll(lane.getUIChildren())));


        initializeUIForCrash();

        labelPane1.getChildren().addAll(LabelDonkeyScore,LabelDonkeyNum);
        labelPane2.getChildren().addAll(LabelDriverScore,LabelDriverNum);
        labelPane3.getChildren().addAll(LabelInstructions);

        rootPaneGame.getChildren().addAll(backgroundGame, gameRoadPaneBox, carPaneBox,donkeyPaneBox,
                carRightPaneBox, donkeyRightPaneBox, carLeftPaneBox,
                donkeyLeftPaneBox, labelPane1, labelPane2, labelPane3);

        carPaneBox.setAlignment(Pos.TOP_CENTER);
        donkeyPaneBox.setAlignment(Pos.TOP_CENTER);

        GameScreen = new Scene(rootPaneGame, WIDTH, HEIGHT);

}

    /**
     * This method sets up the panes for the crash sequence.
     */
    public void initializeUIForCrash()
    {
        carPaneBox.getChildren().add(carImageView);
        donkeyPaneBox.getChildren().add(donkeyImageView);

        carRightPaneBox.getChildren().add(carRightImageView);
        carLeftPaneBox.getChildren().add(carLeftImageView);
        donkeyRightPaneBox.getChildren().add(donkeyRightImageView);
        donkeyLeftPaneBox.getChildren().add(donkeyLeftImageView);
        carRightPaneBox.setAlignment(Pos.TOP_CENTER);
        carLeftPaneBox.setAlignment(Pos.TOP_CENTER);
        donkeyRightPaneBox.setAlignment(Pos.TOP_CENTER);
        donkeyLeftPaneBox.setAlignment(Pos.TOP_CENTER);

        carRightImageView.setVisible(false);
        carLeftImageView.setVisible(false);
        donkeyRightImageView.setVisible(false);
        donkeyLeftImageView.setVisible(false);
    }

    /**
     * This method creates all the labels used for the two game screens.
     */
    public void createLabels()
    {
        LabelIBM = new Label("From IBM");
        LabelAuthor = new Label("Kyle Faith, Cameron Yeager, Kyle Mack, William Sarabia");
        LabelContinue = new Label("Press space bar to continue");
        LabelDonkey = new Label("DONKEY");
        LabelRemaster = new Label("Remastered");
        LabelPC = new Label("Personal Computer");

        LabelIBM.setTextFill(ColorScheme.colorTitleWhite);
        LabelDonkey.setTextFill(ColorScheme.colorTitleGreen);
        LabelPC.setTextFill(ColorScheme.colorTitleWhite);
        LabelRemaster.setTextFill(ColorScheme.colorTitleGreen);
        LabelAuthor.setTextFill(ColorScheme.colorTitleWhite);
        LabelContinue.setTextFill(ColorScheme.colorTitleYellow);

        LabelIBM.setFont(Font.font("Rockwell", 40));
        LabelDonkey.setFont(Font.font("Rockwell", 40));
        LabelPC.setFont(Font.font("Rockwell", 40));
        LabelRemaster.setFont(Font.font("Rockwell", 40));
        LabelAuthor.setFont(Font.font("Rockwell",  40));
        LabelContinue.setFont(Font.font("Rockwell", 40));

        LabelIBM.setMaxWidth(Double.MAX_VALUE);
        LabelDonkey.setMaxWidth(Double.MAX_VALUE);
        LabelContinue.setMaxWidth(Double.MAX_VALUE);
        LabelPC.setMaxWidth(Double.MAX_VALUE);
        LabelRemaster.setMaxWidth(Double.MAX_VALUE);
        LabelAuthor.setMaxWidth(Double.MAX_VALUE);

        LabelIBM.setAlignment(Pos.BASELINE_CENTER);
        LabelDonkey.setAlignment(Pos.BASELINE_CENTER);
        LabelPC.setAlignment(Pos.BASELINE_CENTER);
        LabelRemaster.setAlignment(Pos.BASELINE_CENTER);
        LabelAuthor.setAlignment(Pos.BASELINE_CENTER);
        LabelContinue.setAlignment(Pos.BASELINE_CENTER);

        // insets are top ,right ,bottom ,left
        LabelIBM.setPadding(new Insets(75,0,0, 0));
        LabelPC.setPadding(new Insets(20,0,0, 0));
        LabelDonkey.setPadding(new Insets(75,0,0, 0));
        LabelRemaster.setPadding(new Insets(15,0,0, 0));
        LabelAuthor.setPadding(new Insets(75,0,0, 0));
        LabelContinue.setPadding(new Insets(150,0,0, 0));

        LabelDonkeyScore = new Label("Donkey");
        LabelDriverScore = new Label("Driver");
        LabelDonkeyNum = new Label("" + DonkeyNum);
        LabelDriverNum = new Label("" + DriverNum);

        LabelInstructions = new Label("Move Left: Left Arrow\n" +
                "Move Right: Right Arrow\n" +
                "Speed Up: Up Arrow\n" +
                "Slow Down: Down Arrow\n" +
                "Exit: Esc");

        LabelDonkeyScore.setTextFill(ColorScheme.colorGameWhite);
        LabelDriverScore.setTextFill(ColorScheme.colorGameWhite);
        LabelDonkeyNum.setTextFill(ColorScheme.colorGameWhite);
        LabelDriverNum.setTextFill(ColorScheme.colorGameWhite);
        LabelInstructions.setTextFill(ColorScheme.colorGameWhite);

        LabelDonkeyScore.setFont(Font.font("Rockwell", 55));
        LabelDriverScore.setFont(Font.font("Rockwell", 55));
        LabelDonkeyNum.setFont(Font.font("Rockwell", 55));
        LabelDriverNum.setFont(Font.font("Rockwell", 55));
        LabelInstructions.setFont(Font.font("Rockwell", 24));

        LabelDonkeyScore.setMaxWidth(Double.MAX_VALUE);
        LabelDriverScore.setMaxWidth(Double.MAX_VALUE);
        LabelDonkeyNum.setMaxWidth(Double.MAX_VALUE);
        LabelDriverNum.setMaxWidth(Double.MAX_VALUE);
        LabelInstructions.setMaxWidth(Double.MAX_VALUE);

        LabelDonkeyScore.setAlignment(Pos.TOP_LEFT);
        LabelDriverScore.setAlignment(Pos.TOP_RIGHT);
        LabelDonkeyNum.setAlignment(Pos.TOP_LEFT);
        LabelDriverNum.setAlignment(Pos.TOP_RIGHT);
        LabelInstructions.setAlignment(Pos.BOTTOM_RIGHT);

        // insets are top ,right ,bottom ,left
        LabelDonkeyScore.setPadding(new Insets(20,0,0, 50));
        LabelDriverScore.setPadding(new Insets(20,75,0, 0));
        LabelDonkeyNum.setPadding(new Insets(30,0,0, 120));
        LabelDriverNum.setPadding(new Insets(30,125,0, 0));
        LabelInstructions.setPadding(new Insets(500,1000,0, 0));

    }

    /**
     * //This code creates the window, makes it non-resizable, sets the creation location, and sets the size..
     * @param stage1 the main stage everything is implemented on.
     */
    public void createStage(Stage stage1)
    {
        this.stage = stage1;
        stage.setResizable(false);
        stage.show();
        stage.setX(0);
        stage.setY(0);
        stage.setMaxHeight(HEIGHT);
        stage.setMaxWidth(WIDTH);
    }

    /**
     * refreshDriverScore creates the car score label
     * @return Label
     */
    public Label refreshDriverScore()
    {
        LabelDriverNum = new Label("" + carScore);
        LabelDriverNum.setTextFill(ColorScheme.colorGameWhite);
        LabelDriverNum.setFont(Font.font("Rockwell", 55));
        LabelDriverNum.setMaxWidth(Double.MAX_VALUE);
        LabelDriverNum.setAlignment(Pos.TOP_RIGHT);
        LabelDriverNum.setPadding(new Insets(30,125,0, 0));

        return LabelDriverNum;
    }

    /**
     * refreshDonkeyScore creates the donkey score label
     * @return Label
     */
    public Label refreshDonkeyScore()
    {
        LabelDonkeyNum = new Label("" + donkeyScore);
        LabelDonkeyNum.setTextFill(ColorScheme.colorGameWhite);
        LabelDonkeyNum.setFont(Font.font("Rockwell", 55));
        LabelDonkeyNum.setMaxWidth(Double.MAX_VALUE);
        LabelDonkeyNum.setAlignment(Pos.TOP_LEFT);
        LabelDonkeyNum.setPadding(new Insets(30,0,0, 120));

        return LabelDonkeyNum;
    }

    /**
     * Changing the scene to GameScreen
     */
    public void ChangeGameScreen()
    {
        stage.setScene(GameScreen);
        inputParser = new InputParser(this, stage, false);
        inputParser.addKeyHandler();
        startDonkey();
    }
    public void incrementGameSpeed(){
        setGameSpeed(gameSpeed - .5f);
    }

    public void decrementGameSpeed(){
        setGameSpeed(gameSpeed + .5f);
    }

    public void setGameSpeed(float speed){
        if (crashAnimationActive){
            return;
        }
        speed = Math.min(speed, MIN_GAME_SPEED);
        speed = Math.max(speed, MAX_GAME_SPEED);
        gameSpeed = speed;

        loop.stop();
        loop = new Timeline(
                new KeyFrame(Duration.millis(gameSpeed), e -> moveDonkey(Operator.VERTICAL)));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }
    /**
     * startDonkey starts the timeline for the donkey to move down the screen.
     */
    public void startDonkey()
    {
        loop = new Timeline(
                new KeyFrame(Duration.millis(gameSpeed), e -> moveDonkey(Operator.VERTICAL)));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public void startDonkeyDiagonal(){
        hasGoneDiagonal = true;
        loop = new Timeline(
                new KeyFrame(Duration.millis(gameSpeed), e -> moveDonkey(Operator.VERTICAL)));
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();

        diagonalLoop = new Timeline(new KeyFrame(Duration.millis(750), e -> moveDonkey(Operator.DIAGONAL)));
        diagonalLoop.setCycleCount(Timeline.INDEFINITE);
        diagonalLoop.play();
    }

    public Tuple<Double, Double> getCarPos(){
        return new Tuple<>(carPaneBox.getTranslateX(), carPaneBox.getTranslateY());
    }

    public Tuple<Double, Double> getDonkeyPos(){
        return new Tuple<>(donkeyPaneBox.getTranslateX(), donkeyPaneBox.getTranslateY());
    }


    public Tuple<Double,Double> getNextCarPosition(Operator sign){

        double carMoveIncrement = (HEIGHT - (donkey.getDonkeyImg().getHeight() + wiggleRoom + car.getCarImg().getHeight())) / 11;

        Tuple<Double, Double> deltaPosition = new Tuple<>(0.0,0.0);

        if(sign == Operator.INITIAL) {
            deltaPosition = positionCalculator.calculateCarInitialPosition();
        }
        else if(sign == Operator.LEFT || sign == Operator.RIGHT) {
            deltaPosition = positionCalculator.calculateLaneChangePosition(sign, getCarPos());

        }
        else if(sign == Operator.DOWN) {
            deltaPosition = positionCalculator.calculateCarMoveBackwardPosition(carMoveIncrement, getCarPos());

        }
        else if(sign == Operator.UP || sign == Operator.VERTICAL) {
            deltaPosition = positionCalculator.calculateCarMoveForwardPosition(carMoveIncrement, getCarPos());

        }

        return deltaPosition;
    }

    /**
     * moveCar repositions the carPaneBox from one lane to the other.
     *  Also moves HitBox of car
     * @params Operator Sign enum
     */


    // split into method to calculate new position of car and method to actually move it.
    public void moveCar(Operator sign){




        if (sign == Operator.LEFT || sign == Operator.RIGHT || sign == Operator.UP || sign == Operator.DOWN){
            sounds.playLaneSounds();
            if(checkCollision())
            {
                startCrashAnimation();
                addPointDonkey();
                resetDonkey();
                resetCar();
            }
        }

         Tuple<Double, Double> newPosition = getNextCarPosition(sign);
        car.moveCarHitBox((int)newPosition.getX().doubleValue(),(int) newPosition.getY().doubleValue());

        carPaneBox.setTranslateX(newPosition.getX().doubleValue());
        carPaneBox.setTranslateY(newPosition.getY().doubleValue());
    }

    public Tuple<Double, Double> calculateNextDonkeyPosition(Operator sign){

        double carMoveIncrement = 1;

        Tuple<Double, Double> deltaPosition = new Tuple<>(0.0,0.0);

        if(sign == Operator.INITIAL) {
            deltaPosition = positionCalculator.calculateDonkeyInitialPosition();
        }
        else if(sign == Operator.LEFT || sign == Operator.RIGHT) {
            deltaPosition = positionCalculator.calculateDonkeyLaneChangePosition(sign, getDonkeyPos());

        }
        else if(sign == Operator.VERTICAL)
        {
            deltaPosition = positionCalculator.calculateDonkeyForwardAdvance(1, getDonkeyPos());

        }
        else if(sign == Operator.DIAGONAL)
        {
            deltaPosition = positionCalculator.calculateDonkeyDiagonalAdvance(getDonkeyPos());
        }

        return deltaPosition;
    }

    /* moveDonkey repositions the DonkeyPaneBox from one lane to the other.
     *  Also moves HitBox of Donkey
     * @params Operator Sign enum
     * */
    public void moveDonkey(Operator sign) {

        if (crashAnimationActive){
            return;
        }

        Tuple<Double, Double> nextPosition = calculateNextDonkeyPosition(sign);
        donkey.movedonkeyHitBox((int)nextPosition.getX().doubleValue(), (int)nextPosition.getY().doubleValue());

        if(sign == Operator.INITIAL) {//initial placement of donkey
            sounds.playDonkeySounds();
        }

        donkeyPaneBox.setTranslateX(nextPosition.getX().doubleValue());
        donkeyPaneBox.setTranslateY(nextPosition.getY().doubleValue());
        if(sign == Operator.UP || sign == Operator.VERTICAL || sign == Operator.DIAGONAL)
        {
            if(checkCollision())
            {

                sounds.playLoseSounds();
                startCrashAnimation();
                addPointDonkey();
                resetDonkey();
                resetCar();
                determineDonkeyMovement();
            }
            if (donkeyPaneBox.getTranslateY() > (HEIGHT - donkey.getDonkeyImg().getHeight())) //if donkey passes end border, reset.
            {
                resetDonkey();
                determineDonkeyMovement();
                progressCar();
                donkeyPassedCount++;
                if (donkeyPassedCount == 11) {
                    sounds.playWinSounds();
                    addPointCar();
                    resetCar();
                }
            }
        }
    }

    /**
     * resetCar resets the car to the bottom of the screen and resets the donkey passed count.
     */
    public void resetCar()
    {
        carPaneBox.setTranslateY(yLaneHeight - (yLaneHeight/4) - 50);
        car.moveCarHitBox((int)carPaneBox.getTranslateX(), (int)carPaneBox.getTranslateY());
        donkeyPassedCount = 0;
    }

    /**
     * resetDonkey simply resets the donkey to a random lane at the top of the screen.
     */
    public void resetDonkey()
    {
        randomVariable  = random.nextInt(laneCount) ;
        positionCalculator.setCurrentDonkeyLane(0);

        moveDonkey(Operator.LEFT);
        for (int i = 0; i < randomVariable; i++) {

            moveDonkey(Operator.RIGHT);
        }
        donkeyPaneBox.setTranslateY(0);

        sounds.playDonkeySounds();
        donkey.movedonkeyHitBox((int)donkeyPaneBox.getTranslateX(), (int)donkeyPaneBox.getTranslateY());
    }

    /**
     * CheckCollision checks if the car and donkey hitboxes have collided.
     * @return boolean
     */
    public boolean checkCollision()
    {
        return car.getHitbox().detectCollision(donkey.getHitbox());
    }

    /**
     * addPointCar gives the cars a point and changes the scoreboard
     */
    public void addPointCar()
    {
        float changePerPoint = .2f;
        carScore++;
        labelPane2.getChildren().remove(1);
        labelPane2.getChildren().add(refreshDriverScore());
        MIN_GAME_SPEED = Math.max(.5F, MIN_GAME_SPEED - changePerPoint);
        MAX_GAME_SPEED = Math.max(.5F, MAX_GAME_SPEED - changePerPoint);
        setGameSpeed(gameSpeed - changePerPoint);
    }

    /**
     * addPointDonkey gives the donkeys a point and changes the scoreboard
     */
    public void addPointDonkey()
    {
        donkeyScore++;
        labelPane1.getChildren().remove(1);
        labelPane1.getChildren().add(refreshDonkeyScore());
    }

    /**
     * Progress Car moves the car up
     */
    public void progressCar()
    {
        moveCar(Operator.VERTICAL);
    }

    /**
     * carDonkeyImages initializes the imageViews
     */
    public void carDonkeyImages()
    {
        carImageView = new ImageView();
        carImageView.setImage(car.getCarImg());
        donkeyImageView = new ImageView();
        donkeyImageView.setImage(donkey.getDonkeyImg());
    }

    /**
     * startCrashAnimation stops the loop timeline and starts the timeline for the crash sequence.
     */
    public void startCrashAnimation()
    {
        crashAnimationActive = true;
        loop.stop();
        carImageView.setVisible(false);
        donkeyImageView.setVisible(false);

        carRightPaneBox.setTranslateX(carPaneBox.getTranslateX() + car.getLCarImg().getWidth());
        carRightPaneBox.setTranslateY(carPaneBox.getTranslateY());
        carLeftPaneBox.setTranslateX(carPaneBox.getTranslateX());
        carLeftPaneBox.setTranslateY(carPaneBox.getTranslateY());

        donkeyRightPaneBox.setTranslateX(donkeyPaneBox.getTranslateX() + donkey.getLDonkeyImg().getWidth());
        donkeyRightPaneBox.setTranslateY(donkeyPaneBox.getTranslateY());
        donkeyLeftPaneBox.setTranslateX(donkeyPaneBox.getTranslateX());
        donkeyLeftPaneBox.setTranslateY(donkeyPaneBox.getTranslateY());

        carRightImageView.setVisible(true);
        carLeftImageView.setVisible(true);
        donkeyRightImageView.setVisible(true);
        donkeyLeftImageView.setVisible(true);


        crashLoop = new Timeline(
                new KeyFrame(Duration.millis(100), e -> crashAnimation()));
        crashLoop.setCycleCount(Timeline.INDEFINITE);
        crashLoop.play();
    }

    /**
     * Crash CarSetup instantiates the car half image views
     */
    public void crashCarSetup()
    {
        carRightImageView = new ImageView();
        carRightImageView.setImage(car.getRCarImg());

        carLeftImageView = new ImageView();
        carLeftImageView.setImage(car.getLCarImg());
    }

    /**
     *  Crash DonkeySetup instantiates the donkey half image views
     */
    public void crashDonkeySetup()
    {
        donkeyRightImageView = new ImageView();
        donkeyRightImageView.setImage(donkey.getRDonkeyImg());

        donkeyLeftImageView = new ImageView();
        donkeyLeftImageView.setImage(donkey.getLDonkeyImg());
    }

    /**
     * Crash animation moves the car and donkey image halves off screen.
     */
    public void crashAnimation()
    {
        if (donkeyRightPaneBox.getTranslateX() < WIDTH && carRightPaneBox.getTranslateX() < WIDTH)
        {
            carRightPaneBox.setTranslateX((carRightPaneBox.getTranslateX() + car.getRCarImg().getWidth()));
            carRightPaneBox.setTranslateY((carRightPaneBox.getTranslateY() + car.getRCarImg().getHeight()));
            carLeftPaneBox.setTranslateX((carLeftPaneBox.getTranslateX() - car.getLCarImg().getWidth()));
            carLeftPaneBox.setTranslateY((carLeftPaneBox.getTranslateY() + car.getLCarImg().getHeight()));

            donkeyRightPaneBox.setTranslateX((donkeyRightPaneBox.getTranslateX() + donkey.getRDonkeyImg().getWidth()));
            donkeyRightPaneBox.setTranslateY((donkeyRightPaneBox.getTranslateY() - donkey.getRDonkeyImg().getHeight()));
            donkeyLeftPaneBox.setTranslateX((donkeyLeftPaneBox.getTranslateX() - donkey.getLDonkeyImg().getWidth()));
            donkeyLeftPaneBox.setTranslateY((donkeyLeftPaneBox.getTranslateY() - donkey.getLDonkeyImg().getHeight()));
        }
        else
            stopCrashAnimation();




    }

    /**
     *  Stop the Crash animation and time clock
     * restarts the loop time clock.
     */
    public void stopCrashAnimation()
    {
        crashLoop.stop();
        if (crashAnimationActive){
            resetDonkey();
            crashAnimationActive = false;
            loop.play();
        }

        carImageView.setVisible(true);
        donkeyImageView.setVisible(true);

        carRightImageView.setVisible(false);
        carLeftImageView.setVisible(false);
        donkeyRightImageView.setVisible(false);
        donkeyLeftImageView.setVisible(false);
    }



    public double getxLaneWidth(){return this.xLaneWidth;}
    public double getyLaneHeight(){return this.yLaneHeight;}


    public static void main( String[] args )
    {
        Application.launch(args);
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public StackPane getDonkeyPaneBox() {
        return donkeyPaneBox;
    }

    public void setDonkeyPaneBox(StackPane pane) {
        this.donkeyPaneBox  = pane;
    }

    public void determineDonkeyMovement(){
        if(lanes.size() != 2){
            loop.stop();
            if(hasGoneDiagonal){
                diagonalLoop.stop();
                hasGoneDiagonal = false;
            }
            int donkeyDiagonalDecider = random.nextInt(4);
            if(donkeyDiagonalDecider == 2){
                startDonkeyDiagonal();
            }
            else{
                startDonkey();
            }

        }
    }
}

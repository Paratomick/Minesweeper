import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.World;

public class Main extends Application {

    private static Scene scene;
    private static StackPane root;

    public static World world;
    public static PlayingField playingField;
    public static TopBar topBar;
    private static Timeline timeline;

    public static boolean gameOver;
    public static boolean win;
    public static boolean timeStopped;
    public static Timeline timer;
    public static int time;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new StackPane();
        scene = new Scene(root, 510, 635);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            time++;
            int i = time;
            int min = i / 60;
            int s = i % 60;

            topBar.labelTime.setText((min<10?"0":"") + min + ":" + (s<10?"0":"") + s);
        }));
        timer.setCycleCount(Timeline.INDEFINITE);

        scene.setOnKeyReleased(k -> {
            if(k.getCode().equals(KeyCode.R)) {
                reset();
            }
        });

        world = new World();
        reset();
    }

    public static void reset() {
        timeStopped = true;
        gameOver = false;
        win = true;
        timer.stop();
        time = 0;
        int w = (int)scene.getWidth()/50;
        int h = (int)(scene.getHeight()-30)/50;
        world.init(h, w, (int)(w*h*0.2));
        world.generateBombs();

        topBar = new TopBar();
        topBar.setMark(world.numBombs);
        playingField = new PlayingField();
        root.getChildren().setAll(new VBox(topBar, playingField));
    }

    public static void startTimer() {
        timer.play();
        timeStopped = false;
    }

    public static void updateRemainingFields() {
        int num = world.width * world.height;
        int remBombs = 0;
        int explo = 0;
        for(Node h: playingField.getChildren()) {
            for(Node f: ((HBox)h).getChildren()) {
                Field field = (Field)f;
                boolean isBomb = world.isBomb(field.getY(), field.getX());
                if(isBomb && !(field.marked() || field.bomb())) remBombs++;
                if(field.bomb()) explo++;
                if(!field.hidden()) num--;
            }
        }
        topBar.setMark(remBombs);
        if(num == 0 && remBombs == 0) {
            gameOver = true;
            timer.stop();
            System.out.println("Game Over: " + (explo==0?"Won":"Lost with " + explo + " exploded bombs") + " after " + topBar.labelTime.getText());
        }
    }

    public static void main(String[] args) {
        Main.launch(args);
    }
}

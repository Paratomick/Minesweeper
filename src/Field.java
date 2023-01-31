import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;

public class Field extends StackPane {

    private int y, x;

    public Field(int y, int x) {
        this.y = y;
        this.x = x;
        hide();

        setOnMouseClicked(k -> {
            boolean primeAction = k.getButton().equals(MouseButton.PRIMARY) && !Main.topBar.cbActionMode.isSelected()
                    || k.getButton().equals(MouseButton.SECONDARY) && Main.topBar.cbActionMode.isSelected();
            click(primeAction);
        });
    }

    public void show() {
        if(getStyleClass().isEmpty()) {
            if (Main.world.isBomb(y, x)) {
                getStyleClass().add("red");
                Main.win = false;
            } else {
                int num = Main.world.getNumber(y, x);
                if(num == 0) {
                    getStyleClass().add("greenFlat");
                    Main.playingField.clickNeighbors(y, x);
                } else {
                    getStyleClass().add("green");
                    getChildren().add(new Label("" + num));
                }
            }
        }
    }

    public void mark() {
        if(hidden() && !marked()) {
            getStyleClass().add("yellow");
            Main.topBar.setMark(Main.topBar.getMark() - 1);
        } else if(marked()) {
            getStyleClass().clear();
            Main.topBar.setMark(Main.topBar.getMark() + 1);
        }
    }

    public void hide() {
        getStyleClass().clear();
        getChildren().clear();
    }

    public boolean hidden() {
        return getStyleClass().isEmpty();
    }

    public boolean marked() {
        return getStyleClass().contains("yellow");
    }

    public boolean bomb() {
        return getStyleClass().contains("red");
    }

    public void click(boolean primeAction) {
        if(Main.gameOver) return;
        if(Main.timeStopped) Main.startTimer();
        if(!hidden() && !marked()) {
            if(!Main.world.isBomb(y, x)) {
                if(Main.playingField.getNeighborMarks(y, x) >= Main.world.getNumber(y, x)) {
                    Main.playingField.clickNeighbors(y, x);
                }
            }
        } else if(!primeAction) {
            mark();
            Main.updateRemainingFields();
        } else {
            if(Main.world.isBomb(y, x)) {
                if(Main.world.firstClick) {
                    do {
                        Main.world.generateBombs();
                    } while (Main.world.isBomb(y, x));
                    Main.world.firstClick = false;
                }
                show();
                Main.topBar.setMark(Main.topBar.getMark() - 1);
            } else {
                show();
                Main.world.firstClick = false;
            }
            Main.updateRemainingFields();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

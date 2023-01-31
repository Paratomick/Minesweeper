import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class TopBar extends HBox {

    public Label labelNumMines, labelTime;
    public Button btnReset;
    public CheckBox cbActionMode;

    private int mark;

    public TopBar() {
        labelNumMines = new Label("NumMines: 0");
        labelTime = new Label("00:00");
        btnReset = new Button();
        cbActionMode = new CheckBox();
        btnReset.setOnAction(e -> Main.reset());
        Region spacer1 = new Region(), spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        getChildren().addAll(labelNumMines, spacer1, btnReset, cbActionMode, spacer2, labelTime);

        mark = 0;
    }

    public void setMark(int mark) {
        this.mark = mark;
        labelNumMines.setText("NumMines: " + mark);
    }

    public int getMark() {
        return mark;
    }
}

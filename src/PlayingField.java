import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayingField extends VBox {

    public PlayingField() {
        init();
    }

    public void init() {
        getChildren().clear();
        for(int y = 0; y < Main.world.height; y++) {
            HBox box = new HBox();
            for(int x = 0; x < Main.world.width; x++) {
                box.getChildren().add(new Field(y, x));
            }
            getChildren().add(box);
        }
    }

    public void clickNeighbors(int y, int x) {
        for(int iy = (y>0?-1:0); iy < (y<Main.world.height-1?2:1); iy++) {
            for(int ix = (x>0?-1:0); ix < (x<Main.world.width-1?2:1); ix++) {
                Field f = ((Field)((HBox)getChildren().get(y+iy)).getChildren().get(x+ix));
                if(f.hidden()) f.click(true);
            }
        }
    }

    public int getNeighborMarks(int y, int x) {
        int num = 0;
        for(int iy = (y>0?-1:0); iy < (y<Main.world.height-1?2:1); iy++) {
            for(int ix = (x>0?-1:0); ix < (x<Main.world.width-1?2:1); ix++) {
                if(((Field)((HBox)getChildren().get(y+iy)).getChildren().get(x+ix)).marked()
                        || ((Field)((HBox)getChildren().get(y+iy)).getChildren().get(x+ix)).bomb()) {
                    num++;
                }
            }
        }
        return num;
    }
}

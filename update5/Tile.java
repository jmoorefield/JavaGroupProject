import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import java.io.FileInputStream;


public class Tile
{
    private Image imageTile;
    private ImageView imageView;
    private final StackPane holder;
    private String Letter;
    private int value;
    private int row;
    private int column;

    private EventHandler<MouseEvent> commitPositionOnRelease;
    private EventHandler<MouseEvent> dragging;
    private EventHandler<DragEvent> droppedTile;
    private EventHandler<DragEvent> overTile;


    private BooleanProperty isDraggable;

    public Tile(String letter, int value, FileInputStream path)
    {
        this.Letter = new String(letter);
        this.value = value;
        this.imageTile = new Image(path);
        this.imageView = new ImageView(imageTile);
        this.imageView.setFitHeight(40);
        this.imageView.setFitWidth(40);
        this.holder = new StackPane();
        holder.setAlignment(Pos.CENTER);
        this.holder.getChildren().add(imageView);
        setHandlers();
        setHandlerEvents();
        this.isDraggable.set(true);

    }
    public Tile(String letter)
    {
        this.Letter = letter;
        this.value = 0;
        this.holder = new StackPane();
        this.holder.setAlignment(Pos.CENTER);
        this.holder.setStyle("-fx-background-color: #c6bda1");
        setHandlers();
        setHandlerEvents();
        this.isDraggable.set(true);
    }

    //accessor function lets you manipulate the 'Text' from a Tile **careful**
    public String getLetter()       { return Letter; }
    public StackPane getHolder()    { return new StackPane(holder); }
    public Image getImage()         { return new Image(imageTile.getUrl()); }
    public int getValue()           { return value; }
    public int getRow()             { return row; }
    public int getColumn()          { return column; }


    public void setImageTile(Image image)
    {
        this.imageTile = image;
        this.imageView = new ImageView(image);
        this.imageView.setFitHeight(40);
        this.imageView.setFitWidth(40);
        this.holder.getChildren().add(imageView);
    }
    public void setLetter(String letter)    { this.Letter = letter; }
    public void setPointsValue(int points)  { this.value = points; }

    private void setHandlers()
    {


        commitPositionOnRelease = event ->
        {
            System.out.println(event.getSource().getClass().toString());
            System.out.println(event.getTarget().getClass().toString());
            if(event.getTarget().getClass() == StackPane.class)
            {
                GridPane test = (GridPane) holder.getParent().getParent();
                this.row = test.getRowIndex(holder.getParent());
                this.column = test.getColumnIndex(holder.getParent());
                for(int x = 0; x < test.getChildren().size(); x++)
                {
                    if(test.getChildren().get(x) instanceof StackPane && test.getRowIndex(test.getChildren().get(x)) == row && test.getColumnIndex(test.getChildren().get(x)) == column)
                    {
                        test.getChildren().get(x).setVisible(true);
                        break;
                    }
                }
            }



        };

        dragging = event ->
        {
            GridPane parent = (GridPane) holder.getParent().getParent();
            StackPane child = null;
            this.row = parent.getRowIndex(holder.getParent());
            this.column = parent.getColumnIndex(holder.getParent());
            for(int x = 0; x < parent.getChildren().size(); x++)
            {
                if(parent.getChildren().get(x) instanceof StackPane && parent.getRowIndex(parent.getChildren().get(x)) == row && parent.getColumnIndex(parent.getChildren().get(x)) == column)
                {
                    child = (StackPane) parent.getChildren().get(x);
                    break;
                }
            }
            if(child.isVisible())
            {
                Dragboard db = holder.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                db.setDragView(imageTile);
                content.putImage(imageTile);
                content.putString(Letter + " " + String.valueOf(value));
                db.setContent(content);
                for(int x = 0; x < parent.getChildren().size(); x++)
                {
                    if(parent.getChildren().get(x) instanceof StackPane && parent.getRowIndex(parent.getChildren().get(x)) == row && parent.getColumnIndex(parent.getChildren().get(x)) == column)
                    {
                        ((StackPane) parent.getChildren().get(x)).getChildren().remove(0);
                        break;
                    }
                }
            }
        };

        overTile = event ->
        {
            if (event.getGestureSource() != holder &&
                    event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        };

        droppedTile = event ->
        {

            Dragboard db = event.getDragboard();
            if(event.getTarget().getClass() == StackPane.class)
            {
                setImageTile(db.getImage());
                String temp = db.getString();
                String[] split = temp.split(" ");
                setLetter(split[0]);
                holder.setDisable(true);
                GridPane parent = (GridPane) holder.getParent().getParent();
                this.row = parent.getRowIndex(holder.getParent());
                this.column = parent.getColumnIndex(holder.getParent());
                temp = db.getString();
                split = temp.split(" ");
                Board.addTileToMove(split[0], row, column, split[1]);
            }
        };
    }

    public void setHandlerEvents()
    {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                holder.addEventFilter(MouseEvent.DRAG_DETECTED,dragging);
                holder.addEventFilter(DragEvent.DRAG_OVER,overTile);
                holder.addEventFilter(DragEvent.DRAG_DROPPED,droppedTile);
                holder.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            } else {

                holder.removeEventFilter(MouseEvent.DRAG_DETECTED,dragging);
                holder.removeEventFilter(DragEvent.DRAG_OVER,overTile);
                holder.removeEventFilter(DragEvent.DRAG_DROPPED,droppedTile);
                holder.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
        });
    }

    public void setDraggable(boolean bool)
    {
        this.isDraggable.set(bool);
    }
}

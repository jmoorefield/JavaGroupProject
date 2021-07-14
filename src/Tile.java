import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import java.io.FileInputStream;
import java.io.InputStream;


public class Tile
{
    private Image imageTile;
    private ImageView imageView;
    private final StackPane holder;
    private String Letter;
    private int value;

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
        this.holder.setAlignment(Pos.CENTER);
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
        this.holder.setAlignment(Pos.CENTER);;
        this.imageView = new ImageView();
        this.imageView.setFitHeight(40);
        this.imageView.setFitWidth(40);
        setHandlers();
        setHandlerEvents();
        this.isDraggable.set(true);
    }

    //accessor function lets you manipulate the 'Text' from a Tile **careful**
    public String getLetter()       { return Letter; }
    public StackPane getHolder()    { return new StackPane(holder); }
    public Image getImage()         { return new Image(imageTile.getUrl()); }
    public int getValue()           { return value; }
    public ImageView getImageView(){return this.imageView;}


    public void setImageTile(Image image)
    {
        this.imageTile = image;
        this.imageView.setImage(imageTile);
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
            System.out.println("yes");

            //holder.getChildren().add(imageView);
        };

        dragging = event ->
        {
            if(!holder.getChildren().isEmpty())
            {
                Dragboard db = holder.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                db.setDragView(imageTile);
                content.putImage(imageTile);
                content.putString(Letter + " " + String.valueOf(value));
                db.setContent(content);
                holder.getChildren().remove(0);
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
            boolean success = false;
            System.out.println(event.getGestureSource().toString());
            /*
            if(!event.getGestureSource().equals(holder))
                System.out.println("Over a Tile");

             */
            if (db.hasImage())
            {
                setImageTile(db.getImage());
                setLetter(db.getString());
                success = true;
            }

            //Here you get the indexes for the grid positions
            // need to transfer score value
            GridPane test = (GridPane) holder.getParent().getParent();
            int row = test.getRowIndex(holder.getParent());
            int column = test.getColumnIndex(holder.getParent());

            String temp = db.getString();
            String[] split = temp.split(" ");
            Board.addTileToMove(split[0], row, column, split[1]);
            System.out.println("Value is: " + split[1]);
            event.setDropCompleted(success);

        };
    }

    public void setHandlerEvents() {
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

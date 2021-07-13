
//class still needs work!
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.effect.Blend;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import java.io.FileInputStream;


public class Tile
{
    private Image imageTile;
    private ImageView imageView;
    private final StackPane holder;
    private final String Letter;
    private final int value;

    private double anchorX;
    private double anchorY;

    private double mouseOffsetFromNodeZeroX;
    private double mouseOffsetFromNodeZeroY;

    private EventHandler<MouseEvent> setAnchor;
    private EventHandler<MouseEvent> updatePositionOnDrag;
    private EventHandler<MouseEvent> commitPositionOnRelease;
    private EventHandler<MouseEvent> dragging;
    private EventHandler<DragEvent> droppedTile;
    private EventHandler<DragEvent> overTile;

    private final int ACTIVE = 1;
    private final int INACTIVE = 0;
    private int cycleStatus = INACTIVE;

    private BooleanProperty isDraggable;

    public Tile(String letter, int value, FileInputStream path)
    {
        this.Letter = new String(letter);
        this.value = value;
        this.imageTile = new Image(path);
        this.imageView = new ImageView(imageTile);
        this.imageView.setFitHeight(50);
        this.imageView.setFitWidth(50);
        this.holder = new StackPane();
        holder.setAlignment(Pos.CENTER);
        this.holder.getChildren().add(imageView);
        setHandlers();
        setHandlerEvents();
        this.isDraggable.set(true);

    }
    public Tile(String letter)
    {
        this.Letter = new String(letter);
        this.value = 0;
        this.holder = new StackPane();
        holder.setAlignment(Pos.CENTER);
        setHandlers();
        setHandlerEvents();
        this.isDraggable.set(true);

    }
    //accessor function lets you manipulate the 'Text' from a Tile **careful**
    public String getLetter(){ return this.Letter;}
    public StackPane getHolder(){return this.holder;}
    public Image getImage(){return this.imageTile;}
    public void setImageTile(Image image)
    {
        this.imageTile = image;
        this.imageView = new ImageView(image);
        this.imageView.setFitHeight(30);
        this.imageView.setFitWidth(30);
        this.holder.getChildren().add(imageView);
    }

    private void setHandlers()
    {
        setAnchor = event -> {
            if (event.isPrimaryButtonDown()) {
                cycleStatus = ACTIVE;
                anchorX = event.getSceneX();
                anchorY = event.getSceneY();
                mouseOffsetFromNodeZeroX = event.getX();
                mouseOffsetFromNodeZeroY = event.getY();
            }

            if (event.isSecondaryButtonDown()) {
                cycleStatus = INACTIVE;
                holder.setTranslateX(0);
                holder.setTranslateY(0);
            }
        };

        updatePositionOnDrag = event -> {
            if (cycleStatus != INACTIVE)
            {
                holder.setEffect(new Blend());
                holder.setTranslateX(event.getSceneX() - anchorX);
                holder.setTranslateY(event.getSceneY() - anchorY);
            }
        };

        commitPositionOnRelease = event -> {
            if (cycleStatus != INACTIVE) {
                //commit changes to LayoutX and LayoutY
                holder.setLayoutX(event.getSceneX() - mouseOffsetFromNodeZeroX);
                holder.setLayoutY(event.getSceneY() - mouseOffsetFromNodeZeroY);
                holder.setTranslateX(0);
                holder.setTranslateY(0);
            }
        };

        dragging = event ->
        {
            Dragboard db = holder.startDragAndDrop(TransferMode.ANY);
            db.setDragView(imageTile);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageTile);
            db.setContent(content);
        };


        overTile = event ->
        {
            if (event.getGestureSource() != holder &&
                    event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        };

        droppedTile = event ->
        {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                setImageTile(db.getImage());
                success = true;
            }
            event.setDropCompleted(success);

        };
    }

    public void setHandlerEvents() {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                holder.addEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                holder.addEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                holder.addEventFilter(MouseEvent.DRAG_DETECTED,dragging);
                holder.addEventFilter(DragEvent.DRAG_OVER,overTile);
                holder.addEventFilter(DragEvent.DRAG_DROPPED,droppedTile);
                holder.addEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            } else {
                holder.removeEventFilter(MouseEvent.MOUSE_PRESSED, setAnchor);
                holder.removeEventFilter(MouseEvent.MOUSE_DRAGGED, updatePositionOnDrag);
                holder.removeEventFilter(MouseEvent.DRAG_DETECTED,dragging);
                holder.removeEventFilter(DragEvent.DRAG_OVER,overTile);
                holder.removeEventFilter(DragEvent.DRAG_DROPPED,droppedTile);
                holder.removeEventFilter(MouseEvent.MOUSE_RELEASED, commitPositionOnRelease);
            }
        });
    }

    public boolean isIsDraggable() {
        return isDraggable.get();
    }

    public BooleanProperty isDraggableProperty() {
        return isDraggable;
    }
    public void setIsDraggable(boolean set)
    {
        this.isDraggable.set(set);
    }




}

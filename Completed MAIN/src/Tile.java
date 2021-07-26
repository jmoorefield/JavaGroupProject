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


/// *************************************************************
/// *                      Tile Class                           *
/// *************************************************************
/* COP3252
Jess Moorefield, Roderick Quiel
Group Project
25 July 2021
*/

public class Tile
{
    //holds image file
    private Image imageTile;
    //to show image in GUI
    private ImageView imageView;
    //holds the image in the GUI
    private final StackPane holder;
    //Letter
    private String Letter;
    //points
    private int value;
    //positions
    private int row;
    private int column;
    //checks previous move
    private boolean fromPreviousMove;
    //EventHandlers for dragging
    private EventHandler<MouseEvent> dragging;
    private EventHandler<DragEvent> droppedTile;
    private EventHandler<DragEvent> overTile;
    private BooleanProperty isDraggable;



    //*******************************************************************************
    // * Name: Tile                                                                 *
    // * Description: initializes tiles with the letter, points, and image.         *
    // *    Also sets the GUI configurations and Event Handlers for each tile.      *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public Tile(String letter, int value, FileInputStream path)
    {
        this.Letter = letter;
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
        this.fromPreviousMove = false;
    }

    //*******************************************************************************
    // * Name: Tile                                                                 *
    // * Description:  initializes the tiles "empty" tiles for the board            *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
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

    //accessor functions
    public String getLetter()       { return Letter; }
    public StackPane getHolder()    { return this.holder; }
    public int getValue()           { return value; }
    public int getRow()             { return row; }
    public int getColumn()          { return column; }
    public boolean isPreviousMove() { return fromPreviousMove; }


    //*******************************************************************************
    // * Name: setImageTile                                                         *
    // * Description:  sets the image for tile in the board when dragging           *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public void setImageTile(Image image)
    {
        this.imageTile = image;
        this.imageView = new ImageView(imageTile);
        this.imageView.setFitHeight(40);
        this.imageView.setFitWidth(40);
        this.holder.getChildren().add(imageView);
    }

    //mutator functions
    public void setLetter(String letter)    { this.Letter = letter; }
    public void setPointsValue(int points)  { this.value = points; }
    public void setPreviousMove(boolean isUsed) { this.fromPreviousMove = isUsed; }

    //*******************************************************************************
    // * Name: setHandlers                                                          *
    // * Description:  here we set up the events for dragging the tiles             *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    private void setHandlers()
    {
        dragging = event ->
        {
            //first we check that the "tile" is not empty ( has no image )
           if(!holder.getChildren().isEmpty())
           {
               GridPane parent = (GridPane) holder.getParent();
               StackPane child = null;
               this.row = parent.getRowIndex(holder);
               this.column = parent.getColumnIndex(holder);
               for(int x = 0; x < parent.getChildren().size(); x++)
               {
                   if(parent.getChildren().get(x) instanceof StackPane && parent.getRowIndex(parent.getChildren().get(x)) == row && parent.getColumnIndex(parent.getChildren().get(x)) == column)
                   {
                       child = (StackPane) parent.getChildren().get(x);
                       break;
                   }
               }
               //if child has image and is visible we initiate the dragging. if is not it means tile cannot be dragged
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
                           parent.getChildren().get(x).setVisible(false);
                           break;
                       }
                   }
                   event.consume();
               }
           }
        };

        overTile = event ->
        {
            //if they are no the same "holder" and there is and image then transfer is accepted from target
            if (event.getGestureSource() != holder &&
                    event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
                event.consume();
            }
        };

        droppedTile = event ->
        {
            //here we just "drop" the tile and transfer its contents to the empty tile in teh board
            Dragboard db = event.getDragboard();
            setImageTile(db.getImage());
            String temp = db.getString();

            String[] split = temp.split(" ");
            setLetter(split[0]);
            //we disable the dropped tile for logic checks and to make tile unusable
            holder.setDisable(true);
            //here we set up its new position and return the tile's data to the board logic
            GridPane parent = (GridPane) holder.getParent();
            this.row = parent.getRowIndex(holder);
            this.column = parent.getColumnIndex(holder);
            Board.addTileToMove(split[0], row, column, split[1]);
            db.clear();
            event.setDropCompleted(true);
            event.consume();
        };
    }

    //*******************************************************************************
    // * Name: setHandlerEvents                                                     *
    // * Description: events handlers set up for each specific event                *
    // * Last Modified by: Roderick Quiel                                           *
    // * Date:  07/26/2021                                                          *
    // ******************************************************************************
    public void setHandlerEvents()
    {
        isDraggable = new SimpleBooleanProperty();
        isDraggable.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                this.holder.addEventFilter(MouseEvent.DRAG_DETECTED,dragging);
                this.holder.addEventFilter(DragEvent.DRAG_OVER,overTile);
                this.holder.addEventFilter(DragEvent.DRAG_DROPPED,droppedTile);
            } else {

                this.holder.removeEventFilter(MouseEvent.DRAG_DETECTED,dragging);
                this.holder.removeEventFilter(DragEvent.DRAG_OVER,overTile);
                this.holder.removeEventFilter(DragEvent.DRAG_DROPPED,droppedTile);
            }
        });
    }
}

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

/**
 * <p>Design and implement an application that draws an equilateral triangle 
 * using a rubber banding technique. The triangle size and orientation are 
 * determined by a mouse drag. Use the original mouse press location as the 
 * center, and the dragged position of the mouse as one of the corners. 
 * Draw a filled circle at the center.</p>
 *
 * @author Gaurav Minhas SetC
 * @version 1.0
 */
public class DrawTriangle extends Application {
         
    /** <p>The contents of the application scene.</p> */
    private Group root;
    
    /**
     * <p>Stores the coordinates of the equilateral triangle.</p>
     */
    private Polygon triangle;
    

    /** center point. */
    private Point2D center;
    /** circle to move to first mouse click location. */
    private Circle atCenter = new Circle(0, 0, 2 + 1);
   
    /**
     * Displays an initially empty scene, waiting for the user to draw lines
     * with the mouse.
     * 
     * @param primaryStage
     *            a Stage
     */
    public void start(Stage primaryStage) {
        
        atCenter.setFill(Color.CYAN);
        triangle = new Polygon();
       
       root = new Group(atCenter, triangle);
        final int appWidth = 800;
        final int appHeight = 500;
        Scene scene = new Scene(root, appWidth, appHeight, Color.BLACK);
        
        scene.setOnMousePressed(this::processMousePress);
        scene.setOnMouseDragged(this::processMouseDrag);

        primaryStage.setTitle("Equilateral Triangle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * <p>Adds a new line to the scene when the mouse button is pressed.</p>
     * 
     * @param event
     *            invoked this method
     */
    public void processMousePress(MouseEvent event) {
        root.getChildren().remove(triangle);
        center = new Point2D(event.getX(), event.getY());
        atCenter.setTranslateX(event.getX());
        atCenter.setTranslateY(event.getY());
        
    }
    
    /**
     * <p>Updates the end point of the current line as the mouse is dragged,
     * creating the rubber band effect.</p>
     * 
     * @param event
     *            invoked this method
     */
    public void processMouseDrag(MouseEvent event) {
        final double angle120 = (2 * Math.PI) / 3;
        root.getChildren().remove(triangle);
        Point2D current = new Point2D(event.getX(), event.getY());
        double radius = center.distance(current);
        Point2D another = new Point2D(current.getX() - center.getX(), 
                                        current.getY() - center.getY());
        double angle =  Math.atan2(another.getY(), another.getX());
        Point2D p2 = new Point2D(radius * Math.cos(angle + angle120),
                                    radius * Math.sin(angle + angle120));
        Point2D p3 = new Point2D(radius * Math.cos(angle - angle120),
                                    radius * Math.sin(angle - angle120));
        triangle = new Polygon(current.getX(), current.getY(), 
                center.getX() + p2.getX(), center.getY() + p2.getY(),
                p3.getX() + center.getX(), p3.getY() + center.getY());
        triangle.setStroke(Color.CYAN);
        triangle.setFill(Color.TRANSPARENT);
        triangle.setStrokeWidth(2);
        root.getChildren().add(triangle);
             
    }


    /**
     * <p>Launches the JavaFX application.</p>
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}


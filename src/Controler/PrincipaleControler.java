package Controler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrincipaleControler  {

    private static final double maxDisplay = 800;
    private static final double maxZoom = 6;

    private static double initx;
    private static double inity;

    private static Scene initialScene;

    //    Image properties
    private static double ratio; // image width / image height
    private static String path;
    private static Image source;
    private static int height; // real sizes
    private static int width;  // of the image
    private static double displayedWidth; // size of the imageView
    private static double displayedHeight;

    private static double offSetX,offSetY,zoomlvl = 1;

    //    FXML fields
    @FXML public MenuItem menu_item_new;
    @FXML public MenuItem menu_item_close;
    @FXML public BorderPane displayer;
    @FXML public ImageView mapDisplay;
    @FXML public VBox root;
    @FXML public HBox zoomSliderContainer;


    public  void loadMap(){

        root.setAlignment(Pos.CENTER);

        try {
            source = new Image(new FileInputStream(path));
        } catch (FileNotFoundException ee) {
            ee.printStackTrace();
        }

        mapDisplay.setImage(source);
        ratio = source.getWidth()/source.getHeight();

        mapDisplay.setOnMouseClicked(ev ->{

            System.out.println(getXCoordinate(ev.getX()) + " " + getYCoordinate(ev.getY()));
        });

        if(maxDisplay/ratio < maxDisplay) {
            displayedWidth=maxDisplay;
            displayedHeight=(int) (displayedWidth/ratio);
        }else if(maxDisplay*ratio < maxDisplay){
            displayedHeight = maxDisplay;
            displayedWidth = (int) (displayedHeight*ratio);
        }else {
            displayedHeight = maxDisplay;
            displayedWidth = maxDisplay;
        }
        mapDisplay.setPreserveRatio(false);
        mapDisplay.setFitWidth(displayedWidth);
        mapDisplay.setFitHeight(displayedHeight);

        height = (int) source.getHeight();
        width = (int) source.getWidth();

        zoomSliderContainer.setAlignment(Pos.CENTER);

        Slider zoomLvl = new Slider();
        zoomLvl.setMax(maxZoom);
        zoomLvl.setMin(1);
        zoomLvl.setMaxWidth(200);
        zoomLvl.setMinWidth(200);
        Label hint = new Label("Zoom Level");
        Label value = new Label("1.0");

        offSetX = width/2;
        offSetY = height/2;

        zoomSliderContainer.getChildren().addAll(hint,zoomLvl,value);

        Slider Hscroll = new Slider();
        Hscroll.setMin(0);
        Hscroll.setMax(width);
        Hscroll.setMaxWidth(mapDisplay.getFitWidth());
        Hscroll.setMinWidth(mapDisplay.getFitWidth());
        Hscroll.setTranslateY(-20);
        Hscroll.setVisible(false);
        Slider Vscroll = new Slider();
        Vscroll.setMin(0);
        Vscroll.setMax(height);
        Vscroll.setMaxHeight(mapDisplay.getFitHeight());
        Vscroll.setMinHeight(mapDisplay.getFitHeight());
        Vscroll.setOrientation(Orientation.VERTICAL);
        Vscroll.setTranslateX(-20);
        Vscroll.setVisible(false);

        BorderPane.setAlignment(Hscroll, Pos.CENTER);
//        BorderPane.setAlignment(Vscroll, Pos.CENTER_LEFT);

        Hscroll.valueProperty().addListener(e->{
            offSetX = Hscroll.getValue();
            zoomlvl = zoomLvl.getValue();
            double newValue = (double)((int)(zoomlvl*10))/10;
            value.setText(newValue+"");
            if(offSetX<(width/newValue)/2) {
                offSetX = (width/newValue)/2;
            }
            if(offSetX>width-((width/newValue)/2)) {
                offSetX = width-((width/newValue)/2);
            }

            mapDisplay.setViewport(new Rectangle2D(offSetX-((width/newValue)/2), offSetY-((height/newValue)/2), width/newValue, height/newValue));
        });
        Vscroll.valueProperty().addListener(e->{
            offSetY = height-Vscroll.getValue();
            zoomlvl = zoomLvl.getValue();
            double newValue = (double)((int)(zoomlvl*10))/10;
            value.setText(newValue+"");
            if(offSetY<(height/newValue)/2) {
                offSetY = (height/newValue)/2;
            }
            if(offSetY>height-((height/newValue)/2)) {
                offSetY = height-((height/newValue)/2);
            }
            mapDisplay.setViewport(new Rectangle2D(offSetX-((width/newValue)/2), offSetY-((height/newValue)/2), width/newValue, height/newValue));
        });
        displayer.setCenter(mapDisplay);
        displayer.setBottom(Hscroll);
        displayer.setRight(Vscroll);


        zoomLvl.valueProperty().addListener(e->{
            zoomlvl = zoomLvl.getValue();
            double newValue = (double)((int)(zoomlvl*10))/10;
            value.setText(newValue+"");
            if(offSetX<(width/newValue)/2) {
                offSetX = (width/newValue)/2;
            }
            if(offSetX>width-((width/newValue)/2)) {
                offSetX = width-((width/newValue)/2);
            }
            if(offSetY<(height/newValue)/2) {
                offSetY = (height/newValue)/2;
            }
            if(offSetY>height-((height/newValue)/2)) {
                offSetY = height-((height/newValue)/2);
            }
            Hscroll.setValue(offSetX);
            Vscroll.setValue(height-offSetY);
            mapDisplay.setViewport(new Rectangle2D(offSetX-((width/newValue)/2), offSetY-((height/newValue)/2), width/newValue, height/newValue));

        });

        displayer.setCursor(Cursor.OPEN_HAND);
        mapDisplay.setOnMousePressed(e->{
            initx = e.getSceneX();
            inity = e.getSceneY();
            displayer.setCursor(Cursor.CLOSED_HAND);
        });
        mapDisplay.setOnMouseReleased(e->{
            displayer.setCursor(Cursor.OPEN_HAND);
        });
        mapDisplay.setOnMouseDragged(e->{
            Hscroll.setValue(Hscroll.getValue()+(initx - e.getSceneX()));
            Vscroll.setValue(Vscroll.getValue()-(inity - e.getSceneY()));
            initx = e.getSceneX();
            inity = e.getSceneY();
        });
    }

    public void selectMap(ActionEvent actionEvent) {

        Stage openImage = new Stage();
        openImage.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(20);grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        Label hint = new Label("Select Your Image");
        TextField URL = new TextField();
        URL.setEditable(false);
        URL.setPrefWidth(350);

        Button browse = new Button("Browse");
        FileChooser fc = new FileChooser();
        ExtensionFilter png = new ExtensionFilter("png", "*.png");
        ExtensionFilter jpg = new ExtensionFilter("jpg", "*.jpg");
        fc.getExtensionFilters().addAll(png,jpg);
        browse.setOnAction(e->{
            URL.setText(fc.showOpenDialog(openImage).getAbsolutePath());
        });

        Button open = new Button("Open");
        open.setOnAction(e->{

            path = URL.getText();
            System.out.println(path);
//            initView();
//            s.setScene(View);
            loadMap();
            openImage.close();
        });

        grid.add(hint, 0, 0);
        grid.add(URL, 1, 0);
        grid.add(browse, 2, 0);
        grid.add(open, 2, 1);

        initialScene = new Scene(grid,600,100);
        openImage.setScene(initialScene);
        openImage.show();

    }

    public double getXCoordinate(double x){
        double zoom = (double)((int)(zoomlvl*10))/10;
        return offSetX - (width/zoom)/2 + (x*(width/zoom)/displayedWidth);
    }

    public double getYCoordinate(double y){
        double zoom = (double)((int)(zoomlvl*10))/10;
        return offSetY - (height/zoom)/2 + (y*(height/zoom)/displayedHeight);
    }
}
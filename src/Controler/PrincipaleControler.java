package Controler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PrincipaleControler  {

    private static final double maxDisplay = 800;
    private static final double maxZoom = 6;

    private static Scene initialScene;

    //    Image properties
    private static double ratio; // image width / image height
    private static String path ;//= "C:\\Users\\amine\\Downloads\\47268855_994303264105576_1914520653315178496_n.png";
    private static Image source;
    private static int height; // real sizes
    private static int width;  // of the image
    private static double displayedWidth; // size of the imageView
    private static double displayedHeight;

    private static Sig sig;
    private static String user;
    private static ArrayList<Couche> couches = new ArrayList<>();
    private static ArrayList<Canvas> couches_canvas = new ArrayList<>();

    private static double zoomlvl = 1;

    public ImageView mapDisplay;

    //    FXML fields
    @FXML public Button newBTN;
    @FXML public MenuItem menu_item_close;
    @FXML public BorderPane displayer;
    @FXML public VBox root;
    @FXML public HBox zoomSliderContainer;
    @FXML public VBox coucheList;
    @FXML public StackPane stack_display;
    @FXML public ScrollPane scroll_display;
    @FXML public AnchorPane rootPanel;
    @FXML public Button addCoucheBTN;

    @FXML
    public void initialize(){

        scroll_display.setFitToWidth(false);
        scroll_display.setFitToHeight(false);
        scroll_display.setMinHeight(600);
        scroll_display.setPrefSize(800, 600);
        scroll_display.setMaxSize(800, 600);
        scroll_display.setPannable(true);
        System.out.println("loling");

    }

    public  void loadMap(){


        try {
            source = new Image(new FileInputStream(path));
        } catch (FileNotFoundException ee) {
            ee.printStackTrace();
        }

        mapDisplay = new ImageView(source);
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

        zoomSliderContainer.getChildren().addAll(hint,zoomLvl,value);

        zoomLvl.valueProperty().addListener(e->{
            zoomlvl = zoomLvl.getValue();
            double newValue = cleanValue(zoomlvl);
            value.setText(newValue+"");

            mapDisplay.setFitWidth(displayedWidth*newValue);
            mapDisplay.setFitHeight(mapDisplay.getFitWidth()/ ratio);
            for (Canvas c : couches_canvas){
                c.setScaleX(newValue);
                c.setScaleY(newValue);
            }
        });

        stack_display.getChildren().add(mapDisplay);
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
        ExtensionFilter bmp = new ExtensionFilter("bmp", "*.bmp");
        fc.getExtensionFilters().addAll(jpg,png, bmp);
        browse.setOnAction(e->{
            URL.setText(fc.showOpenDialog(openImage).getAbsolutePath());
        });

        Button open = new Button("Open");
        open.setOnAction(e->{

            path = URL.getText();

            sig = new Sig(user, path);

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
        // to avoid values like : 1.40000000002
        double zoom = cleanValue(zoomlvl);
        return x/zoom;
    }

    public double getYCoordinate(double y){
        // to avoid values like : 1.40000000002
        double zoom = cleanValue(zoomlvl);
        return y / zoom;
    }

    public double cleanValue(double v){
        return (double)((int)(v*10))/10;
    }

    public void createCouche(){
        Stage createCoucheStage = new Stage();
        createCoucheStage.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(20);grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        Label hint = new Label("Nom de la couche :");
        TextField name = new TextField();
//        name.setEditable(false);
        name.setPrefWidth(250);

        ChoiceBox selectType = new ChoiceBox(FXCollections.observableArrayList("Point", "Ligne", "Polygone"));

        Button valider = new Button("Valider");
        valider.setOnAction(e->{
            String coucheName = name.getText();
            String type = (String) selectType.getValue();

            // Creation du Canvas associé a la couche
            Canvas can = new Canvas(displayedWidth, displayedHeight);

            // Creation de la couche
            Couche c = null;
            Drawer drawer = null;
            switch (type){
                case "Point":{
                    c = new CouchePoint(coucheName);
                    drawer = new PointDrawer(c, can);
                }break;
                case "Ligne":{
                    c = new CoucheLigne(coucheName);
                    drawer = new LineDrawer(c, can);
                }break;
                case "polygone":{
                    c = new CouchePolygone(coucheName);
                    drawer = new PolygoneDrawer(c, can);
                }break;
            }
            couches.add(c); // Ajout dans la liste des couches

            GraphicsContext gc = can.getGraphicsContext2D();
            gc.strokeText(coucheName, 500, 500);

            can.setOnMouseClicked(me ->{
//                drawer.draw(me.getX(), me.getY());
            });

            couches_canvas.add(can);
            stack_display.getChildren().add(can);

            // Add couche to left sideBar (couche list)
            HBox p = new HBox();
            TextField title = new TextField(coucheName);
            title.setEditable(false);

            CheckBox visible = new CheckBox();
            visible.setIndeterminate(false);
            visible.setOnAction(v_event -> {
                if (visible.isSelected()){
                    can.setVisible(true);
                    mapDisplay.toBack();
                }
                else  {
                    can.setVisible(false);
                }
                System.out.println("canvas of "+ coucheName+ " is visible" + can.isVisible());
            });

            p.getChildren().addAll(title, visible);
            coucheList.getChildren().add(p);

            createCoucheStage.close();
        });

        grid.add(hint, 0, 0);
        grid.add(name, 1, 0);
        grid.add(selectType, 2, 0);
        grid.add(valider, 2, 1);

        initialScene = new Scene(grid,600,100);
        createCoucheStage.setScene(initialScene);
        createCoucheStage.show();
    }
}
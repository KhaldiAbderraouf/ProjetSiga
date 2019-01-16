package Controler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Principale2Controller  {

    private static final double maxDisplay = 800;
    private static final double maxZoom = 6;

    private static Scene initialScene;
    @FXML public Label X_coordinate;
    @FXML public Label Y_coordinate;

    public static void setInitialScene(Scene initialScene) {
        Principale2Controller.initialScene = initialScene;
    }

    public static Scene getInitialScene(){
        return initialScene;
    }
    //    Image properties
    private static double ratio; // image width / image height
    private static String path ;//= "C:\\Users\\amine\\Downloads\\47268855_994303264105576_1914520653315178496_n.png";
    private static Image source;
    private static int height; // real sizes
    private static int width;  // of the image
    private static double displayedWidth; // size of the imageView
    private static double displayedHeight;

    private static Sig sig;
    private static String user = "1";
    private static String sigName = "Siga";
    private static ArrayList<Couche> couches = new ArrayList<>();
    private static ArrayList<CanvasWithName> couches_canvas = new ArrayList<>();



    private static Map<String, ShapeDrawer> drawers = new TreeMap<>();


    private static double zoomlvl = 1;

    public ImageView mapDisplay;

    //    FXML fields
    @FXML public Button newBTN;
    @FXML public MenuItem menu_item_close;
    @FXML public BorderPane displayer;
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
        scroll_display.setMinHeight(500);
        scroll_display.setPrefSize(800, 500);
        scroll_display.setMaxSize(800, 500);
        scroll_display.setPannable(true);



        stack_display.setOnMouseMoved(e -> {
            X_coordinate.setText("X = " + getXCoordinate(e.getX()));
            Y_coordinate.setText("Y = " + getYCoordinate(e.getY()));
        });

    }

    public  void loadMap(){


        try {
            source = new Image(new FileInputStream(path));
            System.out.println("found the file");
        } catch (FileNotFoundException ee) {
            System.out.println("erreur in loadmap");
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
            for (CanvasWithName c : couches_canvas){
                c.setScaleX(newValue);
                c.setScaleY(newValue);
            }
        });

        stack_display.getChildren().add(mapDisplay);
    }

    public void selectMap() {

        Stage openImage = new Stage();
        openImage.setResizable(false);

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
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
        fc.getExtensionFilters().addAll(jpg, png, bmp);
        browse.setOnAction(e -> {
            URL.setText(fc.showOpenDialog(openImage).getAbsolutePath());
        });

        Button open = new Button("Open");
        open.setOnAction(e -> {

            path = URL.getText();
            if (!path.equals("")){
                //System.out.println(path + "  here is the path ");

                sig = new Sig(sigName, user, path);

                loadMap();

                System.out.println("after load");
                openImage.close();
            }

        });

        grid.add(hint, 0, 0);
        grid.add(URL, 1, 0);
        grid.add(browse, 2, 0);
        grid.add(open, 2, 1);

        initialScene = new Scene(grid, 600, 100);
        openImage.setScene(initialScene);
        openImage.show();

    }

    public static double getXCoordinate(double x){
        // to avoid values like : 1.40000000002
        double zoom = cleanValue(zoomlvl);
        return round(x/zoom, 1);
    }

    public static double getYCoordinate(double y){
        // to avoid values like : 1.40000000002
        double zoom = cleanValue(zoomlvl);
        return round(y/ zoom, 1) ;
    }

    public static double cleanValue(double v){
        return (double)((int)(v*10))/10;
    }

    public static double round(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public void createCouche(String type , String coucheName){
            CanvasWithName can = new CanvasWithName(displayedWidth, displayedHeight,coucheName);

            double zoom = cleanValue(zoomlvl);


            can.setScaleX(zoom);
            can.setScaleY(zoom);

            // Creation de la couche

            switch (type){
                case "Point":{
                    sig.addCouchePoint(coucheName);
                    drawers.put(coucheName, new PointShapeDrawer(coucheName, sig, can)) ;
                }break;
                case "Ligne":{
                    sig.addCoucheLigne(coucheName);
                    drawers.put(coucheName, new LineShapeDrawer(coucheName, sig, can)) ;
                }break;
                case "Polygone":{
                    sig.addCouchePolygone(coucheName);
                    drawers.put(coucheName, new PolygoneShapeDrawer(coucheName, sig, can)) ;
                }break;
            }
            couches.add(sig.getCouche(coucheName)); // Ajout dans la liste des couches

//            GraphicsContext gc = can.getGraphicsContext2D();
//            gc.strokeText(coucheName, 500, 500);

            /*can.setOnMouseClicked(me ->{
                drawers.get(coucheName).draw(me);
            });*/
            couches_canvas.add(can);
            stack_display.getChildren().add(can);
    }

    public void drawOnThis (String coucheName){
        CanvasWithName can = couches_canvas.get(0);
        for (int i=0 ; i<couches_canvas.size();i++){
            if (couches_canvas.get(i).getName().equals(coucheName)){
                can = couches_canvas.get(i);
                break;
            }
        }
        can.toFront();
        can.setOnMouseClicked(me ->{
            drawers.get(coucheName).draw(me);
//            System.out.println("From principal controller x = " + me.getX()+" y = " + me.getY());
        });
    }

    public void saveSig(ActionEvent actionEvent) {
        sig.dbSave();
    }

    public void canVisibility (Boolean state , String couchename){
        CanvasWithName can = couches_canvas.get(0);
        for (int i=0 ; i<couches_canvas.size();i++){
           if (couches_canvas.get(i).getName().equals(couchename)){
               can = couches_canvas.get(i);
               break;
           }
        }

        if (state) {
            can.setVisible(true);
            System.out.println("hnankheli lacouche");
            mapDisplay.toBack();
        } else {
            System.out.println("hna nahi la couche");
            can.setVisible(false);
        }
    }

    public Sig getSig(){
        return sig;
    }

    public void DeleteShape(String nomcouche , String nomform){

        sig.removeShape(nomcouche, nomform);
        drawers.get(nomcouche).reDrawAll();

    }

    public static Map<String, ShapeDrawer> getDrawers() {
        return drawers;
    }
}
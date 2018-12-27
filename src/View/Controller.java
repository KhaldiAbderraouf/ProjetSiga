package View;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.FileInputStream;


public class Controller {

    @FXML
    private ImageView imageFond;

    public void importImage(){
        Image back = null;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        Stage frame = new Stage();
        try{
            back =  new Image(new FileInputStream(fileChooser.showOpenDialog(frame)));
        }catch (Exception e){
            System.out.println("Couldn't load map : "+ e);
        }
        imageFond.setImage(back);
    }

    public void addLayer(){

    }

    public void addPoint(){

    }

    public void addLine(){

    }

    public void addPolygone(){

    }

    public void symbo(){

    }
}

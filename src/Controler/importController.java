package Controler;

import javafx.event.ActionEvent;

public class importController {
    public Principale2Controller principale2Controller;
    /*public importController(){
        principale2Controller = Principale2Controller.getInstance();
    }*/

    public void setPrincipale2Controller(Principale2Controller principale2Controller) {
        this.principale2Controller = principale2Controller;
    }

    public void selectMap (){
        principale2Controller.selectMap();
    }
}


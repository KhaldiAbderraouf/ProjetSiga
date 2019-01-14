package Model;

//<<<<<<< HEAD
//import com.sun.xml.internal.ws.api.ha.StickyFeature;
//=======
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.scene.paint.Color;
//>>>>>>> 5b103d76fa0e76264f91d25bb7e08d32c2ed5190

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class Couleur {

    protected long id = 0;
    protected String nom;





    public abstract void dbSave(long idSymbologie);


}

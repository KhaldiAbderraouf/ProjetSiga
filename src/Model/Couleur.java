package Model;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class Couleur {

    protected long id = 0;
    protected String nom;





    public abstract void dbSave(long idSymbologie);


}

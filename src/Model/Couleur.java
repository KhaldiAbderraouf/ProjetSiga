package Model;

//import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.util.ArrayList;
import java.util.List;

public abstract class Couleur {

    private long id = 0;
    private String nom;

    public void dbSave(long idSymbologie) {
        if(id == 0)
            this.dbAjouter(idSymbologie);
        else
            this.Modifier();
    }

    private void Modifier() {
        String query = "UPDATE Couleur SET Nom = ? WHERE ID = ?";
        ArrayList<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(this.id));
        BDD.execute(query, args);
    }

    private void dbAjouter(long idCouche) {
        String query = "INSERT INTO Couleur VALUES(null, ?, ?)";
        List<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(idCouche));
        id = BDD.execute(query, args);
    }
}

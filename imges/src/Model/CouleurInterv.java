package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CouleurInterv extends Couleur {

    private int red;
    private int green;
    private int blue;

    public CouleurInterv(String nom, int red, int green, int blue){
        this.nom = nom;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Map<String, Integer> getRGB(){
        Map<String, Integer> rgb = new TreeMap<String, Integer>();
        rgb.put("red", this.red);
        rgb.put("green", this.green);
        rgb.put("blue", this.blue);
        return rgb;
    }
    private void Modifier() {
        String query = "UPDATE Couleur SET Nom = ?, Red = ?, Green = ?, Blue = ? WHERE ID = ?";
        ArrayList<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(this.red));
        args.add(String.valueOf(this.green));
        args.add(String.valueOf(this.blue));
        args.add(String.valueOf(this.id));
        BDD.execute(query, args);
    }

    private void dbAjouter(long idSymbologie) {
        String query = "INSERT INTO Couleur VALUES(null, ?, ?, ?, ?, ?)";
        List<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(this.red));
        args.add(String.valueOf(this.green));
        args.add(String.valueOf(this.blue));
        args.add(String.valueOf(idSymbologie));
        id = BDD.execute(query, args);
    }

    @Override
    public void dbSave(long idSymbologie) {
        {
            if(id == 0)
                this.dbAjouter(idSymbologie);
            else
                this.Modifier();
        }
    }

    public static Couleur dbFetchWithId(long id) {
        String query = "SELECT * FROM Couleur WHERE ID = ?";
        List<String> args = new ArrayList<String>();
        args.add(String.valueOf(id));
        ResultSet rs = BDD.fetch(query, args);
        Couleur c = null;
        try {
            if(rs.next()){
                c = new CouleurInterv(rs.getString("Nom"), rs.getInt("Red"), rs.getInt("Green"), rs.getInt("Blue"));
                c.id = rs.getLong("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return c;
    }
}

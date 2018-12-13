package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Colonne {
    public int id;
    public String nom;
    public int idTableAttr;

    public Colonne(int id, String nom, int idTableAttr){
        this.id = id;
        this.nom = nom;
        this.idTableAttr = idTableAttr;
    }

    public Colonne(String nom, int idTableAttr){
        this.nom = nom;
        this.idTableAttr = idTableAttr;
    }

    private static ArrayList<Colonne> adaptResultSetToArrayList(ResultSet rs){
        ArrayList<Colonne> list = new ArrayList<Colonne>();
        try {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("NOM");
                int idTable = rs.getInt("IDTableAttr");
                Colonne colonne = new Colonne(id, nom, idTable);
                list.add(colonne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<Colonne> dbGetAll(){
        String query = "SELECT * FROM Colonne";
        ResultSet rs = BDD.fetchAll(query, null);
        ArrayList<Colonne> list= adaptResultSetToArrayList(rs);
        return list;
    }

    public static ArrayList<Colonne> dbGetAllWithIDTableAttr(int idTableAttr){
        String query = "SELECT * FROM Colonne WHERE IDTableAttr = ?";
        ResultSet rs = BDD.fetchAll(query, Arrays.asList(String.valueOf(idTableAttr)));
        ArrayList<Colonne> list= null;
        list = adaptResultSetToArrayList(rs);
        return list;
    }


    public void dbAjouter(){
        String query = "INSERT INTO Colonne VALUES(null, ?, ?)";
        BDD.execute(query, Arrays.asList(this.nom, String.valueOf(this.idTableAttr)));
    }

    public void dbModifier(){
        String query = "UPDATE Colonne SET Nom = ?, IDTableAttr = ? WHERE ID = ?";
        ArrayList<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(this.idTableAttr));
        args.add(String.valueOf(this.id));
        BDD.execute(query, args);
    }

    public static Colonne dbFetchWithID(int idToFetch){
        Colonne colonne;
        String query = "SELECT * FROM Colonne WHERE ID = ?";
        ResultSet rs = BDD.fetchAll(query, Arrays.asList(String.valueOf(idToFetch)));
        ArrayList<Colonne> list= adaptResultSetToArrayList(rs);
        colonne = new Colonne(list.get(0).id, list.get(0).nom, list.get(0).idTableAttr);
        return colonne;
    }

}

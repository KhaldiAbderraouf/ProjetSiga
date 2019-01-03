package Model;

//import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Colonne {

    private int taille;
    private String name;
    private long id;
    private String nom;

    private ArrayList<String> col = new ArrayList<String>();

    public Colonne(String name,int taille, Object ... list){
        this.name=name;
        this.taille=taille;
        int cpt=0;

        for (Object l: list) {
            col.add(l.toString());
            cpt++;
        }
        for (int i=cpt;i<taille;i++){
            col.add("vide");
        }
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getCol(){
        return col;
    }

    public String getColIndex(int index){
        return col.get(index);
    }

    public int getTaille() {
        return taille;
    }

    public void addElement(Object e){
        col.add(e.toString());
        taille++;
    }

    public void modifierElem(int index,String s){
        col.get(index).replace(col.get(index),s);
    }

    public void modifierElem(String o,String n){
        for (String e:col){
            if(e==o){
                e.replace(o,e);
            }
        }
    }

    public void retirerElem(int index){
        col.remove(index);
        if(taille>0){taille--;}
    }

    public  void retirerElem(String e){
        if (col.remove(e)) if(taille>0){taille--;}
    }

    public void retirerElem(){
        col.remove(taille-1);
        if(taille>0){taille--;}
    }

    public boolean equals(Object col){
        if(col.getClass()!=this.getClass()){return false;}
        boolean res=true;
        if (((Colonne)col).getTaille()==this.taille){
            for (int i=0;i<taille;i++){
                if(((Colonne)col).getColIndex(i)!=this.getColIndex(i)) return false;
            }
        }
        return res;
	}
    
    public Colonne(long id, String nom){
        this.id = id;
        this.nom = nom;
    }

    public Colonne(String nom){
        this.nom = nom;
    }

    private static List adaptResultSetToArrayList(ResultSet rs){
        List list = new ArrayList<Colonne>();
        try {
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("NOM");
                Colonne colonne = new Colonne(id, nom);
                list.add(colonne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List dbGetAll(){
        String query = "SELECT * FROM Colonne";
        ResultSet rs = BDD.fetchAll(query, null);
        List list= adaptResultSetToArrayList(rs);
        return list;
    }

    public static List dbGetAllWithIDTableAttr(int idTableAttr){
        String query = "SELECT * FROM Colonne WHERE IDTableAttr = ?";
        ResultSet rs = BDD.fetchAll(query, Arrays.asList(String.valueOf(idTableAttr)));
        List list= null;
        list = adaptResultSetToArrayList(rs);
        return list;
    }

    public static Colonne dbFetchWithID(int idToFetch){
        Colonne colonne;
        String query = "SELECT * FROM Colonne WHERE ID = ?";
        ResultSet rs = BDD.fetch(query, Arrays.asList(String.valueOf(idToFetch)));
        List<Colonne> list= adaptResultSetToArrayList(rs);
        colonne = new Colonne(list.get(0).id, list.get(0).nom);
        return colonne;
    }

    public void dbSave(long idTableAttr) {
        if(id == 0)
            this.dbAjouter(idTableAttr);
        else
            this.Modifier();
    }

    private void Modifier() {
        String query = "UPDATE Colonne SET Nom = ? WHERE ID = ?";
        ArrayList<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(this.id));
        BDD.execute(query, args);
    }

    private void dbAjouter(long idCouche) {
        String query = "INSERT INTO Colonne VALUES(null, ?, ?)";
        List<String> args = new ArrayList<String>();
        args.add(this.nom);
        args.add(String.valueOf(idCouche));
        id = BDD.execute(query, args);
    }

}

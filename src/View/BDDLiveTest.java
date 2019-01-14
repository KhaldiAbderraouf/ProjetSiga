package View;

import Controler.*;
import Model.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BDDLiveTest {
    public static void main(String[] argz) throws SQLException {


        TableAttr table = TableAttr.dbFetchWithID(1);
        List<String> newlistCol = new ArrayList<String>();
        newlistCol.add("aaaaa");newlistCol.add("f");newlistCol.add("cccccc");newlistCol.add("ddddd");
        table.addColonne("nouvelle col", newlistCol);
        System.out.println(table.tables.get(0).getCol());
        System.out.println(table.tables.get(5).getCol());

//        TableAttr table = TableAttr.dbFetchWithID(1);
//        List<String> newlistCol = new ArrayList<String>();
//        newlistCol.add("aaaaa");newlistCol.add("bbbbbb");newlistCol.add("cccccc");newlistCol.add("ddddd");
//        table.tables.add(new Colonne("nom_jdid", newlistCol));
//        table.dbSave(1);

//
//        TableAttr table = new TableAttr();
//
//        List<String> listColID = new ArrayList<String>();
//        listColID.add("a1");listColID.add("b2");listColID.add("c3");listColID.add("d4");
//        table.tables.add(new Colonne("ID", listColID));
//
//        List<String> listCol1 = new ArrayList<String>();
//        listCol1.add("champs1");listCol1.add("champs2");listCol1.add("change");listCol1.add("champs4");
//        table.tables.add(new Colonne("col1", listCol1));
//
//        List<String> listCol2 = new ArrayList<String>();
//        listCol2.add("champs1");listCol2.add("change");listCol2.add("champs3");listCol2.add("champs4");
//        table.tables.add(new Colonne("col2", listCol2));
//
//        List<String> listCol3 = new ArrayList<String>();
//        listCol3.add("change");listCol3.add("champs2");listCol3.add("champs3");listCol3.add("change");
//        table.tables.add(new Colonne("col3", listCol3));
//
//        table.dbSave(1);
//
        //Symbologie
//        Symbologie sym = Symbologie.dbFetchWithId(1);
//        System.out.println(sym.couleurs.get(0));
//        System.out.println(sym.couleurs.get(1));
//        System.out.println(sym.couleurs.get(2));
//        sym.nom = "nom changé";
//        sym.dbSave(1);

        //Couleur
//        CouleurBuilder couleurBuilder = new CouleurBuilder();
//        Couleur couleur1 = couleurBuilder.creerCouleurIntervale();
//        Couleur couleur2 = couleurBuilder.creerCouleurIntervale();
//        Couleur couleur3 = CouleurInterv.dbFetchWithId(3);
//        System.out.println(couleur1+"\n");
//        System.out.println(couleur2+"\n");
//        System.out.println(couleur3+"\n");
//        couleur3.nom = "nom changé";
//        couleur3.dbSave(1);
//        couleur3.dbSave(1);
//        couleur2.dbSave(1);
//        couleur1.dbSave(1);


        //SIG
//        Sig sig = Sig.dbFetchWithID(1);
//        sig.getCouches().get("1").nom = "nom changé de sigs";
//        sig.dbSave();

        //COUCHE
//        Couche couche = (CouchePolygone) Couche.dbFetchWithId(4);
//        ((CouchePolygone) couche).polys.get(1).setName("poly changé couche");
//        couche.dbSave(1);


        //POLYGONE
//        Polygone polygone = Polygone.dbFetchWithID(1);
//        polygone.points.get(0).changeXY(100,100);
//        polygone.setName("nom changé");
//        polygone.dbSave(3);


        //LIGNE
//        Ligne ligne = Ligne.dbFetchWithID(1);
//        ligne.points.get(0).changeXY(100, 100);
//        ligne.setName("nom hcangé");
//        ligne.dbSave(2);


        //POINTNOMER
//        PointNomer pn = PointNomer.dbFetchWithID(1);
//        System.out.println("ID = "+pn.getID()+" nom ="+pn.getName()+" X ="+pn.getX()+" Y ="+pn.getY());
//        pn.setName("nom changé");
//        pn.changeXY(9, 11);
//        pn.dbSave(1);

//        ResultSet resultSet = BDD.fetch(query, args);
//        ResultSetMetaData rsmd = resultSet.getMetaData();
//        int columnsNumber = rsmd.getColumnCount();
//        while (resultSet.next()) {
//            for (int i = 1; i <= columnsNumber; i++) {
//                if (i > 1) System.out.print(",  ");
//                String columnValue = resultSet.getString(i);
//                System.out.print(columnValue + " " + rsmd.getColumnName(i));
//            }
//            System.out.println("  ");
//        }
    }
}

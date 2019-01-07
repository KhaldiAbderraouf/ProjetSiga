package View;

import Controler.*;
import Model.*;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BDDLiveTest {
    public static void main(String[] argz) throws SQLException {

        Sig sig = Sig.dbFetchWithID(1);
        sig.getCouches().get("1").nom = "nom changé de sigs";
        sig.dbSave();

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

package View;

import Model.BDD;
import Model.Colonne;
import Model.Point;
import Model.PointNomer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BDDLiveTest {
    public static void main(String[] args) throws SQLException {
        Point point = new Point( -10, 7);
        PointNomer pn = new PointNomer(point, "nouveau");
        pn.dbSave(1);
        System.out.println("ID du pn est "+pn.getID()+"\nID du point est"+point.getID()+"\n");
    }
}

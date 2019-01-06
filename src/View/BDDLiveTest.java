package View;

import Controler.Sig;
import Model.BDD;
import Model.Colonne;
import Model.Point;
import Model.PointNomer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BDDLiveTest {
    public static void main(String[] args) throws SQLException {
        Sig sig = new Sig("nouveau sig de raouf", "user");
        sig.dbSave();
    }
}

import Model.BDD;
import Model.Colonne;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainClassTesting {
    public static void main(String[] args) throws SQLException {
        Colonne colonne = Colonne.dbFetchWithID(5);
        System.out.println("ID : "+ colonne.id+"| Nom : "+ colonne.nom +"| IDTable : "+ colonne.idTableAttr +";");
        colonne.idTableAttr = 1;
        colonne.dbModifier();
        ArrayList<Colonne> listColonne = Colonne.dbGetAll();
        for (Colonne c:listColonne) {
            System.out.println("ID : "+ c.id+"| Nom : "+ c.nom +"| IDTable : "+ c.idTableAttr +";");
        }
        System.out.println("executed ");
    }
}

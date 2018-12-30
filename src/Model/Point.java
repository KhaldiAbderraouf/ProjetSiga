package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Point{
	public int id;
	public int X,Y;
	public String nom;
	public int idCouche;

	public void changeXY(int x, int y){
		this.X=x;
		this.Y=y;
	}
	
	public Point(int x, int y){
		this.X=x;
		this.Y=y;
	}
	public void savePoint(){
		//save the point
	}

    public Point(int id, String nom, int X, int Y, int idCouche){
        this.X = X;
        this.Y = Y;
        this.nom = nom;
        this.id = id;
        this.idCouche = idCouche;
    }

    public Point(String nom, int X, int Y, int idCouche){
        this.X = X;
        this.Y = Y;
        this.nom = nom;
        this.idCouche = idCouche;
    }

	public int getX(){
		return this.X;
	}
	public int getY(){
		return this.Y;
	}
	public boolean equals(Object point){
		if((this.X==((Point)point).getX())&&(this.Y==((Point)point).getY())){
			return true;
		}
		else {
			return false;
		}
	}

    private static List adaptResultSetToArrayList(ResultSet rs){
        ArrayList<Point> list = new ArrayList<Point>();
        try {
            while (rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("Nom");
                int X = rs.getInt("X");
                int Y = rs.getInt("Y");
                int idCouche = rs.getInt("IDCouche");
                list.add(new Point(id, name, X, Y, idCouche));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

	public static Point dbFetchWithID(int idToFetch){
	    String query = "SELECT * FROM Point INNER JOIN PointAbs ON Point.IDPointAbs=PointAbs.ID WHERE Point.ID = ? ";
	    ResultSet rs = BDD.fetch(query, Arrays.asList(String.valueOf(idToFetch)));
        List<Point> list = adaptResultSetToArrayList(rs);
        return new Point(list.get(0).id, list.get(0).nom, list.get(0).X, list.get(0).Y, list.get(0).idCouche);

    }

    public void dbAjouter(){
        List<String> queries = new ArrayList<String>();
        String query1 = "INSERT INTO PointAbs VALUES (null, ?, ?, null);";
        String query2 = "INSERT INTO Point VALUES (null, ?, LAST_INSERT_ID(), ?);";
        queries.add(query1);
        queries.add(query2);

        List<String> args1 = new ArrayList<String>();
        List<String> args2 = new ArrayList<String>();
        List<List<String>> argsList = new ArrayList<List<String>>();
        args1.add(String.valueOf(this.X));
        args1.add(String.valueOf(this.Y));
        args2.add(this.nom);
        args2.add(String.valueOf(this.idCouche));
        argsList.add(args1);
        argsList.add(args2);

        BDD.executeTransaction(queries, argsList);
//        BDD.execute(query2, args2);
    }
	
}

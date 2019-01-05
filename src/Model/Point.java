package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Point {
	private long id = 0;
	private int X, Y;

	public void changeXY(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public Point(int x, int y) {
		this.X = x;
		this.Y = y;
	}

	public void savePoint() {
		// save the point
	}

	public Point(long id, int X, int Y) {
		this.X = X;
		this.Y = Y;
		this.id = id;
	}

	public Point(String nom, int X, int Y) {
		this.X = X;
		this.Y = Y;
	}

	public int getX() {
		return this.X;
	}

	public int getY() {
		return this.Y;
	}

	public boolean equals(Object point) {
		if ((this.X == ((Point) point).getX()) && (this.Y == ((Point) point).getY())) {
			return true;
		} else {
			return false;
		}
	}

	private static List adaptResultSetToArrayList(ResultSet rs) {
		ArrayList<Point> list = new ArrayList<Point>();
		try {
			while (rs.next()) {
				int id = rs.getInt("ID");
				int X = rs.getInt("X");
				int Y = rs.getInt("Y");
				list.add(new Point(id, X, Y));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Point dbFetchWithID(int idToFetch) {
		String query = "SELECT * FROM Point WHERE Point.ID = ? ";
		ResultSet rs = BDD.fetch(query, Arrays.asList(String.valueOf(idToFetch)));
		List<Point> list = adaptResultSetToArrayList(rs);
		return new Point(list.get(0).id, list.get(0).X, list.get(0).Y);

	}

	public void dbAjouter(long idShape, String type) {

		String query = "";
		switch (type) {
		case "point":
			query = "INSERT INTO Point VALUES (null, ?, ?, null, null, ?);";
			break;
		case "ligne":
			query = "INSERT INTO Point VALUES (null, ?, ?, null, ?, null);";
			break;
		case "polygone":
			query = "INSERT INTO Point VALUES (null, ?, ?, ?, null, null);";
			break;
		}
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(this.X));
		args.add(String.valueOf(this.Y));
		args.add(String.valueOf(idShape));
		id = BDD.execute(query, args);

	}

	public void dbModifier() {

		String query = "Update Point SET Nom = ?, X = ?, Y = ? WHERE ID = ?;";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(this.X));
		args.add(String.valueOf(this.Y));
		args.add(String.valueOf(id));
		BDD.execute(query, args, false);
	}

	public void dbSave(long idShape, String type) {
		if (id == 0)
			this.dbAjouter(idShape, type);
		else
			this.dbModifier();
	}

	public long getID() {
		return id;
	}
}

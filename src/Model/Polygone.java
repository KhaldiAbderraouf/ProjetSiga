package Model;

import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Polygone implements Subject {
	private long id;
	public List<Point> points= new ArrayList<Point>();
	private String name;
	private int lenght;

	public Polygone(String name){
		this.name=name;
		lenght=0;
	}

	public Polygone(String name,Observer o){
		this.name=name;
		lenght=0;
		add(o);
		execute();
	}

	public Point head(){
		if(!points.isEmpty()){
			return points.get(0);
		}
		else{
			return null;
		}
	}
	public void add(Point point){
		points.add(point);
		lenght++;
	}
	public void add(int x, int y){
		Point point= new Point(x,y);
		points.add(point);
		lenght++;
	}

	public void remove(Point point){
		if(points.contains(point)){
			points.remove(point);
			lenght--;
		}
	}
	public void remove(int x, int y){
		Point point= new Point(x,y);
		if(points.contains(point)){
			points.remove(point);
			lenght--;
		}
	}
	public double superficie(){
		//la Superficie en kilometre carr� du polygone
		return 0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getlenght(){
		return this.lenght;
	}
	public Point getPoint(int i){
		if(lenght>i){
			return points.get(i);
		}
		return null;
	}
	public List<Point> getPoints(){
		return points;
	}
	public boolean equals(Object poly){
		if(this.name==((Polygone)poly).getName()){
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void add(Observer o) {
		observers.add(o);
	}

	@Override
	public void execute() {
		for (Observer observer : observers) {
			observer.update("ID",name,"Surface",0);
		}
	}

	public void dbSave(long idCouche) {
		if (id == 0)
			this.dbAjouter(idCouche);
		else
			this.dbModifier();
		for (Point point:points) {
			point.dbSave(id, "polygone");
		}

	}


	private void dbModifier() {
		String query = "UPDATE Polygone SET Nom = ? WHERE ID = ?";
		List<String> args = new ArrayList<String>();
		args.add(this.name);
		args.add(String.valueOf(this.id));
		BDD.execute(query, args);
	}

	private void dbAjouter(long idCouche) {
		String query = "INSERT INTO Polygone VALUES (null, ?, ?);";
		List<String> args = new ArrayList<String>();
		args.add(this.name);
		args.add(String.valueOf(idCouche));
		id = BDD.execute(query, args);
	}

	public static Polygone dbFetchWithID(long id){
		Polygone polygone = null;
		String query = "SELECT * FROM Polygone INNER JOIN Point ON Point.IDPolygone=Polygone.ID WHERE Polygone.ID = ?";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(id));
		ResultSet rs = BDD.fetch(query, args);
		try {
			boolean polygoneCreated = false;
			while(rs.next()){
				if(!polygoneCreated){
					polygone = new Polygone(rs.getString("Nom"));
					polygone.id = rs.getLong("Polygone.ID");
					polygoneCreated = true;
				}
				Point point = new Point(rs.getInt("X"), rs.getInt("Y"));
				point.setID(rs.getInt("Point.ID"));
				polygone.add(point);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return polygone;
	}

	public static List<Polygone> dbFetchWithIDCouche(long idCouche){
		List<Polygone> polygoneList = null;
		String query = "SELECT * FROM  Polygone  WHERE IDCouche = ?";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(idCouche));
		ResultSet rs = BDD.fetchAll(query, args);
		try {
			boolean createdList = false;
			while(rs.next()){
				if(!createdList){
					polygoneList = new ArrayList<Polygone>();
					createdList = true;
				}
				Polygone polygone = Polygone.dbFetchWithID(rs.getInt("ID"));
				polygoneList.add(polygone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return polygoneList;
	}
}

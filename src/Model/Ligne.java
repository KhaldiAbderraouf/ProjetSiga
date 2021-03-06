package Model;

import Controler.JTS;
import Controler.Operations;

import java.util.ArrayList;
import java.util.List;

public class Ligne extends Shape implements Subject {

	private long id;
	private ArrayList<Point> points = new ArrayList<Point>();
	private int lenght;

	public Ligne(String name) {
		this.setName(name);
		lenght = 0;
	}
	
	public Ligne(String name, CouleurInterv couleur) {
		this.setName(name);
		lenght = 0;
		setColor(couleur);
	}
	
	public Ligne(String name, Observer o) {
		this.setName(name);
		lenght = 0;
		add(o);
		execute();
	}

	public Point head() {
		return points.get(0);
	}

	public Point last() {
		if (!points.isEmpty()) {
			return points.get(lenght);
		} else {
			return null;
		}
	}

	public void add(Point point) {
		points.add(point);
		lenght++;
	}

	public void add(int x, int y) {
		Point point = new Point(x, y);
		points.add(point);
		lenght++;
	}

	public void remove(Point point) {
		if (points.contains(point)) {
			points.remove(point);
			lenght--;
		}
	}

	public void remove(int x, int y) {
		Point point = new Point(x, y);
		if (points.contains(point)) {
			points.remove(point);
			lenght--;
		}
	}

	public int getlenght() {
		return this.lenght;
	}

	public Point getPoint(int i) {
		if (lenght > i) {
			return points.get(i);
		}
		return null;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public boolean equals(Object ligne) {
		if (this.name == ((Ligne) ligne).getName()) {
			return true;
		} else {
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
			observer.update("ID", name, "Longeur", 0);
		}
	}

	public void dbSave(long idCouche) {
		if (id == 0)
			this.dbAjouter(idCouche);
		else
			this.dbModifier();
		for (Point point : points) {
			point.dbSave(id, "ligne");
		}

	}

	private void dbModifier() {
		String query = "UPDATE Ligne SET Nom = ? WHERE ID = ?";
		List<String> args = new ArrayList<String>();
		args.add(this.name);
		args.add(String.valueOf(this.id));
		BDD.execute(query, args);
	}

	private void dbAjouter(long idCouche) {
		String query = "INSERT INTO Ligne VALUES (null, ?, ?);";
		List<String> args = new ArrayList<String>();
		args.add(this.name);
		args.add(String.valueOf(idCouche));
		id = BDD.execute(query, args);
	}

    @Override
    public int longeur() {
        JTS jts = new Operations();
        return jts.longeur(points);

    }

    @Override
    public int surface() {
        // TODO Auto-generated method stub
        return 0;
    }


}

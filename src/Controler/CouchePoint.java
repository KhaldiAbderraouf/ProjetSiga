package Controler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.BDD;
import Model.Point;
import Model.PointNomer;

public class CouchePoint extends Couche {
	private ArrayList<PointNomer> pointsn = new ArrayList<PointNomer>();

	public CouchePoint( String name){
		this.nom=name;
	}

	public void add(PointNomer point){
		pointsn.add(point);
	}
	public void add(int x, int y, String name ){
		PointNomer point= new PointNomer(x,y,name);
		pointsn.add(point);
	}

	public PointNomer getPoint(String point){
		for (int i =0 ; i<pointsn.size();i++){
			if(pointsn.get(i).getName()==point) return pointsn.get(i);
		}
		return null;
	}


	@Override
	public void add(String name, int... x) {
		try {
			PointNomer p = new PointNomer(x[0], x[1],name);
			pointsn.add(p);
		}catch (NullPointerException e){
			e.printStackTrace();
		}
	}

	public void remove(String point){
		for (int i =0 ; i<pointsn.size();i++){
			if(pointsn.get(i).getName()==point) pointsn.remove(i);
		}
	}

	@Override
	public void remove(String name, int... x) {
		for (int i =0 ; i<pointsn.size();i++){
			if(pointsn.get(i).getName()==name) pointsn.remove(i);
		}
	}

	@Override
	public void dbSave(long idSIG) {

	    dbSaveCouche(idSIG);
		for (PointNomer pointNomer: pointsn) {
			pointNomer.dbSave(id);
		}
	}

	public static CouchePoint dbFetchWithID(long id){
		CouchePoint couchePoint = null;
		String query = "SELECT * FROM Couche WHERE ID = ?";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(id));
		ResultSet rs = BDD.fetch(query, args);
		try {
			if(rs.next()){
				couchePoint = new CouchePoint(rs.getString("Nom"));
				couchePoint.id = rs.getLong("ID");
				List<PointNomer> pointNomerList = PointNomer.dbFetchWithIDCouche(id);
				if(pointNomerList != null){
					for (PointNomer pointNomer : pointNomerList) {
						couchePoint.add(pointNomer);
					}
				}
				else couchePoint = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couchePoint;
	}


}

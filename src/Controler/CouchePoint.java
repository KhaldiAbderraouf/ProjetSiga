package Controler;
import java.util.ArrayList;

import Model.PointNomer;
import Model.Shape;

public class CouchePoint extends Couche {

	private ArrayList<PointNomer> pointsn = new ArrayList<PointNomer>();
	private String name;


	@Override
	public String getName() {
		return this.name;
	}
	
	public CouchePoint( String name){
		this.name=name;
	}

	public void add(PointNomer point){
		pointsn.add(point);
	}
	public void add(int x, int y, String name ){
		PointNomer point= new PointNomer(x,y,name);
		pointsn.add(point);
	}

	public ArrayList<PointNomer> getPoints(){
		return pointsn;
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

	public void removeLast(){
		pointsn.remove(pointsn.size() - 1);
	}
	public boolean isEmpty(){
		return pointsn.isEmpty();
	}
	@Override
	public void dbSave(long idSIG) {

		dbSaveCouche(idSIG);
		for (PointNomer pointNomer: pointsn) {
			pointNomer.dbSave(id);
		}
	}
	@Override
	public ArrayList<String> getListShape() {
		ArrayList<String> l = new ArrayList<String>();
		for(int i=0; i<pointsn.size();i++){
			l.add(pointsn.get(i).getName());
		}
		return l;
	}
	@Override
	public Shape getShape(String s) {
		for(int i=0;i<pointsn.size();i++){
			if(s==pointsn.get(i).getName())
			return pointsn.get(i);
		}
		return null;
	}

}

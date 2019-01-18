package Controler;

import java.util.ArrayList;
import java.util.List;

import Model.Point;
import Model.Polygone;
import Model.Shape;

public class CouchePolygone extends Couche {
	private List<Polygone> polys = new ArrayList<Polygone>();
	private String name;

	public CouchePolygone( String name){
		this.name=name;
		//getTableAt().addColonne("Surface");
	}

	public void add(Polygone poly){
		polys.add(poly);
	}

	public void add(String name){
		Polygone poly = new Polygone(name);
		polys.add(poly);
	}
	

	public List<Polygone> getPolys(){
		return polys;
	}

	public void remove(Polygone poly){
		if(polys.contains(poly)){
			polys.remove(poly);
		}
	}
	public void remove(String name){
		Polygone poly = new Polygone(name);
		if(polys.contains(poly)){
			polys.remove(poly);
		}
	}

	public Polygone getPolygone(int i){
		return polys.get(i);
	}
	public Polygone getPolygone(String name){
		int i=0;
		while((i<polys.size())&&(polys.get(i).getName()!=name)){
			i++;
		}
		if(i<polys.size()){
			return polys.get(i);
		}
		return null;
	}

	public Polygone getLast(){
		return polys.get(polys.size() - 1);
	}


	public void remove(String name, int... x)
	{
		Polygone poly = getPolygone(name);
		if(poly!=null){
			for (int i=0;i<x.length/2;i++){
				poly.remove(x[2*i], x[1+(2*i)]);
			}
		}
	}

	@Override
	public void dbSave(long idSIG) {

		dbSaveCouche(idSIG);
		for (Polygone polygone : polys) {
			polygone.dbSave(id);
		}
	}

	@Override
	public ArrayList<String> getListShape() {
		ArrayList<String> l = new ArrayList<String>();
		for(int i=0; i<polys.size();i++){
			l.add(polys.get(i).getName());
		}
		return l;
	}
	@Override
	public Shape getShape(String s) {
		for(int i=0;i<polys.size();i++){
			if(s==polys.get(i).getName())
			return polys.get(i);
		}
		return null;
	}
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void add(String name, ArrayList<Point> x) {
		Polygone poly = getPolygone(name);
		int n=x.size();
		if(poly!=null) {
			for (int i=0;i<n;i++){
				poly.add(x.get(i%n).getX(),x.get(i%n).getY());
			}
		}
		else{
			poly = new Polygone(name);
			for (int i=0;i<n;i++){
				poly.add(x.get(i%n).getX(),x.get(i%n).getY());
			}
			polys.add(poly);
		}
	}
	
	public ArrayList<Shape> getShapes() {
		return  (ArrayList<Shape>)(ArrayList<?>) polys;
	}
}

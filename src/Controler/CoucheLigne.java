package Controler;

import java.util.ArrayList;
import java.util.List;

import Model.Ligne;
import Model.Point;
import Model.Shape;


public class CoucheLigne extends Couche {

	private ArrayList<Ligne> lignes= new ArrayList<Ligne>();
	private String name;
	private int lenght;

	public CoucheLigne( String name){
		this.name=name;
		lenght=0;
		//getTableAt().addColonne("Longeur");
	}
	public void add(Ligne ligne){
		lignes.add(ligne);
		lenght++;
	}
	public void add(String name){
		Ligne ligne = new Ligne(name);
		lignes.add(ligne);
		lenght++;
	}

	public void remove(Ligne ligne){
		if(lignes.contains(ligne)){
			lignes.remove(ligne);
			lenght--;
		}
	}
	public void remove(String name){
		Ligne ligne = new Ligne(name);
		if(lignes.contains(ligne)){
			lignes.remove(ligne);
			lenght--;
		}
	}

	public Ligne getLigne(int i){
		return lignes.get(i);
	}
	public Ligne getLigne(String name){
		int i=0;
		while((i<lenght)&&(lignes.get(i).getName()!=name)){
			i++;
		}
		if(i<lenght){
			return lignes.get(i);
		}
		return null;
	}
	public Ligne getLast(){
		return lignes.get(lignes.size() - 1);
	}

	public List<Ligne> getLignes(){
		return lignes;
	}
	public void add(String name, ArrayList<Point> x)
	{
		Ligne ligne = getLigne(name);
		if(ligne!=null){
			for (int i=0;i<x.size();i++){
				ligne.add(x.get(i).getX(),x.get(i).getY());
				lenght++;
			}
		}
		else{
			ligne = new Ligne(name);
			for (int i=0;i<x.size();i++){
				ligne.add(x.get(i).getX(),x.get(i).getY());
			}
			lignes.add(ligne);
			lenght++;
		}
	}
	public void remove(String name, int... x)
	{
		Ligne ligne = getLigne(name);
		if(ligne!=null){
			for (int i=0;i<x.length/2;i++){
				ligne.remove(x[2*i],x[1+(2*i)]);
			}
		}
	}


	@Override
	public void dbSave(long idSIG) {
		dbSaveCouche(idSIG);

		for ( Ligne ligne: lignes) {
			ligne.dbSave(id);
		}
	}
	@Override
	public ArrayList<String> getListShape() {
		ArrayList<String> l = new ArrayList<String>();
		for(int i=0; i<lignes.size();i++){
			l.add(lignes.get(i).getName());
		}
		return l;
	}
	@Override
	public Shape getShape(String s) {
		for(int i=0;i<lignes.size();i++){
			if(s==lignes.get(i).getName())
			return lignes.get(i);
		}
		return null;
	}
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public ArrayList<Shape> getShapes() {
		return  (ArrayList<Shape>)(ArrayList<?>) lignes;
	}

}

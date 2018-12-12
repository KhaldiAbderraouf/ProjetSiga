package Controler;

import java.util.List;

import Model.Ligne;


public class CoucheLigne extends Couche {
	private List<Ligne> lignes;
	private String name;
	private int lenght;
	
	public CoucheLigne( String name){
		this.name=name;
		lenght=0;
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
	public void add(String name, int x, int y)
	{
		Ligne ligne = getLigne(name);
		if(ligne!=null){
			ligne.add(x, y);
		}
	}
	public void remove(String name, int x, int y)
	{
		Ligne ligne = getLigne(name);
		if(ligne!=null){
			ligne.remove(x, y);
		}
	}
	
}

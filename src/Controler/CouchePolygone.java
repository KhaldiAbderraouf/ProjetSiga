package Controler;

import java.util.List;

import Model.Polygone;

public class CouchePolygone extends Couche {
	private List<Polygone> polys;
	private String name;
	private int lenght;
	
	public CouchePolygone( String name){
		this.name=name;
	}
	public void add(Polygone poly){
		polys.add(poly);
	}
	
	public void add(String name){
		Polygone poly = new Polygone(name);
		polys.add(poly);
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
	
	public Polygone getLigne(int i){
		return polys.get(i);
	}
	public Polygone getLigne(String name){
		int i=0;
		while((i<lenght)&&(polys.get(i).getName()!=name)){
			i++;
		}
		if(i<lenght){
			return polys.get(i);
		}
		return null;
	}
	
	public void add(String name, int x, int y)
	{
		Polygone poly = getLigne(name);
		if(poly!=null){
			poly.add(x, y);
		}
	}
	public void remove(String name, int x, int y)
	{
		Polygone poly = getLigne(name);
		if(poly!=null){
			poly.remove(x, y);
		}
	}
}

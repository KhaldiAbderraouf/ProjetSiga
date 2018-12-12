package Controler;
import java.util.Map;

import Model.Point;

public class CouchePoint extends Couche {
	private Map<String,Point> points;
	private String name;
	public CouchePoint( String name){
		this.name=name;
	}
	public void add(Point point, String name){
		points.put(name, point);
	}
	public void add(int x, int y, String name ){
		Point point= new Point(x,y);
		points.put(name, point);
	}
	
	public void remove(String point){
		if(points.containsKey(point)){
			points.remove(point);
		}
	}
	
}

package Model;

import java.util.List;

public class Polygone {
	private List<Point> points;
	private String name;
	private int lenght;
	
	public Polygone(String name){
		this.name=name;
		lenght=0;
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
		//la Superficie en kilometre carré du polygone
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
	public boolean equals(Polygone poly){
		if(this.name==poly.getName()){
			return true;
		}
		else {
			return false;
		}
	}
}

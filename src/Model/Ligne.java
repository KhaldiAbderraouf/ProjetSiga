package Model;

import java.util.List;

public class Ligne {
	private List<Point> points;
	private String name;
	private int lenght;
	
	public Ligne(String name){
		this.setName(name);
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
	public Point last(){
		if(!points.isEmpty()){
			return points.get(lenght);
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
	public double longeur(){
		//la longeur en kilometre de la ligne
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
	public boolean equals(Object ligne){
		if(this.name==((Ligne)ligne).getName()){
			return true;
		}
		else {
			return false;
		}
	}
}

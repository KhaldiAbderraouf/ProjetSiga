package Model;

public class Point {
	int X,Y;
	
	public Point(int x, int y){
		this.X=x;
		this.Y=y;
	}
	private void savePoint(){
		//save the point
	}
	public int getX(){
		return this.X;
	}
	public int getY(){
		return this.Y;
	}
	public boolean equals(Object point){
		if((this.X==((Point)point).getX())&&(this.Y==((Point)point).getY())){
			return true;
		}
		else {
			return false;
		}
	}
	
}

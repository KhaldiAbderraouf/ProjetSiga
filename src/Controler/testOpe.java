package Controler;

import java.util.ArrayList;

import Model.Point;

public class testOpe {

	public static void main(String[] args) {
		Operations jts = new Operations();
		ArrayList<Point> A=new ArrayList<Point>(),B= new ArrayList<Point>();
		A.add(new Point(0,1));
		A.add(new Point(1,3));
		A.add(new Point(3,2));
		A.add(new Point(4,0));
		A.add(new Point(5,0));
		B.add(new Point(1,1));
		B.add(new Point(2,2));
		//B.add(new Point(3,2));
		B.add(new Point(3,0));
		//B.add(new Point(5,1));
		//B.add(new Point(4,0));
		//B.add(new Point(7,0));
		//B.add(new Point(5,4));
		//B.add(new Point(1,9));
		//System.out.println(jts.isInSegment(A, B, I));
		//System.out.println(jts.intersectsegment(A, B, I, P));
		int[][]res=jts.topoLigneLigne(A,B);
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				System.out.println(res[i][j]);
			}
		}
		//System.out.println(jts.isInSegment(A.get(0), A.get(1), B.get(0)));
		
		
	}

}

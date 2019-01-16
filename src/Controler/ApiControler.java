package Controler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Model.Ligne;
import Model.Point;
import Model.PointNomer;
import Model.Polygone;
import Model.Shape;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ApiControler implements Initializable{
	
	@FXML
    private ComboBox<String> couche1;
	@FXML
    private ComboBox<String> couche2;
	@FXML
    private ComboBox<String> form1;
	@FXML
    private ComboBox<String> form2;
	@FXML
    private GridPane tableau;
	
	private JTS jts=new Operations();
	
	public Principale2Controller principale2Controller;
	public ClassesController classesController;
	
	Sig sig;
	
	    public void setClassesController(ClassesController classesController) {
	        this.classesController = classesController;
	    }

	    public void setPrincipale2Controller(Principale2Controller principale2Controller) {
	        this.principale2Controller = principale2Controller;
	    }

	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	for(int i=0;i<3;i++){
	    		for(int j=0;j<3;j++){
		    		tableau.add(new Label(), i, j);
		    	}
	    	}
	    }
	    
	    public void init() {
	    	sig= principale2Controller.getSig();
	    	/*sig.addPolygone("couchepoly1","poly1",0,1,2,2,1,4,4,3,5,1);
	    	sig.addPolygone("couchepoly2","poly1",4,0,3,2,4,4,7,3,6,1);
	    	sig.addPolygone("couchepoly2","poly2",7,0,6,1,7,3,8,1);
	    	sig.addLigne("coucheLigne1","ligne1",0,0,3,2,4,4,2,8);
	    	sig.addLigne("coucheLigne2","ligne1",5,5,4,4,3,6,4,7);
	    	sig.addLigne("coucheLigne2","ligne2",5,6,6,6,8,2,9,2);
	    	sig.addPoint("couchePoint1","point1",7,1);
	    	sig.addPoint("couchePoint2","point1",7,4);
	    	sig.addPoint("couchePoint2","point2",2,5);*/
	    	
	    	remplirCouche();
	    }
	    
	public void couche1selected(){
		remplirCouche(couche1.getValue());
		remplirShape1(couche1.getValue());
	}
	public void couche2selected(){
		remplirShape2(couche2.getValue());
	}
	public void calculateclementini(){
		Shape a,b;
		a=getShapeFromCouche(couche1.getValue(),form1.getValue());
		b=getShapeFromCouche(couche2.getValue(),form2.getValue());
		System.out.println(form2.getValue());
		int i,j;
		int[][]res;
		i=poidShape(a);
		j=poidShape(b);
		if(i==1){
			if(j==1){
				res=jts.topoPointPoint(((PointNomer)a).getPoint(),((PointNomer)b).getPoint());
			}
			else{
				if(j==2){
					res=jts.topoLignePoint(((Ligne)b).getPoints(),((PointNomer)a).getPoint());
				}
				else{
					res=jts.topoPolyPoint(((Polygone)b).getPoints(),((PointNomer)a).getPoint());
				}
			}
		}else{
			if(i==2){
				if(j==1){
					res=jts.topoLignePoint(((Ligne)a).getPoints(),((PointNomer)b).getPoint());
				}
				else{
					if(j==2){
						res=jts.topoLigneLigne(((Ligne)a).getPoints(),((Ligne)b).getPoints());
					}
					else{
						res=jts.topoPolyLigne(((Polygone)b).getPoints(),((Ligne)a).getPoints());
					}
				}
			}else{
				if(j==1){
					res=jts.topoPolyPoint(((Polygone)a).getPoints(),((PointNomer)b).getPoint());
				}
				else{
					if(j==2){
						res=jts.topoPolyLigne(((Polygone)a).getPoints(),((Ligne)b).getPoints());
					}
					else{
						res=jts.topoPolyPoly(((Polygone)a).getPoints(),((Polygone)b).getPoints());
					}
				}
			}
		}
		remplirGrid(res);
	}
	
	public void remplirCouche(){
		couche1.getItems().clear();
	    ArrayList<String> mylist = sig.getListCouches();
	    if (mylist!=null){
	    	for (int i=0; i<mylist.size();i++){
	    		couche1.getItems().add(mylist.get(i));
	        }
	    }
	}
	public void remplirCouche(String c1){
		int ci=poid(sig.get(c1));
		couche2.getItems().clear();
	    ArrayList<Couche> mylist = sig.getCouches();
	    if (mylist!=null){
	    	for (int i=0; i<mylist.size();i++){
	    		if(ci>=poid(mylist.get(i))){
	    			couche2.getItems().add(mylist.get(i).getName());
	    		}
	    		
	        }
	    }
	}

	public void remplirShape1(String c1){
		form1.getItems().clear();
	    ArrayList<String> mylist = sig.get(c1).getListShape();
	    if (mylist!=null){
	    	for (int i=0; i<mylist.size();i++){
	    		form1.getItems().add(mylist.get(i));
	        }
	    }
	}
	public void remplirShape2(String c1){
		form2.getItems().clear();
	    ArrayList<String> mylist = sig.get(c1).getListShape();
	    if (mylist!=null){
	    	for (int i=0; i<mylist.size();i++){
	    		form2.getItems().add(mylist.get(i));
	        }
	    }
	}
	
	public void remplirGrid(int[][]res){
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				//System.out.println(res[i][j]);
				tableau.add(new Label((res[i][j])+""), i, j);
			}
		}
	}
	
	public int poid(Couche c){
		if(c instanceof CouchePoint){
			return 1;
		}else{
			if(c instanceof CoucheLigne){
				return 2;
			}else{
				return 3;
			}
		}
	}
	
	public int poidShape(Shape c){
		if(c instanceof PointNomer){
			return 1;
		}else{
			if(c instanceof Ligne){
				return 2;
			}else{
				return 3;
			}
		}
	}
	public ArrayList<Point> getShapeCoor(Shape c){
		if(poidShape(c)==3){
			return ((Polygone)c).getPoints();
		}else{
			if(poidShape(c)==2){
				return ((Ligne)c).getPoints();
			}
		}
		return null;
	}
	public Shape getShapeFromCouche(String c,String s){
		return sig.get(c).getShape(s);
	}
}

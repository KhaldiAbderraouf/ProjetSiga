package Controler;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import Model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class Sig {

	private long id;
	private User user;
	private String nom;
	private String cheminImageFond;
	private Image fond;
	private ArrayList<Couche> Couches = new ArrayList<Couche>();
	public JTS op=new Operations();
	private SystemeCoordonnees[] coord;

	public Sig(String user, String fond ){
		this.user = new User(user);
		this.cheminImageFond = fond;
		try {
			this.fond = new Image(new FileInputStream(fond));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Sig(String nom, String user, String fond ){
		this.user = new User(user);
		this.cheminImageFond = fond;
		this.nom = nom;
		try {
			this.fond = new Image(new FileInputStream(fond));
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Sig(String u ){
		user= new User(u);
		addFond();
		//coord[0]= new CoordonneesXY();
		//coord[0]= new CoordonneesLL();
	}

	public ArrayList<Couche> getCouches() {
		return Couches;
	}
	
	public ArrayList<String> getListCouches() {
		ArrayList<String> l = new ArrayList<String>();
		for(Couche c:Couches){
			l.add(c.getName());
		}
		return l;
	}
	
	
	public void setCouches(ArrayList<Couche> couches) {
		Couches = couches;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public void addFond(){
		javafx.scene.image.Image back = null;
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (.jpg)", ".JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (.png)", ".PNG");
		Stage frame = new Stage();
		try{
			this.cheminImageFond=fileChooser.showOpenDialog(frame).getPath();
			back =  new javafx.scene.image.Image(new FileInputStream(cheminImageFond));
		}catch (Exception e){
			System.out.println("Couldn't load map : "+ e);
		}
		this.fond=back;
	}

	public Image getFond(){
		return this.fond;
	}
	public Image getFond(ImageView vImg){
		vImg.setImage(fond);
		return this.fond;
	}

	public String getCheminImageFond(){return this.cheminImageFond;}

	public void addCouchePoint(String name){
		CouchePoint c = new CouchePoint(name);
		Couches.add(c);
	}
	public void addCoucheLigne(String name){
		CoucheLigne c = new CoucheLigne(name);
		Couches.add(c);
	}
	public void addCouchePolygone(String name){
		CouchePolygone c = new CouchePolygone(name);
		Couches.add(c);
	}
	
	public Couche get(String couche){
		//System.out.println(couche);
		for (Couche c: Couches){
			if (c.getName()==couche){
				return c;
			}
		}
		return null;
	}
	/*
	public Couche getCouche(String name){
		return Couches.get(name);
	}
	*/
	public Ligne getLigne(String couche, String ligne){
		return ((CoucheLigne) get(couche)).getLigne(ligne);
	}
	public PointNomer getPoint(String couche, String point){
		return ((CouchePoint) get(couche)).getPoint(point);
	}
	public Polygone getPolygone(String couche, String ligne){
		return ((CouchePolygone) get(couche)).getPolygone(ligne);
	}

	public void addPoint(String couche, String point,ArrayList<Point> y){
		if (get(couche)!=null){
			get(couche).add(point,y);
		}else{
			CouchePoint couchep = new CouchePoint(couche);
			couchep.add(point,y);
			Couches.add(couchep);
		}
	}	
	public void addPoint(String couche, PointNomer point){
		if (get(couche)!=null){
			((CouchePoint) get(couche)).add(point);
		}else{
			CouchePoint couchep = new CouchePoint(couche);
			couchep.add(point);
			Couches.add( couchep);
		}
	}

	public void addLigne(String couche, String ligne, ArrayList<Point> x){
		if (get(couche)!=null){
			get(couche).add(ligne,x);
		}else{
			CoucheLigne couchep = new CoucheLigne(couche);
			couchep.add(ligne,x);
			Couches.add( couchep);
		}

	}
	public void addLigne(String couche, Ligne ligne){
		if (get(couche)!=null){
			((CoucheLigne) get(couche)).add(ligne);
		}else{
			CoucheLigne couchep = new CoucheLigne(couche);
			couchep.add(ligne);

			Couches.add( couchep);
		}
	}

	public void addPolygone(String couche, String poly, ArrayList<Point> x){
		if (get(couche)!=null){
			get(couche).add(poly,x);
		}else{
			CouchePolygone couchep = new CouchePolygone(couche);
			couchep.add(poly,x);
			Couches.add( couchep);
		}

	}
	public void addPolygone(String couche, Polygone poly){
		if (get(couche)!=null){
			((CouchePolygone) get(couche)).add(poly);
		}else{
			CouchePolygone couchep = new CouchePolygone(couche);
			couchep.add(poly);

			Couches.add( couchep);
		}
	}


	public void removeShape(String coucheName, String shapeName){
		if (get(coucheName)!=null){
			get(coucheName).remove(shapeName);
		}
	}

	public void removePoint(String couche, String point, int x, int y){
		if (get(couche)!=null){
			get(couche).remove(point);
		}
	}
	public void removeLigne(String couche, String ligne, int x, int y){
		if (get(couche)!=null){
			get(couche).remove(ligne,x);
		}
	}
	public void removePolygone(String couche, String poly, int x, int y){
		if (get(couche)!=null){
			get(couche).remove(poly,x);
		}
	}

	public void addColonne(String cs,String name,Object... list){
		Couche c= get(cs);
		c.addColonne(name,list);
	}
	public void removeColonne(String cs,String name){
		Couche c= get(cs);
		c.removeColonne(name);
	}

	public void addToColonne(String cs,String col,Object o){
		Couche c= get(cs);
		c.addToColonne(col,o);
	}
	public void addToColonne(String cs,Object... o){
		Couche c= get(cs);
		c.addToColonne(o);
	}

	public void removeFromColonne(String cs,Object... list){
		Couche c= get(cs);
		c.removeFromColonne(list);
	}
	public void removeFromColonne(String cs,String col,String e){
		Couche c= get(cs);
		c.removeFromColonne(col,e);
	}
	public void removeFromColonne(String cs,String col,int index){
		Couche c= get(cs);
		c.removeFromColonne(col,index);
	}

	public void dbSave(){
		if(id == 0)
			this.ajouter();
		else
			this.modifier();

		for(Couche entry : Couches) {
			Couche couche = entry;
			couche.dbSave(id);
		}
	}

	private void modifier() {
		String query = "UPDATE SIG SET Nom = ?, LienFond=? WHERE ID = ?;";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(this.nom));
		args.add(this.cheminImageFond);
		args.add(String.valueOf(this.id));
		BDD.execute(query, args);
	}

	public void ajouter(){
		String query = "INSERT INTO SIG VALUES (null, ?, ?, ?);";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(this.nom));
		args.add(String.valueOf(this.user.getId()));
		args.add(this.cheminImageFond);
		id = BDD.execute(query, args);
	}

}

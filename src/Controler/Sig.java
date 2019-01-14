package Controler;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
	private Map<String,Couche> Couches = new TreeMap<>();
	public JTS op=new Operations();
	private SystemeCoordonnees[] coord;

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

	public Sig(String nom, String u){
		user= new User(u);
		this.nom = nom;

	}

	public Map<String, Couche> getCouches() {
		return Couches;
	}
	public void setCouches(Map<String, Couche> couches) {
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
		Couches.put(name,c);
	}
	public void addCoucheLigne(String name){
		CoucheLigne c = new CoucheLigne(name);
		Couches.put(name,c);
	}
	public void addCouchePolygone(String name){
		CouchePolygone c = new CouchePolygone(name);
		Couches.put(name,c);
	}

	public Couche getCouche(String name){
		return Couches.get(name);
	}

	public Ligne getLigne(String couche, String ligne){
		return ((CoucheLigne)Couches.get(couche)).getLigne(ligne);
	}
	public PointNomer getPoint(String couche, String point){
		return ((CouchePoint)Couches.get(couche)).getPoint(point);
	}
	public Polygone getPolygone(String couche, String ligne){
		return ((CouchePolygone)Couches.get(couche)).getPolygone(ligne);
	}

	public void addPoint(String couche, String point, int x, int y){
		if (Couches.containsKey(couche)){
			Couches.get(couche).add(point,x,y);
		}else{
			CouchePoint couchep = new CouchePoint(couche);
			couchep.add(point,x,y);
		}
	}
	public void addPoint(String couche, PointNomer point){
		if (Couches.containsKey(couche)){
			((CouchePoint)Couches.get(couche)).add(point);
		}else{
			CouchePoint couchep = new CouchePoint(couche);
			couchep.add(point);
			Couches.put(couche, couchep);
		}
	}

	public void addLigne(String couche, String ligne, int... x){
		if (Couches.containsKey(couche)){
			Couches.get(couche).add(ligne,x);
		}else{
			CoucheLigne couchep = new CoucheLigne(couche);
			couchep.add(ligne,x);
		}

	}
	public void addLigne(String couche, Ligne ligne){
		if (Couches.containsKey(couche)){
			((CoucheLigne)Couches.get(couche)).add(ligne);
		}else{
			CoucheLigne couchep = new CoucheLigne(couche);
			couchep.add(ligne);

			Couches.put(couche, couchep);
		}
	}

	public void addPolygone(String couche, String poly, int... x){
		if (Couches.containsKey(couche)){
			Couches.get(couche).add(poly,x);
		}else{
			CouchePolygone couchep = new CouchePolygone(couche);
			couchep.add(poly,x);
		}

	}
	public void addPolygone(String couche, Polygone poly){
		if (Couches.containsKey(couche)){
			((CouchePolygone)Couches.get(couche)).add(poly);
		}else{
			CouchePolygone couchep = new CouchePolygone(couche);
			couchep.add(poly);

			Couches.put(couche, couchep);
		}
	}


	public void removeShape(String coucheName, String shapeName){
		if (Couches.containsKey(coucheName)){
			Couches.get(coucheName).remove(shapeName);
		}
	}

	public void removePoint(String couche, String point, int x, int y){
		if (Couches.containsKey(couche)){
			Couches.get(couche).remove(point);
		}
	}
	public void removeLigne(String couche, String ligne, int x, int y){
		if (Couches.containsKey(couche)){
			Couches.get(couche).remove(ligne,x);
		}
	}
	public void removePolygone(String couche, String poly, int x, int y){
		if (Couches.containsKey(couche)){
			Couches.get(couche).remove(poly,x);
		}
	}

	public void addColonne(String cs,String name,Object... list){
		Couche c= getCouche(cs);
		c.addColonne(name,list);
	}
	public void removeColonne(String cs,String name){
		Couche c= getCouche(cs);
		c.removeColonne(name);
	}

	public void addToColonne(String cs,String col,Object o){
		Couche c= getCouche(cs);
		c.addToColonne(col,o);
	}
	public void addToColonne(String cs,Object... o){
		Couche c= getCouche(cs);
		c.addToColonne(o);
	}

	public void removeFromColonne(String cs,Object... list){
		Couche c= getCouche(cs);
		c.removeFromColonne(list);
	}
	public void removeFromColonne(String cs,String col,String e){
		Couche c= getCouche(cs);
		c.removeFromColonne(col,e);
	}
	public void removeFromColonne(String cs,String col,int index){
		Couche c= getCouche(cs);
		c.removeFromColonne(col,index);
	}

	public void dbSave(){
		this.user.dbSave();
		if(id == 0)
			this.ajouter();
		else
			this.modifier();

		for(Map.Entry<String,Couche> entry : getCouches().entrySet()) {
			Couche couche = entry.getValue();
			couche.dbSave(id);
		}
	}

//	private void modifier() {
//		String query = "UPDATE SIG SET Nom = ?, LienFond=? WHERE ID = ?;";
//		List<String> args = new ArrayList<String>();
//		args.add(String.valueOf(this.nom));
//		args.add(this.cheminImageFond);
//		args.add(String.valueOf(this.id));
//		BDD.execute(query, args);
//	}
//
//	public void ajouter(){
//		String query = "INSERT INTO SIG VALUES (null, ?, ?, ?);";
//		List<String> args = new ArrayList<String>();
//		args.add(String.valueOf(this.nom));
//		args.add(String.valueOf(this.user.getId()));
//		args.add(this.cheminImageFond);
//		id = BDD.execute(query, args);
//	}
//=======



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
//>>>>>>> 5b103d76fa0e76264f91d25bb7e08d32c2ed5190

	public static Sig dbFetchWithID(long id){
		Sig sig = null;
		String query = "SELECT * FROM SIG WHERE ID = ?";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(id));
		ResultSet rs = BDD.fetch(query, args);
		try {
			if(rs.next()){
			    User user = new User("user");/////////////////////////USER TEST////////////////
				sig = new Sig(rs.getString("Nom"), user.getName());
				sig.id = rs.getLong("ID");
				List<Couche> coucheList = Couche.dbFetchWithIDSig(id);
				int i=0;
				for (Couche couche : coucheList) {
					sig.Couches.put(String.valueOf(i), couche);/////////////////////////COUCHE TEST////////////////
                    i++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sig;
	}

}

package Controler;

import Model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Couche {
	private Symbologie sym=new Symbologie();
	private TableAttr tableAt=new TableAttr();

	protected long id=0;
	private String nom;

	public Symbologie getSym() {
		return sym;
	}
	public void setSym(Symbologie sym) {
		this.sym = sym;
	}

	public TableAttr getTableAt() {
		return tableAt;
	}
	public void setTableAt(TableAttr tableAt) {
		this.tableAt = tableAt;
	}

	public void addColonne(String name,Object... list){
		this.tableAt.addColonne(name,list);
	}
	public void removeColonne(String name){
		this.tableAt.removeColonne(name);
	}

	public void addToColonne(String col,Object o){
		this.tableAt.addToColonne(col,o);
	}
	public void addToColonne(Object... o){
		this.tableAt.addToColonne(o);
	}

	public void removeFromColonne(Object... list){
		this.tableAt.removeFromColonne(list);
	}
	public void removeFromColonne(String col,String e){
		this.tableAt.removeFromColonne(col,e);
	}
	public void removeFromColonne(String col,int index){
		this.tableAt.removeFromColonne(col,index);
	}

	public abstract void add(String name, int... x);
	public abstract void remove(String name);
	public abstract void remove(String name, int... x);

	public abstract void dbSave(long idSigidSIG);

	public void dbAjouter(long idSIG) {
		String query = "INSERT INTO Couche VALUES (null, ?, ?);";
		List<String> args = new ArrayList<String>();
		args.add(this.nom);
		args.add(String.valueOf(idSIG));
		id = BDD.execute(query, args);
	}

	public void dbModifier() {
		String query = "UPDATE Couche SET Nom = ? WHERE ID = ?";
		ArrayList<String> args = new ArrayList<String>();
		args.add(this.nom);
		args.add(String.valueOf(this.id));
		BDD.execute(query, args);
	}

	protected void dbSaveCouche(long idSIG){
		if(id == 0)
			this.dbAjouter(idSIG);
		else
			this.dbModifier();

		this.sym.dbSave(id);
		this.tableAt.dbSave(id);

	}
}

package Controler;

import Model.TableAttr;

public abstract class Couche {
	private Symbologie sym=new Symbologie();
	private TableAttr tableAt=new TableAttr();

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
}

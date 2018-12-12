package Controler;

import Model.TableAttr;

public abstract class Couche {
	private Symbologie sym;
	private TableAttr tableAt;
	/**
	 * @return the sym
	 */
	public Symbologie getSym() {
		return sym;
	}
	/**
	 * @param sym the sym to set
	 */
	public void setSym(Symbologie sym) {
		this.sym = sym;
	}
	/**
	 * @return the tableAt
	 */
	public TableAttr getTableAt() {
		return tableAt;
	}
	/**
	 * @param tableAt the tableAt to set
	 */
	public void setTableAt(TableAttr tableAt) {
		this.tableAt = tableAt;
	}
}

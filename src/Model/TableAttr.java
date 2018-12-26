package Model;

import java.util.ArrayList;

public class TableAttr implements Observer{
	/*
	 * les autres attributs 
	 */
	private ArrayList<Colonne> tables= new ArrayList<>();
	private int taille=0;
	public TableAttr(){
		initilaiser();
	}

	private void initilaiser() {
		Colonne names = new Colonne("ID",0,"");
		Colonne colors = new Colonne("Colors",0,"");
		names.retirerElem(0);
		colors.retirerElem(0);
		tables.add(names);
		tables.add(colors);
	}

	public TableAttr(Object... list){
		taille=list.length;

		Colonne names = new Colonne("ID",taille,list);
		Colonne colors = new Colonne("Colors",taille,"Vide");
		tables.add(names);
		tables.add(colors);
	}
	public void addColonne(String name,Object... list){
		tables.add(new Colonne(name,taille,list));
	}
	public void removeColonne(String name) {
		if (name != "ID"){
			tables.remove(tables.get(getColonneName(name)));
		}else {
			tables.clear();
			initilaiser();
		}
	}

	public void addToColonne(String col,Object o){
		for (Colonne c : tables) {
			if(c.getName()==col){
				c.addElement(o);
				if (c.getTaille()>taille && c.getName()=="ID"){
					for (Colonne e : tables){
						if(e.getName()!="ID"){
							e.addElement("Vide");
						}
					}
					taille=c.getTaille();
					break;
				}
			}
		}
	}
	public void addToColonne(Object...list){
		for (int i=0;i<list.length;i+=2){
			try {
				for (Colonne e : tables){
					if(e.getName()!=list[i].toString()){
						e.addElement(list[i+1]);
					}
				}
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		}
	}
	public void removeFromColonne(String col,int index){
		tables.get(getColonneName(col)).retirerElem(index);
	}
	public void removeFromColonne(String col,String e){
		tables.get(getColonneName(col)).retirerElem(e);
	}
	public void removeFromColonne(Object... list){
		for (int i=0;i<list.length;i+=2){
			try {
				tables.get(getColonneName(list[i].toString())).retirerElem(list[i+1].toString());
			}catch (NullPointerException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Object... list) {
		this.addToColonne(list);
	}

	private int getColonneName(String name){
		int i=-1;
		for (Colonne e : tables){
			i++;
			if(e.getName()==name){
				return i;
			}
		}
		return i;
	}
}

package Controler;

import java.util.ArrayList;
import java.util.List;

import Model.Ligne;


public class CoucheLigne extends Couche {

	private List<Ligne> lignes= new ArrayList<Ligne>();
	private String name;
	private int lenght;

	public CoucheLigne( String name){
		this.name=name;
		lenght=0;
		getTableAt().addColonne("Longeur");
	}
	public void add(Ligne ligne){
		lignes.add(ligne);
		lenght++;
	}
	public void add(String name){
		Ligne ligne = new Ligne(name);
		lignes.add(ligne);
		lenght++;
	}
	
	public void remove(Ligne ligne){
		if(lignes.contains(ligne)){
			lignes.remove(ligne);
			lenght--;
		}
	}
	public void remove(String name){
		Ligne ligne = new Ligne(name);
		if(lignes.contains(ligne)){
			lignes.remove(ligne);
			lenght--;
		}
	}
	
	public Ligne getLigne(int i){
		return lignes.get(i);
	}
	public Ligne getLigne(String name){
		int i=0;
		while((i<lenght)&&(lignes.get(i).getName()!=name)){
			i++;
		}
		if(i<lenght){
			return lignes.get(i);
		}
		return null;
	}
	public void add(String name, int... x)
	{
		Ligne ligne = getLigne(name);
		if(ligne!=null){
			for (int i=0;i<x.length/2;i++){
				ligne.add(x[2*i],x[1+(2*i)]);
			}
		}
		else{
			ligne = new Ligne(name);
			for (int i=0;i<x.length/2;i++){
				ligne.add(x[2*i],x[1+(2*i)]);
			}
		}
	}
	public void remove(String name, int... x)
	{
		Ligne ligne = getLigne(name);
		if(ligne!=null){
			for (int i=0;i<x.length/2;i++){
				ligne.remove(x[2*i],x[1+(2*i)]);
			}
		}
	}


    @Override
    public void dbSave(long idSIG) {
	    dbSaveCouche(idSIG);

        for ( Ligne ligne: lignes) {
            ligne.dbSave(id);
        }
    }

}

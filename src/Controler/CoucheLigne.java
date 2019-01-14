package Controler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.BDD;
import Model.Ligne;
import Model.Polygone;


public class CoucheLigne extends Couche {

	private List<Ligne> lignes= new ArrayList<Ligne>();
	private int lenght;

	public CoucheLigne( String name){
		this.nom=name;
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
	public Ligne getLast(){
		if (lignes.size() > 0)
			return lignes.get(lignes.size() - 1);
		return null;
	}

	public List<Ligne> getLignes(){
		return lignes;
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

    public static CoucheLigne dbFetchWithID(long id){
        CoucheLigne coucheLigne = null;
        String query = "SELECT * FROM Couche WHERE ID = ?";
        List<String> args = new ArrayList<String>();
        args.add(String.valueOf(id));
        ResultSet rs = BDD.fetch(query, args);
        try {
            if(rs.next()){
                coucheLigne = new CoucheLigne(rs.getString("Nom"));
                coucheLigne.id = rs.getLong("ID");
                List<Ligne> ligneList = Ligne.dbFetchWithIDCouche(id);
                if(ligneList != null){
					for (Ligne ligne : ligneList) {
						coucheLigne.add(ligne);
					}

				}
				else coucheLigne = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coucheLigne;
    }

}

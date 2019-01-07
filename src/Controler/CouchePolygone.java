package Controler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.BDD;
import Model.PointNomer;
import Model.Polygone;

public class CouchePolygone extends Couche {
	private List<Polygone> polys = new ArrayList<Polygone>();

	public CouchePolygone( String name){
		this.nom=name;
		getTableAt().addColonne("Surface");
	}

	public void add(Polygone poly){
		polys.add(poly);
	}
	
	public void add(String name){
		Polygone poly = new Polygone(name);
		polys.add(poly);
	}
	public void add(String name, int... x)
	{
		Polygone poly = getPolygone(name);
		if(poly!=null) {
			for (int i = 0; i <= x.length / 2; i++) {
				poly.add(x[(2*i)%x.length], x[1+(2*i)%x.length]);
			}
		}
		else{
			poly = new Polygone(name);
			for (int i=0;i<=x.length/2;i++){
				poly.add(x[(2*i)%x.length], x[1+(2*i)%x.length]);
			}
		}
	}
	
	public void remove(Polygone poly){
		if(polys.contains(poly)){
			polys.remove(poly);
		}
	}
	public void remove(String name){
		Polygone poly = new Polygone(name);
		if(polys.contains(poly)){
			polys.remove(poly);
		}
	}
	
	public Polygone getPolygone(int i){
		return polys.get(i);
	}
	public Polygone getPolygone(String name){
		int i=0;
		while((i<polys.size())&&(polys.get(i).getName()!=name)){
			i++;
		}
		if(i<polys.size()){
			return polys.get(i);
		}
		return null;
	}

	public void remove(String name, int... x)
	{
		Polygone poly = getPolygone(name);
		if(poly!=null){
			for (int i=0;i<x.length/2;i++){
				poly.remove(x[2*i], x[1+(2*i)]);
			}
		}
	}

    @Override
    public void dbSave(long idSIG) {

        dbSaveCouche(idSIG);
        for (Polygone polygone : polys) {
            polygone.dbSave(id);
        }
    }

    public String getName() {
		return nom;
	}

	public static CouchePolygone dbFetchWithID(long id){
		CouchePolygone couchePolygone = null;
		String query = "SELECT * FROM Couche WHERE ID = ?";
		List<String> args = new ArrayList<String>();
		args.add(String.valueOf(id));
		ResultSet rs = BDD.fetch(query, args);
		try {
			if(rs.next()){
				couchePolygone = new CouchePolygone(rs.getString("Nom"));
				couchePolygone.id = rs.getLong("ID");
				List<Polygone> polygoneList = Polygone.dbFetchWithIDCouche(id);
				if(polygoneList != null){
                    for (Polygone polygone : polygoneList) {
                        couchePolygone.add(polygone);
                    }

                }
                else couchePolygone = null;

            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return couchePolygone;
	}
}

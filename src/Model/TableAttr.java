package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TableAttr implements Observer {
	/*
	 * les autres attributs
	 */
	private ArrayList<Colonne> tables = new ArrayList<>();
	private int taille = 0;
	private long id;
	private String nom;
	//private ArrayList<String> col
	public ArrayList<Colonne> getTables(){
		return this.tables;
	}

	public Colonne getColonne(int i){
		return tables.get(i);
	}

	public TableAttr() {
		//initilaiser();
	}

	private void initilaiser() {
		Colonne names = new Colonne("ID", 0, "");
		Colonne colors = new Colonne("Colors", 0, "");
		names.retirerElem(0);
		colors.retirerElem(0);
		tables.add(names);
		tables.add(colors);
	}

	/*public TableAttr(Object... list) {
		taille = list.length;

		Colonne names = new Colonne("ID", taille, list);
		Colonne colors = new Colonne("Colors", taille, "Vide");
		tables.add(names);
		tables.add(colors);
	}*/

	public void addColonne(String name, Object... list) {
		int i=0;
		for (i=0;i<tables.size();i++){
			if (tables.get(i).getName().equals(name)) break;
		}
		if (i == tables.size())
			tables.add(new Colonne(name, taille, list));
	}

	public void removeAllFromCol(String name){
		int i=0;
		for (i=0;i<tables.size();i++){
			if (tables.get(i).getName().equals(name)) break;
		}
		tables.get(i).vidercolon();

	}

	public void removeColonne(String name) {
		if (name != "ID") {
			tables.remove(tables.get(getColonneName(name)));
		} else {
			tables.clear();
			initilaiser();
		}
	}

	public Colonne getcolnew(String name){
		int i=0;
		while (!tables.get(i).getName().equals(name)){
			i++;
		}
		return tables.get(i);
	}
	public void addtocol(String col,String o){
		int i=0;
		while (!tables.get(i).getName().equals(col)){
			i++;
		}
		tables.get(i).addstring(o);
	}

	public void addToColonne(String col, Object o) {
		for (Colonne c : tables) {
			if (c.getName().equals(col) ) {
				c.addElement(o);
				if (c.getTaille() > taille && c.getName().equals("ID") ) {
					for (Colonne e : tables) {
						if (!e.getName().equals("ID") ) {
							e.addElement("Vide");
						}
					}
					taille = c.getTaille();
					break;
				}
			}
		}
	}

	public void changeFromCol(int colnumber , String newvalue,int index){
		//int i=1;
		tables.get(colnumber).changeElem(newvalue,index);
        /*for (i=1;i<tables.size();i++){
            tables.get(i).changeElem(newvalue,index);
        }*/


	}

	public void addToColonne(Object... list) {
		for (int i = 0; i < list.length; i += 2) {
			try {
				for (Colonne e : tables) {
					if (e.getName() != list[i].toString()) {
						e.addElement(list[i + 1]);
					}
				}
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	public void removeFromColonne(String col, int index) {
		tables.get(getColonneName(col)).retirerElem(index);
	}

	public void removeFromColonne(String col, String e) {
		tables.get(getColonneName(col)).retirerElem(e);
	}

	public void removeFromColonne(Object... list) {
		for (int i = 0; i < list.length; i += 2) {
			try {
				tables.get(getColonneName(list[i].toString())).retirerElem(list[i + 1].toString());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Object... list) {
		this.addToColonne(list);
	}

	private int getColonneName(String name) {
		int i = -1;
		for (Colonne e : tables) {
			i++;
			if (e.getName() == name) {
				return i;
			}
		}
		return i;
	}



	private void Modifier() {
		String query = "UPDATE TableAttr SET Nom = ? WHERE ID = ?";
		ArrayList<String> args = new ArrayList<String>();
		args.add(this.nom);
		args.add(String.valueOf(this.id));
		BDD.execute(query, args);
	}

	private void dbAjouter(long idCouche) {
		String query = "INSERT INTO TableAttr VALUES(null, ?, ?)";
		List<String> args = new ArrayList<String>();
		args.add(this.nom);
		args.add(String.valueOf(idCouche));
		id = BDD.execute(query, args);
	}
	public void setTaille(int taille){
		this.taille=taille;
	}
    public void dbSave(long idCouche) {
        this.nom = "TableAttr_"+idCouche;
        this.dbDropTableAttr();
        this.dbCreateTableAttr();
        this.dbInsertTableAttrData();
    }


    private void dbDropTableAttr(){
        String query = "DROP TABLE IF EXISTS "+this.nom;
        BDD.execute(query, null);
    }

    private void dbCreateTableAttr() {
        String colonneNamesForCreateQuery = this.getColonneNamesForCreateQuery();
        String query = "Create TABLE "+this.nom+" ("+ colonneNamesForCreateQuery+");";
        BDD.execute(query, null);
    }

    private void dbInsertTableAttrData() {
        String query = "INSERT INTO "+ this.nom +" VALUES (?";
        int nombre_colonnes = tables.size();
        for (int i = 0; i < nombre_colonnes-1; i++) {
            query += ",?";
        }
        query += ")";
        Map<String, List<String>> table = this.getMapTableAttr();
        int nombre_lignes = this.taille;
        for (int i = 0; i < nombre_lignes; i++) {
            ArrayList<String> args = new ArrayList<String>();
            for (String nomColonne: table.keySet()) {
                String arg = table.get(nomColonne).get(i);
                args.add(arg);
            }
            BDD.execute(query, args);
        }
    }

    private String getColonneNamesForCreateQuery() {
        String colonneQuery = "ID VARCHAR(50) NOT NULL PRIMARY KEY ";
        Map<String, List<String>> table = this.getMapTableAttr();
        for (String nomColonne: table.keySet()) {
            if (!nomColonne.equals("ID")){
                colonneQuery+=", "+ nomColonne +" VARCHAR(50)";
            }

        }
        return  colonneQuery;
    }

    public Map<String, List<String>> getMapTableAttr(){
        Map<String, List<String>> table = new TreeMap<String, List<String>>();
        for (Colonne colonne: tables) {
            table.put(colonne.getName(), colonne.getCol());
            this.taille = colonne.getCol().size();
        }
        return table;
    }

    public static TableAttr dbFetchWithID(long idCouche){
        TableAttr tableAttr = null;
        String nomTableAttr = "TableAttr_"+idCouche;
        String query = "SELECT * FROM "+nomTableAttr;
        ResultSet rs = BDD.fetchAll(query, null);
        List<String> nomColonneList = BDD.getColumnNamesFromRSM(rs);
        try {
            Map<String, List<String>> tableAttrMap = new TreeMap<String, List<String>>();
            while (rs.next()) {
                for (String nomColonne: nomColonneList) {
                    String data = rs.getString(nomColonne);
                    addItemToTableAttrMap(tableAttrMap, nomColonne, data);
                }
            }
            tableAttr = createTableAttrFromMap(tableAttrMap, nomTableAttr);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return tableAttr;
    }

    private static synchronized void addItemToTableAttrMap(Map<String, List<String>> tableAttrMap, String key, String itemToAdd){
        List<String> tableAttrMapList = tableAttrMap.get(key);

        if(tableAttrMapList == null){
            tableAttrMapList = new ArrayList<String>();
            tableAttrMapList.add(itemToAdd);
            tableAttrMap.put(key, tableAttrMapList);
        }
        else {
            tableAttrMapList.add(itemToAdd);
        }
    }

    private static TableAttr createTableAttrFromMap(Map<String, List<String>> tableAttrMap, String nom){
        TableAttr tableAttr = new TableAttr();
        tableAttr.nom = nom;

        for (String colonneName: tableAttrMap.keySet()) {
            List<String> colonneList = tableAttrMap.get(colonneName);
            tableAttr.tables.add(new Colonne(colonneName, colonneList));
        }

        return tableAttr;
    }

}

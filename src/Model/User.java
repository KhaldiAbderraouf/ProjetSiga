package Model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String password;
    private long id;


    public User(String username){
        this.username=username;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }


    public long getId() {
        return this.id;
    }

    public void dbSave(){
//        if(id == 0)
//            this.ajouter();
//        else
//            this.modifier();

    }

    private void modifier() {
        String query = "UPDATE User SET Username = ?, Password=? WHERE ID = ?;";
        List<String> args = new ArrayList<String>();
        args.add(String.valueOf(this.username));
        args.add(String.valueOf(this.password));
        args.add(String.valueOf(this.id));
        BDD.execute(query, args);
    }

    public void ajouter(){
        String query = "INSERT INTO User VALUES (null, ?, ?, false);";
        List<String> args = new ArrayList<String>();
        args.add(String.valueOf(this.username));
        args.add(String.valueOf(this.password));
        id = BDD.execute(query, args);
    }
}

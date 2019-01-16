package Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACER E1 on 26/12/2018.
 */
public class PointNomer extends Shape implements Subject  {
    private long id;
    private Point point;
    private String name;

    public PointNomer(int x, int y, String name){
        this.name=name;
        this.point= new Point(x,y);
    }

    public PointNomer(int x, int y, String name,Observer o){
        this.name=name;
        this.point= new Point(x,y);
        add(o);
        execute();
    }

    public PointNomer(Point p, String name){
        this.name=name;
        this.point= p;
    }


    public void savePoint(){
        this.point.savePoint();
        //save name
    }
    public int getX(){
        return this.point.getX();
    }
    public int getY(){
        return this.point.getY();
    }
    public boolean equals(Object point){
        return ((this.getX()==((PointNomer)point).getX())&&(this.getY()==((PointNomer)point).getY())&&(this.name==((PointNomer)point).getName()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeXY(int x, int y){
        this.point.changeXY(x,y);
    }

    @Override
    public void add(Observer o) {
        observers.add(o);
    }

    @Override
    public void execute() {
        for (Observer observer : observers) {
            observer.update("ID",name);
        }
    }

    public void dbSave(long idCouche) {
        if (id == 0)
            this.dbAjouter(idCouche);
        else
            this.dbModifier();

        point.dbSave(id, "point");

    }

    private void dbModifier() {
        String query = "UPDATE PointNomer SET Nom = ?,WHERE ID = ?";
        List<String> args = new ArrayList<String>();
        args.add(this.name);
        args.add(String.valueOf(this.id));
        BDD.execute(query, args);
    }

    private void dbAjouter(long idCouche) {
        String query = "INSERT INTO PointNomer VALUES (null, ?, ?);";
        List<String> args = new ArrayList<String>();
        args.add(this.name);
        args.add(String.valueOf(idCouche));
        id = BDD.execute(query, args);
    }


    public long getID() {
        return id;
    }
}

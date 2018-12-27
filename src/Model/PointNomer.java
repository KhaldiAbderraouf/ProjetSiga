package Model;

/**
 * Created by ACER E1 on 26/12/2018.
 */
public class PointNomer implements Subject {
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
}

package Model;

import java.util.ArrayList;

/**
 * Created by ACER E1 on 26/12/2018.
 */
public interface Subject {

    ArrayList<Observer> observers = new ArrayList<>();
    void add(Observer o);
    void execute();

}

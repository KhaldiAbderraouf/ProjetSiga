package Model;

import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;

public class Colonne {
    private int taille;
    private String name;
    private ArrayList<String> col = new ArrayList<String>();

    public Colonne(String name,int taille, Object ... list){
        this.name=name;
        this.taille=taille;
        int cpt=0;

        for (Object l: list) {
            col.add(l.toString());
            cpt++;
        }
        for (int i=cpt;i<taille;i++){
            col.add("vide");
        }
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getCol(){
        return col;
    }

    public String getColIndex(int index){
        return col.get(index);
    }

    public int getTaille() {
        return taille;
    }

    public void addElement(Object e){
        col.add(e.toString());
        taille++;
    }

    public void modifierElem(int index,String s){
        col.get(index).replace(col.get(index),s);
    }

    public void modifierElem(String o,String n){
        for (String e:col){
            if(e==o){
                e.replace(o,e);
            }
        }
    }

    public void retirerElem(int index){
        col.remove(index);
        if(taille>0){taille--;}
    }

    public  void retirerElem(String e){
        if (col.remove(e)) if(taille>0){taille--;}
    }

    public void retirerElem(){
        col.remove(taille-1);
        if(taille>0){taille--;}
    }

    public boolean equals(Object col){
        if(col.getClass()!=this.getClass()){return false;}
        boolean res=true;
        if (((Colonne)col).getTaille()==this.taille){
            for (int i=0;i<taille;i++){
                if(((Colonne)col).getColIndex(i)!=this.getColIndex(i)) return false;
            }
        }
        return res;
    }

}

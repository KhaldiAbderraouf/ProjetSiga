package Model;

public abstract class Shape {
	protected String name;
	private CouleurInterv color;

    public String getName(){
        return name;
    }
    public void setColor(int r, int g, int b){
        color = new CouleurInterv("", r, g, b);
    }

    public void setColor(CouleurInterv c){
        color = c;
    }
    public CouleurInterv getColor(){
        return color;
    }
	public void setName(String name) {
		this.name = name;
	}

    public abstract int longeur();
    public abstract int surface();
}

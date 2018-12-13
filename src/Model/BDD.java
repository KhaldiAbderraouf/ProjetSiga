package Model;
import Controler.Couche;

import java.sql.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class BDD extends Loader{

    private static final String    DBName="SIGDB";
    private static final String    DBUrl="jdbc:mariadb://localhost:3306/"+DBName;
	private static final String    JDBC_DRIVER = "com.mariadb.jdbc.Driver";
    private static final String    DBUserName="root";
    private static final String    DBPasswd="root";
    private static final int       DBPort=3096;
    private static final String    DBServerName="root";

	public void setUser(User user)
	{
		
	}
	public User getUser(String user)
	{
		return null;
		
	}
	public void setCouche(Couche couche)
	{
		
	}
	public Couche getCouche(String user)
	{
		return null;
		
	}
	public void setPoint(Point point)
	{
		
	}
	public Point getPoint(String point)
	{
		return null;
		
	}
	public void setLigne(Ligne ligne)
	{
		
	}
	public Ligne getLigne(String ligne)
	{
		return null;
		
	}
	public void setPoly(Polygone Poly)
	{
		
	}
	public Polygone getPoly(String Poly)
	{
		return null;
		
	}

	private static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DBUrl, DBUserName, DBPasswd);
		return conn;
	}

    public static ResultSet execute(String query, List<String> args){

        PreparedStatement preparedStatement = null;
        ResultSet querySet = null;
        try(Connection connection= BDD.getConnection()) {
            connection.setAutoCommit(false);
            int cpt=1;
            preparedStatement = connection.prepareStatement(query);
            if(args != null)
                for (Iterator<String> i = args.iterator(); i.hasNext();) {
                    String arg = i.next();
                    preparedStatement.setString(cpt, arg);
                    cpt++;
                }
            querySet = preparedStatement.executeQuery();
            connection.commit();
            preparedStatement.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        } finally {
            return querySet;
        }
    }

    public static ResultSet fetchAll(String query, List<String> args){

        return execute(query, args);
    }

    public static ResultSet fetch(String query, List<String> args){

        return fetchAll(query, args);

    }

}

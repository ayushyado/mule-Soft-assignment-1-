package net.movies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLiteConnection {
	static final String jdbcURL = "jdbc:sqlite:movies.db";
	
	public static Connection returnConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcURL);
			String sql = "create table if not exists movies (name varchar(20),actress varchar(20),director varchar(20),year_of_release int)";
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			System.out.println("Table movies created");
						
		} catch (SQLException e) {
			System.out.println("Error connecting to SQLite Database\n");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static void insert(String name,String actress,String director,Integer year_of_release)
	{
		System.out.println("Insert");
		try {
			Connection connection = returnConnection();
			String sql = "insert into movies (name,actress,director,year_of_release) values ('"+name+"','"+actress+"','"+director+"','"+year_of_release+"');";
			
			Statement statement = connection.createStatement();
			int rows = statement.executeUpdate(sql);
			System.out.println("Created row: "+ rows);
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void getAllData() {
		try {
			
			Connection connection = returnConnection();
			String sql = "select rowid,* from movies";
			
			Statement statement = connection.createStatement();
		
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				String name = result.getString("name");
				String actress = result.getString("actress");
				String director = result.getString("director");
				Integer year_of_release = result.getInt("year_of_release");
				
				System.out.println("Name: "+name+"| Actress: "+actress+"| Director: "+director+"| Year:"+year_of_release);
				
			}
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static void getDataByName(String queryName) {
		try {
			
			Connection connection = returnConnection();
			String sql = "select rowid,* from movies where name='"+queryName+"'";
			
			Statement statement = connection.createStatement();
		
			ResultSet result = statement.executeQuery(sql);
			
			while(result.next()) {
				String name = result.getString("name");
				String actress = result.getString("actress");
				String director = result.getString("director");
				Integer year_of_release = result.getInt("year_of_release");
				
				System.out.println("Name: "+name+"| Actress: "+actress+"| Director: "+director+"| Year:"+year_of_release);
				
			}
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Welcome to Movies database");
		
		Scanner input = new Scanner(System.in);
		int choice,year_of_release;
		String name,actress,director;
		
		while(true)
		{
			System.out.println("What would you like to do?");
			System.out.println("1. Insert new data");
			System.out.println("2. View all data");
			System.out.println("3. Query movie by name.");
			System.out.println("4. Exit.");	
			
			choice = input.nextInt();
			
			switch(choice){
			case 1:
				System.out.println("Enter movie name");
				name = input.next();
				System.out.println("Enter movie actress");
				actress = input.next();
				System.out.println("Enter movie director");
				director = input.next();
				System.out.println("Enter movie year of release");
				year_of_release = input.nextInt();
				
				insert(name,actress,director,year_of_release);
				break;
			case 2:
				getAllData();
				break;
			case 3:
				System.out.println("Enter Movie name");
				name = input.next();
				
				getDataByName(name);
				break;
			case 4:
				input.close();
				System.out.println("Closing Application. Bye bye ....");
				return;
			}
			
		}
	}

}

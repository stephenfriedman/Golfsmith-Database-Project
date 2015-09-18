package createProjectData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InventoryFill {
	static Connection con = null;

	public static void main(String[]args)
	{
		String[][] inventory=new String[4][1537];
		inventory[1][0]="10000000000";
		inventory[1][1]="10000000001";
		inventory[1][2]="10000000002";
		inventory[1][3]="10000000003";
		inventory[1][4]="10000000004";
		inventory[1][5]="10000000005";
		inventory[1][6]="10000000006";
		inventory[1][7]="10000000007";
		inventory[1][8]="10000000008";
		inventory[1][9]="10000000009";
		inventory[1][10]="10000000010";
		inventory[1][11]="10000000011";
		inventory[1][12]="10000000012";
		inventory[1][13]="10000000013";
		inventory[1][14]="10000000014";
		inventory[1][15]="10000000015";
		inventory[1][16]="10000000016";
		inventory[1][17]="10000000017";
		inventory[1][18]="10000000018";
		inventory[1][19]="10000000019";
		inventory[1][20]="10000000020";
		inventory[1][21]="10000000021";
		inventory[1][22]="10000000022";
		inventory[1][23]="10000000023";
		inventory[1][24]="10000000024";
		inventory[1][25]="10000000025";
		inventory[1][26]="10000000026";
		inventory[1][27]="10000000027";
		inventory[1][28]="10000000028";
		for(int i=29;i<1537;i++)
		{
			inventory[1][i]=inventory[1][i-29];
		}
		int counter=0;
		for(int i=1;i<54;i++)
		{
			for(int j=0;j<29;j++)
			{
				inventory[0][counter]=Integer.toString(i);
				counter++;
			}
		}
		for(int i=0;i<1537;i++)
		{
			int qoh=(int)(Math.random()*500);
			inventory[2][i]=Integer.toString(qoh);
		}
		for(int i=0;i<1537;i++)
		{
			double price=(Math.random()*500);
			inventory[3][i]=Double.toString(price);
		}
		
		
		
		
		try {
			Class.forName ("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean loggedIn=false;
		while(!loggedIn)
		{
			String password=null;
			@SuppressWarnings("resource")
			Scanner getPassword=new Scanner(System.in);
			getPassword.reset();
			System.out.println("Input STEPHEN FRIEDMAN password from coursesite to login in to his Oracle databases.");
			password=getPassword.next();
			try {
				loggedIn=true;
				 con =DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241","saf217",
					       password);
			} catch (SQLException e) {
				System.out.println("Invalid login info. Try again.");
				loggedIn=false;
			}
		}
		ResultSet r=null;
		Statement s=null;
		try {
			s=con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0;i<1537;i++)
		{
			try {
				r=s.executeQuery("insert into inventory (store_num,upc,qoh,selling_price) values('"+inventory[0][i]+"','"+inventory[1][i]+"',"+inventory[2][i]+","+inventory[3][i]+")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

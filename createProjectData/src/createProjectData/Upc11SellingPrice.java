package createProjectData;

import java.util.Random;

public class Upc11SellingPrice {
	
	public static void main (String[]args)
	{
		Random r = new Random();
		String[][] uq = new String[2][500];
		
			for(int j=0;j<500;j++)
			{
				r =new Random();
				int upcraw= (int)(r.nextDouble()*29);
				String upc ="100000000";
				if(upcraw<10)
				{
					upc=upc.concat("0" + Double.toString(upcraw));
				}
				else
				{
					upc=upc.concat(Double.toString(upcraw));
				}
				uq[0][j]=upc.trim();
				r=new Random();
				double price =(r.nextDouble()*401);
				String duplicate=Double.toString(price);
				int period = duplicate.indexOf(".");
				String useThis=Double.toString(price).substring(0,period+3);
				
				uq[1][j]=useThis;
		}
			
			
			for(int i=0;i<500;i++)
			{
				//System.out.println(uq[1][i]);
				r =new Random();
				int upcraw= (int)(r.nextDouble()*1001);
				System.out.println(upcraw);
			}
	}

}

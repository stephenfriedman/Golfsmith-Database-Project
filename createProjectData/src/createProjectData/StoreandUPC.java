package createProjectData;

import java.util.ArrayList;
import java.util.Random;

public class StoreandUPC {
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
				upc=upc.substring(0, 11);
				r=new Random();
				int rawstore =(int)(r.nextDouble()*52+1);
				String store=Double.toString(rawstore);
				store=store.substring(0, store.length()-2);
				uq[0][j]=store;
				ArrayList<String> usedUPCs=new ArrayList<String>();
				for(int k=0;k<j;k++)
				{
					if(uq[0][k].equals(store) && uq[1][k].equals(upc))
					{
						usedUPCs.add(upc);
						while(usedUPCs.contains(upc))
						{
							 upcraw= (int)(r.nextDouble()*23+1);
							 upc ="100000000";
							if(upcraw<10)
							{
								upc=upc.concat("0" + Double.toString(upcraw));
							}
							else
							{
								upc=upc.concat(Double.toString(upcraw));
							}
							upc=upc.substring(0, 11);
						}
						
					}
					
				}
				uq[1][j]=upc;
		}
			
			
			for(int i=0;i<500;i++)
			{
				System.out.println(uq[0][i]);
			}
			System.out.println("-------------------------------------");
			for(int p=0;p<500;p++)
			{
				System.out.println(uq[1][p]);
			}
	}
}

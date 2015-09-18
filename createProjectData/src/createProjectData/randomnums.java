package createProjectData;

import java.util.Random;

public class randomnums {
	public static void main (String []args)
	{
		for(int i =0;i<500;i++)
		{
		Random r=new Random();
		int price=(int)(r.nextDouble()*600);
		System.out.println(price);
		}
	}

}

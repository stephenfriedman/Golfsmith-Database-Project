package createProjectData;

import java.util.Random;

public class states {
	public static void main(String args[]) {
		String[] states = new String[50];
		states[0] = "Alabama";
		states[1] = "Alaska";
		states[2] = "Arizona";
		states[3] = "Arkansas ";
		states[4] = "California ";
		states[5] = "Colorado ";
		states[6] = "Connecticut ";
		states[7] = "Delaware ";
		states[8] = "Florida ";
		states[9] = "Georgia ";
		states[10] = "Hawaii ";
		states[11] = "Idaho ";
		states[12] = "Illinois";
		states[13] = "Indiana ";
		states[14] = "Iowa ";
		states[15] = "Kansas ";
		states[16] = "Kentucky ";
		states[17] = "Louisiana ";
		states[18] = "Maine ";
		states[19] = "Maryland ";
		states[20] = "Massachusetts";
		states[21] = "Michigan";
		states[22] = "Minnesota";
		states[23] = "Mississippi";
		states[24] = "Missouri";
		states[25] =  "Montana";
		states[26] = "Nebraska";
		states[27] = "Nevada";
		states[28] = "New Hampshire";
		states[29] = "New Jersey";
		states[30] = "New Mexico";
		states[31] = "New York";
		states[32] = "North Carolina";
		states[33] = "North Dakota";
		states[34] = "Ohio";
		states[35] = "Oklahoma";
		states[36] = "Oregon";
		states[37] = "Pennsylvania";
		states[38] = "Rhode Island";
		states[39] = "South Carolina";
		states[40] = "South Dakota";
		states[41] = "Tennessee";
		states[42] = "Texas";
		states[43] = "Utah";
		states[44] = "Vermont";
		states[45] = "Virginia";
		states[46] = "Washington";
		states[47] = "West Virginia";
		states[48] = "Wisconsin";
		states[49] = "Wyoming";
		Random r= new Random();
		double spot=-1;
		for(int i=0;i<52;i++)
		{
		     spot = r.nextDouble()*50;
			System.out.println(states[(int)spot]);
			r=new Random();
		}
	}

}

import java.util.ArrayList;
import java.util.Scanner;

//Slow version

/*
 * We will follow the same steps than in the fast version. There are coments in this version 
 * with the things that make this version slower than the other.
 */

public class Main {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		int numberRowsRoutingTable = scan.nextInt();  //Number of rows of the Routing table
		int numberIPs = scan.nextInt(); //Number of IPs that we will want to compare
		
		String[] ipsTable= new String[numberRowsRoutingTable]; //IPs of the table
		String[] masks = new String[numberRowsRoutingTable]; //Masks of the table
		String[] nextHops = new String[numberRowsRoutingTable]; //Next hops of the table
		String[] ips = new String[numberIPs]; //IPs that we want to compare
		
		//Reading
		for(int i = 0; i < numberRowsRoutingTable; i++){
			ipsTable[i] = scan.nextLine();
			masks[i] = scan.nextLine();
			nextHops[i] = scan.nextLine();
		}
		
		for(int j = 0; j < numberIPs; j++){
			ips[j] = scan.nextLine();
		}
		
		scan.close();
		//End reading
		
		for(int x = 0; x < numberIPs; x++){
			String ip = ips[x]; //We don't need it
			ArrayList<String> matchedIPs = new ArrayList<String>();
			ArrayList<String> matchedNextHops = new ArrayList<String>();
			ArrayList<String> matchedMasks = new ArrayList<String>();
			
			for(int y = 0; y < numberRowsRoutingTable; y++){
				boolean match = true;
				String ipTable= ipsTable[y]; //We don't need it
				String mask = masks[y]; //We don't need it
				String nextHop = nextHops[y]; //We don't need it
				
				//Split each direction in parts
				String[] ipParts = ip.split(".");
				String[] ipTableParts= ipTable.split(".");
				String[] maskParts = mask.split(".");
				
				int[] ipTableDestParts = new int[ipParts.length];
				int[] ipDestParts = new int[ipParts.length];
				
				for(int z = 0; z < ipParts.length; z++){ //Apply the mask
					ipTableDestParts[z] = Integer.parseInt(ipTableParts[z]) & Integer.parseInt(maskParts[z]);
				}
				//We can do this step in one loop with the previous loop
				for(int a = 0; a < ipParts.length; a++){ //Apply the mask
					ipDestParts[a] = Integer.parseInt(ipParts[a]) & Integer.parseInt(maskParts[a]);
				}
				//We can do this step in one loop with the previous loop too
				for(int b = 0; b < ipParts.length; b++){ //Compare
					if(ipTableDestParts[b] != ipDestParts[b]){
						match = false;
					}
				}
				
				if(match){//If a match has been produced, I store it.
					matchedIPs.add(ipTable);
					matchedMasks.add(mask);
					matchedNextHops.add(nextHop);
				}
			}
			
			if(matchedIPs.size() == 0){ //We don't have any match
				System.out.println("We don't have any match, next hop is the default router next hop");
			}else if(matchedIPs.size() == 1){//We have a match. We don't need this particular case
				System.out.println("We have a match with: " + matchedIPs.get(0) + "\nNext hop is : " + matchedNextHops.get(0));
			}else if(matchedIPs.size() < 1){//We have more than one match and we select the longest prefix
				int indexLongestPrefix = 0;
				
				//search the index of the longest prefix
				for(int k = 0; k < matchedIPs.size(); k++){ //One more iteration than the other version
					if(matchedMasks.get(indexLongestPrefix).compareTo(matchedMasks.get(k)) < 0){
						indexLongestPrefix = k;
					}
				}
				System.out.println("We have a match with: " + matchedIPs.get(indexLongestPrefix) + "\nNext hop is : " + matchedNextHops.get(indexLongestPrefix));
			}
		}
	}
}

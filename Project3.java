import java.util.*;

public class Project3 {		
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		HashMap<String, String> files = new HashMap<String, String>();		
		HashMap<Integer, String> numFile = new HashMap<Integer, String>();		
		HashMap<Integer, String> opened = new HashMap<Integer, String>();				
		HashMap<Integer, Integer> seeker = new HashMap<Integer, Integer>();											
		while (sc.hasNextLine()){
			String input = sc.nextLine();
	      	String[] part = input.split(" ");
			String command = part[0];						
			if (command.equals("in")){				
				System.out.println("");											
				if (part.length == 1){				
					files = new HashMap<String, String>();					
					System.out.println("disk initialized");									
					numFile = new HashMap<Integer, String>();					
				} else {
					System.out.println("disk restored");								
				}											
				opened = new HashMap<Integer, String>();				
				seeker = new HashMap<Integer, Integer>(); 												
			}			
			else if (command.equals("cr")){				
				if (files.containsKey(part[1])){					
					System.out.println("error");					
				} else {					
					System.out.println(part[1] + " created");				
					int i = 0;					
					while (true){
						if (numFile.containsKey(i) != true){
							numFile.put(i, part[1]);
							break;
						}						
						i++;
					}					
					files.put(part[1], "");
				}
			}									
			else if (command.equals("op")){																								
				int free = 1;				
				int exists = 0;				
				while (opened.containsKey(free)){					
					if (opened.get(free).equals(part[1])){
						exists = 1;
					}					
					free++;										
				}				
				opened.put(free, part[1]);				
				if (exists == 1){
					System.out.println("error");
				} else {
					System.out.println(part[1] + " opened " + free);					
				}												
				int i = 1;				
				while (opened.containsKey(i)){
					i++;
				}				
			}	
			else if (command.equals("cl")){
				System.out.println(part[1] + " closed");				
				opened.remove(Integer.parseInt(part[1]));	
			}
			else if (command.equals("wr")){
				try{
					String write = opened.get(Integer.parseInt(part[1]));				
					if (files.containsKey(write) != true){
						files.put(write, "");
					}				
					int position = 0; 				
					if (seeker.containsKey(Integer.parseInt(part[1]))){
						position = seeker.get(Integer.parseInt(part[1]));
					}				
					String join = "";				
					for (int i = 0; i < Integer.parseInt(part[3]); i++){
						join = join + part[2];
					}
					seeker.put(Integer.parseInt(part[1]), position + Integer.parseInt(part[3]));
					files.put( write, saving(files.get(write), join, position) );
					System.out.println(part[3] + " bytes written");
					System.out.println("");
				} catch(Exception e){
					System.out.println("error");
				}
			}
			else if (command.equals("sk")){
				String text = files.get(opened.get(Integer.parseInt(part[1])));				
				if (text.length() <= Integer.parseInt(part[2]) ){
						System.out.println("error");
				} else {
					System.out.println("position is " + part[2]);					
				}
				seeker.put( Integer.parseInt(part[1]), Integer.parseInt((part[2])) );
			}						
			else if (command.equals("rd")){	
				try{
				String text = files.get(opened.get(Integer.parseInt(part[1])));				
				int position = 0;			
				if (seeker.containsKey(Integer.parseInt(part[1]))){
					position = seeker.get(Integer.parseInt(part[1]));
				}				
				int track = 0;				
				while (track != Integer.parseInt(part[2])){					
					if (position + track < text.length()){
						System.out.print(text.charAt(position + track));
					}					
					track++;
				}				
				seeker.put(Integer.parseInt(part[1]), position + Integer.parseInt(part[2]));
				} catch(Exception e){
					System.out.println("error");
				}								
			}			
			else if (command.equals("dr")){				
				ArrayList<Integer> drive = new ArrayList<Integer>(numFile.keySet());
				Collections.sort(drive);
				for (int m = 0; m < drive.size(); m++){				
					int i = drive.get(m);					
					System.out.print(numFile.get(i) + " ");					
				}				
				System.out.println("");				
			}								
			else if (command.equals("sv")){
				System.out.println("disk saved");			
			}						
			else if (command.equals("de")){				
				if (files.containsKey(part[1])){
					System.out.println(part[1] + " destroyed");
					files.remove(part[1]);
				} else {
					System.out.println("error");					
				}											
				ArrayList<Integer> listing = new ArrayList<Integer>(opened.keySet());
				Collections.sort(listing);				
				for (int m = 0; m < listing.size(); m++){					
					int i = listing.get(m);					
					if (opened.get(i).equals(part[1])){					
						opened.remove(i);						
						seeker.remove(i);
					}					
				}				
				ArrayList<Integer> indexing = new ArrayList<Integer>(numFile.keySet());
				Collections.sort(indexing);
				for (int m = 0; m < indexing.size(); m++){					
					int i = indexing.get(m);					
					if (numFile.get(i).equals(part[1])){						
						numFile.remove(i);
					}											
				}								
			}						
		}
		sc.close();
	}
	public static String saving(String save, String data, int index) {
	    String begin = save.substring(0,index);
	    String end = save.substring(index);
	    return begin + data + end;
	}	
}
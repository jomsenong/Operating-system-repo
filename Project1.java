// Ong Mu Sen Jeremy
// A0108310Y

import java.util.*;

public class Project1{
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Integer> FIFO = new ArrayList<Integer>();
		ArrayList<Integer> FIFO_ans = new ArrayList<Integer>();
		ArrayList<Integer> LRU = new ArrayList<Integer>();
		ArrayList<Integer> LRU_ans = new ArrayList<Integer>();
		ArrayList<Integer> SECONDCHANCE = new ArrayList<Integer>();
		HashMap<Integer, Integer> SECONDCHANCE_track = new HashMap<Integer, Integer>();
		ArrayList<Integer> SECONDCHANCE_ans = new ArrayList<Integer>();
		
		int counter = 1;
		int pointer = 0;
		int pageIndex = 0;
		
		for(int i = 0; i < 16; i++){
			FIFO.add(i);
			LRU.add(i);
			SECONDCHANCE.add(i);
			SECONDCHANCE_track.put(i, 1);
		}
		while(sc.hasNextInt()){
			int input = sc.nextInt();
			
			if(!(FIFO.contains(input))){
				FIFO.remove(0);
				FIFO.add(input);
				FIFO_ans.add(counter);
			}
			
			if(!(LRU.contains(input))){
				LRU.remove(0);
				LRU.add(input);
				LRU_ans.add(counter);
			} else{
				LRU.remove(LRU.indexOf(input));
				LRU.add(input);
			}
			
			if(!(SECONDCHANCE.contains(input))){
				while(true){
					if(SECONDCHANCE_track.get(SECONDCHANCE.get(pointer)) == 0){
						pageIndex = pointer;
						pointer++;
						break;
					} else{
						SECONDCHANCE_track.put(SECONDCHANCE.get(pointer), 0);
					}
					pointer = (pointer + 1) % 16;
				}
				SECONDCHANCE.set(pageIndex, input);
				SECONDCHANCE_ans.add(counter);
				SECONDCHANCE_track.put(input, 1);
			} else{
				SECONDCHANCE_track.put(input, 1);
			}
			counter++;
		}
		System.out.print(FIFO_ans.size());	
		for (int aa = 0; aa < FIFO_ans.size(); aa++){
			System.out.print(" " + FIFO_ans.get(aa));
		}
		System.out.println();	
		System.out.print(LRU_ans.size());
		for (int ab = 0; ab < LRU_ans.size(); ab++){
			System.out.print(" " + LRU_ans.get(ab));
		}
		System.out.println();
		System.out.print(SECONDCHANCE_ans.size());
		for (int ac = 0; ac < SECONDCHANCE_ans.size(); ac++){
			System.out.print(" " + SECONDCHANCE_ans.get(ac));
		}
		System.out.println();
	}
}
// Ong Mu Sen Jeremy
// A0108310Y

import java.util.*;
import java.math.*;

public class Project2{

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		ArrayList<Integer> arrivalTime = new ArrayList<Integer>();
		ArrayList<Integer> serviceTime = new ArrayList<Integer>();
		while(sc.hasNextInt()){
			arrivalTime.add(sc.nextInt());
			serviceTime.add(sc.nextInt());
		}
		FIFO(arrivalTime, serviceTime);
		SJF(arrivalTime, serviceTime);
		SRT(arrivalTime, serviceTime);
		MLF(arrivalTime, serviceTime);
	}
	
	public static void FIFO(ArrayList<Integer> arrivalTime, ArrayList<Integer> serviceTime){
		HashMap<Integer, Integer> fifo = new HashMap<Integer, Integer>();
		int fifoTime = 0;
		int process = arrivalTime.size();
		while(fifo.size() != process){
			int lowest = -1;
			int id = 0;
			for(int i = 0; i < arrivalTime.size(); i++){
				if(fifo.containsKey(i) == false){
					if(lowest == -1 || arrivalTime.get(i) < lowest){
						lowest = arrivalTime.get(i);
						id = i;
					}
				}
			}
			if(fifoTime < arrivalTime.get(id)){
				fifoTime = arrivalTime.get(id);
			}
			fifoTime = fifoTime + serviceTime.get(id);
			fifo.put(id, fifoTime);
		}
		double totalFIFOTime = 0.0;
		for(int i = 0; i < process; i++){
			totalFIFOTime += fifo.get(i) - arrivalTime.get(i);
		}
		System.out.print(new BigDecimal(totalFIFOTime/ process).setScale(2, BigDecimal.ROUND_DOWN));
		for(int i = 0; i < process; i++){
			System.out.print(" " + (fifo.get(i) - arrivalTime.get(i)));
		}
		System.out.println();
	}
	
	public static void SJF(ArrayList<Integer> arrivalTime, ArrayList<Integer> serviceTime){
		HashMap<Integer, Integer> sjf = new HashMap<Integer, Integer>();
		int sjfTime = 0;
		int process = arrivalTime.size();
		while(sjf.size() != process){
			int lowest = -1;
			int id = 0;
			int setProcess = 0;
			for(int i = 0; i < arrivalTime.size(); i++){
				if(sjf.containsKey(i) == false){
					if(lowest == -1 || serviceTime.get(i) < lowest){
						if(arrivalTime.get(i) > sjfTime){
							continue;
						}
						lowest = serviceTime.get(i);
						id = i;
						setProcess = 1;
					}
				}
			}
			if(setProcess == 0){
				for(int i = 0; i < serviceTime.size(); i++){
					if(sjf.containsKey(i) == false){
						id = i;
					}
				}
			}
			if(sjfTime < arrivalTime.get(id)){
				sjfTime = arrivalTime.get(id);
			}
			sjfTime += serviceTime.get(id);
			sjf.put(id, sjfTime);
		}
		double totalSJFTime = 0.0;
		for(int i = 0; i < process; i++){
			totalSJFTime += sjf.get(i) - arrivalTime.get(i);
		}
		System.out.print(new BigDecimal(totalSJFTime/ process).setScale(2, BigDecimal.ROUND_DOWN));
		for(int i = 0; i < process; i++){
			System.out.print(" " + (sjf.get(i) - arrivalTime.get(i)));
		}
		System.out.println();
	}
	
	public static void SRT(ArrayList<Integer> arrivalTime, ArrayList<Integer> serviceTime){
		HashMap<Integer, Integer> srt = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> srtRem = new HashMap<Integer, Integer>();
		int srtTime = 0;
		int process = arrivalTime.size();
		for(int i = 0; i < serviceTime.size(); i++){
			srtRem.put(i, serviceTime.get(i));
		}
		while(srt.size() != process){
			int lowest = -1;
			int id = 0;
			int setProcess = 0;
			
			for(int i = 0; i < serviceTime.size(); i++){
				if(srtRem.get(i) != 0){
					if(arrivalTime.get(i) > srtTime){
						continue;
					}
					if(lowest == -1 || srtRem.get(i) < lowest){
						lowest = srtRem.get(i);
						id = i;
						setProcess = 1;
					}
				}
			}
			if(setProcess == 0){
				srtTime++;
			} else{
				srtRem.put(id, srtRem.get(id) - 1);
				srtTime++;
				if(srtRem.get(id) == 0){
					srt.put(id, srtTime);
				}
			}
		}
		double totalSRTTime = 0.0;
		for(int i = 0; i < process; i++){
			totalSRTTime += srt.get(i) - arrivalTime.get(i);
		}
		System.out.print(new BigDecimal(totalSRTTime/ process).setScale(2, BigDecimal.ROUND_DOWN));
		for(int i = 0; i < process; i++){
			System.out.print(" " + (srt.get(i) - arrivalTime.get(i)));
		}
		System.out.println();
	}
	
	public static void MLF(ArrayList<Integer> arrivalTime, ArrayList<Integer> serviceTime){
		ArrayList<ArrayList<Integer>> mlfPriority = new ArrayList<ArrayList<Integer>>();
		HashMap<Integer, Integer> mlf = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> mlfRem = new HashMap<Integer, Integer>();
		int mlfTime = 0;
		int current = 0;
		int layer = 0;
		int process = arrivalTime.size();
		
		for(int i = 0; i < 5; i++){
			mlfPriority.add(new ArrayList<Integer>());
		}
		for(int i = 0; i < serviceTime.size(); i++){
			mlfPriority.get(0).add(i);
			mlfRem.put(i, serviceTime.get(i));
		}
		while(mlf.size() != process){
			int id = 0;
			int setProcess = 0;
			
			find_valid: {
				for(int j = 0; j < 5; j++){
					current = j;
					for(int index = 0; index < mlfPriority.get(j).size(); index++){
						layer = index;
						int k = mlfPriority.get(j).get(index);
						if(mlf.containsKey(k) == false){
							if(arrivalTime.get(k) > mlfTime){
								continue;
							}
							if(mlfRem.get(k) != 0){
								id = k;
								setProcess = 1;
								break find_valid;
							}
						}
					}
				}
			}
			if(setProcess == 0){
				mlfTime++;
			} else{
				mlfRem.put(id, mlfRem.get(id) - 1);
				mlfTime++;
				int totalTime = serviceTime.get(id) - mlfRem.get(id);
				if(mlfRem.get(id) == 0){
					mlf.put(id, mlfTime);
					mlfPriority.get(current).remove(layer);
				} else if(totalTime == Math.pow(2, current + 1) - 1){
					mlfPriority.get(current).remove(layer);
					mlfPriority.get(current + 1).add(id);
				}
			}
		}
		double totalMLFTime = 0.0;
		for(int i = 0; i < process; i++){
			totalMLFTime += mlf.get(i) - arrivalTime.get(i);
		}
		System.out.print(new BigDecimal(totalMLFTime/ process).setScale(2, BigDecimal.ROUND_DOWN));
		for(int i = 0; i < process; i++){
			System.out.print(" " + (mlf.get(i) - arrivalTime.get(i)));
		}
	}
}
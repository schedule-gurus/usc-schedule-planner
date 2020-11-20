//Made by Kevin Lee
package models;

import java.util.BitSet;

/**
 *  0700->2200, M-F, 30 minute bins = 150 bins to represent the whole week
 */
public class Binaryizer {
	public BitSet output;
	
    public Binaryizer(){
        output = new BitSet(450);
    }
    
    public Binaryizer(String days, int startTime, int endTime){
        // Input: Starttime, Endtime
        //      format: military time eg. 1300, 1500
        // Output: length 450 binary set of when schedules take the time
    	output = new BitSet(450);
    	
    	//Only add times if the inputted time is valid
    	if((startTime != -1 || endTime != -1) && startTime >= 700 && endTime <= 2200) {
    		
	        //Each day is divided into 90 bits each 1 bit = 10 minutes
	        // Start of day 0700 & End of day 2200
	        // 15 hour days * 60 minutes = 900 minutes --> 900 minutes / 10 minute bins = 90 bins * 5 days = 450 bins/bits
	
	
	        // The course only occurs once a week
	        //for each day to friday, I add 30 bins which equates to one full day
	        if (days.toLowerCase().contains("m")){
	            output.set(binFinder(startTime), binFinder(endTime));
	        }
	        if (days.toLowerCase().contains("t")){
	            output.set(binFinder(startTime) + 90, binFinder(endTime) + 90);
	        }
	        if (days.toLowerCase().contains("w")){
	            output.set(binFinder(startTime) + 180, binFinder(endTime) + 180);
	        }
	        if (days.toLowerCase().contains("h")){
	            output.set(binFinder(startTime) + 270, binFinder(endTime) + 270);
	        }
	        if (days.toLowerCase().contains("f")){
	            output.set(binFinder(startTime) + 360, binFinder(endTime)+ 360);
	        }
    	}

    }

    public BitSet dayBinaryizer(int startTime, int endTime){
        BitSet temp = new BitSet(30);

        //Step 1: Get the Start and End positions of in BitSet form
        //subtract 700 (start time) and divide 30 to get starting position of bin
        int binStart = binFinder(startTime);
        //Bitset position end
        int binEnd = binFinder(endTime);

        //Step 2: Turn everything between binEnd and binStart to positive bits
        temp.set(binStart, binEnd);

        return temp;
    }
    
    public String printBitSet(){
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0 ; b.length > i; i++){
//            String s1 = String.format("%8s", Integer.toBinaryString(b[i] & 0xFF)).replace(' ', '0');
//            sb.append(s1);
//        }
//        String s1 = String.format("%8s", Integer.toBinaryString(b[1] & 0xFF)).replace(' ', '0');

//        byte bb = (byte) 15;
//        temp.set(31);
    	return output.toString();
    }
    
    public int binFinder(int inputTime){
    	
        int startTime = 700;
        //modulo %60
        int binPos = 0;
        int tracker = 0;
        while (startTime != inputTime) {
            tracker += 10;
            binPos++;
            startTime += 10;
            tracker = tracker % 60;
            if (tracker == 0) {
                startTime += 40;
            }
        }
        return binPos;

    }
}


package org.compression.arrayprofilers;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Intarrayprofilers {

	
	public int profileIntArray(List<Integer> inArray){
		int shortSize = 32767; 
		int superShortSize = 128;
		int [] intArr = new int [inArray.size()];
		int numShorts=0;
		int numSuperShorts=0;
		for(int i=0; i<inArray.size();i++){
			int num = inArray.get(i);
			intArr[i] = num;
			// 3) The number that aren't shorts
			if(num<shortSize && num>-shortSize){
				numShorts+=1;
			}
			if(num<superShortSize && num>-superShortSize){
				numSuperShorts+=1;
			}
		}
		// 1) The mean 
		OptionalDouble mean = IntStream.of(intArr).parallel().average();
		System.out.println("MEAN: "+mean.getAsDouble());
		// 2) The largest
		OptionalInt max = IntStream.of(intArr).parallel().max();
		System.out.println("MAX: "+max.getAsInt());
		// 3) The number that aren't shorts
		float numShortFloat = numShorts;
		float arrSizeFloat = inArray.size();
		System.out.println("% SHORTS: "+numShortFloat/arrSizeFloat*100.0);
		System.out.println("% SUPER SHORTS: "+numSuperShorts/arrSizeFloat*100.0);
		
		// 4) The number of bytes (if stored as shorts and longs
		int numBytes = numShorts*2+(inArray.size()-numShorts)*4;
		System.out.println("NUM BYTES: "+numBytes);
		return numBytes;
	}
}

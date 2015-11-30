package org.compression.stringarraycodec;

import java.util.ArrayList;
import java.util.Random;

import org.compression.stringarraycompressors.StringArrayCompressor;
import org.compression.stringarraydecompressors.StringArrayDeCompressor;
import org.apache.commons.lang.RandomStringUtils;

public class AbstractStringArrayCodec {
	
	
		public boolean runTest(StringArrayCompressor stringArrComp, StringArrayDeCompressor stringArrDeComp){
			ArrayList<String> inArray = getStringArray(100,100);
			ArrayList<String> compressedArray = stringArrComp.compressStringArray(inArray);
			ArrayList<String> outArray = stringArrDeComp.deCompressStringArray(compressedArray);
			// Now do the test
			if(inArray.size()!=outArray.size()){			
				return false;
			}
			for(int i=0; i<inArray.size(); i++){
				if(inArray.get(i).equals(outArray.get(i))==false){
					return false;
				}
			}
			return true;
		}
		
		private ArrayList<String> getStringArray(int numStrings, int stringLen){
			RandomStringUtils rsu = new RandomStringUtils();
			Random rg = new Random();
			ArrayList<String> newArray = new ArrayList<String>();
			for (int i=0;i<numStrings;i++){
				String newString = rsu.random(stringLen);
				int numThisString = rg.nextInt(100);
				for(int j=0; j<numThisString;j++){
					newArray.add(newString);
				}
				
			}
			return newArray;
			
		}
		
	}

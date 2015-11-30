package org.compression.intarraycompressors;

import java.util.ArrayList;
import java.util.Arrays;

import me.lemire.integercompression.Composition;
import me.lemire.integercompression.FastPFOR;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import me.lemire.integercompression.VariableByte;

public class DeltaZigZag extends AbstractIntArrayCompressor implements IntArrayCompressor {

	public ArrayList<Integer> compressIntArray(ArrayList<Integer> inArray) {
		// Function to compress an array - using PFOR compression
		int N = inArray.size();
		int[] data = convertIntegers(inArray);
    	int[] compressed = new int [N+1024];// could need more
    	IntegerCODEC codec =  new

            Composition(
                     new FastPFOR(),
                     new VariableByte());
         // compressing
         IntWrapper inputoffset = new IntWrapper(0);
         IntWrapper outputoffset = new IntWrapper(0);
         codec.compress(data,inputoffset,data.length,compressed,outputoffset);
         System.out.println("compressed unsorted integers from "+data.length*4/1024+"KB to "+outputoffset.intValue()*4/1024+"KB");
         System.out.println(N);
         // we can repack the data: (optional)
         compressed = Arrays.copyOf(compressed, outputoffset.intValue());
         int[] recovered = new int[N];
         IntWrapper recoffset = new IntWrapper(0);
         codec.uncompress(compressed,new IntWrapper(0),compressed.length,recovered,recoffset);
         if(Arrays.equals(data, recovered))
           System.out.println("data is recovered without loss");
         else
           throw new RuntimeException("bug"); // could use assert
         // ADD ONE NUMBER AT THE START TO INDICATE THE SIZE OF THE COMPRESSED ARRAY
         ArrayList<Integer> outArray = (ArrayList<Integer>) convertArrayListIntegers(compressed);
         outArray.add(0, N);
         // Now return this
		return outArray;
	}

}

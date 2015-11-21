package org.compression.intarraydecompressors;

import java.util.ArrayList;

import me.lemire.integercompression.Composition;
import me.lemire.integercompression.FastPFOR;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import me.lemire.integercompression.VariableByte;

public class PFORDeCompress extends AbstractIntArrayDeCompressor implements IntArrayDeCompressor {

	public ArrayList<Integer> decompressIntArray(ArrayList<Integer> inArray) {
		// Get the size
		int arraySize = inArray.get(0);
		inArray.remove(0);
    	IntegerCODEC codec =  new     
            Composition(
                     new FastPFOR(),
                     new VariableByte());
         // Conver the arrayList to an int[]
         int[] compressed = convertIntegers(inArray);
         // Make an intarray to store the output
         int[] recovered = new int[arraySize];
         IntWrapper recoffset = new IntWrapper(0);
         codec.uncompress(compressed,new IntWrapper(0),compressed.length,recovered,recoffset);
         // Now return this
		return convertArrayListIntegers(recovered);
	}

}

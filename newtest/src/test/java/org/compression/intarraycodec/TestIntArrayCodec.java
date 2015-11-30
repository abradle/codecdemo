package org.compression.intarraycodec;

import static org.junit.Assert.assertTrue;

import org.compression.intarraycompressors.AddMinVal;
import org.compression.intarraycompressors.FindDeltas;
import org.compression.intarraycompressors.IntArrayCompressor;
import org.compression.intarraycompressors.PFORCompress;
import org.compression.intarraycompressors.RunLengthEncode;
import org.compression.intarraydecompressors.IntArrayDeCompressor;
import org.compression.intarraydecompressors.PFORDeCompress;
import org.compression.intarraydecompressors.RemoveDeltas;
import org.compression.intarraydecompressors.RunLengthDecode;
import org.compression.intarraydecompressors.SubMinVal;
import org.junit.Test;
import org.junit.internal.runners.statements.RunAfters;

public class TestIntArrayCodec extends AbstractIntArrayCodec {
	
	
	@Test
	public void testPFORCodec() {
		IntArrayCompressor intArrComp = new PFORCompress();
		IntArrayDeCompressor intArrDeComp = new PFORDeCompress();
		assertTrue(runTest(intArrComp, intArrDeComp));
	}

	
	@Test
	public void testDeltaCodec() {
		IntArrayCompressor intArrComp = new FindDeltas();
		IntArrayDeCompressor intArrDeComp = new RemoveDeltas();
		assertTrue(runTest(intArrComp, intArrDeComp));
	}
	
	@Test
	public void testRunLengthCodec() {
		IntArrayCompressor intArrComp = new RunLengthEncode();
		IntArrayDeCompressor intArrDeComp = new RunLengthDecode();
		assertTrue(runTest(intArrComp, intArrDeComp));
	}
	
	@Test
	public void testSubMinValCodec() {
		IntArrayCompressor intArrComp = new AddMinVal();
		IntArrayDeCompressor intArrDeComp = new SubMinVal();
		assertTrue(runTest(intArrComp, intArrDeComp));
	}
}

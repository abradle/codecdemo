package org.compression.stringarraycodec;

import static org.junit.Assert.assertTrue;

import org.compression.stringarraycompressors.RunLengthEncodeString;
import org.compression.stringarraycompressors.StringArrayCompressor;
import org.compression.stringarraydecompressors.RunLengthDecodeString;
import org.compression.stringarraydecompressors.StringArrayDeCompressor;
import org.junit.Test;

public class TestStringArrayCodec extends AbstractStringArrayCodec {		
		@Test
		public void testRunLengthStringCodec() {
			StringArrayCompressor stringArrComp = new RunLengthEncodeString();
			StringArrayDeCompressor stringArrDeComp = new RunLengthDecodeString();
			assertTrue(runTest(stringArrComp, stringArrDeComp));
		}
	}
	
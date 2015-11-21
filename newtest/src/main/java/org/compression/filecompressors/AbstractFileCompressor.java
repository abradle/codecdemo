package org.compression.filecompressors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AbstractFileCompressor {
	protected static void writeToOutputStream(InputStream fis, OutputStream fos){
		try {
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}

/**
 * 
 */
package org.crossroad.jdbc.dump.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author e.soden
 *
 */
public class CSVContainer extends AbstractJDBCLogger{
	private FileOutputStream stream = null;
	private PrintWriter writer = null;
	

	/**
	 * 
	 */
	public CSVContainer() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void open(String outFile) throws FileNotFoundException
	{
		log.info("opening ["+outFile+"]");
		stream = new FileOutputStream(outFile);
		writer = new PrintWriter(stream);
	}
	
	
	public void writeData(String data) throws IOException
	{
		writer.println(data);
		writer.flush();
		stream.flush();
		
	}
	
	public void close() {
		if (writer != null) {
			try {
				writer.flush();
				writer.close();
			} catch (Exception e) {
			}
		}
		if (stream != null) {
			try {
				stream.flush();
				stream.close();
			} catch (IOException e) {
			}

		}
	}

}

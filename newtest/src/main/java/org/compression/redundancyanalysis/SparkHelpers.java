package org.compression.redundancyanalysis;

import org.apache.spark.SparkConf;

public class SparkHelpers {
	public 	SparkConf setConf(String appName){
		int cores = Runtime.getRuntime().availableProcessors();

	    System.out.println("SparkUtils: Available cores: " + cores);
	    SparkConf conf = new SparkConf()
	            .setMaster("local[" + cores + "]")
	            .setAppName(appName)
	            .set("spark.driver.maxResultSize", "4g")
	            .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
	            .set("spark.kryoserializer.buffer.max", "1g");

	    return conf;
		}
}

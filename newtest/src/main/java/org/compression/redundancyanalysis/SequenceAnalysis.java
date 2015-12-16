package org.compression.redundancyanalysis;

import scala.Tuple2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SequenceAnalysis {
	
	private static final String LINE = "\n";	
	private static final int SIZE = 20;


	  public static void main(String[] args) throws Exception {
		SparkHelpers sp = new SparkHelpers();
		JavaSparkContext ctx = new JavaSparkContext(sp.setConf("WORD COUNT"));
	    JavaRDD<String> lines = ctx.textFile("/Users/anthony/Documents/anthonytest/newtest/just_seqs", 1);
	    JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
	      @Override
	      public Iterable<String> call(String s) {
	    	  
	    	  String[] outArr = new String[s.length()-SIZE+1];
	    	  // CREATE THE ARRAY HERE 
	    	  if(s.length()<SIZE){
	    		  return Arrays.asList(new String[0]);
	    	  }
	    	  for(int i=0;i<s.length()-SIZE+1;i++){
		    	  char [] dst = new char [SIZE];
		    	  s.getChars(i, i+SIZE, dst, 0);
		    	  outArr[i] = dst.toString();  
	    	  }
	    	  return Arrays.asList(outArr);
	      }
	    });

	    JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {
	      @Override
	      public Tuple2<String, Integer> call(String s) {
	        return new Tuple2<String, Integer>(s, 1);
	      }
	    });

	    JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
	      @Override
	      public Integer call(Integer i1, Integer i2) {
	        return i1 + i2;
	      }
	    });
	    PrintWriter out = new PrintWriter("/Users/anthony/Documents/anthonytest/newtest/counts");
	    // Now get the data - and write it to a file
	    List<Tuple2<String, Integer>> output = counts.collect();
	    for (Tuple2<?,?> tuple : output) {
	      out.println(tuple._1() + ": " + tuple._2());
	    }
	    out.close();
	    ctx.stop();
	    ctx.close();
	  }


}

package org.compression.webapp;

import com.mongodb.*;

import spark.ModelAndView;
import spark.template.jetbrick.JetbrickTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.compression.methodbenchmarking.BenchmarkRun;
import org.json.simple.JSONObject;

public class Bootstrap {
	private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
	private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;

	public static void main(String[] args) throws Exception {
		ipAddress("0.0.0.0");
		port(PORT);
		staticFileLocation("/public");
		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<>();
			model.put("target", "spark-template-jetbrick");
			return new ModelAndView(model, "template/hello.jetx");
		}, new JetbrickTemplateEngine());
		get("/hello/:name","application/json",  (request, response) -> {
			BenchmarkRun br = new BenchmarkRun();
			return br.getResultsPDB(request.params(":name"));
		});
		get("/servefile/:name","text/plain",  (request, response) -> {
			Bootstrap bs = new Bootstrap();
			response.header("Access-Control-Allow-Origin", "*");
			return bs.getPDBFile(request.params(":name").substring(0, 4));
		});    
		get("/servefilegzip/:name","text/plain",  (request, response) -> {
			Bootstrap bs = new Bootstrap();
			response.header("Content-Encoding", "gzip");
			response.header("Access-Control-Allow-Origin", "*");
			return bs.getPDBFile(request.params(":name").substring(0, 4));
		});
	}
//
//	public FileInputStream getPDBFileGZIP(String pdbCode) throws FileNotFoundException, IOException{
//		String basePath = "/Users/anthony/PDB_CACHE/data/structures/divided/mmCIF/"+pdbCode.substring(1, 3)+"/"+pdbCode+".cif.gz";
//		return new FileInputStream(basePath);
//	}
	
	public GZIPInputStream getPDBFile(String pdbCode) throws FileNotFoundException, IOException{
		String basePath = "/Users/anthony/PDB_CACHE/data/structures/divided/mmCIF/"+pdbCode.substring(1, 3)+"/"+pdbCode+".cif.gz";
		return new GZIPInputStream(new FileInputStream(basePath));
	}


}

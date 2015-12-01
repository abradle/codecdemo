package org.compression.webapp;

import com.mongodb.*;

import spark.ModelAndView;
import spark.template.jetbrick.JetbrickTemplateEngine;

import static spark.Spark.get;
import static spark.Spark.ipAddress;
import static spark.Spark.port;
import static spark.Spark.staticFileLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.compression.methodbenchmarking.BenchmarkRun;
import org.json.simple.JSONObject;
 
public class Bootstrap {
    private static final String IP_ADDRESS = System.getenv("OPENSHIFT_DIY_IP") != null ? System.getenv("OPENSHIFT_DIY_IP") : "localhost";
    private static final int PORT = System.getenv("OPENSHIFT_DIY_PORT") != null ? Integer.parseInt(System.getenv("OPENSHIFT_DIY_PORT")) : 8080;
 
    public static void main(String[] args) throws Exception {
        ipAddress(IP_ADDRESS);
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
        
        
       // new TodoResource(new TodoService(mongo()));
    }
 
//    private static DB mongo() throws Exception {
//        String host = System.getenv("OPENSHIFT_MONGODB_DB_HOST");
//        if (host == null) {
//            MongoClient mongoClient = new MongoClient("localhost");
//            return mongoClient.getDB("todoapp");
//        }
//        int port = Integer.parseInt(System.getenv("OPENSHIFT_MONGODB_DB_PORT"));
//        String dbname = System.getenv("OPENSHIFT_APP_NAME");
//        String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
//        String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");
//        MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
//        MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
//        mongoClient.setWriteConcern(WriteConcern.SAFE);
//        DB db = mongoClient.getDB(dbname);
//        if (db.authenticate(username, password.toCharArray())) {
//            return db;
//        } else {
//            throw new RuntimeException("Not able to authenticate with MongoDB");
//        }
//    }
}

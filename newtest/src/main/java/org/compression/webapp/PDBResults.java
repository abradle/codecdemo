//package org.compression.webapp;
//import java.util.HashMap;
//import java.util.Map;
//
//import spark.ModelAndView;
//import spark.template.jetbrick.JetbrickTemplateEngine;
//
//import static spark.Spark.get;
//
///**
// * spark-template-jetbrick example
// */
//public class PDBResults {
//	static String IP_ADDRESS = "127.0.0.1";
//	static String PORT = "9001";
//    public static void main(String[] args) {
//
//        get("/", (request, response) -> {
//            Map<String, Object> model = new HashMap<>();
//            staticFileLocation("/public");
//            model.put("target", "spark-template-jetbrick");
//
//            return new ModelAndView(model, "template/hello.jetx");
//        }, new JetbrickTemplateEngine());
//
//    }
//
//}
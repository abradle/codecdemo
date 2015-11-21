package org.compression.helpers;

public class Typetester {
    void printType(byte x) {
        System.out.println(x + " is a byte");
    }
    void printType(int x) {
        System.out.println(x + " is an int");
    }
    void printType(float x) {
        System.out.println(x + " is a float");
    }
    void printType(double x) {
        //System.out.println(x + " is an double");
    }
    void printType(char x) {
        System.out.println(x + " is a char");
    }
    void printType(String x) {
        System.out.println(x + " is a String");
    }
    void printType(Object x) {
        System.out.println(x + " is an Object");
    }
}

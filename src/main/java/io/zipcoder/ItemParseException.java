package io.zipcoder;

public class ItemParseException extends Exception{

    public static int numberOfErrors = 0;

    public ItemParseException(String errors){
        super(errors);
        numberOfErrors++;
    }

    public static String errorsOutput(){
        return "Errors\t\t\t\tseen: " + numberOfErrors + " times";
    }
}

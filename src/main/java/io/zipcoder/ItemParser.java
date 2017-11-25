package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static com.sun.tools.javac.util.StringUtils.toLowerCase;

public class ItemParser{

    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawData);
        return response;
    }

    public Item parseStringIntoItem(String rawItem) throws ItemParseException{

        ArrayList<String> kvp = findKeyValuePairsInRawItemData(rawItem);

        String name;
        Double price;
        String type;
        String expiration;

        //name block (with regex for milk, bread, cookies, apples)
        Pattern namePattern = Pattern.compile("([Nn][Aa][Mm][Ee]):(\\w*\\d*)");
        Matcher nameMatcher = namePattern.matcher(kvp.get(0));
        if(nameMatcher.matches()){
            name = nameMatcher.group(2);
        }
        else {
            throw new ItemParseException("invalid name");
        }

        //price block (with regex for all prices)
        Pattern pricePattern = Pattern.compile("([Pp][Rr][Ii][Cc][Ee]):(\\d.\\d\\d)");
        Matcher priceMatcher = pricePattern.matcher(kvp.get(1));
        if(priceMatcher.matches()){
            price = Double.parseDouble(priceMatcher.group(2));
        }
        else {
            throw new ItemParseException("invalid price");
        }

        //type block (with regex for all types)
        Pattern typePattern = Pattern.compile("([Tt][Yy][Pp][Ee]):([A-Za-z0-9]*)");
        Matcher typeMatcher = typePattern.matcher(kvp.get(2));
        if(typeMatcher.matches()){
            type = typeMatcher.group(2);
        }
        else {
            throw new ItemParseException("invalid type");
        }

        //expiration block (with regex for all dates)
        Pattern expPattern = Pattern.compile("([Ee][Xx][Pp][Ii][Rr][Aa][Tt][Ii][Oo][Nn]):(\\d/\\d{2}/\\d{4})##");
        Matcher expMatcher = expPattern.matcher(kvp.get(3));
        if(expMatcher.matches()){
            expiration = expMatcher.group(2);
        }
        else {
            throw new ItemParseException("invalid expiration");
        }
        return new Item(name, price, type, expiration);
    }

    //want to check and change spelling and casing here
    public String checkSpelling(String itemName){
        Matcher matcher = Pattern.compile("0", Pattern.CASE_INSENSITIVE).matcher(itemName);
        matcher.find();
        return matcher.replaceAll("o");
    }

    public String checkCase(String itemName){
        if(itemName != null){
            itemName = checkSpelling(toLowerCase(itemName));
        }
        return itemName;
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;|^|!|%|*|@]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern , rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }

    public void errors(){
        System.out.println(ItemParseException.errorsOutput());
    }
}

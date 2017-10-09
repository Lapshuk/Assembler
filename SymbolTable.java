
package cs220_Roman_Lapshuk;

import java.util.HashMap;


public class SymbolTable {
    HashMap<String, Integer> symbolTable = new HashMap<>();
    public static final String INITIAL_VALID_CHARS = "^[a-zA-Z_.$]";
    public static final String ALL_VALID_CHARS = "[a-zA-Z0-9_.$]*$";
    
    //Symbol table constructor
    public SymbolTable(){
        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);
        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);
    }
    
    /*
     * Precondition: Symbol has a value, address has a properly assigned RAM address
     * Postcondition: The integer value of symbol's ram address returned
     */
    public boolean addEntry(String symbol,Integer address){
        if(!symbolTable.containsKey(symbol)){//if does not contain add to table
            
            symbolTable.put(symbol, address);
            
            return true;
        } else {
            return false;
        }
    }
    
    /*
     * Precondition: Symbol is not null
     * Postcondition: Returns boolean whether or not hash map has a key
     */
    public boolean contains(String symbol){
        if(!(symbol == null)){
            return symbolTable.containsKey(symbol);
        } else {
            return false;
        }    
    }
    /*
     * Precondition: Symbol has a value
     * Postcondition: The integer value of symbol's ram address returned
     */
    public int getAddress(String symbol){
        return symbolTable.get(symbol);
    }
    /*
     * Precondition: Name is not null and has 1 or more chars
     * Postcondition: Name is checked for valid chars. Boolean returned.
     */
    public boolean validName(String name){
        if(!(name==null)){
            
            if (name.substring(0,1).matches(INITIAL_VALID_CHARS)){
               if (name.matches(ALL_VALID_CHARS)){
                   return true;
               }  
            } else {
                System.err.println("SYMBOL NAME IS NOT VALID");
            }
        }
        return false;
    }
    /*
     * Precondition: Symbol table contains keys and values
     * Postcondition: Symbol table outputed
     */
    public String toString(){
        String temp = "";
        
        for (String key : symbolTable.keySet()) {
        temp += (key + " " + symbolTable.get(key)+"\n");
        }
        return temp;
    }
    
}//end of Symbol Table Class

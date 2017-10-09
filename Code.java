
package cs220_Roman_Lapshuk;
import java.util.*;
import java.lang.NumberFormatException;

public class Code {
    //creating the hash maps
    HashMap<String, String> destCodes = new HashMap<>();
    HashMap<String, String> compCodes = new HashMap<>();
    HashMap<String, String> jumpCodes = new HashMap <>();
    
    
    Code(){
        destCodes.put(null,"000");
        destCodes.put("M","001");
        destCodes.put("D","010");
        destCodes.put("MD","011");
        destCodes.put("A","100");
        destCodes.put("AM","101");
        destCodes.put("AD","110");
        destCodes.put("AMD","111");
        
        jumpCodes.put(null, "000");
        jumpCodes.put("JGT", "001");
        jumpCodes.put("JEQ", "010");
        jumpCodes.put("JGE", "011");
        jumpCodes.put("JLT", "100");
        jumpCodes.put("JNE", "101");
        jumpCodes.put("JLE", "110");
        jumpCodes.put("JMP", "111");
        
        //if a==0
        compCodes.put(null,"0000000");
        compCodes.put("0","0101010");
        compCodes.put("1","0111111");
        compCodes.put("-1","0111010");
        compCodes.put("D","0001100");
        compCodes.put("A","0110000");
        compCodes.put("!D","0001101");
        compCodes.put("!A","0110001");
        compCodes.put("-D","0001111");
        compCodes.put("-A","0110011");
        compCodes.put("D+1","0011111");
        compCodes.put("A+1","0110111");
        compCodes.put("D-1","0001110");
        compCodes.put("A-1","0110010");
        compCodes.put("D+A","0000010");
        compCodes.put("D-A","0010011");
        compCodes.put("A-D","0000111");
        compCodes.put("D&A","0000000");
        compCodes.put("D|A","0010101");
        
        //if a==1
        compCodes.put("M","1110000");
        compCodes.put("!M","1110001");
        compCodes.put("-M","1110011");
        compCodes.put("M+1","1110111");
        compCodes.put("M-1","1110010");
        compCodes.put("D+M","1000010");
        compCodes.put("D-M","1010011");
        compCodes.put("M-D","1000111");
        compCodes.put("D&M","1000000");
        compCodes.put("D|M","1010101");
    }
    
    /*
     * Precondition: The dest hash map is filled in with keys and  values
     * Postcondition: The value of a specific key is being returned
     */
    public String dest(String dest){
        return destCodes.get(dest);
    }
    
    /*
     * Precondition: The comp hash map is filled in with keys and  values
     * Postcondition: The value of a specific key is being returned
     */
    public String comp(String comp){
        return compCodes.get(comp);
    }
    
    /*
     * Precondition: The jump hash map is filled in with keys and  values
     * Postcondition: The value of a specific key is being returned
     */
    public String jump(String jmp){
        return jumpCodes.get(jmp);
    }
    
    /*
     * Precondition: In "number" is whole number woth no decimal point
     * Postcondition: 16 bit binary representation of a number returned as string
     */
    public String decimalToBinary16(int number){
        try{
            String zeros = "0000000000000000";
            String temp = Integer.toBinaryString(number);
            if(temp.length()<16){
                temp = zeros.substring(0,16-temp.length()).concat(temp);
            }
            return temp;
        } catch (NumberFormatException e){
            return "\""+number+"\" - Is not a number. Conversion failed";
        }
    }
    
    /*
     * Precondition: String "number" is whole number
     * Postcondition: 16 bit binary representation of a number returned as string
     */
    public String decimalToBinary16(String number){
        String zeros = "0000000000000000";
        try{
            String temp = Integer.toBinaryString(Integer.parseInt(number));
            if(temp.length()<16){
                temp = zeros.substring(0,16-temp.length()).concat(temp);
            }
            return temp;
        } catch (NumberFormatException e){
            return "\""+number+"\" - Is not a number. Conversion failed";
        }
    }
    
    //outputs 3 hash maps to the screen
    @Override
    public String toString(){
        String temp = "";
        
        for (String key : destCodes.keySet()) {
        temp += (key + " " + destCodes.get(key)+"\n");
        }
        
        temp += "\n";//new line
        
        for (String key : compCodes.keySet()) {
        temp += (key + " " + compCodes.get(key)+"\n");
        }
        
        temp += "\n";//new line
        
        for (String key : jumpCodes.keySet()) {
        temp += (key + " " + jumpCodes.get(key)+"\n");
        }
        return temp;
    }

}//end of Code class

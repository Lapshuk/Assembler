
package cs220_Roman_Lapshuk;

import java.util.Scanner;

public class Parser {
    private Scanner inputFile;
    private String rawLine;
    private String cleanLine;
    private Command commandType;
    private int actualLineNumber;
    private String symbol;
    private String dest;
    private String comp;
    private String jump;
    public enum Command{A_COMMAND,C_COMMAND,L_COMMAND}
    
    public Parser(){
        //empty
    }
    //Parser constructor as argument takes the Scanner Class file
    public Parser(Scanner file){
        inputFile = file;
    }
    /*
     * Precondition: There a line of text in the file
     * Postcondition: The comments and white spaces was removed from the line
     */
    public void cleanLine(){
        if(hasMoreCommands()){
            rawLine = inputFile.nextLine();

            while(rawLine.isEmpty() || rawLine.trim().equals("") 
                    || rawLine.trim().equals("\n")
                    || rawLine.charAt(0) == '/'){

                rawLine = inputFile.nextLine();
                actualLineNumber++;//increment line even when the line is comment

            }
            //in case if there is a comments after command take a substring 
            //before comments
            if(rawLine.contains("//")){
                rawLine = rawLine.substring(0,rawLine.lastIndexOf("//"));
            }
            cleanLine = rawLine.trim();
            actualLineNumber++;//increment any line
        } else {
            System.err.println("Reached the end of the file. Line #"+actualLineNumber);
        }
    }
    
    /*
     * Precondition: CommandType value was successfuly parsed from the line
     * Postcondition: Command type returned
     */
    public Command commandType(){
        return commandType;
    }
    
    /*
     * Precondition: Input file was found
     * Postcondition: Returns the boolean whether Scanner reach the end of file
     */
    public boolean hasMoreCommands(){
        return inputFile.hasNextLine();
    }
    
    /*
     * Precondition: No need for symbol to have a value
     * Postcondition: Returns String of symbol value or null
     */
    public String symbol(){
        return symbol;
    }
    
    /*
     * Precondition: No need for dest to have a value
     * Postcondition: Returns String of dest value or null
     */
    public String dest(){
        return dest;
    }
    
    /*
     * Precondition: No need for comp to have a value
     * Postcondition: Returns String of comp value or null
     */
    public String comp(){
        return comp;
    }
    
    /*
     * Precondition: No need for jump to have a value
     * Postcondition: Returns String of jump value or null
     */
    public String jump(){
        return jump;
    }
    
    /*
     * Precondition: The line number was incremented with each call of nextLine()
     * Postcondition: Returns the actual line number even counts the comments line
     */
    public int getActualLineNumber(){
        return actualLineNumber;
    }
    
    /*
     * Precondition: The line of text contains the command
     * Postcondition: commandType variable was set to one of 3 cmd types.
     */
    private void parseCommandType(){
        if (cleanLine.charAt(0)=='@'){
            commandType = Command.A_COMMAND;
        } else if (cleanLine.charAt(0)=='('){
            commandType = Command.L_COMMAND;
        } else {
            commandType = Command.C_COMMAND;
        }    
    }
    
    /*
     * Precondition: The commandType is C_command
     * Postcondition: The comp variable was set
     */
    private void parseComp(){
            if (commandType == Command.C_COMMAND){
                if(cleanLine.contains("=")){
                    comp = cleanLine.substring(cleanLine.lastIndexOf('=')+1,cleanLine.length());
                }
                if(cleanLine.contains(";")){
                    comp = cleanLine.substring(0,cleanLine.lastIndexOf(';'));
                }
            } else {
                comp = null;
            }

    }
    /*
     * Precondition: The commandType is C_command
     * Postcondition: The dest variable was set
     */
    private void parseDest(){
        if (commandType == Command.C_COMMAND){
            if(cleanLine.contains("=")){
                dest = cleanLine.substring(0,cleanLine.lastIndexOf('='));
            } else {
                dest = null;
            }    
        } else {
            dest = null;
        }
    }
    
    /*
     * Precondition: The commandType is A or C command
     * Postcondition: The symbol variable was set
     */
    private void parseSymbol(){
        if(commandType == Command.L_COMMAND){
            symbol = cleanLine.substring(cleanLine.indexOf("(")+1, 
                    cleanLine.lastIndexOf(')'));
        } else if (commandType == Command.A_COMMAND){
            symbol = cleanLine.substring(cleanLine.lastIndexOf('@')+1, 
                    cleanLine.length());
        } else {
            symbol = null; 
        }
        
    }
    
    /*
     * Precondition: The commandType is C_command
     * Postcondition: The jump variable was set
     */
    private void parseJmp(){

            if (commandType == Command.C_COMMAND){
                if(cleanLine.contains(";")){
                    jump = cleanLine.substring(cleanLine.lastIndexOf(';')+1,cleanLine.length());
                } else {
                    jump = null;
                }
            } else { 
                jump = null;
            }
        
    }
    
    /*
     * Precondition: All individual inner method's conditions were met
     * Postcondition: The line of text was separeted on individual commands
     */
    public void parse(){
        cleanLine();
        parseCommandType();
        parseSymbol();
        parseComp();
        parseDest();
        parseJmp();
        
    }
    
    /*
     * Precondition: The symbol variable has a value other than null
     * Postcondition: Returns boolean if the value is numberic or symbolic
     */
    public boolean isSymbolNumeric(){ 
        try{  
            double number = Integer.parseInt(symbol);  
        } catch(NumberFormatException e) {  
            return false;  
        }  
        return true;  
    }  
    
    /*
     * Precondition: All the variables needed for one of 3 commands were set
     * Postcondition: The current line's information is outputed to screen
     */
    @Override
    public String toString(){
        return "The Current Command Values"+"\nSymbol = "+symbol +"\nComp = "
                +comp+"\nDest = "+dest+"\nJump = "+jump;
    }
    
}//end of Parser Class

    


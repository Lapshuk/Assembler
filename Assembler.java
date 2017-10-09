/* Chapter No. 6 – Project No.6 

 File Name: Assembler

 Programmer: Roman Lapshuk 

 Date Last Modified: Wednesday, March 4, 2015 

 Problem Statement: Make an Assembler 
  
   

 Overall Plan : 

1. Draw diagram of needed classes
2. Decided what methods are needed
3. Decided what constants do we have to store in a hash maps
4. Implement the key methods like cleanLine and parsing methods
5. Implement the other methods needed for looking up the constants 
6. Implement the assembler so it only works when there is now varibales and lables
7. Implement the two passes logic in a program. First pass to collect the lables
   Second to write to file and add new variables to the stored table of variables
8. Clean the code, implement error checking


Imported classes needed and Purpose: 
                                    java.util.*;
                                    java.lang.NumberFormatException;
                                    java.io.File;
                                    java.io.FileNotFoundException;
                                    java.io.FileOutputStream;
                                    java.io.PrintWriter;
                                    java.util.Scanner;
                                    javax.swing.JFileChooser;
                                    javax.swing.JOptionPane;
Imported classes are used for a file input/output, exceptions, simple gui.

*/
package cs220_Roman_Lapshuk;

import cs220_Roman_Lapshuk.Parser.Command;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Assembler {
    
    //declaring the static variables so they can be used inside the static 
    //methods
    public static final int STARTING_RAM_ADDRESS = 15;
    public static final int STARTING_ROM_ADDRESS = 15;
    static String outputFileName;
    static PrintWriter outputFileWriter;
    static SymbolTable symbolTable = new SymbolTable();
    static int romAddress = STARTING_ROM_ADDRESS;
    static int ramAddress = STARTING_RAM_ADDRESS;
    static int lineNumber = 0;
    static File inputFile;
    static Scanner fileToScan;
    static Parser program;
    static Code codes = new Code();
    static JFileChooser chooser;
    static JOptionPane dialog;
    
    
    //Main method all runs in here
    public static void main(String[] args) {
        
        chooseIOFile();
        firstPass();
        secondPass();
          
    }//end of main
    
    /*
     * Precondition: User has a file with assembley language code
     * Postcondition: The explorer windows was opened to choose the file to translate.
     */
    private static void chooseIOFile(){
        chooser = new JFileChooser();
        int response = chooser.showOpenDialog(null);
        if(response == JFileChooser.APPROVE_OPTION){//checks if user cliked "Ok"
               inputFile = chooser.getSelectedFile();
               outputFileName = inputFile.getName();
               //output file name is the input file name + .hack extenssion
               outputFileName = 
                       (outputFileName.substring(0, outputFileName.lastIndexOf('.'))+
                               ".hack");
        } else {
            System.exit(0);//if decided to close file chooser > close the program
        }
    }
    
    /*
     * Precondition: User choose the correct file
     * Postcondition: The symbol table was filled in with the (labels) from code
     */
    private static void firstPass(){
        //First Pass is to fill symbol table with labels
        try {
            fileToScan = new Scanner(inputFile);
            //fileToScan = new Scanner(new File(inputFileName));
            program = new Parser(fileToScan);//initializing the object of a Parser
            
            //while there are any commands(lines) in a file keep parsing lines
            while(program.hasMoreCommands()){
                
                program.parse();//parsing the line 

                //if the  line is the L_COMMAND add the LABLE to the symbol table
                if(program.commandType() == Command.L_COMMAND){
                    romAddress = lineNumber;
                    if(symbolTable.validName(program.symbol())){
                        symbolTable.addEntry(program.symbol(), romAddress);
                    } else {
                        System.err.println("ERROR "
                                + "AT LINE #"+program.getActualLineNumber());
                        System.exit(0); 
                    }
                } else {
                    //Because line number is used for assigning the rom address
                    //it is not the actual line number and it gets incremented
                    //only when the command is not L.
                    lineNumber++;
                }
                
                //reseting the ROM addresses# for a next command
                romAddress = STARTING_ROM_ADDRESS;
                
            }
            //After the last command close the file;
            //So we can start reading it from start on the second pass;
            fileToScan.close();
        } catch (FileNotFoundException ex) {
            System.err.println("ASM FILE WAS NOT FOUND");
            System.exit(0);
        }
    }
    
    /*
     * Precondition: The firstPass() was successfully called and completed
     * Postcondition: The code was translated to binary and outputed to file
     */
    private static void secondPass(){
        //Second Pass - after the symbol table is filled with labels
        //still need to add varibales to it and generate the binary code
        try{
            //opening the file again 
            //fileToScan = new Scanner(new File(inputFileName));
            fileToScan = new Scanner(inputFile);
            program = new Parser(fileToScan);
            //initializing the output file.
            outputFileWriter = new PrintWriter(new FileOutputStream(outputFileName));
            //setting lineNumber to its initial value
            lineNumber = 1;
            
            //keep parsing until there are any commands(more lines in a file)
            while(program.hasMoreCommands()){
                
                program.parse();
                
                /* 
                 * If the command is A there are 2 cases variable or a digit
                 *  if A is digit then convert to binary and output
                 *  if A is a symbol look for its memory address in symbol table
                 *  if symbol was not added to table yet -> add it and assign
                 *  the RAM address corresponding to lineNumber(skip lables)
                 */
                //there are 2 options
                if(program.commandType() == Command.A_COMMAND ){
                    
                    if(program.isSymbolNumeric()){
                        outputFileWriter.println(codes.decimalToBinary16(program.symbol()));    
                    } else {
                        if(!symbolTable.contains(program.symbol())){

                            ramAddress += lineNumber;

                            symbolTable.addEntry(program.symbol(), ramAddress);
                            //only if varibale was added to symbol line++;
                            lineNumber++;
                        }
                        //write the  A command to file
                        outputFileWriter.println(codes.decimalToBinary16
                            (symbolTable.getAddress(program.symbol())));
                    }
                
                }
                //before start reading each line set ramAddress back to initial
                //value in case there are any new variables in a command.
                ramAddress = STARTING_RAM_ADDRESS; 
                
                /* 
                 *  If the command is C output the parsed parts of 
                 *  a C command to a file. Also get the "a-bit" according to
                 *  the type of computation.
                 */
                if(program.commandType() == Command.C_COMMAND){   
                    outputFileWriter.println("111"+codes.comp(program.comp())
                            +codes.dest(program.dest())
                    +codes.jump(program.jump()));          
                }
            }
            //close the file after finished writing to it.
            outputFileWriter.close();
            //message with the status of translation and the translated file location 
            JOptionPane.showMessageDialog(null, "The program \""+inputFile.getName()
            + "\" was successfully assembled.\n"
                    + "File \""+outputFileName+"\" was saved in "
                    + "program's folder.");
            System.exit(0);
        } catch (FileNotFoundException ex) {
            System.err.println("PROBLEM OUTPUTING THE FILE FOUND");
            System.exit(0);
        }
    }
    
}//end of Assembler

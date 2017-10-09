//Chapter No. 4 – Project No.4 
//Programmer: Roman Lapshuk
//Date Last Modified: February 18, 2015


// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.

//initial setup
      @KBD              //point to the keyboard location in RAM
      D=A               //store keyboard location to D register
      @maxscreen        //create a variable for the maximum size of the screen
      M=D               //store the keyboard location to the max size of screen
      @SCREEN           //point to screen
      D=A               //store the screen RAM address to D register
      @maxscreen        //finish setting the maximum screen size
      M=M-D             //max screen size equals KBD address-SCREEN adress
      @counter          //create the counter variable
      M=0               //set the starting counter value equal to 0

(LOOP)
      @KBD        //point to keyboard
      D=M         //store the keyboard value to D
      @BLACK      //store black out command to memory
      D;JGT       //if D(keyboard value) is greater than 0 jump to black the screen.
                  //esle keep executing the program
(WHITE)
      @counter    //point to the counter
      D=M         //store the counter value in D
      @LOOP       //point to LOOP
      D;JLT       //if the counter is less then 0 jump back to keyboard listener
      @counter
      D=M         //store the counter value into D
      @SCREEN     //point to screen address
      A=A+D       //go to the next line of the screen
      M=0         //set the line white
      @counter    //point to counter
      M=M-1       //decrement the counter
      @LOOP
      0;JMP       //unconditional jump to the listener loop

(BLACK)
      @counter    //point to counter
      D=M         //store the counter value to D
      @maxscreen  //point to screen maximum size value in RAM
      D=D-M       //subtract the max screen size from the counter value
      @LOOP
      D;JGE       //if the value is => jump to loop
      @counter    //point to counter
      D=M         //store counter value to D
      @SCREEN     //point to screen
      A=A+D       //store the counter value + screen address
      M=-1        //set the the current line to black
      @counter
      M=M+1       //increment counter by 1
      @LOOP
      0;JMP       //unconditional jump to loop.

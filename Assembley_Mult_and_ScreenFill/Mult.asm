//Chapter No. 4 – Project No.4
//Programmer: Roman Lapshuk
//Date Last Modified: February 18, 2015

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)


//initial setup
      @R2
      M=0         //Setting R2 to 0
      @counter
      M=1         //Setting counter to 1
(LOOP)
      @counter
      D=M         //storing the value of the counter to D
      @R1
      D=D-M       //substracting the value of R1 from the value of a counter
      @END
      D;JGT       //if the difference of counter - R1 is > 0 jump to the end loop.
                  //else keep evaluating the next line
      @R0
      D=M         //storing the value of R0 to D register.
      @R2
      M=M+D       //storing the sum of R2 + R0 into R2
      @counter
      M=M+1       //incrementing the counter
      @LOOP
      0;JMP       //loop again until
                //the difference of counter - R1 will be > 0
(END)
      @END
      0;JMP       //infinite loop ends the program

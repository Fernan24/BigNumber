# BigNumber

This is project is a simple text-based  Stock Exchange System that runs in the console or terminal.

## Notes

For the instructions of how to compile and run this project I am going to asume you have [Java Runtime Environment](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html). if you don’t please go ahead and install it, since this project is a java program and it will not run without it.

## How to compile and run from terminal 

1. Download and save the program in your **Desktop** as it will be easier to access and i will follow this method.
2. Unzip the File.
3. Open the Terminal or Console 
4. Navigate to the project directory `cd Desktop/P2_4035_802137210_152`

5. To Compile`javac -sourcepath src/ src/theSystem/MySystem.java`
6. If you get a warning like this: <img src="http://i.imgur.com/02Gc0n0.png" title=“Error” /> you will have to compile again `javac -sourcepath src/ src/theSystem/MySystem.java -Xlint:unchecked`

7. Run the program `java -classpath src/ theSystem/MySystem`

8. The program should be running now.

## User Stories

- [x] User can Create variable initialized to 0.0
- [x] User can remove variable.
- [x] User can add 2 numbers
- [x] User can subtract 2 numbers
- [x] User can multiply 2 numbers
- [x] User can compute the factorial of a positive integer number
- [x] User can determine if a positive interger is prime
- [x] User can show a variable
- [x] User can save variables to a file
- [x] User can load variables from a file
- [x] User can show a help for commands


## License

    Copyright 2016 Fernando Rodriguez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

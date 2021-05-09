/*
COMPSCI 2210B
Rishabh Jain
February 22th, 2021
*/

import java.io.*;

// Class Compress contains the main method and compresses the file that is inputted
public class Compress {

    public static void main(String[] args) {
        // Creates Dictionary of size 9973 (largest prime number less than 10000)
        Dictionary compressionDictionary = new Dictionary(9973);

        // Initializes hash table
        try {
            // inserts characters of ASCII table 0 to 255
            for (int i = 0; i <= 255; i++) {
                // Insert pair
                compressionDictionary.insert(new DictEntry(String.valueOf((char) i), i));
            }
        }
        // Catch errors while initializing the table and print the message
        catch (DictionaryException e) {
            System.out.println(e.getMessage());
            System.out.println("Dictionary could not be initialized");
            return;
        }

        // Input the file through BufferedInputStream
        BufferedInputStream in;
        try {
            in = new BufferedInputStream(new FileInputStream(args[0]));
        }
        // Print message if file count not be opened
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            return;
        }

        // Output file file through BufferedOutputStream
        BufferedOutputStream out;
        try {
            out = new BufferedOutputStream(new FileOutputStream(args[0] + ".zzz"));
        }
        // Print message if file count not be opened
        catch (FileNotFoundException e) {
            System.out.println("File could not be created");
            return;
        }

        // integer value of current character being read
        int readValue;
        // String inputString for copying contents of the file into a single string
        String inputString = "";
        // Code for reading the input file
        try {
            // reads first value to enter the while loop
            readValue = in.read();
            // Continues to execute until readValue equals -1 and exits the loop
            while (readValue != -1) {
                // Since it is not the end of a file, appends the value to inputString
                inputString = inputString + ((char) readValue);
                // Stores the value from the file into readValue
                readValue = in.read();
            }
        }
        // Catches error while performing the file read and string operations
        catch (IOException e) {
            System.out.println("Error");
            return;
        }

        // Integer variables used when adding records to the dictionary
        int records = 256, characterPosition = 0, inputSize = inputString.length();
        // Outer while loop terminates once the position of the character is no longer less than the size of the input
        while (characterPosition < inputSize) {
            // s is the current string and sLargest is the largest string that was found
            String s = "", sLargest = "";
            // appends the character to the string s
            s = s + inputString.charAt(characterPosition);
            // Append additional characters to the string s until a match is not found
            while (compressionDictionary.find(s) != null) {
                // a copy of the largest matching string is saved in sLargest
                sLargest = s;
                // Checks for remaining characters
                if (characterPosition < (inputSize - 1)) {
                    // next character appended to the string s
                    s = s + inputString.charAt(++characterPosition);
                }
                // no remaining characters
                else {
                    // increasing characterPosition and adding break statement to exit both loops
                    characterPosition++;
                    break;
                }
            }

            // Write code value of sLargest to the compressed file
            try {
                MyOutput.output(compressionDictionary.find(sLargest).getCode(), out);
            }
            // Catch any error while writing to the file
            catch (IOException e) {
                System.out.println("Error while writing to file");
                return;
            }

            // If there are remaining characters in the inputString and less than 4096 records in the dictionary
            if (characterPosition < inputSize && compressionDictionary.numElements() < 4096) {
                try {
                    // insert string s to the dictionary since it was not found
                    compressionDictionary.insert(new DictEntry(s, records));
                    // increment records by 1 as the next code value
                    records = records + 1;
                }
                // Catch errors with inserting this record
                catch (DictionaryException e) {
                    System.out.println("Insertion Error");
                }
            }
        }

        // Close the input file and terminate program
        try {
            in.close();
            MyOutput.flush(out);
            out.close();
        }
        // Catch errors while closing both input and output files
        catch (IOException e) {
            System.out.println("Error while closing files");
        }
    }
}

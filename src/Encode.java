/*
* Author: Evan Buss
* Major: Computer Science
* Creation Date: April 17, 2019
* Due Date: April 23, 2019
* Course: CSC402 - Data Structures 2
* Professor: Dr. Spiegel
* Assignment: Project #3
* Filename: Encode.java
* Purpose:  Encode a file of state data for easy data
*           retrieval
* Language: Java (Version 8)
* Compilation Command: javac Encode.java
* Execution Command: java Encode [state file name]
*/

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Encode a states data file for fast data retrieval
 * <p>
 * Creates two new files:
 * <ul>
 * <li>states.enc - new encoded file</li>
 * <li>states.idx - new index file that contains a state name and its position
 * in the encoded file</li>
 * </ul>
 */

public class Encode {

  /**
   * Delimiter to separate state objects in the encoded file
   */
  private static final String DELIMITER = "~";
  /**
   * Delimiter to separate individual state fields in the encoded file.
   */
  private static final String FIELD_DELIMITER = "#";
  /**
   * ArrayList of {@link State} objects to hold data from parsed file.
   */
  private static List<State> states;

  /**
   * Parses a state data file and then encodes the data into a new file
   *
   * @param args Only argument is the state data file name
   */
  public static void main(String[] args) {
    // Read states from file given as ARG

    if (args.length == 1) {
      File file = new File(args[0]);

      if (file.exists()) {
        parseStates(file);

        // Encode the states
        // Create data file and index file.
        try {
          encodeFile();
        } catch (IOException e) {
          e.printStackTrace();
          System.err.println("Could not create new files...");
          System.exit(-1);
        }
        System.out.println("Success! State data parsed and encoded.");
      } else {
        System.err.println("States file could not be found...");
        System.exit(-1);
      }

    } else {
      System.out.println("Usage: java Encode [states file]");
      System.exit(-1);
    }
  }

  /**
   * Writes state objects to states.enc in encoded format. Writes state names and
   * their position in the encoded file to states.idx
   *
   * @throws IOException If new files cannot be created.
   */
  private static void encodeFile() throws IOException {
    BufferedWriter encodedWriter =
        new BufferedWriter(new FileWriter("states.enc"));
    PrintWriter indexWriter =
        new PrintWriter(new FileWriter("states.idx"));

    int stateIndex = 0;
    for (State state : states) {
      indexWriter.println(state.name + " " + stateIndex);
      stateIndex += writeData(state.name, encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.population),
          encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.popRank),
          encodedWriter, true);
      stateIndex += writeData(Float.toString(state.popDensity),
          encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.areaOfState),
          encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.areaRank),
          encodedWriter, true);
      stateIndex += writeData(state.getAdmissionDate().toEncode(FIELD_DELIMITER),
          encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.orderOfAdmission),
          encodedWriter, true);
      stateIndex += writeData(state.capital, encodedWriter, false);

      // Write the DELIMITER character and increment index
      encodedWriter.write(DELIMITER);
      stateIndex += DELIMITER.length();

      // Write data from buffer to data file
      encodedWriter.flush();
    }
    // Write data from buffer to index file
    indexWriter.flush();

    indexWriter.close();
    encodedWriter.close();
  }

  /**
   * Writes data to the BufferedWriter and inserts a space if addSpace is true.
   * This function is used to write the data and return the data length in order
   * to increment the index for writing positions to states.idx
   *
   * @param data     Value to be written to file
   * @param writer   Points to the file you want written to
   * @param addSpace Whether or not a space should be inserted after data
   * @return Number of characters written to writer.
   * @throws IOException If not able to write to file.
   */
  private static int writeData(String data,
                               BufferedWriter writer,
                               boolean addSpace) throws IOException {
    String encodedData = addSpace ? data + FIELD_DELIMITER : data;
    writer.write(encodedData);
    return encodedData.length();
  }

  /**
   * Create state objects from a properly formatted state data file.
   *
   * @param stateFile file pointing to the state data file
   */
  private static void parseStates(File stateFile) {
    Scanner fileScanner = null;
    states = new ArrayList<>();

    try {
      fileScanner = new Scanner(stateFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.err.println("Could not find file...");
      System.exit(-1);
    }

    /*
     * Field Order in States File:
     * State Name
     * Population,
     * Population Rank,
     * Population Density,
     * Area,
     * Area Rank,
     * Month,
     * Day,
     * Year of Admission,
     * Order of Admission,
     * State Capital
     */

    while (fileScanner.hasNextLine()) {
      State state = new State();

      state.name = fileScanner.nextLine().trim();
      state.population = fileScanner.nextInt();
      state.popRank = fileScanner.nextInt();
      state.popDensity = fileScanner.nextFloat();
      state.areaOfState = fileScanner.nextInt();
      state.areaRank = fileScanner.nextInt();
      state.setAdmissionDate(fileScanner.nextInt(),
          fileScanner.nextInt(),
          fileScanner.nextInt());
      state.orderOfAdmission = fileScanner.nextInt();
      state.capital = fileScanner.next();

      fileScanner.nextLine();

      states.add(state);
    }
  }
}

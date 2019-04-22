/*
 * Author: Evan Buss
 * Major: Computer Science
 * Creation Date: April 17, 2019
 * Due Date: April 23, 2019
 * Course: CSC402 - Data Structures 2
 * Professor: Dr. Spiegel
 * Assignment: Project #3
 * Filename: Access.java
 * Purpose:  Access the data from an encoded file.
 * Language: Java (Version 8)
 * Compilation Command: javac Access.java
 * Execution Command: java Access [index file] [encode file]
 */

import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Access data from an encoded states data file.
 *
 * <ul>
 * <li>Parse the states.idx file into a HashMap to enable fast retrievals.</li>
 * <li>Use the HashMap to find the state's index in the encoded file.</li>
 * <li>Use a RandomAccessFile to seek to the given position in the encoded
 * file</li>
 * <li>Read from the given position until DELIMITER is encountered</li>
 * <li>Split the new string on the FIELD_DELIMITER</li>
 * <li>Load this data into a {@link State} object</li>
 * </ul>
 */
public class Access {

  /**
   * Delimiter that separates state objects in the given encoded file
   */
  private static final char DELIMITER = '~';
  /**
   * Delimiter that separates individual state object files in given encoded file
   */
  private static final String FIELD_DELIMITER = "#";
  /**
   * Index file that contains the state's position in the encoded file.
   */
  private static File indexFile;
  /**
   * Encoded file that can be read non-sequentially using seek()
   */
  private static RandomAccessFile dataFile;
  /**
   * Stores state names with their index
   */
  private static HashMap<String, Integer> states = new HashMap<>();
  /**
   * Format long numbers with commas and 2 decimal places.
   */
  private static DecimalFormat df = new DecimalFormat("#,###.##");

  public static void main(String[] args) {
    if (args.length == 2) {
      indexFile = new File(args[0]);

      System.out.print("Index File: ");

      if (!indexFile.exists()) {
        System.err.println("Not found...");
        System.exit(-1);
      }

      System.out.println("Loaded");

      try {
        System.out.print("Encoded File: ");
        dataFile = new RandomAccessFile(args[1], "r");
      } catch (FileNotFoundException e) {
        System.err.println("Not Found...");
        System.exit(-1);
      }

      System.out.println("Loaded");

      parseIndex();
      menu();

    } else {
      System.out.println("Usage: java Access [index file] [data file]");
      System.exit(-1);
    }
  }

  /**
   * Read from the provided index file, storing the data in a HashMap that uses
   * the state's name as the key and the states index in the encoded file as the
   * value.
   */
  private static void parseIndex() {
    String line;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(indexFile));
      while ((line = reader.readLine()) != null) {

        int splitPos = line.lastIndexOf(" ");

        states.put(line.substring(0, splitPos), Integer.parseInt(line.substring(splitPos + 1)));
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Error reading from index file.");
    }
    System.out.println();
  }

  /**
   * Display main menu and handle user inputs.
   */
  private static void menu() {
    Scanner keyboard = new Scanner(System.in);
    String input;
    boolean run = true;

    while (run) {
      System.out.println("\nF)ull State Output\n" +
          "P)rint State Names\n" +
          "L)ookup State Name\n" +
          "T)otal Population\n" +
          "A)verages\n" +
          "Q)uit");

      System.out.print("  > ");
      input = keyboard.nextLine();
      System.out.println();

      switch (input.toLowerCase()) {
        case "f":
          // Get user input
          System.out.print("State Name: ");
          State state = getStateData(keyboard.nextLine());

          if (state != null) {
            System.out.println("\n" + state);
          } else {
            System.err.println("Error: State not found!");
          }
          break;
        case "p":
          displayStates();
          break;
        case "l":
          submenu(keyboard);
          break;
        case "t":
          System.out.println("Total Population: ");
          int totalPopulation = calculateTotalPopulation();
          System.out.println("  " + df.format(totalPopulation) + " people");
          break;
        case "a":
          calculateAverages();
          break;
        case "q":
          System.out.println("Exiting program...");
          run = false;
          break;
        default:
          System.err.println("  Invalid Selection!");
      }
    }
  }

  /**
   * Display submenu that reads a state name from the user then loads the data
   * from the encoded file.
   *
   * @param keyboard Scanner pointing to user input
   */
  private static void submenu(Scanner keyboard) {
    String input, stateName;
    boolean run = true;

    // Get user input
    System.out.print("State Name: ");
    stateName = keyboard.nextLine();

    State state = getStateData(stateName);

    if (state == null) {
      run = false;
      System.err.println("Error: State not found!");
    }

    while (run) {
      System.out.println(
          "\n  P)opulation\n" +
              "  #)Population Rank\n" +
              "  >)Population Density\n" +
              "  A)rea\n" +
              "  $)Area Rank\n" +
              // " <)Area Density\n" +
              "  D)ate of Admission\n" +
              "  O)rder of Admission\n" +
              "  C)apital\n" +
              "  B)ack to Main Menu");

      System.out.print("  > ");
      input = keyboard.nextLine();
      System.out.println();

      switch (input.toLowerCase()) {
        case "p":
          System.out.println(state.getPopulationF());
          break;
        case "#":
          System.out.println(state.getPopRankF());
          break;
        case ">":
          System.out.println(state.getPopDensityF());
          break;
        case "a":
          System.out.println(state.getAreaOfStateF());
          break;
        case "$":
          System.out.println(state.getAreaRankF());
          break;
        // case "<":
        // System.out.println(" " + df.format(state.areaOfState / (float) state.pop) + "
        // square miles.");
        // break;
        case "d":
          System.out.println(state.getAdmissionDateF());
          break;
        case "o":
          System.out.println(state.getOrderOfAdmissionF());
          break;
        case "c":
          System.out.println(state.getCapitalF());
          break;
        case "b":
          run = false;
          break;
        default:
          System.err.println("  Invalid Selection!");
      }
    }
  }

  /**
   * Load state data from the encoded file by searching the HashMap for a specific
   * state name.
   * <p>
   * Access the encoded file by using the provided index from the HashMap using
   * the seek() function of the RandomAccessFile.
   *
   * @param stateName State name to search for in the index file
   * @return State with loaded data or null if the state could not be found.
   */
  private static State getStateData(String stateName) {
    int offset = states.getOrDefault(stateName.toUpperCase(), -1);
    if (offset == -1) {
      return null;
    }

    byte c;

    StringBuilder output = new StringBuilder();
    try {
      dataFile.seek(offset);
      while ((c = (byte) dataFile.read()) != DELIMITER) {

        output.append((char) c);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    String[] parsedData = output.toString().split(FIELD_DELIMITER);

    // Create State here
    State state = new State();
    state.name = parsedData[0];
    state.population = Integer.parseInt(parsedData[1]);
    state.popRank = Integer.parseInt(parsedData[2]);
    state.popDensity = Float.parseFloat(parsedData[3]);
    state.areaOfState = Integer.parseInt(parsedData[4]);
    state.areaRank = Integer.parseInt(parsedData[5]);
    state.setAdmissionDate(Integer.parseInt(parsedData[6]), Integer.parseInt(parsedData[7]),
        Integer.parseInt(parsedData[8]));
    state.orderOfAdmission = Integer.parseInt(parsedData[9]);
    state.capital = parsedData[10];

    return state;
  }

  /**
   * Calculate the total population of the United States by parsing all data from
   * the encoded file and tallying the populations of each state.
   *
   * @return Total population
   */
  private static int calculateTotalPopulation() {
    int totalPopulation = 0;
    for (String stateName : states.keySet()) {
      State state = getStateData(stateName);
      if (state != null) {
        totalPopulation += state.population;
      }
    }
    return totalPopulation;
  }

  /**
   * Calculates the average population and area per state in the United States.
   * Retrieves the total population from totalPopulation and divides it by the
   * number of entries in the HashMap (number of states).
   */
  private static void calculateAverages() {
    int stateCount = states.size();
    System.out.println("Average Population Per State: ");

    int totalPopulation = calculateTotalPopulation();

    System.out.println("  " + df.format(totalPopulation / (float) stateCount) + " people");

    System.out.println("Average Area Per State: ");

    int totalArea = 0;
    for (String stateName : states.keySet()) {
      State state = getStateData(stateName);
      if (state != null) {
        totalArea += state.areaOfState;
      }
    }

    System.out.println("  " + df.format(totalArea / (float) stateCount) + " square miles");
  }

  /**
   * Displays all possible state choices to the user.
   */
  private static void displayStates() {
    System.out.println("\nState List: ");
    for (String state : states.keySet()) {
      System.out.println("  " + state);
    }
  }
}

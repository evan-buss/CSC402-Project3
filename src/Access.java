import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Access {

  private static char delimiter = '~';
  private static File indexFile;
  private static RandomAccessFile dataFile;
  private static HashMap<String, Integer> states = new HashMap<>();


  public static void main(String[] args) {

    if (args.length == 2) {
      indexFile = new File(args[0]);

      if (!indexFile.exists()) {
        System.err.println("Cannot find given index file!");
        System.exit(-1);
      }

      try {
        dataFile = new RandomAccessFile(args[1], "r");
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

      parseIndex();
      menu();

    } else {
      System.out.println("Usage: java Access [index file] [data file]");
      System.exit(-1);
    }
  }

  private static void parseIndex() {
    String line;
    try {
      BufferedReader reader = new BufferedReader(new FileReader(indexFile));
      while ((line = reader.readLine()) != null) {

        int splitPos = line.lastIndexOf(" ");

        states.put(line.substring(0, splitPos),
            Integer.parseInt(line.substring(splitPos + 1)));
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Error reading from index file.");
    }
    System.out.println();
  }

  private static void menu() {
    Scanner keyboard = new Scanner(System.in);
    String input;
    boolean run = true;

    while (run) {
      System.out.println("F)ull State Output\n" +
          "P)rint State Names\n" +
          "L)ookup State Names\n" +
          "T)otal Population\n" +
          "A)verages\n" +
          "Q)uit");

      System.out.print("  > ");
      input = keyboard.nextLine();
      System.out.println();

      switch (input.toLowerCase()) {
        case "f":
          // Get user input
          break;
        case "p":
          // Print all state names
          displayStates();
          break;
        case "l":

          submenu(keyboard);
          break;
        case "t":
          System.out.println("Total Population");
          break;
        case "a":
          System.out.println("Average Area");
          break;
        case "q":
          System.out.println("Exiting program...");
          run = false;
          break;
        default:
          System.out.println("Invalid Selection!\n");
      }
    }
  }

  private static void submenu(Scanner keyboard) {
    String input, stateName;
    boolean run = true;

    //  Get user input
    System.out.print("  State Name: ");
    stateName = keyboard.nextLine();

    State state = getStateData(stateName);

    if (state == null) {
      run = false;
      System.err.println("Error: State not found!");
    }

    while (run) {
      System.out.println("  P)opulation\n" +
          "  #)Population Rank\n" +
          "  >)Population Density\n" +
          "  A)rea\n" +
          "  $)Area Rank\n" +
          // "  <)Area Density\n" +
          "  D)ate of Admission\n" +
          "  O)rder of Admission\n" +
          "  C)apital\n" +
          "  B)ack to Main Menu");

      System.out.print("  > ");
      input = keyboard.nextLine();
      System.out.println();

      switch (input.toLowerCase()) {
        case "p":
          System.out.println("Population: " + state.population + " people");
          break;
        case "#":
          System.out.println("Population Rank: " + state.popRank);
          break;
        case ">":
          System.out.println("Population Density: " + state.popDensity);
          break;
        case "a":
          System.out.println("Area: " + state.areaOfState + " miles");
          break;
        case "$":
          System.out.println("Area Rank: " + state.areaRank);
          break;
        // case "<":
        //   System.out.println("");
        //   break;
        case "d":
          System.out.println("Date of Admission: " + state.getAdmissionDate().format());
          break;
        case "o":
          System.out.println("Order of Admission: " + state.orderOfAdmission + " of 50");
          break;
        case "c":
          System.out.println("State Capital: " + state.capital);
          break;
        case "b":
          run = false;
          break;
        default:
          System.out.println("Invalid Selection!");
      }
    }
  }

  private static State getStateData(String stateName) {
    int offset = states.getOrDefault(stateName.toUpperCase(), -1);
    if (offset == -1) {
      return null;
    }

    byte c;

    StringBuilder output = new StringBuilder();
    try {
      dataFile.seek(offset);
      while ((c = (byte) dataFile.read()) != delimiter) {

        output.append((char) c);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    String[] parsedData = output.toString().split("#");

    //Create State here
    State state = new State();
    state.name = parsedData[0];
    state.population = Integer.parseInt(parsedData[1]);
    state.popRank = Integer.parseInt(parsedData[2]);
    state.popDensity = Float.parseFloat(parsedData[3]);
    state.areaOfState = Integer.parseInt(parsedData[4]);
    state.areaRank = Integer.parseInt(parsedData[5]);
    state.setAdmissionDate(Integer.parseInt(parsedData[6]),
        Integer.parseInt(parsedData[7]),
        Integer.parseInt(parsedData[8]));
    state.orderOfAdmission = Integer.parseInt(parsedData[9]);
    state.capital = parsedData[10];

    return state;
  }


  private static void displayStates() {
    for (String state : states.keySet()) {
      System.out.println(state);
    }
    System.out.println();
  }
}

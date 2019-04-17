import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Encode {

  private static String delimiter = "~";
  private static String fieldDelimiter = "#";
  private static List<State> states;

  public static void main(String[] args) {
    // Read states from file given as ARG
    parseStates(args[0]);

    // Encode the states
    // Create data file and index file.
    try {
      encodeFile();
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Could not create new files...");
      System.exit(-1);
    }
  }

  private static void encodeFile() throws IOException {
    BufferedWriter encodedWriter = new BufferedWriter(new FileWriter("states.enc"));
    PrintWriter indexWriter = new PrintWriter(new FileWriter("states.idx"));

    int stateIndex = 0;
    for (State state : states) {
      indexWriter.println(state.name + " " + stateIndex);
      stateIndex += writeData(state.name, encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.population), encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.popRank), encodedWriter, true);
      stateIndex += writeData(Float.toString(state.popDensity), encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.areaOfState), encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.areaRank), encodedWriter, true);
      stateIndex += writeData(state.getAdmissionDate().toEncode(fieldDelimiter), encodedWriter, true);
      stateIndex += writeData(Integer.toString(state.orderOfAdmission), encodedWriter, true);
      stateIndex += writeData(state.capital, encodedWriter, false);

      // Write the delimiter character and increment index
      encodedWriter.write(delimiter);
      stateIndex += delimiter.length();

      // Write data from buffer to data file
      encodedWriter.flush();
    }
    // Write data from buffer to index file
    indexWriter.flush();
  }

  private static int writeData(String data, BufferedWriter writer, boolean addSpace) throws IOException {
    String encodedData = addSpace ? data + fieldDelimiter : data;
    writer.write(encodedData);
    return encodedData.length();
  }

  private static void parseStates(String stateFile) {
    Scanner fileScanner = null;
    states = new ArrayList<>();

    try {
      fileScanner = new Scanner(new File(stateFile));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.err.println("Could not find file...");
      System.exit(-1);
    }

    // State Name
    // Population,
    // Population Rank,
    // Population Density,
    // Area,
    // Area Rank,
    // Month,
    // Day,
    // Year of Admission,
    // Order of Admission,
    // State Capital

    while (fileScanner.hasNextLine()) {
      State state = new State();

      state.name = fileScanner.nextLine();
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

    System.out.println(states);
  }
}

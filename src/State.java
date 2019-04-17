/*
 * Author: Evan Buss
 * Major: Computer Science
 * Creation Date: April 17, 2019
 * Due Date: April 23, 2019
 * Course: CSC402 - Data Structures 2
 * Professor: Dr. Spiegel
 * Assignment: Project #3
 * Filename: State.java
 * Purpose:  Represents a single state from the states file
 * Language: Java (Version 8)
 * Compilation Command: javac State.java
 */

import java.text.DecimalFormat;

/**
 * State represents a single state read from the states data file.
 * <p>
 * Its fields match the data file's fields
 */

public class State {

  String name = "";
  int population = 0;
  int popRank = 0;
  float popDensity = 0;
  int areaOfState = 0;
  int areaRank = 0;
  int orderOfAdmission = 0;
  String capital = "";
  private Date admissionDate;

  private DecimalFormat df = new DecimalFormat("#,###.##");

  /**
   * Create a new state object.
   */
  State() {
  }

  /**
   * Set the admission date of the state.
   * <p>
   * The admission date is stored as a {@link Date} object.
   *
   * @param month date's month
   * @param day   date's day
   * @param year  date's year
   */
  void setAdmissionDate(int month, int day, int year) {
    this.admissionDate = new Date(month, day, year);
  }

  /**
   * Get the admission {@link Date} associated with the State
   *
   * @return Date object.
   */
  Date getAdmissionDate() {
    return admissionDate;
  }

  /**
   * Display all data about the State in an easy to read format.
   *
   * @return String representation of the State
   */
  @Override
  public String toString() {
    return name + "\n  Population: " + df.format(population) + " people" +
        "\n  Capital: " + capital +
        "\n  Population Rank: " + popRank + " of 50" +
        "\n  Population Density: " + df.format(popDensity) + " people/square mile" +
        "\n  Area: " + df.format(areaOfState) + " square miles" +
        "\n  Area Rank: " + areaRank + " of 50" +
        "\n  Order of Admission: " + orderOfAdmission + " of 50" +
        "\n  Admission Date: " + admissionDate;
  }
}

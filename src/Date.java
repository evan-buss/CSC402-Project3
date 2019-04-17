/*
* Author: Evan Buss                                    
* Major: Computer Science
* Creation Date: April 17, 2019
* Due Date: April 23, 2019
* Course: CSC402 - Data Structures 2
* Professor: Dr. Spiegel
* Assignment: Project #3
* Filename: Date.java
* Purpose:  Store a simple Date object (Month/Day/Year)
* Language: Java (Version 8)
* Compilation Command: javac Date.java
*/

/**
 * Simple representation of a Date in history.
 * <p>
 * Dates are only defined using a year, month, and day.
 */
public class Date {

  private int year;
  private int month;
  private int day;

  /**
   * Create a new date with the given properties.
   *
   * @param month new date's month
   * @param day   new date's day
   * @param year  new date's year
   */
  Date(int month, int day, int year) {
    this.month = month;
    this.day = day;
    this.year = year;
  }

  /**
   * Display the formatted date in an easily readable format.
   *
   * @return String representation of the Date object
   */
  @Override
  public String toString() {
    return month + "/" + day + "/" + year;
  }

  /**
   * Encode the date object by separating each element using the specified
   * delimiter.
   *
   * @param delimiter String that should be used to separate fields
   * @return Encoded string representation of the Date object.
   */
  String toEncode(String delimiter) {
    return month + delimiter + day + delimiter + year;
  }
}

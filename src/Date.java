public class Date {

  private int year;
  private int month;
  private int day;

  Date(int month, int day, int year) {
    this.month = month;
    this.day = day;
    this.year = year;
  }

  @Override
  public String toString() {
    return month + " " + day + " " + year;
  }

  String format() {
    return month + "/" + day + "/" + year;
  }

  String toEncode(String delim) {
    return month + delim + day + delim + year;
  }
}

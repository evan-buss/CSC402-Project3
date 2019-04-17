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
    return month + "/" + day + "/" + year;
  }

  String toEncode(String delimiter) {
    return month + delimiter + day + delimiter + year;
  }
}

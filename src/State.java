import java.text.DecimalFormat;

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


  State() {
  }

  void setAdmissionDate(int month, int day, int year) {
    this.admissionDate = new Date(month, day, year);
  }

  Date getAdmissionDate() {
    return admissionDate;
  }

  @Override
  public String toString() {
    return name +
        "\n  Population: " + df.format(population) + " people" +
        "\n  Capital: " + capital +
        "\n  Population Rank: " + popRank + " of 50" +
        "\n  Population Density: " + df.format(popDensity) +
        "\n  Area: " + df.format(areaOfState) + " square miles" +
        "\n  Area Rank: " + areaRank + " of 50" +
        "\n  Order of Admission: " + orderOfAdmission + " of 50" +
        "\n  Admission Date: " + admissionDate;
  }
}

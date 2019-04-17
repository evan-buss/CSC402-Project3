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
    return "State{" +
        "name='" + name + '\'' +
        ", population=" + population +
        ", popRank=" + popRank +
        ", popDensity=" + popDensity +
        ", areaOfState=" + areaOfState +
        ", areaRank=" + areaRank +
        ", orderOfAdmission=" + orderOfAdmission +
        ", capital='" + capital + '\'' +
        ", admissionDate=" + admissionDate +
        '}';
  }
}

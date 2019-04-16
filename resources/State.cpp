#include <iostream>
#include <fstream>
#include <strstream>
#include <iomanip>
#include "State.h"
#include "Date.h"

using namespace std;

//Constructor to initialize all members to null
State::State()
{
  name = "";
  pop = 0;
  popRank = 0;
  popDensity = 0.0;
  areaOfState = 0;
  areaRank = 0;
  orderOfAdmission = 0;
  capital = "";
}

///overloading << operator
ostream& operator<<(ostream& osObject, const State& cObject)
{
  osObject<<"State Name: " <<setw(8) <<"" <<cObject.name <<endl
          <<"Area of State: " <<setw(5) <<"" <<cObject.areaOfState <<endl
          <<"Area Rank: " <<setw(9) <<"" <<cObject.areaRank <<endl
          <<"Capital: " <<setw(10) <<"" <<cObject.capital<<endl
          <<"Date of Admission: " <<setw(1) <<"" <<cObject.dateOfAdmission <<endl
          <<"Order of Admission: " <<cObject.orderOfAdmission <<endl
          <<"Population: " <<setw(8) <<""  <<cObject.pop <<endl
          <<"Population Density: " <<cObject.popDensity <<" people/mi^2" <<endl
          <<"Population Rank: " <<setw(3) <<""  <<cObject.popRank <<endl;
}

//overloading >> operator
ifstream& operator>>(ifstream& isObject, State& cObject)
{
  getline(isObject, cObject.name);

  isObject>>cObject.pop
          >>cObject.popRank
          >>cObject.popDensity
          >>cObject.areaOfState
          >>cObject.areaRank
	  >>cObject.dateOfAdmission
          >>cObject.orderOfAdmission;

   getline(isObject, cObject.capital);
}



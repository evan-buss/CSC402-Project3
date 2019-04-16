#ifndef State_H
#define State_H
#include <iostream>
#include <fstream>
#include <string>
#include "Date.h"

using namespace std;

class State
{
 public:
 
 State();

 bool operator==(string s);
 bool operator>(string s);
 bool operator>(const State&) const;
 bool operator<(const State&) const;

 private:

 string name;
 int pop;
 int popRank;
 float popDensity;
 int areaOfState;
 int areaRank;
 Date dateOfAdmission;
 int orderOfAdmission;
 string capital;

};

ostream& operator<<(ostream&, const State&);
ifstream& operator>>(ifstream&, State&);

#endif

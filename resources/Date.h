//File: Date.h
//Definition of the class Date

#ifndef DATE_H
#define DATE_H
#include <iostream>
#include <fstream>

using namespace std;

class Date
{
  public: 

  //Constructors
  Date( int m = 1, int d = 1, int y = 1700);
  Date(const Date &);

  void setDate(int, int, int);
  Date &operator++();
  Date operator++(int);
  const Date &operator+=(int);
  bool leapYear();

  private:

  int month, day, year;
  
  static const int days[];
  bool endOfMonth();
  void helpIncrement();
  
  friend ostream &operator<<(ostream &, const Date &);
  friend istream &operator>>(istream &, Date &);

};

#endif

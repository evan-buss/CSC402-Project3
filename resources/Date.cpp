//File:Date.cpp
//Member Function Implementations for Date class
#include <string>
#include <iostream>
#include "Date.h"

const int Date::days[]={0,31,28,31,30,31,30,31,31,30,31,30,31};

//Date Constructor
Date::Date(int m, int d, int y)
{
  setDate(m, d, y);
}

//Copy Constructor
Date::Date(const Date &d)
{
  setDate(d.day, d.month, d.year);
}

//Set the Date
void Date::setDate(int mm, int dd, int yy)
{
  month = (mm >= 1 && mm <=12) ? mm : 1;
  year = (yy >= 1700 && yy <= 2100) ? yy : 1700;

  //Test for a leap year

  if(month == 2 && leapYear())
    day = (dd >= 1 && dd <= 29) ? dd : 1;
  else
    day = (dd >= 1 && dd <= days[month]) ? dd : 1;
}

//Pre-Increment operator overloaded as a member function
Date &Date::operator++()
{
  helpIncrement();
  return *this;
}

//Post-Increment operator overloaded as a meber function
Date Date::operator++(int)
{
  Date temp = *this;
  helpIncrement();

  //returns the *non incremented* date object called temp
  return temp;
}

//Add a specific number of days to a date
const Date &Date::operator+=(int additionalDays)
{
  for(int i = 0; i < additionalDays; i++)
    {
      helpIncrement();
    }
  
  return *this;
}

//Leap year function
//modified from original code, why do you need a integer parameter (removed)
//can't you just use the private "year" value from the object/class?
bool Date::leapYear()
{
  if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
    return true;
  else
    return false;
}

//Determine if day is at the end of the month
//Modified for same reason above, no day parameter needed, I think :)
bool Date::endOfMonth()
{
  if(month == 2 && leapYear())
    return (day == 29);
  else
    return (day == days[month]);
}

//Increment the date
void Date::helpIncrement()
{
  if(endOfMonth() && month == 12)
    {
      day = 1;
      month = 1;
      ++year;
    }

  else if(endOfMonth())
         {
           day = 1;
           ++month;
	 }

       else 
           ++day;
}

//Overloaded output operator
ostream &operator<<(ostream &output, const Date &d)
{
  static string monthName[13] = {"", "January", "February", "March", "April", "May", "June",
                                 "July", "August", "September", "October", "November", "December"};

  output << monthName[d.month] << ' '
         <<d.day <<", " <<d.year;

  return output;
}

//overloading >> operator
istream &operator>>(istream &Data, Date &D)
{
  Data >> D.month 
       >> D.day 
       >> D.year;
}










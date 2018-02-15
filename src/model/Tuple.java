package model;
/*====================================================================
    Class Name:  Tuple
       Purpose:  Created to store immutable information about
                 locations on the chess board
  Parent Class:  None
====================================================================*/
public class Tuple {
  private int first;
  private int second;

  public Tuple(int f, int s) {
    first = f;
    second = s;
  } //end constructor

  public int getFirst() {
    return first;
  } //end method

  public int getSecond() {
    return second;
  } //end method

  /*====================================================================
     Method Name:  hashCode
         Purpose:  rewrites the hashcode for the comparison of other
                   tuple objects
      Parameters:  None
         Returns:  integer representaiton of what is the hash associated
                   with each instance.
  ====================================================================*/
  @Override
  public int hashCode() {
    //prime number used for nique mapping
    final int prime = 31;
    int result = 1;

    //usese information from this tuple for hashcode
    result = prime * result + first;
    result = prime * result + second;

    return result;
  } //end method

  /*====================================================================
     Method Name:  equlas
         Purpose:  Overwrites comparison method to compare other tuples
      Parameters:  obj: other object to compare to
         Returns:  Boolean concerning if two tuples are equal
  ====================================================================*/
  @Override
  public boolean equals(Object obj) {
    //other variable is set equal to tuple instance of passed in object
    Tuple other = (Tuple) obj;

    return (first == other.getFirst() && second == other.getSecond());
  } //end method

  public String toString() {
    return "(" + first + ", " + second + ")";
  } //end method
} //end class

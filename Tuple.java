public class Tuple {
  private int first;
  private int second;

  public Tuple(int f, int s) {
    first = f;
    second = s;
  }

  public int getFirst() {
    return first;
  }

  public int getSecond() {
    return second;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + first;
    result = prime * result + second;
    return result;
    }

  @Override
  public boolean equals(Object obj) {
    Tuple other = (Tuple) obj;
    return (first == other.getFirst() && second == other.getSecond());
  }

  public String toString() {
    return "(" + first + ", " + second + ")";
  }
}

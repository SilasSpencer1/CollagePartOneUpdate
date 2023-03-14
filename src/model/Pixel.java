package model;

public class Pixel {

  int red;
  int green;
  int blue;
  int alpha;

  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public Pixel(int red, int green, int blue, int alpha) {
    this.red = red;
    this.blue = blue;
    this.green = green;
    this.alpha = alpha;
  }

  public int getGreen() {
    return green;
  }

  public int getRed() {
    return red;
  }

  public int getBlue() {
    return blue;
  }

  public void setRed(int red) {
    this.red = red;
  }

  public void setBlue(int blue) {
    this.blue = blue;
  }

  public void setGreen(int green) {
    this.green = green;
  }

  public void setAlpha(int alpha) {
    this.alpha = alpha;
  }

  public String toString() {
    return red + " " + green + " " + blue + " ";
  }
}

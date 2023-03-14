package model;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PPMimage implements Image {

  List<List<Pixel>> image;
  String filename;

  public PPMimage(String filename) {
    this.filename = filename;
    this.image = new ArrayList<>();
    readImage(filename);
  }

  private void readImage(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filename+ " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

    // Read the pixel data
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        int red = sc.nextInt();
        int green = sc.nextInt();
        int blue = sc.nextInt();
        Pixel pixel = new Pixel(red, green, blue); // alpha is set to 255
        row.add(pixel);
      }
      image.add(row);
    }

  }

  /**
   * brightens a given image by an amount from 0-255
   *
   * @param intensity the amount we are brightening the image by
   */
  @Override
  public void brighten(int intensity) throws IllegalArgumentException {
    if (intensity < 0 || intensity > 255) throw new IllegalArgumentException();

    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(0).size(); j++) {
        Pixel curr = image.get(i).get(j);
        int blueUpdate = curr.blue + intensity >= 255 ? 255 : curr.blue + intensity;
        int redUpdate = curr.red + intensity >= 255 ? 255 : curr.red + intensity;
        int greenUpdate = curr.green + intensity >= 255 ? 255 : curr.green + intensity;
        curr.setRed(redUpdate);
        curr.setBlue(blueUpdate);
        curr.setGreen(greenUpdate);
      }
    }
  }

  /**
   * darkens a given image by a pixel amount from 0-255
   *
   * @param intensity the amount we are darkening the image by
   */
  @Override
  public void darken(int intensity) {
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(0).size(); j++) {
        Pixel curr = image.get(i).get(j);
        int blueUpdate = curr.blue - intensity <= 0 ? 0 : curr.blue - intensity;
        int redUpdate = curr.red - intensity <= 0 ? 0 : curr.red - intensity;
        int greenUpdate = curr.green - intensity <= 0 ? 0 : curr.green - intensity;
        curr.setRed(redUpdate);
        curr.setBlue(blueUpdate);
        curr.setGreen(greenUpdate);
      }
    }
  }

  /**
   * saves an image.
   * @param filepath where the image is saved to
   * @throws IOException if the filepath provided has some error.
   */
  @Override
  public void saveImage(String filepath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

    // Write the header
    writer.write(String.format("P3 %d %d %d\n", image.get(0).size(), image.size(), 255));

    for (List<Pixel> row : image) {
      for (Pixel pixel : row) {
        writer.write(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + " ");
      }
      writer.write("\n");
    }

    writer.close();
  }

  /**
   * gets the 2D ARRAY of pixels.
   * @return List<List<Pixels></Pixels>
   */
  @Override
  public List<List<Pixel>> getImage() {
    return this.image;
  }

  /**
   * gets a pixel at a row, column in a matrix of pixel data.
   * @param i the row.
   * @param j the column.
   * @return A single pixel in the 2D ARRAY of pixels.
   */
  @Override
  public Pixel getPixelAt(int i, int j) {
    return image.get(i).get(j);
  }

  /**
   * Filters an image to only show the red component
   */
  @Override
  public void setRedComponent() {
    for (List<Pixel> pixels : image) {
      for (Pixel pixel : pixels) {
        pixel.setGreen(0);
        pixel.setBlue(0);
      }
    }
  }

  /**
   * Filters an image to only show the green component
   */
  @Override
  public void setGreenComponent() {
    for (List<Pixel> pixes : image) {
      for (Pixel pixel : pixes) {
        pixel.setRed(0);
        pixel.setBlue(0);
      }
    }
  }

  /**
   * Filters an image to only show the blue component
   */
  @Override
  public void setBlueComponent() {
    for (List<Pixel> pixels : image) {
      for (Pixel pixel : pixels) {
        pixel.setRed(0);
        pixel.setGreen(0);
      }
    }
  }


}

package model;

import java.io.IOException;
import java.util.List;

public interface Image {


  /**
   * brightens a given image by an amount from 0-255
   * @param intensity the amount we are brightening the image by
   * @throws IllegalArgumentException when intensity is less than 0 or greater than 255
   */
  void brighten(int intensity) throws IllegalArgumentException;


  /**
   * darkens a given image by a pixel amount from 0-255
   * @param intensity darkens a given image by a pixel amount from 0-255
   * @throws IllegalArgumentException when intensity is less than 0 or greater than 255
   */
  void darken(int intensity) throws  IllegalArgumentException;


  /**
   * Filters an image to only show the red component
   */
  void setRedComponent();

  /**
   * Filters an image to only show the green component
   */
  void setGreenComponent();

  /**
   * Filters an image to only show the blue component
   */
  void setBlueComponent();

  /**
   * Saves an image and assigns the new file a name
   * @param filename the new file name of the image
   */
  void saveImage(String filename) throws IOException;

  List<List<Pixel>> getImage();

  Pixel getPixelAt(int i, int j);
}

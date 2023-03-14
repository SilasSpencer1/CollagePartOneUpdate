package model;

import java.io.IOException;

public interface ILayer {

  /**
   * darkens all images on the layer by a given intensity;
   * @param intensity the amount we are darkening all images by.
   * @throws IllegalStateException if there are no images on the layer
   */
  void darkenFilter(int intensity) throws IllegalStateException;

  /**
   * brightens all images on the layer by a given intensity
   * @param intensity the
   * @throws IllegalStateException if there are no images on the layer
   */
  void brightenFilter(int intensity) throws IllegalStateException;

  void addImageToLayer(Image image, int xPos, int yPos) throws IOException;
}

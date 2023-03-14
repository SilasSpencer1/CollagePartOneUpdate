package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Layer implements ILayer {


  String layerName;
  List<List<Pixel>> background;
  List<String> commandList;
  int transparency;
  List<Image> imagesOnLayer;
  int width;
  int height;
  Image current;

  public Layer(String layerName, int height, int width) {
    this.layerName = layerName;
    this.imagesOnLayer = new ArrayList<>();
    this.background = new ArrayList<>();
    this.commandList = new ArrayList<>();
    this.transparency = 0;
    this.width = width;
    this.height = height;
    this.current = null;
    makeBackground();
  }

  //makes a completely transparent background
  private void makeBackground() {
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        Pixel curr = new Pixel(1 * transparency/255, 1 * transparency/255,
                1 * transparency/255);
        row.add(curr);
      }
      background.add(row);
    }
    System.out.println("Background width : " + background.get(0).size());
    System.out.println("Background height : " + background.size());
  }


  /**
   * darkens all images on the layer by a given intensity;
   *
   * @param intensity
   */
  @Override
  public void darkenFilter(int intensity) throws IllegalStateException {
    if (imagesOnLayer.size() == 0) {
      throw new IllegalStateException("there are no images to darken on this layer");
    }

    for (Image image : imagesOnLayer) {
      image.darken(intensity);
    }
  }

  /**
   * brightens all images on the layer by a given intensity
   * @param intensity brighten intensity.
   * @throws IllegalStateException if there are no images on the layer
   */
  @Override
  public void brightenFilter(int intensity) throws IllegalStateException {
    if (imagesOnLayer.isEmpty()) {
      throw new IllegalStateException("there are no images to brighten");
    }

    for (Image image : imagesOnLayer) {
      image.brighten(intensity);
    }
  }

  public void addCommand(String s) {
    commandList.add(s);
  }

  @Override
  public void addImageToLayer(Image image, int xPos, int yPos) {

    applyFilterCommands(image);
    List<List<Pixel>> imageData = image.getImage();
    for (int i = 0; i < imageData.size(); i++) {
      for (int j = 0; j < imageData.get(0).size(); j++) {
        int canvasX = xPos + i;
        int canvasY = yPos + j;
        if (canvasX >= 0 && canvasX < height && canvasY >= 0 && canvasY < width) {
          Pixel curr = image.getPixelAt(i, j);
          background.get(canvasX).get(canvasY).setRed(curr.getRed());
          background.get(canvasX).get(canvasY).setBlue(curr.getBlue());
          background.get(canvasX).get(canvasY).setGreen(curr.getGreen());
        }
      }
    }

    imagesOnLayer.add(image);
    this.current = image;
  }


  public void setGreenComponent() {
    for (List<Pixel> pixels : background) {
      for (Pixel pixel : pixels) {
        pixel.setBlue(0);
        pixel.setRed(0);
      }
    }
  }

  public void setRedComponent() {
    for (List<Pixel> pixels : background) {
      for (Pixel pixel : pixels) {
        pixel.setBlue(0);
        pixel.setGreen(0);
      }
    }
  }

  public void setBlueComponent() {
    for (List<Pixel> pixels : background) {
      for (Pixel pixel : pixels) {
        pixel.setGreen(0);
        pixel.setRed(0);
      }
    }

  }

  public void saveLayerAsPPM(String filepath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

    // Write the header
    writer.write(String.format("P3 %d %d %d\n", background.get(0).size(), background.size(), 255));

    for (List<Pixel> row : background) {
      for (Pixel pixel : row) {
        writer.write(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + " ");
      }
      writer.write("\n");
    }

    writer.close();
  }

  private void applyFilterCommands(Image image) {
    if (commandList.size() > 0) {
      if (commandList.contains("red-component")) {
        image.setRedComponent();
      }
      if (commandList.contains("blue-component")) {
        image.setBlueComponent();
      }
      if (commandList.contains("green-component")) {
        image.setGreenComponent();
      }


      // may not be needed!
      for (int i = 0; i < commandList.size(); i++) {
        if (commandList.get(i).contains("dark")) {
          String[] words = commandList.get(i).split(" ");
          int intensity = Integer.parseInt(words[words.length -1]);
          image.darken(intensity);
        }

        if (commandList.get(i).contains("brighten")) {
          String[] words = commandList.get(i).split(" ");
          int intensity = Integer.parseInt(words[words.length - 1]);
          image.brighten(intensity);
        }
      }
    }
  }

  public String getLayerName() {
    return layerName;
  }

  public Image getCurrentImage() {
    return current;
  }
}

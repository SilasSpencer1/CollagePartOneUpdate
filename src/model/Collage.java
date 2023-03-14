package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Collage {

  int width;
  int height;
  List<List<Pixel>> canvas;
  HashMap<String, Layer> layerMap;
  List<Layer> layers;
  Layer current;

  public Collage(int height, int width) {
    this.width = width;
    this.height = height;
    this.canvas = new ArrayList<>();
    this.layerMap = new HashMap<>();
    this.layers = new ArrayList<>();
    this.current = null;

    makeCanvas();
  }

  private void makeCanvas() {
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        Pixel curr = new Pixel(255, 255, 255);
        row.add(curr);
      }
      canvas.add(row);
    }
  }

  public void updateCanvas() {
    for (int i = 0; i < layers.size(); i++) {
      placeLayerDataOnCanvas(layers.get(i));
    }
  }

  private void placeLayerDataOnCanvas(Layer layer) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel layerCurr = layer.background.get(i).get(j);
        canvas.get(i).get(j).setRed(layerCurr.getRed());
        canvas.get(i).get(j).setBlue(layerCurr.getBlue());
        canvas.get(i).get(j).setGreen(layerCurr.getGreen());
      }
    }
  }

  public Layer getSpecificLayer(String layerName) throws IllegalArgumentException {
    if (!layerMap.containsKey(layerName)) {
      throw new IllegalArgumentException("layer not found");
    }
    return layerMap.get(layerName);
  }

  public void addLayer(Layer layer) throws IllegalArgumentException {
    String layerName = layer.getLayerName();
    if (layerMap.containsKey(layerName)) {
      throw new IllegalArgumentException("Layer is already in collage");
    }

    layerMap.put(layerName, layer);
    layers.add(layer);
    this.current = layer;
  }

  public void setFilter(String color, String layerName) throws IllegalArgumentException {
    Layer layer = null;
    for (Map.Entry<String, Layer> layerSet : layerMap.entrySet()) {
      if (layerName.toLowerCase().equals(layerSet.getKey())) {
        layer = layerSet.getValue();
      }
    }
    int index = 0;
    for (int i = 0; i < layers.size(); i++) {
      if (layer.getLayerName() == layers.get(i).getLayerName()) {
        break;
      }
      index++;
    }
    if (layer == null) {
      throw new IllegalArgumentException("layer not found");
    }

    switch (color) {
      case "blue-component":
        for (int i = index; i >= 0; i--) {
          layers.get(index).setBlueComponent();
        }
        break;
      case "red-component":
        for (int i = index; i >= 0; i--) {
          layers.get(index).setRedComponent();
        }
        break;
      case "green-component":
        for (int i = index; i >= 0; i--) {
          layers.get(index).setGreenComponent();
        }
      default:
        throw new IllegalArgumentException("invalid color");
    }
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public void saveCollage(String filepath) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

    updateCanvas();
    // Write the header
    writer.write(String.format("P3 %d %d %d\n", canvas.get(0).size(), canvas.size(), 255));

    for (List<Pixel> row : canvas) {
      for (Pixel pixel : row) {
        writer.write(pixel.getRed() + " " + pixel.getGreen() + " " + pixel.getBlue() + " ");
      }
      writer.write("\n");
    }

    writer.close();
  }

  public List<Layer> getLayers() {
    return layers;
  }

  public Layer getCurrentLayer() {
    return this.current;
  }

}

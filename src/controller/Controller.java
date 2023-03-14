package controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.Collage;
import model.Layer;
import model.PPMimage;

public class Controller {

  Scanner scanner = new Scanner(System.in);

  public Controller(Scanner scanner) {
    this.scanner = scanner;
  }

  public void makeProject() throws IOException {
    System.out.println("enter height of collage");
    int height = scanner.nextInt();
    System.out.println("enter width of collage");
    int width = scanner.nextInt();
    Collage collage1 = new Collage(height, width);

    while(scanner.hasNext()) {
      String command  = scanner.next();
      System.out.println(command);
      switch (command) {
        case "add-layer":
          String layerName = scanner.next();
          System.out.println("Name of layer : " + layerName);
          Layer newLayer = new Layer(layerName, collage1.getHeight(), collage1.getWidth());
          collage1.addLayer(newLayer);
          break;
        case "set-filter":
          String layerFilter = scanner.next();
          String filter = scanner.next();
          /**
          if (filter == "red-component") {
            collage1.setFilter("red-component", layerFilter);
            System.out.println("red filter applied");
          } else if (filter == "blue-component") {
            collage1.setFilter("blue-component", layerFilter);
            System.out.println("blue filter applied");
          } else if (filter == "green-component") {
            collage1.setFilter("green-component", layerFilter);
            System.out.println("green filter applied");
          } else if (filter == "darken") {
            int darkenLevel = scanner.nextInt();
            collage1.getCurrentLayer().darkenFilter(darkenLevel);
            System.out.println("darken filter applied");
          } else if (filter == "brighten") {
            int brightenLevel = scanner.nextInt();
            collage1.getCurrentLayer().brightenFilter(brightenLevel);
            System.out.println("brighten filter applied");
          } else {
            System.out.println("invalid filter");
          }
          */
          switch (filter) {
            case "blue-component":
              collage1.setFilter(filter, layerFilter);
              System.out.println("filter applied: " + filter);
              break;
            case "red-component":
              collage1.setFilter("red-component", layerFilter);
              collage1.getCurrentLayer().addCommand("red-component");
              System.out.println("red filter applied");
              break;
            case "darken":
              try {
                int darkenLevel = scanner.nextInt();
                collage1.getCurrentLayer().darkenFilter(darkenLevel);
                System.out.println("darken filter applied");
                break;
              } catch (IllegalStateException | InputMismatchException e) {
                System.out.println("either no images on layer or bad input. try again.");
                System.out.println("darken was called " + scanner.next());
              }
            case "brighten":
              int brightenLevel = scanner.nextInt();
              collage1.getCurrentLayer().brightenFilter(brightenLevel);
              System.out.println("brighten filter applied");
              break;
            default:
              System.out.println("invalid filter");
          }
          break;
        case "add-image-to-layer":

          String wantedLayerName = scanner.next();
          String filename2 = scanner.next();
          System.out.println(wantedLayerName);
          System.out.println(filename2);
          int xPos = Integer.parseInt(String.valueOf(scanner.next()));
          int yPos = Integer.parseInt(String.valueOf(scanner.next()));
          collage1.getSpecificLayer(wantedLayerName).addImageToLayer(
                  new PPMimage(filename2), xPos, yPos);
          System.out.println("Image added to layer");
          break;
        case "save-image":
          try {
            String filename = scanner.next();
            collage1.getCurrentLayer().getCurrentImage().saveImage(filename);
            System.out.println("image saved");
          } catch (InputMismatchException e) {
            System.out.println("input error: "  + scanner.next());
          }
          break;
        case "save-project":
          try {
            String projectName = scanner.next();
            collage1.saveCollage(projectName);
          } catch (IOException e) {
            System.out.println("bad input. could not save.");
          }


        default:
          System.out.println("invalid command");
      }
    }
  }


}

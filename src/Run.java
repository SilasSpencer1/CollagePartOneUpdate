

import java.io.IOException;
import java.util.Scanner;
import controller.Controller;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method
 *  as required.
 */
public class Run {

  //demo main
  public static void main(String[] args) throws IOException {

    Scanner scanner = new Scanner(System.in);
    Controller controller = new Controller(scanner);

    controller.makeProject();

  }
}


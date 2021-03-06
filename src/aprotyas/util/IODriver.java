/*
 *  File name: IODriver.java
 *  Package: aprotyas.util
 *  File description: Static driver class that handles File I/O. The
 *  static functions in this class are:
 *
 *  i)  readExpressions() - Reads the file with filename specified as
 *  an argument, and returns an ArrayList<String> with each element
 *  being a single line of the input file
 *
 *  ii) writeResults() - Writes each String "result" in an ArrayList<String>
 *  as a separate line in a file with filename specified as an argument.
 */

package aprotyas.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class IODriver {
  public static ArrayList<String> readExpressions(String filename) {
    ArrayList<String> expressions = new ArrayList<String>();

    try {
      // catch exceptions while creating a reader, reading from a reader, or closing
      // it
      BufferedReader in_reader = new BufferedReader(new FileReader(filename));
      String expression = in_reader.readLine();
      while (expression != null) {
        // till EOF reached, keep adding new lines
        expressions.add(expression);
        expression = in_reader.readLine();
      }
      in_reader.close();
    } catch (IOException err) {
      System.out.println("Input file could not be read");
    }

    return expressions;
  }

  public static void writeResults(String filename, ArrayList<String> outputs) {
    try {
      // catch exceptions while creating a writer, writing with a writer, or closing
      // it
      BufferedWriter writer =
          new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));
      for (int i = 0; i < outputs.size(); ++i) {
        writer.write(outputs.get(i));
        if (i < outputs.size() - 1) // no new line for last output
        writer.newLine();
        else writer.flush();
      }
      writer.close();
    } catch (IOException err) {
      System.out.println("Output file could not be written to");
    }
  }
}

/*
ID: elj_4321
LANG: JAVA
TASK: ariprog
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.io.PrintStream;

public class ariprog {

  private static boolean debug = true;
  private static final String task = "ariprog";
  private static PrintStream outs = System.out;

  /**
   * @param args
   */
  public static void main(String[] args) throws IOException, FileNotFoundException
  {
//    long start = System.currentTimeMillis();
    final String infile = task + ".in";
    final String outfile = task + ".out";
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
    Scanner scanr = new Scanner(new File(infile));

    // Read N, length of progressions to search, 3 <= N <= 25
    //      M, upper bound to limit search to bisqures with 0 <= p,q <= M, 1 <= M <= 250
    Set<Integer> bisquares = new HashSet<Integer>();

    int N = scanr.nextInt();
    int M = scanr.nextInt();
    outd("N, M : " + N + ", " + M);

    int maxH = -1;
    int minH = 101;
    for (int i=0; i<numHills; i++) {
      Integer hx = scanr.nextInt();
      outd("Hill: " + hx);
      if (hx < minH) minH = hx;
      if (hx > maxH) maxH = hx;
      hills.add(hx);
    }

    outd("Min hill:" + minH);
    outd("Max hill:" + maxH);


    // Brute force this by examining every possible solution from the lowest hill
    //   to the tallest hill - MAX_DIFF
    int startH = minH;
    int stopH = maxH - MAX_DIFF;
    long minCost = Integer.MAX_VALUE;
    for (int h = startH; h <= stopH; h++)
    {
      long totCost = computeTotalCost(h, h + MAX_DIFF, hills);
      if (totCost < minCost) minCost = totCost;
    }
    outd("minCost: " + minCost);
    out.println(minCost);
    scanr.close();
    out.close();
    System.exit(0);
  }

  static long computeTotalCost(int minHH, int maxHH, List<Integer> allHills)
  {
    long totCost = 0;
    outd("Compute: " + minHH + ":" + maxHH);
    for (Integer hh : allHills)
    {
      if (hh < minHH)
      {
        totCost += (minHH-hh)*(minHH-hh);
      }
      else if (hh > maxHH)
      {
        totCost += (hh-maxHH)*(hh-maxHH);
      }
    }
    outd("totCost: " + totCost);
    return totCost;
  }

  static void outd(String msg)
  {
    if (debug) outs.println(msg);
  }
}

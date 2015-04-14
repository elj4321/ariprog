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
    final String infile = task + ".in";
    final String outfile = task + ".out";
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
    Scanner scanr = new Scanner(new File(infile));

    // Read N, length of progressions to search, 3 <= N <= 25
    //      M, upper bound to limit search to bisquares with 0 <= p,q <= M, 1 <= M <= 250
    Set<Integer> bisquares = new TreeSet<Integer>();

    int N = scanr.nextInt();
    int M = scanr.nextInt();
    int maxBS = 2 * M*M;
//    outd("N, M, maxBS : " + N + ", " + M + ", " + maxBS);

    // Construct set of bisquares
    long start = System.currentTimeMillis();
    for (int p = 0; p <= M; p++)
    {
      for (int q = 0; q <= p; q++)
      {
        Integer bisq = p*p + q*q;
        bisquares.add(bisq);
      }
    }

    long stop = System.currentTimeMillis();
//    outd("Time to construct bisquare set: " + (stop - start)/1000.0);


    // Compute limits on arithmetic progressions
    // Progressions are of form a, a+b, a+2b, ..., a+nb, n=0,1,2,3,...
    // a, b are integers, a >= 0, b > 0
    // Max bisquare maxBS = 2 * M**2
    // MaxA is maxBS = maxA + (N-1)*b => maxA = maxBS - (N-1) * b
    // MaxB is maxBS = (N-1)*maxB => maxB = maxBS/(N-1)

    int numProgs = 0;
    int maxB = maxBS / (N-1);
    for (int b = 1; b <= maxB; b++)
    {
      String bStr = " " + b;
      int maxA = maxBS - (N-1) * b;
//      outd("maxB: " + maxB + " maxA: " + maxA);
//      for (int a = 0; a <= maxA; a++)
//      {
      for (Integer a : bisquares)
      {
        if (a > maxA) continue;

        if (checkAriProg(a, b, N, bisquares))
        {
//          outd("" + a + " " + b);
          StringBuilder sb = new StringBuilder();
          sb.append(a).append(bStr);
//          out.println("" + a + " " + b);
          out.println(sb.toString());
          numProgs++;
        }
        // Check for max numProgs
        if (numProgs >= 10000) break;
      }
      if (numProgs >= 10000) break;
    }
    
    long stop2 = System.currentTimeMillis();
    outd("Time to find progressions: " + (stop2 - stop)/1000.0);
    outd("NumProgs: " + numProgs);
    if (numProgs == 0)
    {
      outd("NONE");
      out.println("NONE");
    }
    scanr.close();
//    out.println("Time to find progressions: " + (stop2 - stop)/1000.0);
    out.close();
    System.exit(0);
  }

  // For given a, b check if first N terms are all bisquares
  static boolean checkAriProg(int aa, int bb, int NN, Set<Integer> bisquares)
  {
//    for (int nn = 0; nn < NN; nn++)
    Integer p = aa + (NN-1)*bb;
    for (int nn = NN-1; nn > 0; nn--)
    {
      if (!bisquares.contains(p))
      {
        return false;
      }
      p = p - bb;
    }
    return true;
  }

  static void outd(String msg)
  {
    if (debug) outs.println(msg);
  }
}

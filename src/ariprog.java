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
    outd("N, M, maxBS : " + N + ", " + M + ", " + maxBS);

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

    outd("Size of bisqure list: " + bisquares.size());
    long stop = System.currentTimeMillis();
    outd("Time to construct bisquare set: " + (stop - start)/1000.0);

    // A must be a bisquare so only need to check A values where A is a bisquare

    // Create arrays to hold a and b values to check
    int[] aArr = new int[bisquares.size()];
//    int[] bArr = new int[bisquares.size()];
    int idx = 0;
    for (Integer a : bisquares)
    {
      aArr[idx] = a.intValue();
      idx++;
    }

    // Compute limits on arithmetic progressions
    // Progressions are of form a, a+b, a+2b, ..., a+nb, n=0,1,2,3,...
    // a, b are integers, a >= 0, b > 0
    // Max bisquare maxBS = 2 * M**2
    // MaxA is maxBS = maxA + (N-1)*b => maxA = maxBS - (N-1) * b
    // MaxB is maxBS = (N-1)*maxB => maxB = maxBS/(N-1)

    long stop2 = 0;
    int numProgs = 0;
    int maxB = maxBS / (N-1);
    outd("maxB: " + maxB);

/*
 * Attempt to limit number of B's to check by using fact that a+b must be a bisquare,
 * but it turned out to be somewhat slower
    // Loop over A first
    for (int ia : aArr)
    {
      // For each A compute all B's to be checked
      idx = 0;
      for (Integer bs : bisquares)
      {
        bArr[idx] = bs - ia;
        idx++;
      }
      // Now loop over allowed B's (i.e. where a+b is a bisquare)
      String aStr = " " + ia + " ";
      for (int ib : bArr)
      {
        if (ib > maxB || ib <= 0) continue;
        // At this point we limited A and B to values where
        // A and A+B are bisquares.
        // Now check if remaining elements are bisquares
        if (checkAriProg(ia, ib, N, bisquares))
        {
          StringBuilder sb = new StringBuilder();
          sb.append(aStr).append(ib);
          out.println(sb.toString());
          outd(sb.toString());
          stop2 = System.currentTimeMillis();
          outd("Time to find progressions: " + (stop2 - stop)/1000.0);
          numProgs++;
        }
        // Check for max numProgs
        if (numProgs >= 10000) break;
      }
      if (numProgs >= 10000) break;
    }
*/

    // TODO: Might be some way to further limit Bs
    // For case N=21,M=200 maxB = 4000 but the highest
    //    B value found is 1092. A little over half
    //    the time is spent checking B values > 1092
    //    and not finding anything.
    //  
    for (int ib = 1; ib <= maxB; ib++)
    {
      String bStr = " " + ib;
      int maxA = maxBS - (N-1) * ib;
//      outd("maxB: " + maxB + " maxA: " + maxA);
//      for (int a = 0; a <= maxA; a++)
//      {
//      for (Integer ia : bisquares)
      for (int ia : aArr)
      {
        if (ia > maxA) continue;

        if (checkAriProg(ia, ib, N, bisquares))
        {
          StringBuilder sb = new StringBuilder();
          sb.append(ia).append(bStr);
          out.println("" + ia + " " + ib);
          out.println(sb.toString());
          outd(sb.toString());
          stop2 = System.currentTimeMillis();
          outd("Lap time to find progressions: " + (stop2 - stop)/1000.0);
          numProgs++;
        }
        // Check for max numProgs
        if (numProgs >= 10000) break;
      }
      if (numProgs >= 10000) break;
    }

    
    stop2 = System.currentTimeMillis();
    outd("Time to find progressions: " + (stop2 - stop)/1000.0);
    outd("NumProgs: " + numProgs);
    if (numProgs == 0)
    {
      outd("NONE");
      out.println("NONE");
    }
    scanr.close();
    out.println("Total time to find progressions: " + (stop2 - stop)/1000.0);
    out.close();
    System.exit(0);
  }

  // For given a, b check if first N terms are all bisquares
  static boolean checkAriProg(int aa, int bb, int NN, Set<Integer> bisquares)
  {
/*
    for (int nn = 2; nn < NN; nn++)
    {
      Integer p = aa + nn*bb;
      if (!bisquares.contains(p)) return false;
    }
*/
    // Start at high end because bisquares are sparser there
    Integer p = aa + (NN-1)*bb;
//    int p = aa + (NN-1)*bb; Seems like this should be faster, but it's actually a little slower
    for (int nn = NN-1; nn > 0; nn--)
    {
      if (!bisquares.contains(p)) return false;
      p = p - bb;
    }
    return true;
  }

  static void outd(String msg)
  {
    if (debug) outs.println(msg);
  }
}

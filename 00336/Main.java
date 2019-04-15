import java.util.*;
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {

        FastReader fr = new FastReader();

        int NC = fr.nextInt();

        int caseCount = 0;
        int largestNode = 0;

        while (NC != 0) {

            HashMap<Integer, HashSet<Integer>> adjList = new HashMap<>();

            for (int i = 0; i < NC; i++) {
                int current = fr.nextInt();
                int next = fr.nextInt();
                if (adjList.containsKey(current)) {
                    adjList.get(current).add(next);
                } else {
                    HashSet<Integer> currentNeighbours = new HashSet<>();
                    currentNeighbours.add(next);
                    adjList.put(current, currentNeighbours);
                }
                if (adjList.containsKey(next)) {
                    adjList.get(next).add(current);
                } else {
                    HashSet<Integer> nextNeighbours = new HashSet<>();
                    nextNeighbours.add(current);
                    adjList.put(next, nextNeighbours);
                }
            }

            int start = fr.nextInt();
            int fieldSetting = fr.nextInt();

            while (!(start == 0 && fieldSetting == 0)) {
                int numUnreachableNodes;
                if (!adjList.containsKey(start)) {
                    numUnreachableNodes = adjList.size();
                } else if (fieldSetting == 0) {
                    numUnreachableNodes = adjList.size() - 1;
                } else {
                    numUnreachableNodes =
                            countUnreachableNodes(adjList, start, fieldSetting);
                }
                caseCount++;
                System.out.format("Case %d: %d nodes not reachable from "
                        + "node %d with TTL = %d.\n", caseCount,
                        numUnreachableNodes, start, fieldSetting);
                start = fr.nextInt();
                fieldSetting = fr.nextInt();
            }

            NC = fr.nextInt();

        }

    }

    private static int countUnreachableNodes(HashMap<Integer, HashSet<Integer>>
            adjList, int start, int fieldSetting) {

        // Initialisation phase
        HashMap<Integer, Integer> distances = new HashMap<>();
        HashMap<Integer, Integer> predecessors = new HashMap<>();
        for (Integer vertex : adjList.keySet()) {
            distances.put(vertex, 1000000);
            predecessors.put(vertex, -1);
        }
        Queue<Integer> bfsQueue = new LinkedList<>();
        bfsQueue.offer(start);
        distances.put(start, 0);
        int numReachableNodes = 1;

        while (!bfsQueue.isEmpty()) {
            Integer current = bfsQueue.poll();
            if (adjList.containsKey(current)) {
                HashSet<Integer> neighbours = adjList.get(current);       
                if (neighbours.size() > 0) {
                    Iterator<Integer> iterator = neighbours.iterator();
                    while (iterator.hasNext()) {
                        Integer next = iterator.next();
                        if (distances.get(next).intValue() == 1000000) {
                            numReachableNodes++;
                            int distance = distances.get(current) + 1;
                            distances.put(next, distance);
                            predecessors.put(next, current);
                            if (distance < fieldSetting) {
                                bfsQueue.offer(next);
                            }
                        }
                    }    
                }
            }
        }

        return adjList.size() - numReachableNodes;

    }

}

/**
 * Fast I/O
 * @source https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
 */
class FastReader 
{ 
    BufferedReader br; 
    StringTokenizer st; 

    public FastReader() 
    { 
        br = new BufferedReader(new
                InputStreamReader(System.in)); 
    } 

    String next() 
    { 
        while (st == null || !st.hasMoreElements()) 
        { 
            try
            { 
                st = new StringTokenizer(br.readLine()); 
            } 
            catch (IOException  e) 
            { 
                e.printStackTrace(); 
            } 
        } 
        return st.nextToken(); 
    } 

    int nextInt() 
    { 
        return Integer.parseInt(next()); 
    } 

    long nextLong() 
    { 
        return Long.parseLong(next()); 
    } 

    double nextDouble() 
    { 
        return Double.parseDouble(next()); 
    } 

    String nextLine() 
    { 
        String str = ""; 
        try
        { 
            str = br.readLine(); 
        } 
        catch (IOException e) 
        { 
            e.printStackTrace(); 
        } 
        return str; 
    } 
}


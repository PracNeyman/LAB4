package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Vector;

/*
 * @author:
 * @param:
 * @return:
 */

import javax.swing.JFrame;

public class Graph {
    int			     n;
    int			     t;
    public ArrayList<String> words;
    public int[][]	     edges;
//    Map<String, Integer>     mark   = new HashMap<String, Integer>();
    private Vector<int[]>    uvtemp = new Vector<int[]>();	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     // ï¿½ï¿½ï¿½Ú¼ï¿½Â¼dijsktraï¿½ï¿½dist(v)-dist(u)
                                                                     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     // =
                                                                     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     	     // c(u,v)ï¿½Ä±ï¿½
    public int[]	     lengths;

    /*
     * @comment:
     * @param:
     * @return:
     */

    Graph(String[] strings) {
	int now = 0;
	words = new ArrayList<String>();
	for (String s1 : strings) {
//	    if (!mark.containsKey(s1)) {
		if (!words.contains(s1)) {
//		mark.put(new String(s1), new Integer(now++));
		words.add(s1);
	    }
	}
	n = words.size();
	edges = new int[n][n];
	for (int i = 0; i < n; ++i) {
	    for (int j = 0; j < n; ++j) {
		edges[i][j] = 0;
	    }
	}
	String s3 = "";
	int a, b;
	for (String s2 : strings) {
	    if (s3 != "") {
//		++(edges[mark.get(s3)][mark.get(s2)]);
	    	++(edges[words.indexOf(s3)][words.indexOf(s2)]);
	    }
	    s3 = s2;
	}
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    public boolean isTheWordExisted(String word) {
//	return mark.containsKey(word);
    	return words.contains(word);
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    String[] queryBridgeWords(String word1, String word2) {
//	if (!mark.containsKey(word1) || !mark.containsKey(word2))
    if (!words.contains(word1) || !words.contains(word2))
	    return null;
//	int mark1 = mark.get(word1), mark2 = mark.get(word2);
    int mark1 = words.indexOf(word1), mark2 = words.indexOf(word2);
	ArrayList<String> result = new ArrayList<String>();
	int num = 0;
	for (int i = 0; i < n; ++i) {
	    if (edges[mark1][i] != 0 && edges[i][mark2] != 0) {
		result.add(words.get(i));
	    }
	}
	return result.toArray(new String[result.size()]);
    }

    String generateNewText(String inputText) {
	Random rand = new Random();
	inputText = inputText.replaceAll("[^a-z^A-Z]", " ");
	String[] words = inputText.split("\\s+");
	String insert = "";
	String[] BridgeWords;
	for (int i = 0; i < (words.length - 1); ++i) {
	    BridgeWords = queryBridgeWords(words[i], words[i + 1]);
	    if (BridgeWords != null && BridgeWords.length > 0) {
		insert = BridgeWords[rand.nextInt(BridgeWords.length)];
		words[i] += " ";
		words[i] += insert;
	    }
	}
	String result = "";
	for (int i = 0; i < words.length; ++i) {
	    result += words[i];
	    result += " ";
	}
	return result;
    }
    /*
     * @comment:
     * @param:
     * @return:
     */

    public void show(String path, JFrame f) {
	new Drawgraph(words, edges, path, f);
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    public void show_path_all(String[] paths, JFrame f) {
	new DrawpathAll(words, edges, paths, f, lengths);
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    public void showTheGraphByConsole() {
	if (n == 0)
//	    System.out.println("ï¿½ï¿½Ç°ï¿½Ú½Ó±ï¿½Îªï¿½Õ£ï¿½");
		;
	else {
	    /*
	     * for (Map.Entry<String,Integer> e:mark.entrySet()) {
	     * System.out.println(e.getValue()+"ï¿½Åµï¿½ï¿½ï¿½:"+e.getKey());
	     * }
	     */
	    int i = 0;
	    for (String e : words) {
//		System.out.println((i++) + "ï¿½Åµï¿½ï¿½ï¿½:" + e);
	    }
	    for (int j = 0; j < n; j++) {
		for (int k = 0; k < n; k++) {
		    System.out.print(edges[j][k] + " ");
		}
		System.out.println();
	    }
	}
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    public String[] calcShortestPath(String word1, String word2) {
	String[] result = new String[n];
	Vector<String> returnItem = new Vector<String>();
//	if (!mark.containsKey(word1)) {
	if (!words.contains(word1)) {
	    System.out.println("µ±Ç°Í¼ÖÐ²»´æÔÚ" + word1 + "Çë¼ì²éÄãµÄÊäÈë");
	    return null;
	}
	else {
//	    int source = mark.get(word1);
//	    int aim = mark.get(word2);
		int source = words.indexOf(word1);
		int aim = words.indexOf(word2);
	    int[] pathlength = new int[n];
	    int[] path = new int[n];
	    int[][] edgesTemp = new int[n][n];
	    for (int k = 0; k < n; k++) {
		path[k] = -1;
		pathlength[k] = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
		    if (edges[k][i] > 0)
			edgesTemp[k][i] = edges[k][i];
		    else
			edgesTemp[k][i] = Integer.MAX_VALUE;
		}
	    }
	    pathlength[source] = 0;
	    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(n, new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
		    if (pathlength[o1] > pathlength[o2])
			return 1;
		    else if (pathlength[o2] < pathlength[o1])
			return -1;
		    else
			return 0;
		}
	    });
	    for (int i = 0; i < n; i++)
		priorityQueue.add(i);
	    while (!priorityQueue.isEmpty()) {
		int temp = priorityQueue.peek();
		for (int i = 0; i < n; i++)
		    relex(temp, i, edgesTemp, pathlength, path, priorityQueue);
		priorityQueue.poll();
	    }
	    for (int i = 0; i < n; i++) {
		if (path[i] == -1 || i == source)
		    continue;
		else {
		    int temp = i;
		    result[i] = new String(word1);
		    Vector<String> resultTemp = new Vector<String>();
		    while (temp != source) {
			resultTemp.add(words.get(temp));
			temp = path[temp];
		    }
		    for (int j = resultTemp.size() - 1; j >= 0; j--) {
			result[i] += " " + resultTemp.get(j);
		    }
		}
	    }
	    returnItem.add(result[aim]);
	    String[] spiltItem = result[aim].split("\\s+");
	    HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
	    for (int i = 0; i < spiltItem.length; i++) {
		hashMap.put(spiltItem[i], i);
	    }
	    for (int[] e : uvtemp) {
		if (hashMap.containsKey(words.get(e[1]))) {
		    System.out.println(words.get(e[0]) + "->" + words.get(e[1]));
		    String newPath = result[e[0]];
		    int flag = 0;
		    for (int i = 0; i < spiltItem.length; i++) {
			if (flag == 1 || spiltItem[i].equals(words.get(e[1]))) {
			    flag = 1;
			    newPath += " " + spiltItem[i];
			}
		    }
		    returnItem.addElement(newPath);
		}
	    }
	    String[] results = new String[returnItem.size()];
	    for (int i = 0; i < returnItem.size(); i++)
		results[i] = new String(returnItem.get(i));
	    lengths = new int[1];
	    lengths[0] = pathlength[aim];
	    uvtemp.clear();
	    return results;
	}
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    public String[] calcShortestPathOfAll(String word1) {
	String[] result = new String[n];
//	if (!mark.containsKey(word1))
	if (!words.contains(word1))
	    System.out.println("µ±Ç°Í¼ÖÐ²»´æÔÚ" + word1 + "Çë¼ì²éÄãµÄÊäÈë");
	else {
//	    int source = mark.get(word1);
		int source = words.indexOf(word1);
	    int[] pathlength = new int[n];
	    int[] path = new int[n];
	    int[][] edgesTemp = new int[n][n];
	    for (int k = 0; k < n; k++) {
		path[k] = -1;
		pathlength[k] = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
		    if (edges[k][i] > 0)
			edgesTemp[k][i] = edges[k][i];
		    else
			edgesTemp[k][i] = Integer.MAX_VALUE;
		}
	    }
	    pathlength[source] = 0;
	    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(n, new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
		    if (pathlength[o1] > pathlength[o2])
			return 1;
		    else if (pathlength[o2] < pathlength[o1])
			return -1;
		    else
			return 0;
		}
	    });
	    for (int i = 0; i < n; i++)
		priorityQueue.add(i);
	    while (!priorityQueue.isEmpty()) {
		int temp = priorityQueue.peek();
		for (int i = 0; i < n; i++)
		    relex(temp, i, edgesTemp, pathlength, path, priorityQueue);
		priorityQueue.poll();
	    }
	    for (int i = 0; i < n; i++) {
		if (path[i] == -1 || i == source)
		    continue;
		else {
		    int temp = i;
		    result[i] = new String(word1);
		    Vector<String> resultTemp = new Vector<String>();
		    while (temp != source) {
			resultTemp.add(words.get(temp));
			temp = path[temp];
		    }
		    for (int j = resultTemp.size() - 1; j >= 0; j--) {
			result[i] += " " + resultTemp.get(j);
		    }
		}
	    }
	    lengths = new int[n];
	    for (int i = 0; i < n; i++)
		lengths[i] = pathlength[i];
	}
	return result;
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    public String randomWalk() {
	if (n == 0)
	    return null;
	int rand = (int) (Math.random() * n);
	boolean[][] isEdgeVisited = new boolean[n][n];
	for (int i = 0; i < n; i++)
	    for (int j = 0; j < n; j++)
		if (edges[i][j] == 0)
		    isEdgeVisited[i][j] = false;
		else
		    isEdgeVisited[i][j] = true;
	return words.get(rand) + " " + DFSByEdge(rand, isEdgeVisited);
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    private String DFSByEdge(int sourceNode, boolean[][] isEdgeVisited) {
	Vector<Integer> chooseEnable = new Vector<Integer>();
	for (int i = 0; i < n; i++)
	    if (isEdgeVisited[sourceNode][i] == true)
		chooseEnable.add(i);
	if (chooseEnable.isEmpty())
	    return "";
	int length = chooseEnable.size();
	int aimNode = (int) (Math.random() * length);
	isEdgeVisited[sourceNode][chooseEnable.get(aimNode)] = false;
	return words.get(chooseEnable.get(aimNode)) + " " + DFSByEdge(chooseEnable.get(aimNode), isEdgeVisited);
    }

    /*
     * @comment:
     * @param:
     * @return:
     */

    private void relex(int u, int v, int[][] edges, int[] pathlength, int[] path,
            PriorityQueue<Integer> priorityQueue) {
	if (pathlength[v] > pathlength[u] + (long) edges[u][v]) {
	    pathlength[v] = pathlength[u] + edges[u][v];
	    priorityQueue.remove(v);
	    priorityQueue.add(v);
	    path[v] = u;
	}
	else if ((pathlength[v] == pathlength[u] + (long) edges[u][v]) && pathlength[v] != Integer.MAX_VALUE
	        && pathlength[u] != Integer.MAX_VALUE && edges[u][v] != Integer.MAX_VALUE) {
	    System.out.println(pathlength[v] + "!!!!" + (pathlength[u] + (long) edges[u][v]));
	    int[] t = new int[2];
	    t[0] = u;
	    t[1] = v;
	    uvtemp.add(t);
	}
    }

}

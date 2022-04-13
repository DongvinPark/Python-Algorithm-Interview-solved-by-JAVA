//교재 324쪽과 326쪽에서 파이썬으로 구현하여 설명하고 있는 DFS, BFS 구현을 자바로 다시 구현해 봤습니다.
// 2021 맥북에에 인텔리제이 2021.3 버전에서 실행해본 코드입니다.
//코드의 작동을 추가하기 위해 삽입한 System.out.print 관련 구문들이 중간중간에 포함돼 있습니다.
//패키지 정보는 각자 컴퓨터 상황에 맞게 수정하시면 될 것 같습니다 :)
package com.company;

import java.util.*;


public class Main {
    public static void main(String[] args) {

        ArrayList< ArrayList<Integer> > graph = new ArrayList<>();
        graph.add(0, new ArrayList<>());
        graph.add(1, new ArrayList<>(Arrays.asList(2,3,4)));
        graph.add(2, new ArrayList<>(Arrays.asList(5)));
        graph.add(3, new ArrayList<>(Arrays.asList(5)));
        graph.add(4, new ArrayList<>());
        graph.add(5, new ArrayList<>(Arrays.asList(6,7)));
        graph.add(6, new ArrayList<>());
        graph.add(7, new ArrayList<>(Arrays.asList(3)));

        ArrayList<Integer> watched = new ArrayList<>();

        ArrayList<Integer> answer = recursiveDfs(1, watched, graph);
        ArrayList<Integer> answer2 = iterativeDfs(1, graph);
        ArrayList<Integer> answer3 = iterativeBfs(1, graph);
        System.out.println("DFS reculsive : " + answer);
        System.out.println("DFS iterative : " + answer2);
        System.out.println("BFS iterative : " + answer3);

    }//main

    //DFS Reculsive Searching Algorithm
    public static ArrayList<Integer> recursiveDfs(int v, ArrayList<Integer> discovered, ArrayList< ArrayList<Integer> > graph){
        discovered.add(v);
        for(int w : graph.get(v)){
            if(!discovered.contains(w)){
                discovered = recursiveDfs(w, discovered, graph);
            }
        }//for
        return discovered;
    }//func


    //DFS Iterative Stack Algorithm
    public static ArrayList<Integer> iterativeDfs(int start_v, ArrayList< ArrayList<Integer> > graph){
        ArrayList<Integer> discovered = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(start_v);
        while(!stack.isEmpty()){
            //System.out.println(stack);
            int v = stack.pop();
            if(!discovered.contains(v)){
                discovered.add(v);
                for(int w : graph.get(v)){
                    stack.push(w);
                }//for
            }
        }//wh

        return discovered;
    }//func


    //BFS iterative Queue Algorithm
    public static ArrayList<Integer> iterativeBfs(int start_v, ArrayList< ArrayList<Integer> > graph){
        ArrayList<Integer> discovered = new ArrayList<>();
        discovered.add(start_v);
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.push(start_v);
            while(!queue.isEmpty()){
                    System.out.println("\n"+queue);
                int v = queue.pop();
                    System.out.println("\t\t" + v + " : popped");
                for(int w : graph.get(v)){
                    if(!discovered.contains(w)){
                            System.out.println("\tif enter");
                        discovered.add(w);
                            System.out.println("\t\tdiscovered : " + discovered);
                        queue.add(w);
                    }
                }//for
            }//wh
        return discovered;
    }//func


}//main class

package com.project.stackoverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StackoverflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(StackoverflowApplication.class, args);
	}

}

//Direected graph
// No cycle
// one player has dependency only on 1 other player
// {(A, B), (C,D), (B,C)}

// B comes after A
// A -> B
// C -> D
// B -> C

// sort it in an order
// topo sort : O(V+E)

// map : A:B, c:D
// A b c d

//dfs(node, st, vis, graph, recStack){
//	if(recStack[node]==true){
//		return true;
//	}
//
//	if(vis[node]== true)
//		return false;
//
//	vis[node]=true;
//	recStack[node]=true;
//
//	for(int : g.get(node)){
//		if(dfs()){
//			true;
//		}
//	}
//	recStack[node]=false;
//	st.push(node);
//
//	return false;
//}
//
//st--> ordering
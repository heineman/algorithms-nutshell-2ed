/**
 * @file mst.cxx    Prim's Algorithm for Minimum Spanning Tree problem
 * @brief 
 *
 *   Defines the implementation using Prim's Algorithm to minimum spanning
 *   tree problem. Intentional slow implementation to show weakness of 
 *   using native priority queue.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <iostream>
#include <queue>

#include "Graph.h"

class Entry {
  public: 
    Entry(int _id, int _key) : id(_id), key(_key) { }
    bool operator<(const Entry&) const;
    bool operator==(const Entry&) const;
    int id, key;
};

bool Entry::operator<(const Entry& right) const
{
  return key > right.key;
}

bool Entry::operator==(const Entry& right) const
{
  return id == right.id;
}


/** Useful method for Debugging. */
void debug (int n, vector<int> key, vector<int> pred) {
  printf ("n  key  pred\n");
  for (unsigned int i = 0; i < (unsigned int) n; i++) {
    cout << i << ". " << key[i] << "  " << pred[i] << endl;
  }
  cout << "--------" << endl;
}

/**
 * Given undirected graph, compute MST starting from a randomly 
 * selected vertex. Encoding of MST is done using 'pred' entries.
 * \param graph    the undirected graph
 * \param pred     pred[] array to contain previous information for MST.
 */
void mst_prim (Graph const &graph, vector<int> &pred) {

  // initialize pred[] and key[] arrays. Start with arbitrary 
  // vertex s=0. Priority Queue PQ contains all v in G.
  const int n = graph.numVertices();
  pred.assign(n, -1);

  // Create Initial Heap
  vector<Entry> pq;
  pq.push_back(Entry(0, 0));
  for (int v = 1; v < n; v++) {
    pq.push_back(Entry(v, numeric_limits<int>::max()));
  }
  make_heap (pq.begin(), pq.end());

  while (!pq.empty()) {
    int u = pq.front().id;
    pq.erase(remove(pq.begin(), pq.end(), pq.front()), pq.end());

    // Process neighbors of u to find if any edge beats best 
    for (VertexList::const_iterator ci = graph.begin(u);
	 ci != graph.end(u); ++ci) {
      int v = ci->first;
      int w = ci->second;

      // If neighbor is in queue, may have to adjust priority
      for (vector<Entry>::iterator ei = pq.begin();
	   ei != pq.end(); ++ei) {
	if (ei->id == v && w < ei->key) {
	  pred[v] = u;
	  ei->key = w;
	  make_heap (pq.begin(), pq.end());
	}
      }
    }
  }
}

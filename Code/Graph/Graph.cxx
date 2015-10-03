/**
 * @file Graph.cxx   Implementation of Graph concept
 * @brief 
 *   Contains the implementation of the Graph class.
 *
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <string.h>
#include "Graph.h"


/**
 * Load graph from file.
 *  
 * File contains:
 *     <pre>
 *     Header
 *     V E
 *     v1,v2
 *     v1,v5
 *     ...
 *     </pre>
 * 
 * \param fileName    contains the graph information.
 */
void Graph::load (char *fileName) {
  FILE *fp = fopen (fileName, "r");
  int nv, ne;

  // sufficient space? we hope so!
  char buf[4096];

  // read header as a whole line.
  fgets (buf, 4096, fp);
  
  // read n and e
  int nr = fscanf (fp, "%d %d %s\n", &nv, &ne, buf);
  if (nr != 3) {
    printf ("Invalid file format %s\n", fileName);
    n_=0;
    delete [] vertices_;
    return;
  }

  if (!strcmp (buf, "directed")) {
    directed_ = true;
  } else {
    directed_ = false;
  }

  vertices_ = new VertexList [nv];
  n_ = nv;
  
  // since graph is known to be directed or undirected, we only need
  // to call addEdge once, since the implementation will do the right
  // thing.
  while (ne-- > 0) {
    int src, tgt, weight;

    int nr = fscanf (fp, "%d,%d,%d\n", &src, &tgt, &weight);
    if (nr == 2) {
      addEdge (src, tgt);
    } else if (nr == 3) {
      addEdge (src, tgt, weight);
    }
  }

  fclose(fp);
}


/**
 * Does the edge (u,v) exist in the graph? 
 * \param u   integer identifier of a vertex
 * \param v   integer identifier of a vertex
 * \return true if edge exists, otherwise false
 */
bool Graph::isEdge (int u, int v) const {
  for (VertexList::const_iterator ei = vertices_[u].begin();
       ei != vertices_[u].end(); 
       ++ei) {
    if (ei->first == v) {
      return true;
    }
  }

  return false;
}

/** 
 * Does the edge (u,v) exist in the graph? And if so, what is its weight? 
 * \param u   integer identifier of a vertex
 * \param v   integer identifier of a vertex
 * \param w   returned weight of the edge, should it exist
 * \return true if edge exists (and w contains meaningful information), otherwise false (and w is not meaningful).
 */
bool Graph::isEdge (int u, int v, int &w) const {
  for (VertexList::const_iterator ei = vertices_[u].begin();
       ei != vertices_[u].end(); 
       ++ei) {
    if (ei->first == v) {
      w = ei->second;
      return true;
    }
  }

  return false;
}

/**
 * Add edge to graph structure from (u,v).
 * If the graph is undirected, then we must add in reverse as well.
 * It is up to user to ensure that no edge already exists. The check
 * will not be performed here.
 * 
 * \param u   integer identifier of a vertex
 * \param v   integer identifier of a vertex
 * \param w   planned weight.
 */
void Graph::addEdge (int u, int v, int w) {
  if (u > n_ || v > n_) {
    throw "Graph::addEdge given vertex larger than graph size";
  }

  pair<int,int> edge(v,w);
  vertices_[u].push_front(edge);

    // undirected have both.
  if (!directed_) {
    pair<int, int> edgeR (u,w);
    vertices_[v].push_front(edgeR);
  }
}

/**
 * Remove edge, and if undirected, remove its opposite as well. 
 * \param u   integer identifier of a vertex
 * \param v   integer identifier of a vertex
 * \return true if edge was removed, false otherwise
 */
bool Graph::removeEdge (int u, int v) {

    bool found = false;
    for (VertexList::const_iterator ei = vertices_[u].begin();
	 ei != vertices_[u].end(); 
	 ++ei) {
      if (ei->first == v) {
	vertices_[u].remove(*ei);
	found = true;
	break;
      }
    }
    
    // not found at all? return
    if (!found) return false;

    if (!directed_) {
      for (VertexList::const_iterator ei = vertices_[v].begin();
	   ei != vertices_[v].end(); 
	   ++ei) {
	if (ei->first == u) {
	  vertices_[v].remove(*ei);
	  break;
	}
      }
    }
    
    return true;
  }

/** 
 * Return edge weight, or INT_MIN if not present. 
 * \param u   integer identifier of a vertex
 * \param v   integer identifier of a vertex
 * \return INT_MIN if edge doesn't exist, otherwise return its weight.
 */
int Graph::edgeWeight (int u, int v) const {
  for (VertexList::const_iterator ei = vertices_[u].begin();
       ei != vertices_[u].end(); 
       ++ei) {
    
    if (ei->first == v) {
      return ei->second;
    }
  }
  return numeric_limits<int>::min();
}


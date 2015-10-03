#ifndef _HELPER_H_
#define _HELPER_H_

extern vector<int> trace;
extern int traceIdx;
extern int countBlackNodes;

// output 's' or 't' or index.
extern void outV(int i);

// generate the data as a table.
extern void generateFigure(Graph const &graph,                   /* in */
	 vector<int> const &d, vector<int> const &f,                 /* in */
	 vector<int> const &pred, vector<vertexColor> const &color   /* in */
);

#endif /*  _HELPER_H_ */

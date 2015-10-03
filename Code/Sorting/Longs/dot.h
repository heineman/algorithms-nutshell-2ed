/**
 * @file dot.h    Define interface for accessing API to generate sorting DOT graphs.
 * @brief
 *    Enable DOT graphs (http://www.graphviz.org) to be generatd from the
 *    invocation of specially crafted sort routines. This code is used to
 *    show the progress of MedianSort and QuickSort in the book.
 *
 * @author George Heineman
 * @date 6/15/08
 */

/** Determines a vertex is to be colored gray. */
#define GRAY  1

/** Determines a vertex is to be colored black with white font. */
#define BLACK 2

/**
 * Core data structure to store all formatting information for the individual
 * vertices for the graph being constructed.
 */
typedef struct df {
  /** Identifier of the vertex. */
  int id;   

  /** 0=sentinel, 1=gray, 2=black+whiteFont */
  int type;

  /** Next format information. */
  struct df   *next;
} DOT_FORMAT, *DOT_FORMAT_PTR;


/** Output the header of the graph. */
void dot_header(char *s);

/** Output the trailer of the graph. */
void dot_trailer();

/** Free associated memory for the formatting information. */
void dot_release (DOT_FORMAT_PTR df);

/** Construct a format node for the given vertex identifier and type information. */
DOT_FORMAT_PTR dot_format_type(int id, int type);

/** Construct a raw format node. */
DOT_FORMAT_PTR dot_format_list();

/** Process format node and output bgcolor information for <TD> tag as appropriate. */
int dot_format_td (int i, DOT_FORMAT_PTR fp);

/** Process format node and output font color information for <TD> tag as appropriate. */
int dot_format_font (int i, DOT_FORMAT_PTR fp);

/** Add the designated format information n to the end of the information pointed to by fp. */
void dot_add_format (DOT_FORMAT_PTR fp, DOT_FORMAT_PTR n);

/** Output the node identified by unique identifier 'id'. */
void dot_nodeid(int id);

/** Output a sub-array of ar[left,right] to appear as node 'id' using the designated format information. This is a powerful API function. */
void dot_node(long *ar, int id, int left, int right, DOT_FORMAT_PTR fmt);

/** Add to the graph a directed edge of the form id -> id2. */
void dot_add_edge (int id, int id2);

/** Add to the graph an undirected edge of the form id -- id2. */
void dot_add_undir_edge (int id, int id2);

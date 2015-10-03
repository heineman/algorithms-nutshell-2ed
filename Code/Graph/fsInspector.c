/**
 * @file fsInspector.c   Driver for generating a graph by interpreting the file system as a graph.
 * @brief 
 *   Traverse the file system from a particular root directory and construct a graph whose vertices are files and directories and whose edges are containment (within a file) or symbol links (as defined by unix).
 *   
 * @author George Heineman
 * @date 6/15/08
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>

// MAC-os differences
#ifdef __MACH__
#include <sys/syslimits.h>
#else
#include <sys/statfs.h>
#endif
#include <sys/stat.h>

#include <errno.h>

/**
 * A NODELIST is a linked list of (node, id) pairs where the node is
 * a string associated with the node, and is an integer identifier.
 */
typedef struct nl {
  char       *node;
  int        id;
  struct nl  *next;

} NODELIST, *NODELIST_PTR;

/** The headVertex of a list containing all visited files. */
NODELIST_PTR headVertex = NULL;


/**
 * An EDGELIST is a linked list of (src,tgt) pairs where the src is a
 * string associated with a file entry and tgt is a string associated with 
 * another file entry. The edge could either be containment or a symbolic
 * link.
 */
typedef struct el {
  char    *src;
  char    *tgt;

  struct el  *next;
} EDGELIST, *EDGELIST_PTR;

/** The headEdge of a list containing all known edges. */
EDGELIST_PTR headEdge = NULL;

/** Number of vertices discovered. */
static int numVertices = 0;

/** Number of edges discovered. */
static int numEdges = 0;

/** 
 * Locate a vertex by name or return NULL if not found. 
 *
 * \param n   desired vertex
 * \return    NODELIST_PTR in the list or NULL if not found.
 */
NODELIST_PTR findVertex (char *n) {
  NODELIST_PTR  p;
  if (headVertex == NULL) { return NULL; }
  
  p = headVertex;
  while (p != NULL) {
    if (!strcmp (p->node, n)) { return p; }

    p = p->next;
  }

  return NULL;
}

/**
 * Record the following string as a vertex. Checks first to see if it
 * has already been discovered, otherwise it is added anew to the headVertex
 * list.
 * \param   n   the string identifier to be recorded (and searched for).
 */
void recordNode (char *n) {
  NODELIST_PTR  p = findVertex (n);
  if (p != NULL) { 
    return; 
  }

  p = (NODELIST_PTR) calloc (1, sizeof(NODELIST));
  p->node = strdup (n);
  p->id   = numVertices;
  p->next = headVertex;

  headVertex = p;
  numVertices++;
}

/** Edges are always added anew. */
void recordEdge (char *u, char *v) {
  EDGELIST_PTR  p;

  numEdges++;
  p = (EDGELIST_PTR) calloc (1, sizeof(EDGELIST));
  p->src = strdup (u);
  p->tgt = strdup (v);
  p->next = headEdge;
  headEdge = p;
}

/**
 *  Process the given directory 'level' levels. 
 *
 * \param level   number of levels to probe
 * \param file    the top-level directory into which to probe.
 */
void process (int level, char *file) {
  DIR *dir;
  struct dirent *dp;

  /* hope for the best. */
  char scratch2[PATH_MAX+1];
  char scratch[PATH_MAX+1];
  char buf[PATH_MAX+1];
  char link[PATH_MAX+1];

  if (level == 0) { return; }
  level--;

  dir = opendir (file);
  if (!dir) { return; }  /* *whoops* not sure what to do... */

  while ((dp = readdir (dir)) != NULL) {
    struct stat lstat_info;

    if (!strcmp(".", dp->d_name) || !strcmp("..", dp->d_name)) {
      continue;
    }

    if (file[strlen(file)-1] == '/') {
      sprintf (buf, "%s%s", file, dp->d_name);
    } else {
      sprintf (buf, "%s/%s", file, dp->d_name);
    }

    recordNode (buf);
    recordEdge (file, buf);

    /* see if symbolic link */
    if (!lstat(buf, &lstat_info)) {
      if (S_ISLNK(lstat_info.st_mode)) {
	char *s;
	bzero (link, PATH_MAX+1);
	readlink(buf, link, lstat_info.st_size);

	/* these links may be relative. DEAL WITH IT by converting to 
	 * absolute path names */
	if (link[0] == '/') {
	  strcpy (scratch2, link);
	} else {
	  if (file[strlen(file)-1] == '/') {
	    sprintf(scratch2, "%s%s", file, link);
	  } else {
	    sprintf(scratch2, "%s/%s", file, link);
	  }
	}

	s = realpath(scratch2, scratch);
	if (errno == 0) {
	  recordNode (s);
	  recordEdge (buf, s);
	} else if (errno == ENOENT) {
	  /* skip */
	} else if (errno == EACCES) {
	  /* no access? Skip */
	} else if (errno == EINVAL) {
	  /* invalid. Likely cause are unexpected file types (consider /proc) */
	} else {
	  printf ("Unable to process %s [%d] %s %s\n", scratch2, errno, link, buf);
	  exit (-1);
	}
      }

      if (S_ISDIR(lstat_info.st_mode)) {
	process (level,buf);
      }
    }
  }

  closedir(dir);

}

/**
 * Application that loads up the file system, starting from '/' and creates
 * a graph based upon the top 5 levels found.
 */
int main (int argc, char **argv) {
  NODELIST_PTR p;
  EDGELIST_PTR q;
  char *last;
  int numLevel;

  if (argc == 1) {
    last = strdup("/");
  } else {
    last = argv[1];
  }

  if (argc >2 ) {
    numLevel = atoi(argv[2]);
  } else {
    numLevel = 2;
  }

  recordNode (last);
  process (numLevel, last);

  printf ("Vertices Edges\n");
  printf ("%d %d\n", numVertices, numEdges);

  p = headVertex;
  while (p != NULL) {
    printf ("%d. %s\n", p->id, p->node);
    p = p->next;
  }

  q = headEdge;
  while (q != NULL) {
    NODELIST_PTR u = findVertex (q->src);
    NODELIST_PTR v = findVertex (q->tgt);
    printf ("%d,%d\n", u->id, v->id);
    q = q->next;
  }

  return 0;
}

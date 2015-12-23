"""
    R Tree Implementation.
    
    Represents two-dimensional rectangles using Region class, but this 
    can be extended to higher dimensions. Two-dimensional code is easier
    to understand.
    
    Every leaf node contains the real data. Instead of computing depth
    from root, we consider height from leaves, where leaf level is zero.
    
    There is no prohibition against duplicate rectangular regions.
    
    Comments in this file are derived from original paper:
    
      R-Trees: Dynamic index structure for spatial searching [Guttman, 1984], 
      Proceedings, 1984 ACM SIGMOD International Conference on Management of Data 
      http://dl.acm.org/citation.cfm?id=602266. 
    
"""

def unionArea(region, other):
    """Return area of union of two rectangular regions."""
    w = max(region.x_max, other.x_max) - min(region.x_min, other.x_min)
    h = max(region.y_max, other.y_max) - min(region.y_min, other.y_min)
    return w*h

class RNode:

    # Monotonically incrementing counter to generate identifiers for non-leaf nodes
    counter = 0

    def __init__(self, M, rectangle = None, ident = None, level = 0):
        """Create RNode object with properties."""
        if rectangle:
            self.region = rectangle.copy()
        else:
            self.region = None
        
        if ident is None:
            RNode.counter += 1
            self.id       = 'R' + str(RNode.counter)
        else:
            self.id       = ident
            
        self.children     = [None] * M
        self.level        = level
        self.count        = 0

    def clear(self, M):
        """Reset node entries."""
        self.region   = None
        self.children = [None] * M
        self.count    = 0

    def size(self):
        """Return number of child leaf nodes. Helper for debugging and test cases."""
        if self.level == 0:
            return self.count
        else:
            ct = 0
            for idx in range(self.count):
                ct += self.children[idx].size()
            return ct

    def search(self, target):
        """Return (rectangle,id) if node contains target rectangle."""
        if self.level == 0:
            for idx in range(self.count):
                if target == self.children[idx].region:
                    return (self.children[idx].region, self.children[idx].id)
        elif self.region.containsRegion(target):
            for idx in range(self.count):
                if self.children[idx].region.containsRegion(target):
                    rc = self.children[idx].search(target)
                    if rc:
                        return rc
        return None

    def range(self, target):
        """Return generator (node,0,True) or (rect,id,False) of all qualifying identifiers overlapping target."""
        
        # Wholly contained for all interior nodes? Return entire node
        if target.containsRegion(self.region):
            yield (self, 0, True)
        else:
            # check leaves and recurse
            if self.level == 0:
                for idx in range(self.count):
                    if target.overlaps (self.children[idx].region):
                        yield (self.children[idx].region, self.children[idx].id, False)
            else:    
                for idx in range(self.count):
                    if self.children[idx].region.overlaps (target):
                        for triple in self.children[idx].range(target):
                            yield triple

    def adjustRegion(self):
        """Adjust region using existing children."""
        if self.count > 0:
            adjusted = self.children[0].region
            for idx in range(1,self.count):
                adjusted = adjusted.unionRect(self.children[idx].region)
            self.region = adjusted
        else:
            self.region = None

    def addRNode(self, rNode):
        """Add previously computed RNode and adjust bounding region."""
        self.children[self.count] = rNode
        self.count += 1
        
        if self.region is None:
            self.region = rNode.region.copy()
        else:
            rectangle = rNode.region
            if rectangle.x_min < self.region.x_min: 
                self.region.x_min = rectangle.x_min
            if rectangle.x_max > self.region.x_max: 
                self.region.x_max = rectangle.x_max
            if rectangle.y_min < self.region.y_min: 
                self.region.y_min = rectangle.y_min
            if rectangle.y_max > self.region.y_max: 
                self.region.y_max = rectangle.y_max
                
    def removeRNode(self, node):
        """Remove given RNode entry."""
        if self.count == 0:
            return
    
        for idx in range(self.count):
            if self.children[idx] == node:
                self.children[idx] = self.children[self.count-1]
                self.children[self.count-1] = None
                self.count -= 1
                return
    
    def removeEntry(self, idx):
        """Remove given entry but do not adjust bounding box."""
        if self.count > 0:
            self.children[idx] = self.children[self.count-1]
            self.children[self.count-1] = None
            self.count -= 1

    def addEntry(self, M, rectangle, ident):
        """Add entry to node and adjust bounding rectangle or set it for first time."""
        self.addRNode(RNode(M, rectangle, ident, self.level))
        
    def pickSeeds(self, M, node, newNode):
        """
        Given M+1 possible, select two entries to start, one in each of self and newNode, and
        return remaining entry RNodes. All entries are cleared from self and node. The selected
        two entries are chosen to maximize performance.
        """
        entries = [e for e in self.children if e != None]
        entries.append(node)
        self.clear(M)
       
        # PS1 [Calculate inefficiency of grouping entries together] For each pair of entries
        # E1 and E2 compose a rectangle J include E1I and E2I. Calculate d=area(J) - area(E1I)-area(E2I).
        maxArea = 0
        chosen = None
        for i1 in range(len(entries)-1):
            area1 = entries[i1].region.area()
            for i2 in range(i1+1, len(entries)):
                area2 = entries[i2].region.area()
                d = unionArea(entries[i1].region, entries[i2].region) - area1 - area2
                if chosen:
                    if d > maxArea:
                        maxArea = d
                        chosen = (i1,i2)
                else:
                    maxArea = d
                    chosen = (i1,i2)
        
        # PS2 [Choose the most wasteful pair]. Choose the pair with largest D
        self.addRNode(entries[chosen[0]])
        newNode.addRNode(entries[chosen[1]])

        del entries[max(chosen)]
        del entries[min(chosen)]
        return entries

    def chooseLeaf(self, rectangle, path):
        """Choose leaf into which to add entry and return path from root."""
        # CL2 [Leaf check] If node is a leaf, return N
        if self.level == 0: 
            return path
        
        # CL3 [Choose subtree] If node is not a leaf, let F be the entry in node
        # whose rectangle FI needs least enlargement to include EI. Resolve
        # ties by choosing the entry with the rectangle of smallest area
        choice = None
        leastArea = -1
        for entry in self.children:
            if not entry:
                break
            
            area = unionArea(entry.region, rectangle)
            if choice is None or area < leastArea:
                leastArea = area
                choice = entry
            elif area == leastArea and choice.region.area() > entry.region.area():
                choice = entry
                
        # CL4 [Descend until a leaf is reached] Set N to be the child node
        # pointed to by Fp and repeat from CL2
        path.append(choice)
        return choice.chooseLeaf(rectangle, path);

    def findLeaf(self, rectangle, path):
        """Find leaf which contains rectangle and return path from root."""
        if self.level == 0: 
            for idx in range(self.count):
                if rectangle == self.children[idx].region:
                    return path + [self.children[idx]]
        elif self.region.containsRegion(rectangle):
            for idx in range(self.count):
                if self.children[idx].region.containsRegion(rectangle):
                    extended = path + [self.children[idx]]
                    result = self.children[idx].findLeaf(rectangle, extended) 
                    if result:
                        return result
                                                     
        return None

    def pickNext(self, newNode, remaining):
        """Return next entry to be added, updating remaining in process."""
        
        # PN1 [Determine cost of putting each entry in each group] For each
        # entry not yet in a group, calculate the area increase required
        # in the covering rectangles of each group
        entry = remaining[0]
        del remaining[0]
        
        d1 = unionArea(self.region, entry.region)
        d2 = unionArea(newNode.region, entry.region)
            
        # PN2 [Find entry with greatest preference 
        # for one group] Choose any entry with the 
        # maximum difference between d1 and d2
        if d1 < d2:
            self.addRNode(entry)
        elif d2 < d1:
            newNode.addRNode(entry)
        else:
            selfArea = self.region.area()
            newArea = newNode.region.area()
            if selfArea < newArea:
                self.addRNode(entry)
            elif newArea < selfArea:
                newNode.addRNode(entry)
            elif self.count < newNode.count:
                self.addRNode(entry)
            else:
                newNode.addRNode(entry)                

    def split(self, node, m, M):
        """
        Split node with M entries and return new node that has (M+1)/2 entries while also adjusting this
        entries. Node causing split, node, may have entries as well. The returned newNode will be a sibling
        to self.
        """
        # QS1 [Pick first entry for each group] Apply algorithm pickSeeds to
        # choose two entries to be the first elements of the groups. Assign
        # each to a group.

        # After pickSeeds, newNode has one entry
        newNode = RNode(M, level = self.level)
        remaining = self.pickSeeds(M, node, newNode)

        # QS2 [Check if done] If all entries have been assigned, stop. If one
        # group has so few entries that all the rest must be assigned to it in
        # order for it to have the minimum number m, assign them and stop.
        while self.count + newNode.count < M + 1:
            if self.count + len(remaining) <= m:
                # Add all back to self
                for n in remaining:
                    self.addRNode(n)
                break
            
            if newNode.count + len(remaining) <= m:
                # Add all into newNode
                for n in remaining:
                    newNode.addRNode(n)
                break
                    
            # QS3 [Select entry to assign] Invoke algorithm PickNext to
            # choose the next entry to assign. Add it to the group whose
            # covering rectangle will have to be enlarged least to 
            # accommodate it. Resolve ties by adding the entry to the group
            # with smaller area, then to the one with fewer entries, then 
            # to either. Repeat from QS2
            self.pickNext(newNode, remaining)

        return newNode
    
    def condenseTree(self, path, m, M):
        """
        Given a leaf node n from which an entry has been deleted, and path to root, 
        eliminate the node if it has too few entries and relocate its entries. 
        Returns list of orphaned nodes (may have multiple entries) to be reinserted
        in order of the way that they should be processed.
        """
        # CT1 [Initialize] Set Q, the set of eliminated nodes, to be empty
        n = self
        Q = []
        last = None
        
        # CT2 [Find parent entry.] If n is the root, go to CT6. otherwise
        # Let P be parent of n and En be N's entry in P
        while len(path) != 0:
            parent = path[-1]
            del path[-1]
            
            # CT3 [Eliminate under-full node] If n has too few entries,
            # delete En from parent and add node to the list of eliminated nodes
            if n.count < m:
                parent.removeRNode(n)
                Q.insert(0,n)
            else:
                # CT4 [Adjust covering rectangle] If node has not been eliminated,
                # adjust EnI to tightly contain all entries in node. Once you start
                # adjusting regions, you are good for rest of way since all have < M
                n.adjustRegion()
                if last is None:
                    last = n
                    
            # CT5 [Move up one level in tree]
            n = parent
        
        return (last, Q)
            
    def leafOrder(self):
        """Returns only leaf nodes (rect,id) which hold the actual regions."""
        if self.level == 0:
            for idx in range(self.count):
                yield (self.children[idx].region, self.children[idx].id)
        else:
            for idx in range(self.count):
                for pair in self.children[idx].leafOrder():
                    yield pair
    
    def __str__(self):
        """Useful debugging function to produce linear tree representation."""
        rep = ""
        for idx in range(self.count):
            rep = "{} {}".format(rep, self.children[idx])
        if self.region:
            regRep = self.region
        else:
            regRep = ""
        return "(" + str(self.id) + ":" + str(regRep) + ": " + rep + ")"
    
class RTree:
    """
    m represents minimum number of regions per node, M represents maximum. Also m <= M/2.
    """
    
    def __init__(self, m=2, M=4):
        """Create empty R tree with (m=2, M=4) default values."""
        self.root = None
        self.m = m
        self.M = M

    def search(self, rectangle):
        """Determine whether R Tree contains rectangle, returning (rectangle,ident) if found or None."""
        if self.root:
            return self.root.search(rectangle)
        return None

    def add(self, rectangle, ident = None):
        """Insert rectangle into proper location with given (optional) identifier."""
        if self.root is None:
            self.root = RNode(self.M, rectangle, None)
            self.root.addEntry (self.M, rectangle, ident)
        else:
            # I1 [Find position for new record] Invoke ChooseLeaf to select a
            # leaf node n in which to place R. Path to leaf returned
            path = self.root.chooseLeaf (rectangle, [self.root]);
            n = path[-1]
            del path[-1]
            
            # I2 [Add record to leaf node] If n has room for another entry,
            # install R. Otherwise invoke SplitNode to obtain n and newLeaf 
            # containing R and all the old entries of n
            newLeaf = None
            if n.count < self.M:
                n.addEntry (self.M, rectangle, ident)
            else:
                newLeaf = n.split (RNode(self.M, rectangle, ident, 0), self.m, self.M)

            # I3 [Propagate changes upwards] Invoke AdjustTree on n, also 
            # passing newLeaf if a split was performed
            newNode = self.adjustTree (n, newLeaf, path)
                
            # I4 [Grow tree taller] If node split propagation caused the root to
            # split, create a new root whose children are the two resulting nodes.
            if newNode:
                newRoot = RNode(self.M, level = newNode.level + 1)
                newRoot.addRNode (newNode)
                newRoot.addRNode (self.root)
                self.root = newRoot

    def adjustTree(self, n, sibling, path):
        """
        Adjust tree (mostly boundaries) in response to split of leaf node 'n' which produced a 
        sibling new node 'nn'. If new root is to be created for entire tree, return that, otherwise return None.
        """
        # AT1 [Initialize] Set N=L. If L was split previously, set NN to be
        # the resulting second node.
        
        # If at root, then return sibling and let caller set the new root for the tree.
        if len(path) == 0:
            return sibling

        # AT2 [Check if done] If N is the root, stop
        while n != self.root:
            parent = path[-1]
            del path[-1]
            
            # AT3 [Adjust covering rectangle in parent entry] Let P be the parent
            # node of N, and let En be N's entry in P. Adjust EnI so that it tightly
            # encloses all entry rectangles in N.
            parent.region = parent.region.unionRect(n.region)
            
            # AT4 [Propagate node split upward] If N has a partner NN resulting from
            # an earlier split, create a new entry Enn with Ennp pointing to NN and
            # Enni enclosing all rectangles in NN. Add Enn to P if there is room.
            # Otherwise, invoke splitNode to produce P and PP containing Enn and
            # all P's old entries.
            newNode = None
            if sibling != None:
                if parent.count < self.M:
                    parent.addRNode(sibling)
                else:
                    newNode = parent.split(sibling, self.m, self.M)  

            # AT5 [Move up to next level] Set N = P and set NN = PP if a split
            # occurred. Repeat from AT2
            n = parent
            sibling = newNode
  
        return sibling   ## NOT SURE

    def chooseLeaf(self, rectangle):
        """Return list of nodes, last one of which is the leaf."""
        # CL1 [Initialize] Set N to be the root node
        return self.root.chooseLeaf(rectangle, [self.root]);

    def size(self):
        """Return number of rectangles in tree. Helpful for debugging."""
        if self.root:
            return self.root.size()
        return 0

    def range(self, target):
        """Return generator of all qualifying (node,0,True) or (rect,id,False) overlapping target."""
        if self.root:
            return self.root.range(target)
        else:
            return None
        
    def remove(self, rectangle):
        """Remove rectangle value from R Tree."""
        if self.root is None:
            return False
        
        # D1 [Find node containing record] Invoke FindLeaf to locate 
        # the leaf node n containing R. Stop if record not found.
        path = self.root.findLeaf (rectangle, [self.root]);
        if path is None:
            return False
            
        leaf = path[-1]
        del path[-1]
        parent = path[-1]
        del path[-1]
        
        # D2 [Delete record.] Remove E from n
        parent.removeRNode (leaf)
                
        # D3 [Propagate changes] Invoke condenseTree on parent
        if parent == self.root:
            self.root.adjustRegion()
        else:
            # last is the topmost node that was adjusted; can be used
            # directly as the new parent of these orphaned nodes, in
            # reverse order.
            parent,Q = parent.condenseTree(path, self.m, self.M)
            self.root.adjustRegion()
            
            # CT6 [Reinsert orphaned entries] Reinsert all entries 
            # of nodes in set Q. 
            for n in Q:
                for rect,ident in n.leafOrder():
                    self.add(rect, ident)
                    
        # D4 [Shorten tree.] If the root node has only one child after
        # the tree has been adjusted, make the child the new root
        while self.root.count == 1 and self.root.level > 0:
            self.root = self.root.children[0]
        if self.root.count == 0:
            self.root = None
            
        return True
        
    def __iter__(self):
        """Leaf-order traversal of elements in the tree."""
        if self.root:
            for e in self.root.leafOrder():
                yield e
                
    def __str__(self):
        """Debugging method."""
        if self.root:
            return self.root.__str__()
        else:
            return '[]'            
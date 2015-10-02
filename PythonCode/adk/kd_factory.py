"""
    Recursively construct balanced kd-tree given data set.
"""
from adk.kd import KDTree, KDNode, VERTICAL
from adk.arraySelect import selectKth
from adk.region import X,Y,maxRegion

def compVertical(p1, p2):
    """Compare according to X coordinate for vertical partitioning."""
    return p1[X] - p2[X]

def compHorizontal(p1, p2):
    """Compare according to Y coordinate for horizontal partitioning."""
    return p1[Y] - p2[Y]

comparators = [compHorizontal, compVertical]

def generateSubTree(d, maxD, points, left, right):
    """
    Generate node for the d-th dimension {0 <= d < maxD} for
    points[left,right] inclusive.
    """
    # Base case first
    if right < left: return None
    if right == left: return KDNode(points[left], d)

    # order array [left,right] so mth element will be the median and
    # the elements prior to it will all be <= though they won't
    # necessarily be sorted; similar the elements after will all be >=
    m = 1+(right-left)//2
    selectKth(points, m, left, right, comparators[d])

    dm = KDNode(points[left+m-1], d)
    d = (d + 1) % maxD

    # recursively compute left and right sub-trees, which translate into
    # below and above for 2-dimensions
    dm.below = generateSubTree(d, maxD, points, left, left+m-2)
    dm.above = generateSubTree(d, maxD, points, left+m, right)
    return dm

def propagate(node, region):
    """
    Because KD Tree is built from the bottom up, we must propagate the regions properly from the top
    down once the tree has been constructed.
    """
    if node is None:
        return
    node.region = region

    # "close off" the above area for below nodes
    if node.below:
        child = node.region.copy()
        if node.orient == VERTICAL:
            child.x_max = node.point[X]
        else:
            child.y_max = node.point[Y]

        propagate(node.below, child)

    # "close off" the below area, since node is above.
    if node.above:
        child = node.region.copy()
        if node.orient == VERTICAL:
            child.x_min = node.point[X]
        else:
            child.y_min = node.point[Y]

        propagate(node.above, child)

def generate(points):
    """Given a list of points construct balanced kd-tree."""
    if len(points) == 0:
        return None

    # VERTICAL=1 is the initial division
    tree = KDTree()
    tree.root = generateSubTree(1, 2, points, 0, len(points)-1)
    propagate(tree.root, maxRegion)
    return tree

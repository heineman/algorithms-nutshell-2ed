"""
    Non-balanced Binary Search Tree.
    
    This class is not to be used in production code. Shown here to demonstrate the
    simple logic yet drastic inefficiencies when tree degenerates. 
    
"""
# Binary Search Tree Implementation

class BinaryNode:

    def __init__(self, value = None):
        """Create binary Node"""
        self.value = value
        self.left = None
        self.right = None

    def add(self, val):
        """Add a new node to BST with this value"""
        if val <= self.value:
            if self.left:
                self.left.add(val)
            else:
                self.left = BinaryNode(val)
        else:
            if self.right:
                self.right.add(val)
            else:
                self.right = BinaryNode(val)

    def inorder(self):
        """In order traversal of tree rooted at given node."""
        if self.left:
            for n in self.left.inorder():
                yield n

        yield self.value

        if self.right:
            for n in self.right.inorder():
                yield n

class BinaryTree:

    def __init__(self):
        """Create empty BST."""
        self.root = None

    def add(self, value):
        """Insert value into proper location in BST."""
        if self.root is None:
            self.root = BinaryNode(value)
        else:
            self.root.add(value)
            
    def __contains__(self, target):
        """Check whether BST contains target value."""
        node = self.root
        while node:
            if target < node.value:
                node = node.left
            elif target > node.value:
                node = node.right
            else:
                return True
        return False

    def __iter__(self):
        """In order traversal of elements in the tree"""
        if self.root:
            return self.root.inorder()

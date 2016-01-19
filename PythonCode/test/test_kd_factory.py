"""
    KD Factory implementation.
"""
import unittest

from adk.kd_factory import generate
from adk.region import X, Y

class TestKDTree(unittest.TestCase):

    def test_three(self):
        pts = [(20, 10), (90, 50), (60, 80)]

        tree = generate(pts)
        self.assertEqual(60, tree.root.point[X])

        # on X-coordinate
        self.assertEqual(20, tree.root.below.point[X])
        self.assertEqual(90, tree.root.above.point[X])

        # regions
        self.assertEqual(60, tree.root.below.region.x_max)

    def test_five(self):
        pts = [(20, 10), (90, 50), (60, 80), (70, 30), (40, 100)]

        tree = generate(pts)
        self.assertEqual(60, tree.root.point[X])

        # on X-coordinate
        self.assertEqual(20, tree.root.below.point[X])
        self.assertEqual(70, tree.root.above.point[X])    
        
        # on Y-coordinate for two grand-children
        self.assertEquals(100, tree.root.below.above.point[Y])
        self.assertEquals(50, tree.root.above.above.point[Y])
        

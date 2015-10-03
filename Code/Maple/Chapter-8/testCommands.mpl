# validate new test case (Figure8_7.java)
with(simplex);

Constraints := [

# conservation of units at each node
e13+e14+e41+e15 = 400, # CHI
e21+e23+e24+e25 = 400, # DC

e13+e23     = 300, # HOU
e14+e41+e24 = 200, # ATL
e15+e25     = 300, # BOS

# maximum flow on individual edges
0 <= e13, e13 <= 200,
0 <= e14, e14 <= 350,
0 <= e41, e41 <= 200,
0 <= e21, e21 <= 200,
0 <= e23, e23 <= 280,
0 <= e15, e15 <= 200,
0 <= e24, e24 <= 200,
0 <= e25, e25 <= 350
];

Cost := 3*e41 + 7*e13 + 3*e14 + 4*e21 + 4*e23 + 7*e24 + 6*e15 + 6*e25;

# Invoke linear programming to solve problem
minimize (Cost, Constraints, NONNEGATIVE);

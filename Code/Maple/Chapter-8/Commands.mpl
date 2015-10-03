with(simplex);

Constraints := [
# conservation of units at each node
e13+e14 = 300, # CHI
e23+e24 = 300, # DC
e13+e23 = 300, # HOU
e14+e24 = 300, # BOS
# maximum flow on individual edges
0 <= e13, e13 <= 200,
0 <= e14, e14 <= 200,
0 <= e23, e23 <= 280,
0 <= e24, e24 <= 350
];

Cost := 7*e13 + 6*e14 + 4*e23 + 6*e24;
# Invoke linear programming to solve problem
minimize (Cost, Constraints, NONNEGATIVE);
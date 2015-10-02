package algs.model.network;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Given a network having sources S, intermediate "warehouse" nodes W, and 
 * demanders D, edge capacities c(i,j), edge costs costs[i][j] with m=|S| suppliers 
 * and n=|D| demanders, construct a flow, if one exists, which minimizes costs. 
 * <p>
 * Note that w=|W| and there are costs associated with the warehouse nodes as well as
 * potential capacity maxima.
 * <p>
 * The resulting Flow Network has m + n + 2*w + 2 nodes. Note that the shipping 
 * "cost" from the virtual new source and new target are zero. All edges store the 
 * shipping cost, in addition to the flow and capacity constraints. 
 * <p>
 * It is assumed that the dimensions of costs matches the respective dimensions
 * of the sup and dem vectors.
 * <p>
 * If a particular shipment is not possible (or simply not selected) then the cost
 * should be Integer.MAX_VALUE.
 */
public class Transshipment extends FlowNetworkArray {

	/** Number of suppliers. */
	public final int m;
	
	/** Number of intermediate warehouses. */
	public final int w;
	
	/** Number of demanders. */
	public final int n;
	
	/** 
	 * Helper method to construct network. 
	 * 
	 * @param sup               vector of the supply capability of suppliers 
	 * @param dem               vector of the demand requested by the demanders
	 * @param sup_transship     vector of shipment costs from suppliers to each warehouse
	 * @param warehouse_costs   vector of costs just for going through warehouse
	 * @param warehouse_limits  vector of capacity for going through warehouse
	 * @param dem_transship     vector of shipment costs from each warehouse to demanders
	 * @param costs             matrix of cost in shipping from sup[i] to dem[j] without
	 *                          going through any warehouse.
	 * @exception IllegalArgumentException   if array boundaries are invalid
	 */
	private static Iterator<EdgeInfo> parse(int sup[], int dem[],
			 int sup_transship[][], int[] warehouse_costs, int[] warehouse_limits, int dem_transship[][],
			 int costs[][]) {
		
		int m = sup.length;
		int n = dem.length;
		int w = warehouse_costs.length;
		
		// validate input, that costs is an MxN matrix.
		if (m != costs.length) {
			throw new IllegalArgumentException ("costs matrix has " + costs.length + 
					" suppliers but sup vector has " + m);
		}
		for (int i = 0; i < m; i++) {
			if (costs[i].length != n) {
				throw new IllegalArgumentException ("costs matrix has " + costs[i].length + 
						" entries but dem vector has " + n);
			}
		}
		
		// if we have warehouses, then we have additional checks.
		if (w != 0) {
			// sup_transship is MxW matrix while dem_transship is WxN matrix.
			if (sup_transship.length != m) {
				throw new IllegalArgumentException ("The size of supplier transshipment costs doesn't match the number of suppliers.");
			}
			if (dem_transship.length != w) {
				throw new IllegalArgumentException ("The size of demanders transshipment costs doesn't match the number of warehouses.");
			}
			
			for (int i = 0; i < w; i++) {
				if (dem_transship[i].length != n) {
					throw new IllegalArgumentException ("The size of demanders transshipment costs doesn't match the number of warehouses.");
				}
			}
			
			for (int i = 0; i < m; i++) {
				if (sup_transship[i].length != w) {
					throw new IllegalArgumentException ("The size of supplier transshipment costs doesn't match the number of warehouses.");
				}
			}
			
		}
		
		// construct edges first, then the network problem.
		ArrayList<EdgeInfo> edges = new ArrayList<EdgeInfo>();

		// FIVE vertex groups. ZERO is the new source, [1,m+1) are the suppliers, [1+m, 1+m+2*w) are the 
		// intermediate warehouses, [1+m+2*w,1+m+2*w+n) are the demanders. The new target is 1+m+2*w+n.
		int supply_offset = 1;
		int w_offset = 1 + m;
		int demand_offset = 1 + m + 2*w;
		int target = 1+m+2*w+n;
		
		// supply edges have zero cost and capacity equal to the supply of the ith supplier.
		for (int i = 0; i < m; i++) {
			edges.add(new EdgeInfo(0, supply_offset+i,  sup[i], 0));
		}

		// demand edges have zero cost and capacity equal to the demand of the ith demander.
		for (int j = 0; j < n; j++) {
			edges.add(new EdgeInfo(demand_offset+j, target, dem[j], 0));
		}
		
		// warehouse internal edges have individual costs (based on warehouse_costs) and capacity  
		// equal to the SUM TOTAL of the SUPPLIERS TOTAL output
		for (int k = 0; k < w; k ++) {
			edges.add(new EdgeInfo(w_offset+2*k, w_offset+(2*k+1), 
					warehouse_limits[k], warehouse_costs[k]));
		}

		// warehouse edges from all suppliers have sup_transship costs and capacity equal to the 
		// specific supplier's output capability.
		for (int i = 0; i < m; i++) {
			for (int k = 0; k < w; k++) {
				edges.add(new EdgeInfo(supply_offset+i, w_offset+2*k, sup[i],
						sup_transship[i][k]));
			}
		}

		// warehouse edges from all warehouses to the demanders have dem_transship costs and capacity
		// equal to the specific demander's output capability.
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < n; j++) {
				edges.add(new EdgeInfo(w_offset+2*i+1, demand_offset+j, dem[j], dem_transship[i][j]));
			}
		}
		
		// interior edges have INF capacity.
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				edges.add(new EdgeInfo(supply_offset+i, demand_offset+j, Integer.MAX_VALUE, costs[i][j]));
			}
		}

		return edges.iterator();
	}
	
	/** 
	 * Construct an instance of the Transshipment problem. 
	 * <p>
	 * Accepts zero warehouse information, in which case this reduces to the {@link Transportation} 
	 * problem.
	 * 
	 * @param sup               vector of the supply capability of suppliers 
	 * @param dem               vector of the demand requested by the demanders
	 * @param sup_transship     vector of shipment costs from suppliers to each warehouse
	 * @param warehouse_costs   vector of costs just for going through warehouse(s)
	 * @param warehouse_limits  vector of capacity for going through warehouse
	 * @param dem_transship     vector of shipment costs from each warehouse to demanders
	 * @param costs             matrix of cost in shipping from sup[i] to dem[j] without
	 *                          going through any warehouse.
	 * @exception IllegalArgumentException   if array boundaries are invalid
	 */
	public Transshipment(int sup[], int dem[], 
						 int sup_transship[][], int warehouse_costs[], int warehouse_limits[], 
						 int dem_transship[][],
						 int costs[][]) {
		super(sup.length+dem.length+2*warehouse_costs.length+2,    /* Number */
			  0,                                                   /* Source. */
			  sup.length+dem.length+2*warehouse_costs.length+1);   /* Sink. */
		
		// Fill in the edges in the network.
		Iterator<EdgeInfo> edges = parse(sup,dem,sup_transship,warehouse_costs,
				                         warehouse_limits,dem_transship,costs);
		
		populate(edges);
		
		this.m = sup.length;
		this.n = dem.length;
		this.w = warehouse_costs.length;
	}

	/** Return the computed minimum cost for this solution. */
	@Override
	public int getCost() {
		int cost = 0;
		
		// FIVE vertex groups. ZERO is the new source, [1,m+1) are the suppliers, [1+m, 1+m+2*w) are the 
		// intermediate warehouses, [1+m+2*w,1+m+2*w+n) are the demanders. The new target is 1+m+2*w+n.
		int supply_offset = 1;
		int w_offset = 1 + m;
		int demand_offset = 1 + m + 2*w;
		
		//EdgeInfo[][] ei = (EdgeInfo[][]) network.getEdgeStructure();
		// warehouse internal edges have individual costs (based on warehouse_costs) and capacity  
		// equal to the SUM TOTAL of the SUPPLIERS TOTAL output
		for (int j = 0; j < w; j ++) {
			EdgeInfo cei = info[w_offset+2*j][w_offset+2*j+1];
			cost += cei.flow * cei.cost;
		}

		// warehouse edges from all suppliers have sup_transship costs and capacity equal to the 
		// specific supplier's output capability.
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < w; j++) {
				EdgeInfo cei = info[supply_offset+i][w_offset+2*j];
				cost += cei.flow * cei.cost;
			}
		}

		// warehouse edges from all warehouses to the demanders have dem_transship costs and capacity
		// equal to the specific demander's output capability.
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < n; j++) {
				EdgeInfo cei = info[w_offset+2*i+1][demand_offset+j];
				cost += cei.flow * cei.cost;
			}
		}
		
		// interior edges have INF capacity.
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				EdgeInfo cei = info[supply_offset+i][demand_offset+j];
				cost += cei.flow * cei.cost;
			}
		}
		
		return cost;
	}
	
	/**
	 * Output the solution grid.
	 * @return string solution of problem.
	 */
	public String output () {
		StringBuilder sb = new StringBuilder();
		
		int supply_offset = 1;
		int w_offset = 1 + m;
		int demand_offset = 1 + m + 2*w;
		
		sb.append ("Solution:").append("\n");
		
		sb.append("Direct shipping results").append("\n");
		sb.append("\t");
		for (int j = 0; j < n; j++) {
			sb.append((j+1) + " ");
		}
		sb.append("\n");
		
		for (int i = 0; i < m; i++) {
			sb.append((i+1) + "\t");
			for (int j = 0; j < n; j++) {
				EdgeInfo cei = info[supply_offset+i][demand_offset+j];
				sb.append(cei.getFlow() + " ");
			}
			sb.append("\n");
		}
		
		sb.append ("Shipping results to warehouses").append("\n");
		for (int j = 0; j < w; j++) {
			sb.append("w" + (j+1) + " ");
			for (int i = 0; i < m; i++) {
				EdgeInfo cei = info[supply_offset+i][w_offset+2*j];
				sb.append(cei.getFlow() + " ");
			}
			sb.append("\n");
		}
		
		sb.append ("Shipping from warehouses").append("\n");
		for (int j = 0; j < w; j++) {
			sb.append("w" + (j+1) + " ");
			for (int i = 0; i < n; i++) {
				EdgeInfo cei = info[w_offset+(2*j+1)][demand_offset+i];
				sb.append(cei.getFlow() + " ");
			}
			sb.append("\n");
		}
		
		sb.append("Computed cost:" + getCost()).append("\n");
		
		return sb.toString();
	}
	
}

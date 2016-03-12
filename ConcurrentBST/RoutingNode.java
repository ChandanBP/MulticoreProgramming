package concurrentDS;


public class RoutingNode {
	
	int key;
	
	RoutingNode left;
	RoutingNode right;
	LeafNode leftLeaf;
	LeafNode rightLeaf; 

	public RoutingNode(int k){ 
		
		this.key = k;
		left = null;
		right = null;
	}
}

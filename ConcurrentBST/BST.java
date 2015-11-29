package concurrentDS;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class BST {
	
	int keySpace;
	RoutingNode head,root;
	ReentrantLock lock = new ReentrantLock();
	ArrayList<Integer> resultList = new ArrayList<Integer>();
	
	public BST(int k){
		keySpace = k;
		head = new RoutingNode(Integer.MIN_VALUE);
        resultList = new ArrayList<Integer>();
	}

	
	public boolean isSorted(){
		boolean sorted = true;
		for(int i=1;i<resultList.size();i++){
			if(resultList.get(i-1).compareTo(resultList.get(i))>0) sorted = false;
		}
		return sorted;
	}
	
	
	public boolean insertKey(int k){
		 
		//System.out.println(k);
		
		   
		// If root node is null
		   if(root==null){
			   
			   LeafNode leafNode = new LeafNode(k);
			   
			   // Create a new routing node for root
			   root = new RoutingNode(k);
			   root.rightLeaf = leafNode;
			   
			   head.right = root;
			   head.left = root;

			   return true;
		   }
		   else{
			   return insert(k,root);  
		   }
	   }
	
	public int getMax(int k1,int k2){
		   return (k1>k2)?k1:k2;
	   }
	
	
	
	
	public boolean insert(int key,RoutingNode node){
		
		boolean inserted = false;
		
		lock.lock();
		   // If key is less than node
		    if(key<node.key && node.left == null){
		    	inserted =  insertLeftLeaf(key,node);
		    }
		    
		    else if(key>=node.key && node.right == null){
		    	inserted =  insertRightLeaf(key,node);
		    }
		    
		    else if(key<node.key){
		    	inserted =  insert(key,node.left);
		    }
		    else{
		    	inserted =  insert(key,node.right);
		   }
		    lock.unlock();
		   return inserted; 
		}

	public void inOrder(RoutingNode node){
		if(node.left!=null){
			inOrder(node.left);
		}
		if(node.leftLeaf!=null) {
			resultList.add(node.leftLeaf.key);}
		if(node.rightLeaf!=null) {
			resultList.add(node.rightLeaf.key);}
	    if(node.right!=null){
			inOrder(node.right);
		}	
		
	}
	
	public boolean insertLeftLeaf(int key,RoutingNode routeNode){
		   
		   
		   LeafNode newLeafNode = new LeafNode(key);
		   if(routeNode.leftLeaf == null){
			   routeNode.leftLeaf = newLeafNode;
			   return true;
		   }
		   else{
			   
			   if(routeNode.leftLeaf.key == key)return false;
			   
			   RoutingNode newRoutingNode;
			   LeafNode oldLeafNode;
			   oldLeafNode = routeNode.leftLeaf;
			   
			   newRoutingNode = new RoutingNode(getMax(key,routeNode.leftLeaf.key));
			   routeNode.left = newRoutingNode;
			   routeNode.leftLeaf = null;
			   
			   newRoutingNode.rightLeaf = (newLeafNode.key>oldLeafNode.key)?newLeafNode:oldLeafNode;
			   newRoutingNode.leftLeaf = (newLeafNode.key<oldLeafNode.key)?newLeafNode:oldLeafNode;
			   return true;
		   }
	   }
   
   
   public boolean insertRightLeaf(int key,RoutingNode routeNode){
	   
	   
	   LeafNode newLeafNode = new LeafNode(key);
	   
	   if(routeNode.rightLeaf == null){
		   routeNode.rightLeaf = newLeafNode;
		   return true;
	   }
	   else{
		   
		   if(routeNode.rightLeaf.key == key)return false;
		   
		   RoutingNode newRoutingNode;
		   LeafNode oldLeafNode;
		   oldLeafNode = routeNode.rightLeaf;
		   
		   newRoutingNode = new RoutingNode(getMax(key,routeNode.rightLeaf.key));
		   routeNode.right = newRoutingNode;
		   routeNode.rightLeaf = null;
		   
		   newRoutingNode.rightLeaf = (newLeafNode.key>oldLeafNode.key)?newLeafNode:oldLeafNode;
		   newRoutingNode.leftLeaf = (newLeafNode.key<oldLeafNode.key)?newLeafNode:oldLeafNode;
		   return true;
	   }
   }
   
  
  	public void constructTree(int low,int high){
		
		if(low<high){
			int m = (low+high)/2;
			insertKey(m);
			constructTree(low, m);
			constructTree(m+1, high);
		}
		
	}
	
	
	public boolean remove(int k){
			
		    
			if(root == null)return false;
			return delete(k,head);
		}

	public void updateGrandParentPointer(RoutingNode grandParent,RoutingNode parent,int key){
		
		if(key<=grandParent.key){
			
			if(key<parent.key){
				if((parent.right!=null && parent.rightLeaf==null) || (parent.rightLeaf!=null && parent.right==null)){
					parent.leftLeaf = null;
				}
				if(parent.right==null && parent.rightLeaf==null){
					grandParent.left=null;
				}
			}
			else{
				// if key>=parent.key
				if((parent.left!=null && parent.leftLeaf==null) || (parent.leftLeaf!=null && parent.left==null)){
					parent.rightLeaf = null;
				}
				if(parent.left==null && parent.leftLeaf==null){
					grandParent.left = null;
				}
			}
			
		}
		if(key>=grandParent.key){
			
			if(key<parent.key){
				if((parent.right!=null && parent.rightLeaf==null) ||(parent.rightLeaf!=null && parent.right==null)){
					parent.leftLeaf=null;
				}
                if(parent.right==null && parent.rightLeaf==null){
					grandParent.right = null;
				}
			}
			else{
				// if key>=parent.key
				if((parent.left!=null && parent.leftLeaf==null) || (parent.leftLeaf!=null && parent.left==null)){
					parent.rightLeaf=null;
				}
				if(parent.left==null && parent.leftLeaf==null){
					grandParent.right=null;
				}
			}
		}
		
	}
	
	
	public boolean delete(int key,RoutingNode node){
		
		boolean doneDelete = false;
		lock.lock();
		RoutingNode parent;
		RoutingNode grandParent;
		

		grandParent = node;
		parent = grandParent.right;
		
		while(node!=null){
			
			if(key<node.key){
				if(node.left==null){
					if(node.leftLeaf!=null && node.leftLeaf.key==key){
						updateGrandParentPointer(grandParent,parent,key);
						doneDelete = true;
					}
				}
				else{
					grandParent = node;
				}
				node = node.left;
				parent = node;
			}
			else{
				if(node.right==null){
					if(node.rightLeaf!=null && node.rightLeaf.key==key){
						updateGrandParentPointer(grandParent,parent,key);
						doneDelete = true;
					}
				}
				else{
					grandParent = node;
				}
				node = node.right;
				parent = node;
			}
		}
		lock.unlock();
		return doneDelete;
	}
	
	public boolean search(int k){
		
		
		if(root == null){
			return false;
		}
		
		return contains(k,root);
		
	}
	
	public boolean contains(int key,RoutingNode node){
		
		boolean isPresent = false;
		
		lock.lock();
		if(key<node.key){
			if(node.left==null){
				if(node.leftLeaf!=null && node.leftLeaf.key == key){
					isPresent = true;
				}
			}
			else{
				isPresent = contains(key, node.left);
			}
		}
		else{
			if(node.right==null){
				if(node.rightLeaf!=null && node.rightLeaf.key == key){
					isPresent = true;
				}
			}
			else{
				isPresent = contains(key, node.right);
			}
		}

		lock.unlock();
		return isPresent;
	}

}

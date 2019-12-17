/*Marcos Hung
 * This class implements a generic RedBlackTree
*/
public class RedBlackTree<Key extends Comparable<Key>> {	
		 RedBlackTree.Node<Key> root;

		public static class Node<Key extends Comparable<Key>> { //changed to static 
			
			  Key key;  		  
			  Node<Key> parent;
			  Node<Key> left;
			  Node<Key> right;
			  boolean isRed;
			  int color;
			  
			  public Node(Key data){
				  this.key = data;
				  left = null;
				  right = null;
			  }		
			  
			  public int compareTo(Node<Key> n){ 	//this < that  <0
			 		return key.compareTo(n.key);  	//this > that  >0
			  }
			  
			  public boolean isLeaf(Node<Key> root){
				  
				  //if it is not a root and both children are null then it is a leaf
				  if (!(this.equals(root)) && this.left == null && this.right == null) 
					  return true;
				  return false;
			  }
		}

		 public boolean isLeaf(RedBlackTree.Node n){
			  if (!(n== root) && (n.left == null && n.right == null)) 
				  return true;
			  return false;
		  }
		
		public interface Visitor<Key extends Comparable<Key>> {
			/**
			This method is called at each node.
			@param n the visited node
			*/
			void visit(Node<Key> n);  
		}
		
		public void visit(Node<Key> n){
			System.out.println(n.key);
		}
		
		public void printTree(){  //preorder: visit, go left, go right
			RedBlackTree.Node<Key> currentNode = root;	
			printTree(currentNode);
		}
		
		public void printTree(RedBlackTree.Node<Key> node){
			if(node == null) {
				return;
			}
			System.out.print(node.key);
			if (node.isLeaf(root)){
				return;
			}
			printTree(node.left);
			printTree(node.right);
		}
		
		// place a new node in the RB tree with data the parameter and colors it red. 
		public void addNode(Key data){  	//this < that  <0.  this > that  >0
			
			//stores the parent node of the node to be inserted
			Node<Key> parentNode = findParent(root, data);
			
			//creates the node to be inserted
			Node<Key> n = new Node<Key>(data);

			
			n.parent =  parentNode;
			n.color = 0;
			n.isRed = true;
			
			//inserts the node into the parent's appropriate child position
			if(n.compareTo(parentNode) < 0) {
				parentNode.left = n;
				
			}else {
				parentNode.right = n;
			}
			
			fixTree(n);
		}	

		public void insert(Key data){
			
			//if there is no current root, create the root and make it black
			if(root == null) {
				root = new Node<Key>(data);
				root.isRed = false;
				root.color = 1;
			}else {
				addNode(data);	
			}
		}
		
		public RedBlackTree.Node<Key> lookup(Key k){ 
		
			Node<Key> target = new Node<Key>(k);
			Node<Key> temp = root;
			boolean done = false;
			while(!done) {
				if(temp.compareTo(target) < 0) {
					temp = temp.right;				//goes right if temp < target

				}else {
					
					temp = temp.left;				//goes left if temp > target

				}
				if(temp == null) { // if reached the end then the target is not in the RBT
					return null;
				}
				if(temp.key.equals(k)) {// target found, return the node
					return temp;
				}
			
			}
			return null;
		}
	 	
		
		
		public RedBlackTree.Node<Key> getSibling(RedBlackTree.Node<Key> n){  
			if(n.parent == null) {
				return null;
			}
			//sibling is the opposite child of the parent
			if(isLeftChild(n.parent, n)) {
				return n.parent.right;
			}else {
				return n.parent.left;
			}
		}
		
		
		public RedBlackTree.Node<Key> getAunt(RedBlackTree.Node n){
			//aunt is the parent's sibling
			return getSibling(n.parent);
		}
		
		public RedBlackTree.Node<Key> getGrandparent(RedBlackTree.Node n){
			if(n.parent == null) {
				return null;
			}
			return n.parent.parent;
		}
		
		public void rotateLeft(RedBlackTree.Node<Key> n){
			//this stores the parent of n to switch with y later
			Node parent = n.parent;
			
			//this will be the node that n will be rotated around
			Node<Key> y = n.right;
			
			//this is child of y that will become n's new child.
			Node temp = y.left;
			
			//rotates nodes accordingly
			y.left =n;
			n.parent = y;
			y.parent = parent;
			n.right = temp;
			
			//places y into the parent's appropriate child position.
			if(n != root) {
				if(isLeftChild(parent,n)) {
					parent.left = y;
				}else {
					parent.right = y;
				}
			}else {
				//if there is no parent then y is the root 
				root = y;
			}
			
			//sets temp's parent as long as temp is not null
			if(temp != null) {
				temp.parent = n;

			}
		}
		
		public void rotateRight(RedBlackTree.Node<Key> n){
			//this stores the parent of n to switch with y later
			Node parent = n.parent;
			
			//this will be the node that n will be rotated around
			Node<Key> x = n.left;
			
			//this is child of x that will become n's new child.
			Node temp = x.right;
			
			//rotates nodes accordingly
			x.right = n;
			n.parent = x;
			x.parent = parent;
			n.left = temp;

			//places x into the parent's appropriate child position.
			if(n != root) {
				if(isLeftChild(parent, n)) {
					parent.left = x;
				}else {
					parent.right = x;
				}
			}else {
				//if there is no parent then x is the root
				root = x;
			}
			
			//sets temp's parent as long as temp is not null
			if(temp != null) {
				temp.parent = n;

			}
		}
		
		public void fixTree(RedBlackTree.Node<Key> current) {

			if(current == root) { //node is root
				//set root to black
				current.isRed = false;
				current.color = 1;
			}else if(!current.parent.isRed) { //parent is black
				return;
			}
			if(current.isRed && current.parent.isRed) { //node and parent are red
				
				if(getAunt(current) == null || !getAunt(current).isRed) { //aunt is black
					if(isLeftChild(getGrandparent(current), current.parent) && !isLeftChild(current.parent, current)) {
						//rotate left around the parent
						Node<Key> original = current.parent;
						rotateLeft(current.parent);
						
						//recursively fix tree
						fixTree(original);
					}
					if(!isLeftChild(getGrandparent(current), current.parent) && isLeftChild(current.parent, current)) {
						//rotate right around the parent
						Node<Key> original = current.parent;
						rotateRight(current.parent);
						
						//recursively fix tree
						fixTree(original);
					}
					if(isLeftChild(getGrandparent(current), current.parent) && isLeftChild(current.parent, current)) {
						//color parent black
						current.parent.isRed = false;
						current.parent.color = 1;
						
						//color grandparent red
						getGrandparent(current).isRed = true;
						getGrandparent(current).color = 0;
						
						//rotate right about grandparent and finish
						rotateRight(getGrandparent(current));
					}
					if(!isLeftChild(getGrandparent(current), current.parent) && !isLeftChild(current.parent, current)) {
	 					//color parent black
						current.parent.isRed = false;
	 					current.parent.color = 1;
	 					
	 					//color grandparent red
						getGrandparent(current).isRed = true;
	 					getGrandparent(current).color = 0;
	 					
	 					//rotate left about grandparent and finish
						rotateLeft(getGrandparent(current));
					}
					
				}else if(getAunt(current).isRed) { //aunt is red -> recolor tree up to grandparent
					//color parent black
					current.parent.isRed = false;
					current.parent.color = 1;
					
					//color aunt black
					getAunt(current).isRed = false;
					getAunt(current).color = 1;
					
					//color grandparent red
					getGrandparent(current).isRed = true;
					getGrandparent(current).color = 0;
					
					//recursively fix tree starting with grandparent
					fixTree(getGrandparent(current));
				}
			}
		}
		
		//returns true if the node has no key
		public boolean isEmpty(RedBlackTree.Node n){
			if (n.key == null){
				return true;
			}
			return false;
		}
		 
		//returns true if the parent's left child equals the child node
		public boolean isLeftChild(RedBlackTree.Node parent, RedBlackTree.Node child)
		{	
			if(parent == null) {
				return false;
			}
			if (child.compareTo(parent) < 0 ) {//child is less than parent
				return true;
			}
			return false;
		}

		//visits the nodes in pre order
		public void preOrderVisit(Visitor v) {
		   	preOrderVisit(root, v);
		}
		 
		
		private static void preOrderVisit(RedBlackTree.Node n, Visitor v) {
		  	if (n == null) {
		  		return;
		  	}
		  	v.visit(n);
		  	preOrderVisit(n.left, v);
		  	preOrderVisit(n.right, v);
		}	
		
		//returns the node of the parent that the current node should have 
		private RedBlackTree.Node<Key> findParent(Node<Key> current, Key data){
			if (current.left!=null && data.compareTo(current.key) < 0) {
				
				current = findParent(current.left, data);	
			}
			else if(current.right != null&&data.compareTo(current.key) > 0) {
				current=findParent(current.right, data);	
			}
			return current;
		}
		
		//rests the tree
		public void clearTree() {
			root = null;
		}
	}


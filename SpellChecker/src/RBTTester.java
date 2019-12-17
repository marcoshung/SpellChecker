
import static org.junit.Assert.*;

import org.junit.Test;


public class RBTTester {
	
	@Test
	public void testCreation() {
		RedBlackTree rbt1 = new RedBlackTree();
		rbt1.insert("4");
		rbt1.insert("2");
		rbt1.insert("1");
		rbt1.insert("3");
		rbt1.insert("5");
		rbt1.printTree();
		assertEquals("21435", makeString(rbt1));
		assertEquals("3", rbt1.lookup("3").key);
		assertEquals(rbt1.lookup("4"),rbt1.getSibling(rbt1.lookup("1")));
	
		rbt1.clearTree();
	}
	

	
	
	@Test
    //Test the Red Black Tree
	public void test() {
		RedBlackTree rbt = new RedBlackTree();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        System.out.println();
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
		//grandparent test
        assertEquals("D",rbt.getGrandparent(rbt.lookup("C")).key); // D is grandparent of C
        assertNotEquals("D",rbt.getGrandparent(rbt.lookup("J")).key); //D is not J's grandparent
        
        //sibling test
        assertEquals("A", rbt.getSibling(rbt.lookup("C")).key); // A and C are sibling
        assertNotEquals(rbt.lookup("A"), rbt.getSibling(rbt.lookup("B"))); // A and b are not siblings
        
        //Leaf test
        assertTrue(rbt.lookup("J").isLeaf(rbt.root));
        assertFalse(rbt.lookup("B").isLeaf(rbt.root));
        
        
    } 
    
    
    //add tester for spell checker
    
    public static String makeString(RedBlackTree t){
       class MyVisitor implements RedBlackTree.Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n){
             result = result + n.key;
          }
       };
       
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    public static String makeStringDetails(RedBlackTree t) {
    	{
    	       class MyVisitor implements RedBlackTree.Visitor {
    	          String result = "";
    	          public void visit(RedBlackTree.Node n){
    	        	  String parent = "";
    	        	  if(n.parent != null) {
    	        		  parent = n.parent.key.toString();
    	        	  }
    	        	  if(n != null)
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: " + parent + "\n";
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       System.out.println(v.result);
    	       return v.result;
    	 }
    }

    
 }
  

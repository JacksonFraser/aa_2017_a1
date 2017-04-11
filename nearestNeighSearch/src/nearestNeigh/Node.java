package nearestNeigh;
public class Node {
	public Point data;
	public Node left;
	public Node right;
	
	public Node(){
		this.data = data;
		this.left = left;
		this.right = right;
		
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public void setLeft(Node left) {
		this.left = left;
	}

}

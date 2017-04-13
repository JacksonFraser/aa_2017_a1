package nearestNeigh;
public class Node {
	public Point point;
	public Node left;
	public Node right;
	
	public Node(){
		this.point = point;
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

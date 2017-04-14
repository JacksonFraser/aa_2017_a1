package nearestNeigh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is required to be implemented. Kd-tree implementation.
 *
 * @author Jeffrey, Youhan
 */
public class KDTreeNN implements NearestNeigh {
	Category edu = Category.EDUCATION;
	Category hos = Category.HOSPITAL;
	Category res = Category.RESTAURANT;
	List<Point> nearestPoint = new ArrayList<Point>();
	List<Point> allList = new ArrayList<Point>();
	List<Point> resList = new ArrayList<Point>();
	List<Point> hosList = new ArrayList<Point>();
	List<Point> eduList = new ArrayList<Point>();
	public Node resroot;
	public Node hosroot;
	public Node eduroot;
	public Node allroot;
	public Node leafnode;
	public Node temp;
	public int catchTop;

	public KDTreeNN() {
		allroot = null;
		resroot = null;
		hosroot = null;
		eduroot = null;
		leafnode = null;
		temp = null;
		catchTop = 0;

	}

	@Override
	public void buildIndex(List<Point> points) {

		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).cat.equals(edu)) {
				eduList.add(points.get(i));
			}
		}
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).cat.equals(hos)) {
				hosList.add(points.get(i));
			}
		}
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).cat.equals(res)) {
				resList.add(points.get(i));
			}
		}

		boolean Dm = true;
		/* allroot = buildTree(points,Dm); */

		/* eduroot=buildTree(eduList,Dm); */
		resroot = buildTree(resList, temp, Dm);
		/*
		 * System.out.println(resroot.point);
		 * System.out.println(resroot.leftChild.point);
		 * System.out.println(resroot.rightChild.point);
		 */
		/*
		 * System.out.println(resroot.leftChild.leftChild.point);
		 * System.out.println(resroot.leftChild.rightChild.point);
		 * System.out.println(resroot.leftChild.leftChild.leftChild.point);
		 * System.out.println(resroot.rightChild.leftChild.point);
		 * System.out.println(resroot.rightChild.rightChild.point);
		 */

		/* inorder(); */
		/* hosroot = buildTree(hosList,Dm); */

		/*
		 * System.out.println(eduroot.point); System.out.println(resroot.point);
		 * System.out.println(hosroot.point);
		 */

	}

	public Node buildTree(List<Point> points, Node temp, boolean Dm) {
		int median;

		List<Point> sortedPoints = new ArrayList<Point>();
		/*
		 * List<Point> leftList = new ArrayList<Point>(); List<Point> rightList
		 * = new ArrayList<Point>();
		 */

		Node leftChild = null, rightChild = null;

		flip(points, Dm);
		sortedPoints = points;
		median = findMedian(sortedPoints);
		// System.out.print(median);

		Node currentNode = new Node();

		currentNode.point = sortedPoints.get(median);
		currentNode.setparent(temp);
		// System.out.println(sortedPoints);
		// System.out.println(sortedPoints.get(median));
		// System.out.println(currentNode.point);

		// System.out.println(leftList);

		// System.out.println(leftList);
		if (Dm == true) {
			Dm = false;
		} else {
			Dm = true;
		}

		// System.out.println(median);
		if (median > 0) {
			temp = currentNode;
			leftChild = buildTree(flip(leftPointList(points, median), Dm), temp, Dm);
			currentNode.setisright(false);
			currentNode.setleftChild(leftChild);

		}
		// System.out.println(median);
		/*
		 * System.out.println(points.size()); System.out.println(median);
		 */
		/* System.out.println("hello"); */

		if (median + 1 < points.size()) {

			/* System.out.println(Dm); */
			temp = currentNode;

			rightChild = buildTree(flip(rightPointList(points, median + 1), Dm), temp, Dm);
			currentNode.setisright(true);
			currentNode.setRightChild(rightChild);

			// System.out.println(currentNode.point);
		}

		return currentNode;

	}

	public List<Point> flip(List<Point> points, boolean p) {
		if (p == true) {

			return AllsortedX(points);
		} else {

			return AllsortedY(points);
		}

	}

	public List<Point> leftPointList(List<Point> points, int median) {
		List<Point> leftList = new ArrayList<Point>();
		for (int i = 0; i < median; i++) {
			leftList.add(points.get(i));
		}
		return leftList;
	}

	public List<Point> rightPointList(List<Point> points, int median) {
		List<Point> rightList = new ArrayList<Point>();
		for (int i = median; i < points.size(); i++) {
			rightList.add(points.get(i));
		}

		return rightList;
	}

	public int findMedian(List<Point> points) {
		int median = 0;
		/*
		 * if(median%2==0){ median = ((points.size()/2) +
		 * (points.size()/2-1))/2;
		 * 
		 * } else{
		 */

		median = points.size() / 2;
		/* } */

		return median;
	}

	public List<Point> AllsortedX(List<Point> points) {
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < points.size() - 1; j++) {
				if (points.get(j).lat > points.get(j + 1).lat) {
					Collections.swap(points, j, j + 1);
				}
			}
		}

		return points;
	}

	public List<Point> AllsortedY(List<Point> points) {
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < points.size() - 1; j++) {
				if (points.get(j).lon > points.get(j + 1).lon) {
					Collections.swap(points, j, j + 1);
				}
			}
		}
		return points;
	}

	@Override
	public List<Point> search(Point searchTerm, int k) {
		boolean check = true;
		ArrayList<Point> abc = null;
		catchTop = 0;
		List<Point> sortedList = new ArrayList<Point>();
		if (searchTerm.cat.equals(res)) {
			findLeafNode(resroot, searchTerm, check);
		}
		System.out.println("leaf is  :" + leafnode.point);
		System.out.println("leaf is far as :" + searchTerm.distTo(leafnode.point));

		

		Node compare = leafnode.parent;
		//for (int i = 0; i < k; i++) {
			abc = compare(searchTerm, leafnode, compare);
			System.out.println("catch top is :" + catchTop);
			System.out.println("nearest is :"+abc.get(0));
			//for(int i = 0; i < resList.size(); ++i){
			//	if(resList.get(i).id.equals("29")){
			//		System.out.println("nearest is :"+ searchTerm.distTo(resList.get(i)));
				
			//	}
				
			//}

		//}

		/*
		 * if(searchTerm.cat.equals(res)){
		 * 
		 * findLeafNode(resroot,searchTerm,check);
		 * nearestPoint(resroot,searchTerm); for(int
		 * i=0;i<nearestPoint.size();i++){ for(int
		 * j=0;j<nearestPoint.size()-1;j++){
		 * if(nearestPoint.get(j).distTo(searchTerm)>nearestPoint.get(j+1).
		 * distTo(searchTerm)){ Collections.swap(nearestPoint, j, j+1); } } }
		 * for(int i=0;i<k;i++){ sortedList.add(nearestPoint.get(i)); }
		 * 
		 * } if(searchTerm.cat.equals(edu)){
		 * 
		 * findLeafNode(eduroot,searchTerm,check);
		 * nearestPoint(eduroot,searchTerm); for(int
		 * i=0;i<nearestPoint.size();i++){ for(int
		 * j=0;j<nearestPoint.size()-1;j++){
		 * if(nearestPoint.get(j).distTo(searchTerm)>nearestPoint.get(j+1).
		 * distTo(searchTerm)){ Collections.swap(nearestPoint, j, j+1); } } }
		 * for(int i=0;i<k;i++){ sortedList.add(nearestPoint.get(i)); }
		 * 
		 * } if(searchTerm.cat.equals(hos)){
		 * 
		 * findLeafNode(hosroot,searchTerm,check);
		 * nearestPoint(hosroot,searchTerm); for(int
		 * i=0;i<nearestPoint.size();i++){ for(int
		 * j=0;j<nearestPoint.size()-1;j++){
		 * if(nearestPoint.get(j).distTo(searchTerm)>nearestPoint.get(j+1).
		 * distTo(searchTerm)){ Collections.swap(nearestPoint, j, j+1); } } }
		 * for(int i=0;i<k;i++){ sortedList.add(nearestPoint.get(i)); }
		 * 
		 * }
		 */

		// To be implemented.
		return null;
	}

	public void findLeafNode(Node root, Point points, Boolean checklat) {

		if (root == null) {
			return;
		}
		if (checklat == true) {

			if (points.lat > root.point.lat) {
				findLeafNode(root.rightChild, points, !checklat);

				if (root.leftChild == null && root.rightChild == null) {

					leafnode = root;
					// nearestPoint.add(leafnode.point);

				}

			}
			if (points.lat < root.point.lat) {

				findLeafNode(root.leftChild, points, !checklat);

				if (root.leftChild == null && root.rightChild == null) {
					leafnode = root;

					/*
					 * System.out.println(root.parent.parent.point);
					 * System.out.println(root.parent.point);
					 * System.out.println(root.point);
					 */
					// nearestPoint.add(leafnode.point);

				}

			}
		}
		if (checklat == false) {
			if (points.lon > root.point.lon) {

				findLeafNode(root.rightChild, points, !checklat);

				if (root.leftChild == null && root.rightChild == null) {
					leafnode = root;

					// nearestPoint.add(leafnode.point);

				}
			}
			if (points.lon < root.point.lon) {

				findLeafNode(root.leftChild, points, !checklat);

				if (root.leftChild == null && root.rightChild == null) {
					leafnode = root;

					// nearestPoint.add(leafnode.point);

				}
			}
		}
	}

	public ArrayList<Point> compare(Point searchTerm, Node NearestNode, Node compare) {
		ArrayList<Point> klist = new ArrayList();
		boolean rightcheck;
		Point nearest = NearestNode.point;
		Point ComparePoint = compare.point;
		double space = searchTerm.distTo(nearest);
		double CompareSpace = searchTerm.distTo(ComparePoint);
		double anotherspace = Math.abs(CompareSpace - space);
		System.out.println("search point again is:" + searchTerm);
        double  variance = Math.abs( searchTerm.lat - nearest.lat)+Math.abs(searchTerm.lon - nearest.lon);
		//Point pointI = klist.get(i);
		//double spaceI = searchTerm.distTo(pointI);
		if (space <= CompareSpace) {
			System.out.println("up start:");
			System.out.println("");
			if (compare.parent == null) {
				System.out.println("top coming");
				System.out.println("");


				if (catchTop == 2) {
					klist.add(NearestNode.point);
					System.out.println("reach top point again is:" + compare.point);
					System.out.println("anotherspace is far as:" + anotherspace);
					System.out.println("variance is far as :" + variance);
					System.out.println("space is far as :" + space);
					System.out.println("CompareSpace is far as :" + CompareSpace);

					return klist;
				} else {
					System.out.println("reach top point is:" + compare.point);
					if (rightcheck = true) {
						catchTop ++;
						//compare(searchTerm, NearestNode, resroot.leftChild);
						System.out.println("go to left child:" + resroot.leftChild.point);
						System.out.println("from parent :" + resroot.point);	
						System.out.println("top check is :" + catchTop);	
						System.out.println("anotherspace is far as:" + anotherspace);
						System.out.println("variance is far as :" + variance);
						System.out.println("space is far as :" + space);
						System.out.println("CompareSpace is far as :" + CompareSpace);
						findLeafNode(resroot.leftChild, searchTerm, true);
						System.out.println("");

						System.out.println("leaf is  :" + leafnode.point);
						System.out.println("leaf is far as :" + searchTerm.distTo(leafnode.point));
					} else {
						catchTop ++;
						//compare(searchTerm, NearestNode, resroot.rightChild);
						System.out.println("go to right child:" + resroot.rightChild.point);
						System.out.println("from parent :" + resroot.point);	
						System.out.println("top check is :" + catchTop);	
						System.out.println("anotherspace is far as:" + anotherspace);
						System.out.println("variance is far as :" + variance);
						System.out.println("space is far as :" + space);
						System.out.println("CompareSpace is far as :" + CompareSpace);	
						findLeafNode(resroot.rightChild, searchTerm, true);
						System.out.println("leaf is  :" + leafnode.point);
						System.out.println("leaf is far as :" + searchTerm.distTo(leafnode.point));
}					compare = leafnode.parent;	
					compare(searchTerm, leafnode, compare);

				}
			} else {
				if (compare.isright == true) {
					System.out.println("go parent from right side:" + compare.point);
					System.out.println("right check is :" + compare.isright);	
					System.out.println("top check is :" + catchTop);
					System.out.println("anotherspace is far as:" + anotherspace);
					System.out.println("variance is far as :" + variance);
					System.out.println("space is far as :" + space);
					System.out.println("CompareSpace is far as :" + CompareSpace);                    compare = compare.parent;
					compare(searchTerm, NearestNode, compare);
					rightcheck = compare.isright;
				} else {
					System.out.println("reach from left point is:" + compare.point);
					System.out.println("left check is :" + compare.isright);	
					System.out.println("top check is :" + catchTop);
					System.out.println("anotherspace is far as:" + anotherspace);
					System.out.println("variance is far as :" + variance);
					System.out.println("space is far as :" + space);
					System.out.println("CompareSpace is far as :" + CompareSpace);                    compare = compare.parent;
					compare(searchTerm, NearestNode, compare);
					rightcheck = compare.isright;
				}
			}
		}
		if (space > CompareSpace) {
			System.out.println("down start:");
			System.out.println("");

			if (compare.parent == null) {
				if (catchTop == 2) {
					
					space = CompareSpace;
					NearestNode = compare;
					klist.add(NearestNode.point);
					System.out.println("get catch top second time" + klist);
					return klist;
				}else{
					System.out.println("reach top point is:" + compare.point);
					if (rightcheck = true) {
						System.out.println("reach from left point is:" + compare.point);
						System.out.println("left check is :" + compare.isright);	
						System.out.println("top check is :" + catchTop);
						System.out.println("anotherspace is far as:" + anotherspace);
						System.out.println("variance is far as :" + variance);
						System.out.println("space is far as :" + space);
						System.out.println("CompareSpace is far as :" + CompareSpace); 
						
						findLeafNode(resroot.rightChild, searchTerm, true);

						catchTop ++;
						space = CompareSpace;
						NearestNode = compare;
						rightcheck = compare.isright;
						System.out.println("nearest is far as :" + NearestNode.point); 

					} else {
						System.out.println("reach from left point is:" + compare.point);
						System.out.println("left check is :" + compare.isright);	
						System.out.println("top check is :" + catchTop);
						System.out.println("anotherspace is far as:" + anotherspace);
						System.out.println("variance is far as :" + variance);
						System.out.println("space is far as :" + space);
						System.out.println("CompareSpace is far as :" + CompareSpace); 
						findLeafNode(resroot.leftChild, searchTerm, true);
						catchTop ++;
						space = CompareSpace;
						NearestNode = compare;
						rightcheck = compare.isright;
						System.out.println("nearest is far as :" + NearestNode.point); 

					}
					
					compare = leafnode.parent;	
					compare(searchTerm, leafnode, compare);

				}
			} else {
				if (rightcheck = true) {
					System.out.println("reach from left point is:" + compare.point);
					System.out.println("left check is :" + compare.isright);	
					System.out.println("top check is :" + catchTop);
					System.out.println("anotherspace is far as:" + anotherspace);
					System.out.println("variance is far as :" + variance);
					System.out.println("space is far as :" + space);
					System.out.println("CompareSpace is far as :" + CompareSpace); 

					space = CompareSpace;
					NearestNode = compare;
					rightcheck = compare.isright;
					compare = compare.leftChild;
					compare(searchTerm, NearestNode, compare);
					System.out.println("nearest is far as :" + NearestNode.point); 

				} else {
					System.out.println("reach from left point is:" + compare.point);
					System.out.println("left check is :" + compare.isright);	
					System.out.println("top check is :" + catchTop);
					System.out.println("anotherspace is far as:" + anotherspace);
					System.out.println("variance is far as :" + variance);
					System.out.println("space is far as :" + space);
					System.out.println("CompareSpace is far as :" + CompareSpace); 

					space = CompareSpace;
					NearestNode = compare;
					rightcheck = compare.isright;
					compare = compare.rightChild;
					compare(searchTerm, NearestNode, compare);
					System.out.println("nearest is far as :" + NearestNode.point); 

				}
			}
		}
		klist.add(NearestNode.point);
		return klist;
	}

	/*
	 * public void nearestPoint(Node root,Point points){ if(root == null){
	 * return; } nearestPoint(root.leftChild,points);
	 * nearestPoint.add(root.point); nearestPoint(root.rightChild,points); }
	 */
	/*
	 * public void inorder() { inorder(allroot); System.out.println(" "); }
	 * protected void inorder(Node root){ if (root == null) { return; }
	 * 
	 * inorder(root.leftChild); System.out.println(root.point+"");
	 * 
	 * 
	 * 
	 * inorder(root.rightChild); }
	 */

	/*
	 * if(leafnode.point.distTo(points)>root.point.distTo(points)){
	 * leafnode=root; findLeafNode(leafnode,points,checklat); } else{ return; }
	 * 
	 * 
	 * }
	 */

	@Override
	public boolean addPoint(Point point) {
		boolean check = true;
		findLeafNode(resroot, point, check);

		return false;
	}

	@Override
	public boolean deletePoint(Point point) {
		// To be implemented.
		return false;
	}

	@Override
	public boolean isPointIn(Point point) {
		// To be implemented.
		return false;
	}

}

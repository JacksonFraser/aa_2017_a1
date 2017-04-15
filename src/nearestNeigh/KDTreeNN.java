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
	public boolean rightcheck;
	public int level;

	public KDTreeNN() {
		allroot = null;
		resroot = null;
		hosroot = null;
		eduroot = null;
		leafnode = null;
		temp = null;
		catchTop = 0;
		level = 0;

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
		currentNode.setlevel(level);
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
			leftChild = buildTree(flip(leftPointList(points, median), Dm),temp, Dm);
			leftChild.setisright(false);
			leftChild.setlevel(level);
			leftChild.setparent(temp);

		}
		if (median +1 < points.size()) {
			temp = currentNode;
			rightChild = buildTree(flip(rightPointList(points, median+1), Dm),temp, Dm);
			rightChild.setisright(true);
			rightChild.setlevel(level);
			rightChild.setparent(temp);

		}
		if(leftChild!=null){
		currentNode.setleftChild(leftChild);}
		if(rightChild!=null){
		currentNode.setRightChild(rightChild);
		}
		level++;



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
    	boolean check =true;
    	ArrayList<Point> searchTermlist = new ArrayList<Point>();

    	List<Point> sortedList = new ArrayList<Point>();
    	if(searchTerm.cat.equals(res)){
    		findLeafNode(resroot,searchTerm,check);
    	}

		System.out.println("leaf is  :" + leafnode.point);
		System.out.println("leaf is far as :" + searchTerm.distTo(leafnode.point));

		
        rightcheck = leafnode.isright;
		Node compare1 = leafnode;
		Node parents = leafnode.parent;
		sortedList = compare(resroot,searchTerm,0,sortedList,k);
    	for (int i = sortedList.size()-1; i >= 0; i--){
    		searchTermlist.add(sortedList.get(i));
            System.out.println("nearest point:" + sortedList.get(i));
    	}
        return searchTermlist;		
		
			
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
			    if(root.leftChild == null){
			    	findLeafNode(root.rightChild, points, !checklat);
			    }

			    }

					// nearestPoint.add(leafnode.point);

				}
			}
		}

	List<Point> compare(Node T, Point value, int layer, List<Point> sortedList, int k) {
		
		if (T == null) {
			return sortedList;
		}
		Node left = T.leftChild;
		Node right = T.rightChild;
	
		if (left == null && right == null) {
			//nothing happens
	
		} else if (left == null) {
			sortedList = compare(right, value, layer + 1, sortedList, k);
	
		} else if (right == null) {
			sortedList = compare(left, value, layer + 1, sortedList, k);
	
		} else {
			//Choose which path to go down based on dimension
			//Also get bounding dist between current node and child node
	
			boolean goLeft = false;
			double divDist = 0;
			if (layer%2 == 0) {
				goLeft = T.point.lat <= value.lat;
				if (goLeft)
					divDist = Math.abs(T.point.lat - value.lat);
				else
					divDist = Math.abs(T.point.lat - value.lat);
			} else {
				goLeft = T.point.lon <= value.lon;
				if (goLeft)
					divDist = Math.abs(T.point.lon - value.lon);
				else
					divDist = Math.abs(T.point.lon - value.lon);
			}
	
			if (goLeft) {
				sortedList = compare(left, value, layer + 1, sortedList, k);
				if (sortedList.size() < k || divDist < sortedList.get(0).distTo(value))
					sortedList = compare(right, value, layer + 1, sortedList, k);
			} else {
				sortedList = compare(right, value, layer + 1, sortedList, k);
				if (sortedList.size() < k || divDist < sortedList.get(0).distTo(value))
					sortedList = compare(left, value, layer + 1, sortedList, k);
			}
		}
	
		if (value.cat.equals(T.point.cat)) {
			
			if (sortedList.size() == 0) {
				sortedList.add(T.point);
			}
			else if (k == 1) {
				if (sortedList.get(0).distTo(value) > T.point.distTo(value)) {
					sortedList.remove(0);
					sortedList.add(T.point);
				}
			}
			else if (sortedList.size() < k) {
				insertInOrder(T, sortedList, value);
			}
			else {
				for (int i = 0; i < sortedList.size(); i++) {
					if (sortedList.get(i) != null) {
						if (sortedList.get(i).distTo(value) > T.point.distTo(value)) {
							sortedList.remove(i);
							sortedList = insertInOrder(T, sortedList, value);
						}
						break;
					}
				}
			}
		}
		return sortedList;
	}

    public List<Point> insertInOrder(Node T, List<Point> sortedList, Point value) {
    	if (sortedList.size() == 0)
    		sortedList.add(T.point);

		for (int i = 0; i < sortedList.size(); i++) {
			if (sortedList.get(i) != null) {
				if (i == sortedList.size()-1)
					sortedList.add(T.point);
				else if (sortedList.get(i).distTo(value) < T.point.distTo(value))
					sortedList.add(i, T.point);
				else
					continue;
				break;
			}
		}
		return sortedList;
    }
	 

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

package nearestNeigh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class is required to be implemented. Kd-tree implementation.
 *
 * @author Jeffrey, Youhan
 */
public class KDTreeNN implements NearestNeigh {
	protected Node eduRoot;
	protected Node hosRoot;
	protected Node resRoot;

	List<Point> eduPointsList = new ArrayList<Point>();
	List<Point> hosPointsList = new ArrayList<Point>();
	List<Point> resPointsList = new ArrayList<Point>();
	List<Point> closestPoints = new ArrayList<Point>();

	Category edu = Category.EDUCATION;
	Category hos = Category.HOSPITAL;
	Category res = Category.RESTAURANT;
	int i = 0;

	public KDTreeNN() {
	}

	@Override
	public void buildIndex(List<Point> points) {
		String axis = "lon";

		for (int i = 0; i < points.size(); ++i) {
			if (points.get(i).cat.equals(edu))
				eduPointsList.add(points.get(i));
			if (points.get(i).cat.equals(hos))
				hosPointsList.add(points.get(i));
			if (points.get(i).cat.equals(res))
				resPointsList.add(points.get(i));

		}
		eduRoot = buildTree(eduPointsList, axis);
		hosRoot = buildTree(hosPointsList, axis);
		resRoot = buildTree(resPointsList, axis);

	}

	Node buildTree(List<Point> points, String axis) {
		List<Point> sortedPoints = sortPoints(points, changeAxis(axis));
		Node n = new Node();
		Node right = new Node();
		Node left = new Node();
		int median = findMedian(sortedPoints);

		n.point = sortedPoints.get(median);
		n.left = null;
		n.right = null;
		if(median == 0){
			return n;
		}
		if (median > 0) {
			left = buildTree(sortedPoints.subList(0, median), changeAxis(axis));
		}
		if (median  < sortedPoints.size()) {
			right = buildTree(sortedPoints.subList(median , sortedPoints.size()), changeAxis(axis));
		}
		n.setLeft(left);
		n.setRight(right);
		return n;

	}

	@Override
	public List<Point> search(Point searchTerm, int k) {
		String axis = "lon";
		Node eduNode = eduRoot;
		Node hosNode = hosRoot;
		Node resNode = resRoot;
		if (searchTerm.cat.equals(edu)) {
			findLeaf(searchTerm, eduNode, axis);
		}
		if (searchTerm.cat.equals(hos)) {
			findLeaf(searchTerm, hosNode, axis);
		}
		if (searchTerm.cat.equals(res)) {
			findLeaf(searchTerm, resNode, axis);
		}
		System.out.println("this is the closest point " + closestPoints.get(0).id);
		dist(searchTerm);
		return closestPoints;
	}

	@Override
	public boolean addPoint(Point point) {

		String axis = "lon";
		Node eduNode = eduRoot;
		Node hosNode = hosRoot;
		Node resNode = resRoot;
		if (point.cat.equals(edu)) {
			eduNode = insert(point, eduNode, axis);
			return true;
		}
		if (point.cat.equals(hos)) {
			hosNode = insert(point, hosNode, axis);
			return true;
		}
		if (point.cat.equals(res)) {
			resNode = insert(point, resNode, axis);
			return true;
		}

		return false;
	}

	@Override
	public boolean deletePoint(Point point) {
		return false;
	}

	@Override
	public boolean isPointIn(Point point) {
		if (point.cat.equals(edu)) {

		}
		if (point.cat.equals(hos)) {
			inorder(hosRoot);
		}
		if (point.cat.equals(res)) {
			inorder(resRoot);
		}

		return false;
	}

	public List<Point> sortPoints(List<Point> points, String axis) {
		Point p = new Point();
		List<Point> sortedList = points;
		if (axis.equals("lon")) {
			for (int i = 1; i < sortedList.size(); ++i) {
				p = sortedList.get(i);
				int j = i;
				while (j > 0 && sortedList.get(j - 1).lon > p.lon) {
					sortedList.set(j, sortedList.get(j - 1));
					j--;

				}
				sortedList.set(j, p);
			}
			return sortedList;
		} else {
			for (int i = 1; i < sortedList.size(); ++i) {
				p = sortedList.get(i);
				int j = i;
				while (j > 0 && sortedList.get(j - 1).lat > p.lat) {
					sortedList.set(j, sortedList.get(j - 1));
					j--;

				}
				sortedList.set(j, p);
			}
			return sortedList;

		}
	}

	public int findMedian(List<Point> sortedList) {
		int median = 0;
		median = sortedList.size() / 2;

		return median;
	}

	public String changeAxis(String axis) {
		if (axis.equals("lon")) {
			axis = "lat";
		} else {
			axis = "lon";
		}
		return axis;
	}

	public Node insert(Point point, Node root, String axis) {
		if (root.point == null) {
			root.point = point;
			return root;

		}
		if (axis.equals("lat")) {
			if (point.lat > root.point.lat) {
				root.right = insert(point, root.right, changeAxis(axis));
			} else if (point.lat < root.point.lat) {
				root.left = insert(point, root.left, changeAxis(axis));
			} else {
			}
			return root;
		} else {
			if (point.lon > root.point.lon) {
				root.right = insert(point, root.right, changeAxis(axis));
			} else if (point.lon < root.point.lon) {
				root.left = insert(point, root.left, changeAxis(axis));
			} else {
			}
			return root;
		}
	}

	public void findLeaf(Point point, Node root, String axis) {
		System.out.println(i + " visited " + root.point.id);
		i++;
		if (root.left.point == null && root.right.point == null) {
			System.out.println("leaf node id " + root.point.id);
			System.out.println();
			if (!closestPoints.isEmpty()) {
				closestPoints.set(0, root.point);
				return;
			} else {
				closestPoints.add(root.point);
				return;
			}

		}
		if (!closestPoints.isEmpty()) {
			if (point.distTo(root.point) < point.distTo(closestPoints.get(0)))
				closestPoints.set(0, root.point);

		}
		if (axis.equals("lat")) {
			searchLat(point, root, axis);
		}
		if (axis.equals("lon"))
			searchLon(point, root, axis);
	}

	public Node searchLat(Point point, Node root, String axis) {
		if (point.lat > root.point.lat) {
			if (root.right.point != null) {
				findLeaf(point, root.right, changeAxis(axis));
				if (!closestPoints.isEmpty()) {
					if (root.left.point != null) {
						if (point.distTo(closestPoints.get(0)) > point.distTo(root.left.point)) {
							findLeaf(point, root.left, axis);
						}
					}
				}
			}
		} else if (point.lat < root.point.lat) {
			if (root.left.point != null) {
				findLeaf(point, root.left, changeAxis(axis));
				if (!closestPoints.isEmpty()) {
					if (root.right.point != null) {
						if (point.distTo(closestPoints.get(0)) > point.distTo(root.right.point)) {
							findLeaf(point, root.right, axis);
						}
					}
				}
			}
		} else {
		}
		return root;
	}

	public Node searchLon(Point point, Node root, String axis) {
		if (point.lon > root.point.lon) {
			if (root.right.point != null) {
				findLeaf(point, root.right, changeAxis(axis));
				if (!closestPoints.isEmpty()) {
					if (root.left.point != null) {
						if (point.distTo(closestPoints.get(0)) > point.distTo(root.left.point)) {
							findLeaf(point, root.left, axis);
						}
					}
				}
			}

		} else if (point.lon < root.point.lon) {
			if (root.left.point != null) {
				findLeaf(point, root.left, changeAxis(axis));
				if (!closestPoints.isEmpty()) {
					if (root.right.point != null) {
						if (point.distTo(closestPoints.get(0)) > point.distTo(root.right.point)) {
							findLeaf(point, root.right, axis);
						}
					}
				}
			}

		} else {
		}
		return root;
	}

	protected void inorder(Node root) {
		if (root.point == null)
			return;
		inorder(root.left);
		inorder(root.right);

	}

	public void dist(Point point) {
		for (int i = 0; i < resPointsList.size(); ++i) {
			if (resPointsList.get(i).id.equals("id87")) {
				System.out.println("distance to 87 " + point.distTo(resPointsList.get(i)));
			}
			if (resPointsList.get(i).id.equals("id925")) {
				System.out.println("distance to 925 " + point.distTo(resPointsList.get(i)));
			}
			if (resPointsList.get(i).id.equals("id368")) {
				System.out.println("distance to 368 " + point.distTo(resPointsList.get(i)));
			}
			if (resPointsList.get(i).id.equals("id29")) {

				System.out.println("distance to 29 " + point.distTo(resPointsList.get(i)));
			}
			if (resPointsList.get(i).id.equals("id657")) {
				System.out.println("distance to 657 " + point.distTo(resPointsList.get(i)));
			}

		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

}

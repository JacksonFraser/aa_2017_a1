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
	Category edu = Category.EDUCATION;
	Category hos = Category.HOSPITAL;
	Category res = Category.RESTAURANT;

	public KDTreeNN() {
		eduRoot = null;
		hosRoot = null;
		resRoot = null;
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
		if (median > 0) {
			left = buildTree(sortedPoints.subList(0, median), changeAxis(axis));
		}
		if (median + 1 < points.size()) {
			right = buildTree(sortedPoints.subList(median + 1, points.size()), changeAxis(axis));
		}
		n.setLeft(left);
		n.setRight(right);
		return n;

	}

	@Override
	public List<Point> search(Point searchTerm, int k) {
		return new ArrayList<Point>();
	}

	@Override
	public boolean addPoint(Point point) {
		String axis = "lon";
		if (point.cat.equals(edu)) {
			eduRoot = insert(point, eduRoot,axis);
			return true;
		}
		if (point.cat.equals(hos)) {
			hosRoot = insert(point, hosRoot,axis);
			return true;
		}
		if (point.cat.equals(res)) {
			resRoot = insert(point, resRoot,axis);
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
		if (sortedList.size() % 2 == 0) {
			median = sortedList.size() / 2;
			median++;
		}
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

	public Node insert(Point point, Node root,String axis) {
		if (root.point == null) {
			root = new Node();
			root.point = point;
			return root;
		} else if (root.point.lat > point.lat) {
			root.right = insert(point, root.right, changeAxis(axis));
		} else if (root.point.lat < point.lat) {
			root.left = insert(point, root.left,changeAxis(axis));
		} else {
		}
		return root;
	}

}

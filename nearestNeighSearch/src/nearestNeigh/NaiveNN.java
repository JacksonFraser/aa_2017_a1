package nearestNeigh;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is required to be implemented. Naive approach implementation.
 *
 * @author Jeffrey, Youhan
 */
public class NaiveNN implements NearestNeigh {
	List<Point> nodesList = new ArrayList<Point>();

	@Override
	public void buildIndex(List<Point> points) {
		nodesList = points;
	}

	@Override
	public List<Point> search(Point searchTerm, int k) {
		List<Point> result = new ArrayList<Point>();
		Point p = new Point();
		List<Point> correctCat = new ArrayList<Point>();
		for (int i = 0; i < nodesList.size(); ++i) {
			if (searchTerm.cat.equals(nodesList.get(i).cat)) {
				correctCat.add(nodesList.get(i));
			}
		}
		for (int i = 1; i < correctCat.size(); ++i) {
			p = correctCat.get(i);
			int j = i;
			while (j > 0 && searchTerm.distTo(correctCat.get(j - 1)) > searchTerm.distTo(p)) {
				correctCat.set(j, correctCat.get(j - 1));
				j--;

			}
			correctCat.set(j, p);
		}
		for(int i = 0; i < k; ++i) {
			result.add(correctCat.get(i));
		}


	return result;

	}

	@Override
	public boolean addPoint(Point point) {
		for (int i = 0; i < nodesList.size(); ++i) {
			if (nodesList.get(i).equals(point)) {
				return false;
			}
		}
		nodesList.add(point);
		return true;
	}

	@Override
	public boolean deletePoint(Point point) {
		for (int i = 0; i < nodesList.size(); ++i) {
			if (nodesList.get(i).equals(point)) {
				nodesList.remove(i);
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean isPointIn(Point point) {
		for (int i = 0; i < nodesList.size(); ++i) {
			if (nodesList.get(i).equals(point)) {
				return true;
			}
		}
		return false;
	}

}

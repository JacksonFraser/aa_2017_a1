package nearestNeigh;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is required to be implemented.  Naive approach implementation.
 *
 * @author Jeffrey, Youhan
 */
public class NaiveNN implements NearestNeigh{
	List nodesList = new ArrayList<E>();

    @Override
    public void buildIndex(List<Point> points) {
    	nodesList.add(points);
    }

    @Override
    public List<Point> search(Point searchTerm, int k) {
    	ArrayList<Point> searchList;
        for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i) == searchTerm) {
        		searchList.add(searchTerm);
        	}
        }
        return searchList;
    }

    @Override
    public boolean addPoint(Point point) {
        for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i) == point) {
        		return false
        	}
        }
        nodesList.add(point);
        return return true;
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

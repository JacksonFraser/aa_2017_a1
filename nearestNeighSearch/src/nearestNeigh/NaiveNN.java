package nearestNeigh;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is required to be implemented.  Naive approach implementation.
 *
 * @author Jeffrey, Youhan
 */
public class NaiveNN implements NearestNeigh{
	List<Point> nodesList = new ArrayList<Point>();
    @Override
    public void buildIndex(List<Point> points) {
        for(int i = 0; i < points.size(); ++i) {
        	nodesList.add(points.get(i));
        }
    }

    @Override
    public List<Point> search(Point searchTerm, int k) {
    	List<Point> result = new ArrayList<Point>();
        for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i) == searchTerm) {
        		result.add(nodesList.get(i));
        	}
        }
        return result;
    }

    @Override
    public boolean addPoint(Point point) {
    	for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i) == point) {
        		return false;
        	}
    	}
        nodesList.add(point);
        return true;
    }

    @Override
    public boolean deletePoint(Point point) {
    	for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i).cat == point.cat && nodesList.get(i).id == point.id
        			&& nodesList.get(i).lat == point.lat && nodesList.get(i).lon == point.lon) {
        		nodesList.remove(i);
        		return true;
        	}
    	}
        return false;
    }

    @Override
    public boolean isPointIn(Point point) {
    	for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i) == point) {
        		return true;
        	}
    	}
        return false;
    }

}

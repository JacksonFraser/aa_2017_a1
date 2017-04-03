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
    	Point p = new Point();
    	List<Point> correctCat = new ArrayList<Point>();
    	for(int i = 0; i < nodesList.size(); ++i) {
    		if(searchTerm.cat.equals(nodesList.get(i).cat)){
    			correctCat.add(nodesList.get(i));
    		}
    	}
    	for(int i = 0; i < correctCat.size(); ++i) {
    		p = nodesList.get(i);
    		for(int j = 1; j < correctCat.size(); ++j) {
    			if(searchTerm.distTo(correctCat.get(j)) < searchTerm.distTo(p)) {
    				p = correctCat.get(j);
    				
    			}
    		}
    		result.add(p);
    			
    		
    		
    		if(result.size() == k)
    			return result;
    	}
    	
        return result;
    }
    @Override
    public boolean addPoint(Point point) {
    	for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i).id.equals(point.id)) {
        		return false;
        	}
    	}
        nodesList.add(point);
        return true;
    }

    @Override
    public boolean deletePoint(Point point) {
    	for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i).id.equals(point.id)) {
        		nodesList.remove(i);
        		return true;
        	}
    	}
        return false;
  
    }

    @Override
    public boolean isPointIn(Point point) {
    	for(int i = 0; i < nodesList.size(); ++i) {
        	if(nodesList.get(i).id.equals(point.id)) {
        		return true;
        	}
    	}
        return false;
    }

}

package nearestNeigh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is required to be implemented.  Naive approach implementation.
 *
 * @author Jeffrey, Youhan
 */
public class NaiveNN implements NearestNeigh{
	//public static List<Point> point = new ArrayList<Point>();
	public List<Point> Allpoint = new ArrayList<Point>();
	

    @Override
    public void buildIndex(List<Point> points) {  
        // To be implemented.
    	Allpoint=points;
    	
    	
    
    }

    @Override
    public List<Point> search(Point searchTerm, int k) {
    	List<Point> newPoint = new ArrayList<Point>();	
    	List<Point> catPoint = new ArrayList<Point>();
    	List<Point> nearestPoint = new ArrayList<Point>();
    	
    	newPoint = Allpoint;
    	
    	for(int i=0;i<newPoint.size();i++)
    	{
    		if(newPoint.get(i).cat.equals(searchTerm.cat)){
    			catPoint.add(newPoint.get(i));
    		}
    	}
    	for(int i=0;i<catPoint.size();i++){
    		for(int j=0;j<catPoint.size()-1;j++){
    			if(catPoint.get(j).distTo(searchTerm)>catPoint.get(j+1).distTo(searchTerm)){
    				Collections.swap(catPoint, j, j+1);		
    				}
    		}
    	}
    	for(int i=0;i<k;i++){
    		nearestPoint.add(catPoint.get(i));
    	}
    	
    	 return nearestPoint;
    }
    	
        // To be implemented.
    	
    	
       
    

    @Override
    public boolean addPoint(Point point) {
    	for(int i=0;i<Allpoint.size();++i){
    		if(Allpoint.get(i).equals(point))
    		{
    			
    			return false;	
    		}
    		
    	}
    	
    	Allpoint.add(point);
    	return true;
     
    }

    @Override
    public boolean deletePoint(Point point) {
    	
    	
    	for(int i= 0; i<Allpoint.size();++i)
    	{
    		if(Allpoint.get(i).equals(point))
    		{
    			Allpoint.remove(i);	
    			return true;
    		}
    	}
        return false;
    }

    @Override
    public boolean isPointIn(Point point) {
        // To be implemented.

    	for(int i= 0; i<Allpoint.size();++i)
    	{
    		if(point.equals(Allpoint.get(i)))
    		{
    			return true;
    		}
    	}
    	
        return false;
    }

}

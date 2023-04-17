import java.util.List;	 
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;

public class ClosestPair {

    /**
     * This is the method that sets off the computation
     * of closest pair.  It takes as input a list containing lines of input
     * as strings.  
     *
     * @return the distances between the closest pair and second closest pair 
     * with closest at position 0 and second at position 1 
     */
    public double[] compute(List<String> fileData) {
        Point[] points = parseDataToPoints(fileData);
        double[] closest = closestPair(points);
        return closest;
    }
    
    private class Point {
    	private double x;
    	private double y;
    	
    	public Point(double x, double y){
    		this.x = x;
    		this.y = y;
    	}
    	
    	public double getX() {
    		return x;
    	}
    	
    	public double getY() {
    		return y;
    	}
    	
    	public double getDistance(Point p) {
    		return Math.sqrt((this.x-p.x)*(this.x-p.x) + (this.y-p.y)*(this.y-p.y));
    	}
    	
    	public boolean equals(Object o) {
    		if (o == null)
    			return false;
    		if (!(o instanceof Point))
    			return false;
    		Point otherObject = (Point) o;
    		if ((this.x == otherObject.x) && (this.y == otherObject.y))
    			return true;
    		else
    			return false;
    	}
    	
    	@Override
    	public String toString() {
    		return "x: " + this.getX() + "     " + "y: " + this.getY();
    	}
    }
    
    private class CmpByX implements Comparator<Point> {

		@Override
		public int compare(Point o1, Point o2) {
			if (o1.getX() < o2.getX()) {
				return -1;
			}
			if (o1.getX() > o2.getX()) {
				return 1;
			}
			return 0;
		}  	
    }
    
    private class CmpByY implements Comparator<Point> {
		@Override
		public int compare(Point o1, Point o2) {
			if (o1.getY() < o2.getY()) {
				return -1;
			}
			if (o1.getY() > o2.getY()) {
				return 1;
			}
			return 0;
		} 	
    }
    
    //parse strings tht contain points information to Point data type
    public Point[] parseDataToPoints(List <String> fileData) {
    	Point[] result = new Point[fileData.size()];
    	for (int i = 0; i < fileData.size(); i++) {
    		String[] parts = fileData.get(i).split(" ");
    		double x = Double.parseDouble(parts[0]);
    		double y = Double.parseDouble(parts[1]);
    		result[i] = new Point(x, y);
    	}
    	return result;
    }
    
    //the function that inplements the Closest Pair algorithm
    public double[] closestPair(Point[] points){
    	double closestDistance1, closestDistance2;
    	if (points == null) {
    		throw new IllegalArgumentException("constructor argument is null");
    	}
    	for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("array element " + i + " is null");
        }
    	
    	if (points.length < 4) {
    		return bruteForceArray(points);
    	}
    	
    	// sort by x-coordinate
    	Point[] pointsByX = new Point[points.length];
    	for (int i = 0; i < points.length; i++) {
    		pointsByX[i] = points[i];
    	}
    	Arrays.sort(pointsByX, new CmpByX());	
    	
    	//get median by dividing length by 2
    	double median = pointsByX[points.length/2].getX();
    	
    	//partition points into two lists of points based on x-coordinate
    	Point[] left = new Point[pointsByX.length/2];
    	left = Arrays.copyOfRange(pointsByX, 0, points.length/2);

    	Point[] right = new Point[points.length - points.length/2];
    	right = Arrays.copyOfRange(pointsByX, points.length/2, points.length);
    	
    	//recursively compute the 2 closest distances in each list
    	double[] distance_L = closestPair(left);
    	double[] distance_R = closestPair(right);
    	
    	//find the actual 2 closest distances (2 in the left + 2 in the right --> eliminate to 2 in total)
    	double[] temp = {distance_L[0], distance_L[1], distance_R[0], distance_R[1]};
    	Arrays.sort(temp);
    	double[] distance_both = {temp[0], temp[1]};
    	
    	// construct list of points in the runway
    	// the width of the runway is determined by the larger "minimum distance" found in the recursive step
    	ArrayList<Point> pointsByY = new ArrayList<Point>(); 
    	for (Point each : pointsByX) {
    		double distanceToMedian = Math.abs(median - each.getX());
    		if (distanceToMedian <= temp[3]) {
    			pointsByY.add(each);
    		}
    	}
    	
    	// sort by y-coordinate
    	Collections.sort(pointsByY, new CmpByY());

    	if (pointsByY.size() <= 1) {
    		return distance_both;
    	}

    	double[] result = bruteForceArrayList(pointsByY); 

    	for (int i = 0; i < 2; i++) {
    		if (result[i] == distance_both[0] || result[i] == distance_both[1]) {
    			result[i] = Integer.MAX_VALUE;
    			break;
    		}	
    	}
	
    	double[] temp2 = {distance_both[0], distance_both[1], result[0], result[1]};
    	Arrays.sort(temp2);
    	closestDistance1 = temp2[0];
    	closestDistance2 = temp2[1];
    	double[] final_result = {closestDistance1, closestDistance2};
    	return final_result;    
    }
    
    public double[] bruteForceArray(Point[] points) {
    	double distance1 = Integer.MAX_VALUE;
    	double distance2 = Integer.MAX_VALUE;

    	for (int i = 0; i < points.length - 1; i++) {
    		for (int j = i+1; j < points.length; j++) {
    			double distance = points[i].getDistance(points[j]);
  
    			if (distance < distance1) {
    				distance2 = distance1;
    				distance1 = distance;
    			}
    			else if (distance < distance2) {
    				distance2 = distance;
    			}
    		}
    	}
    	
    	double[] result = {distance1, distance2};
    	return result;
    }
    
    public double[] bruteForceArrayList(ArrayList<Point> points) {
    	double distance1 = Integer.MAX_VALUE;
    	double distance2 = Integer.MAX_VALUE;
    	
    	for (int i = 0; i < points.size() - 1; i++) {
    		for (int j = i+1; j < points.size(); j++) {
    			double distance = points.get(i).getDistance(points.get(j));
    			if (distance < distance1) {
    				distance2 = distance1;
    				distance1 = distance;
    			}
    			else if (distance < distance2) {
    				distance2 = distance;
    			}
    		}
    	}
    	
    	double[] result = {distance1, distance2};
    	return result;
    }
}

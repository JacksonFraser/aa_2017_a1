package nearestNeigh;

import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TestDataGen {
	public static void main(String[] args) {

		TestDataGen t = new TestDataGen();

		// BuildIndex Test results
		List<Long> avgBuildTimes = t.testNaiveBuildIndex();
		t.printNaiveBuildTimes(avgBuildTimes);

		// Seach test results
		//List<Long> smallKAvg = t.testNaiveSearch(10);
		//List<Long> mediumKAvg = t.testNaiveSearch(20);
		//List<Long> largeKAvg = t.testNaiveSearch(30);
		System.out.println();
		System.out.println("*****NAIVE SEARCH TESTS*****");
		//t.printSmallKSearch(smallKAvg);
		//t.printMediumKSearch(mediumKAvg);
		//t.printLargeKSearch(largeKAvg);
		//t.writeToFile("nearestNeighSearch/src/sampleData50.txt", 50);

		// delete point result
		List<Long> avgDelTimes = t.testNaiveDeletePoint();
		t.printNaiveDelTimes(avgDelTimes);
		//List<Long> avgKdBuildTimes = t.testKdtreeBuildIndex();
		//t.printKdtreeBuildTimes(avgKdBuildTimes);
		List<Long>avgKdSearch = t.testKdtreeSearch(10);
		t.printKdSearch(avgKdSearch);
	}

	/*
	 * Creates the sample data file
	 * fileName will create a file of this name
	 * size is the number of points to create 
	 * */ 
	public void writeToFile(String fileName, int size) {
		double yMean = 145.0f;
		double yVariance = 8.0f;
		double xMean = 36.0f;
		double xVariance = 3.0f;

		Random xr = new Random();
		Random yr = new Random();
		try {
			for (int i = 0; i < size; ++i) {
				double yn = yMean + yr.nextGaussian() * yVariance;
				double xn = xMean + xr.nextGaussian() * xVariance;

				FileWriter fw = new FileWriter(fileName, true);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write("id" + i + " restaurant -" + xn + " " + yn);
				bw.newLine();
				bw.close();
			}
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
		}
	}
	
	//creates the points that are inside the given file
	public List<Point> createPointsList(String fileName) {
		String line = null;
		List<Point> pointsList = new ArrayList<Point>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {

				String delims[] = line.split(" ");
				String id = delims[0];
				Category cat = Point.parseCat(delims[1]);
				double lat = Double.parseDouble(delims[2]);
				double lon = Double.parseDouble(delims[3]);
				Point p = new Point(id, cat, lat, lon);
				pointsList.add(p);
			}
			br.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		return pointsList;

	}

	//tests NaiveNN buildIndex
	public List<Long> testNaiveBuildIndex() {
		List<Long> avgTimes = new ArrayList<Long>();
		TestDataGen t = new TestDataGen();
		List<Point> smallPointsList = new ArrayList<Point>();
		List<Point> mediumPointsList = new ArrayList<Point>();
		List<Point> largePointsList = new ArrayList<Point>();
		smallPointsList = t.createPointsList("nearestNeighSearch/src/sampleData5000.txt");
		mediumPointsList = t.createPointsList("nearestNeighSearch/src/sampleData10000.txt");
		largePointsList = t.createPointsList("nearestNeighSearch/src/sampleData20000.txt");
		long smallTotal = 0;
		long mediumTotal = 0;
		long largeTotal = 0;
		for (int i = 0; i < 20; i++) {
			NaiveNN smallNaive = new NaiveNN();
			NaiveNN mediumNaive = new NaiveNN();
			NaiveNN largeNaive = new NaiveNN();

			long smallStart = System.nanoTime();
			smallNaive.buildIndex(smallPointsList);
			long smallEnd = System.nanoTime();

			smallTotal = smallTotal + (smallEnd - smallStart);

			long mediumStart = System.nanoTime();
			mediumNaive.buildIndex(mediumPointsList);
			long mediumEnd = System.nanoTime();

			mediumTotal = mediumTotal + (mediumEnd - mediumStart);

			long largeStart = System.nanoTime();
			largeNaive.buildIndex(largePointsList);
			long largeEnd = System.nanoTime();

			largeTotal = largeTotal + (largeEnd - largeStart);
		}
		smallTotal = smallTotal / 20;
		mediumTotal = mediumTotal / 20;
		largeTotal = largeTotal / 20;

		avgTimes.add(smallTotal);
		avgTimes.add(mediumTotal);
		avgTimes.add(largeTotal);

		return avgTimes;
	}

	//tests NaiveNN search
	public List<Long> testNaiveSearch(int k) {
		List<Long> avgTimes = new ArrayList<Long>();
		TestDataGen t = new TestDataGen();
		List<Point> smallPointsList = new ArrayList<Point>();
		List<Point> mediumPointsList = new ArrayList<Point>();
		List<Point> largePointsList = new ArrayList<Point>();
		smallPointsList = t.createPointsList("nearestNeighSearch/src/sampleData5000.txt");
		mediumPointsList = t.createPointsList("nearestNeighSearch/src/sampleData10000.txt");
		largePointsList = t.createPointsList("nearestNeighSearch/src/sampleData20000.txt");
		long smallTotal = 0;
		long mediumTotal = 0;
		long largeTotal = 0;
		NaiveNN nS = new NaiveNN();
		NaiveNN nM = new NaiveNN();
		NaiveNN nL = new NaiveNN();
		nS.buildIndex(smallPointsList.subList(0, 1000));
		nM.buildIndex(mediumPointsList.subList(0, 2000));
		nL.buildIndex(largePointsList.subList(0, 3000));
		for (int i = 0; i < 5; i++) {
			long smallStart = System.nanoTime();
			nS.search(smallPointsList.get(500), k);
			long smallEnd = System.nanoTime();

			long mediumStart = System.nanoTime();
			nM.search(mediumPointsList.get(500), k);
			long mediumEnd = System.nanoTime();

			long largeStart = System.nanoTime();
			nL.search(largePointsList.get(500), k);
			long largeEnd = System.nanoTime();

			smallTotal = smallTotal + (smallEnd - smallStart);
			mediumTotal = mediumTotal + (mediumEnd - mediumStart);
			largeTotal = largeTotal + (largeEnd - largeStart);

		}
		smallTotal = smallTotal / 5;
		mediumTotal = mediumTotal / 5;
		largeTotal = largeTotal / 5;

		avgTimes.add(smallTotal);
		avgTimes.add(mediumTotal);
		avgTimes.add(largeTotal);

		return avgTimes;
	}
	//Tests NaiveNN Delete
	public List<Long> testNaiveDeletePoint() {
		List<Long> avgTimes = new ArrayList<Long>();
		TestDataGen t = new TestDataGen();
		List<Point> smallPointsList = new ArrayList<Point>();
		List<Point> mediumPointsList = new ArrayList<Point>();
		List<Point> largePointsList = new ArrayList<Point>();
		smallPointsList = t.createPointsList("nearestNeighSearch/src/sampleData5000.txt");
		mediumPointsList = t.createPointsList("nearestNeighSearch/src/sampleData10000.txt");
		largePointsList = t.createPointsList("nearestNeighSearch/src/sampleData20000.txt");
		long smallTotal = 0;
		long mediumTotal = 0;
		long largeTotal = 0;
		NaiveNN smallNaive = new NaiveNN();
		NaiveNN mediumNaive = new NaiveNN();
		NaiveNN largeNaive = new NaiveNN();
		for (int i = 0; i < 20; i++) {

			long smallStart = System.nanoTime();
			smallNaive.deletePoint(smallPointsList.get(3000));
			long smallEnd = System.nanoTime();

			smallTotal = smallTotal + (smallEnd - smallStart);

			long mediumStart = System.nanoTime();
			mediumNaive.deletePoint(mediumPointsList.get(3000));
			long mediumEnd = System.nanoTime();

			mediumTotal = mediumTotal + (mediumEnd - mediumStart);

			long largeStart = System.nanoTime();
			largeNaive.deletePoint(largePointsList.get(3000));
			long largeEnd = System.nanoTime();

			largeTotal = largeTotal + (largeEnd - largeStart);
		}
		smallTotal = smallTotal / 20;
		mediumTotal = mediumTotal / 20;
		largeTotal = largeTotal / 20;

		avgTimes.add(smallTotal);
		avgTimes.add(mediumTotal);
		avgTimes.add(largeTotal);

		return avgTimes;
	}
	public List<Long> testKdtreeBuildIndex() {
		List<Long> avgTimes = new ArrayList<Long>();
		TestDataGen t = new TestDataGen();
		List<Point> smallPointsList = new ArrayList<Point>();
		List<Point> mediumPointsList = new ArrayList<Point>();
		List<Point> largePointsList = new ArrayList<Point>();
		smallPointsList = t.createPointsList("nearestNeighSearch/src/sampleData5000.txt");
	//	mediumPointsList = t.createPointsList("nearestNeighSearch/src/sampleData10000.txt");
		//largePointsList = t.createPointsList("nearestNeighSearch/src/sampleData20000.txt");
		long smallTotal = 0;
		long mediumTotal = 0;
		long largeTotal = 0;
		KDTreeNN smallKdtree = new KDTreeNN();
		for (int i = 0; i < 20; i++) {

			long smallStart = System.nanoTime();
			smallKdtree.buildIndex(smallPointsList);
			long smallEnd = System.nanoTime();

			smallTotal = smallTotal + (smallEnd - smallStart);
/*
			long mediumStart = System.nanoTime();
			mediumNaive.buildIndex(mediumPointsList);
			long mediumEnd = System.nanoTime();

			mediumTotal = mediumTotal + (mediumEnd - mediumStart);

			long largeStart = System.nanoTime();
			largeNaive.buildIndex(largePointsList);
			long largeEnd = System.nanoTime();

			largeTotal = largeTotal + (largeEnd - largeStart);
	*/
		}
		smallTotal = smallTotal / 20;
	//	mediumTotal = mediumTotal / 20;
		//largeTotal = largeTotal / 20;

		avgTimes.add(smallTotal);
		//avgTimes.add(mediumTotal);
		//avgTimes.add(largeTotal);

		return avgTimes;
	}
	public List<Long> testKdtreeSearch(int k) {
		List<Long> avgTimes = new ArrayList<Long>();
		TestDataGen t = new TestDataGen();
		List<Point> smallPointsList = new ArrayList<Point>();
		List<Point> mediumPointsList = new ArrayList<Point>();
		List<Point> largePointsList = new ArrayList<Point>();
		smallPointsList = t.createPointsList("nearestNeighSearch/src/sampleData5000.txt");
		mediumPointsList = t.createPointsList("nearestNeighSearch/src/sampleData10000.txt");
		largePointsList = t.createPointsList("nearestNeighSearch/src/sampleData20000.txt");
		long smallTotal = 0;
		long mediumTotal = 0;
		long largeTotal = 0;
		KDTreeNN smallkd = new KDTreeNN();
		KDTreeNN mediumkd = new KDTreeNN();
		KDTreeNN largekd = new KDTreeNN();
		smallkd.buildIndex(smallPointsList.subList(0, 1000));
		mediumkd.buildIndex(mediumPointsList.subList(0, 2000));
		largekd.buildIndex(largePointsList.subList(0, 3000));
		for (int i = 0; i < 5; i++) {
			long smallStart = System.nanoTime();
			smallkd.search(smallPointsList.get(500), k);
			long smallEnd = System.nanoTime();

			long mediumStart = System.nanoTime();
			mediumkd.search(mediumPointsList.get(500), k);
			long mediumEnd = System.nanoTime();

			long largeStart = System.nanoTime();
			largekd.search(largePointsList.get(500), k);
			long largeEnd = System.nanoTime();

			smallTotal = smallTotal + (smallEnd - smallStart);
			mediumTotal = mediumTotal + (mediumEnd - mediumStart);
			largeTotal = largeTotal + (largeEnd - largeStart);

		}
		smallTotal = smallTotal / 5;
		mediumTotal = mediumTotal / 5;
		largeTotal = largeTotal / 5;

		avgTimes.add(smallTotal);
		avgTimes.add(mediumTotal);
		avgTimes.add(largeTotal);

		return avgTimes;
	}

	public void printSmallKSearch(List<Long> smallKAvg) {
		System.out.println();
		System.out.println("small list small k average time   = " + smallKAvg.get(0));
		System.out.println("medium list small k average time  = " + smallKAvg.get(1));
		System.out.println("large list small k average time   = " + smallKAvg.get(2));
		System.out.println();
	}

	public void printMediumKSearch(List<Long> mediumKAvg) {
		System.out.println();
		System.out.println("small list medium k average time  = " + mediumKAvg.get(0));
		System.out.println("medium list medium k average time = " + mediumKAvg.get(1));
		System.out.println("large list large k  average time  = " + mediumKAvg.get(2));
		System.out.println();
	}

	public void printLargeKSearch(List<Long> largeKAvg) {
		System.out.println();
		System.out.println("small list large k  average time  = " + largeKAvg.get(0));
		System.out.println("medium list large k average time  = " + largeKAvg.get(1));
		System.out.println("large list large k average time   = " + largeKAvg.get(2));
		System.out.println();
	}

	public void printNaiveBuildTimes(List<Long> avgBuildTimes) {
		System.out.println();
		System.out.println("*****NAIVE BUILD INDEX TESTS*****");
		System.out.println();
		System.out.println("small buildIndex() average time = " + avgBuildTimes.get(0));
		System.out.println("med buildIndex() average time = " + avgBuildTimes.get(1));
		System.out.println("large buildIndex() average time = " + avgBuildTimes.get(2));
		System.out.println();
	}
	public void printNaiveDelTimes(List<Long>avgDelTimes) {
		System.out.println();
		System.out.println("*****NAIVE DELETE POINT TESTS*****");
		System.out.println();
		System.out.println("small deletePoint() average time = " + avgDelTimes.get(0));
		System.out.println("med deletePoint() average time = " + avgDelTimes.get(1));
		System.out.println("large deletePoint() average time = " + avgDelTimes.get(2));
		System.out.println();
	}
	public void printKdtreeBuildTimes(List<Long>avgBuildTimes) {
		System.out.println();
		System.out.println("*****KDTREE BUILD INDEX TESTS*****");
		System.out.println();
		System.out.println("small buildIndex() average time = " + avgBuildTimes.get(0));
		System.out.println();
	}
	public void printKdSearch(List<Long> smallKdAvg) {
		System.out.println();
		System.out.println("*****KDTREE SEARCH TESTS*****");
		System.out.println();
		System.out.println("small list search average time   = " + smallKdAvg.get(0));
		System.out.println("medium list search average time  = " + smallKdAvg.get(1));
		System.out.println("large list search average time   = " + smallKdAvg.get(2));
		System.out.println();
	}
	

}

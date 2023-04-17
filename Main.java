import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {

	public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
		String testFile = "test4.txt";
		if (args.length == 0) {
			System.out.println("User did not specify test file, using default (test4.txt).");
		}
		else {
			testFile = args[0];
			System.out.println("Using " + testFile);
		}
		try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {
			String line;
			while ((line = br.readLine()) != null) {
			    lines.add(line.trim());
            }        

			System.out.println("Distances between the closest pair and second closest pair:");
			// Call method and print the result
            Long start = System.currentTimeMillis();
            ClosestPair cp = new ClosestPair();
            double[] res = cp.compute(lines);
            System.out.print(res[0] + ", ");
            System.out.println(res[1]);
            Long end = System.currentTimeMillis();
            System.out.println("time: " + ((end - start) / 1000.0));

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error occurred when reading file");
		}
	}

}

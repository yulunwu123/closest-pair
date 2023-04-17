# Closest Pair Algorithm

## Overview
This is a Java implementation of the Closest Pair Algorithm, which finds the two closest points in a set of points in a 2D space. The program prints out the closest distance and the second closest distance.

## Implementation Details
1. Read the input file and parse the points into an array of Point objects.
2. Sort the array of points by x-coordinates.
3. Recursively divide the array into left and right subsets, and find **two** closest pair of points in each subset.
4. Find the closest pairs of points that cross the dividing line between the left and right subsets.
5. Compare the distances of the closest and second closest pairs of points found in steps 3 and 4, and print out the results.

The algorithm has a time complexity of O(n log n), where n is the number of points in the set.

## Running the Program
A set of input points in a 2D space are formatted text files with each point on a separate line, and the x and y coordinates separated by a space. There are 4 test files given, if the user does not specify which one in command line, the program defaults to use test4.txt

```
javac Main.java ClosestPair.java

java Main
```

Output: 
```
User did not specify test file, using default (test4.txt).
Distances between the closest pair and second closest pair:
1.4142135623730951, 1.4142135623730951
time: 0.034
```

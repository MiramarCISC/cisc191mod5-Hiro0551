package edu.sdccd.cisc191;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Module 5 Lab: Recursion + Algorithms
 *
 * Reflection Questions:
 * 1. What is the base case for your recursive binary search?
 * 2. Why is recursion natural for the bracket tree?
 * 3. Why might the iterative tile-counting method be safer on a very large map?
 * 4. Which problems in this lab felt more natural with loops, and which felt more natural with recursion?
 */
public class GameAlgorithms {

    /**
     * Searches a sorted array of match IDs recursively.
     *
     * @param sortedMatchIds sorted in ascending order
     * @param target the match ID to find
     * @return index of target, or -1 if not found
     */
    public static int findMatchRecursive(int[] sortedMatchIds, int target) {
        return findMatchRecursiveHelper(sortedMatchIds, target, 0, sortedMatchIds.length - 1);
    }

    /**
     * Helper method for recursive binary search.
     *
     * @param sortedMatchIds sorted in ascending order
     * @param target the match ID to find
     * @param low starting index of the current search range
     * @param high ending index of the current search range
     * @return index of target, or -1 if not found
     */
    private static int findMatchRecursiveHelper(int[] sortedMatchIds, int target, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;

        // Target accquired
        if (sortedMatchIds[mid] == target) {
            return mid;
        }

        // Search left
        if (target < sortedMatchIds[mid]) {
            return findMatchRecursiveHelper(sortedMatchIds, target, low, mid - 1);
        }

        // Search right
        return findMatchRecursiveHelper(sortedMatchIds, target, mid + 1, high);
    }

    /**
     * Searches a sorted array of match IDs iteratively.
     *
     * @param sortedMatchIds sorted in ascending order
     * @param target the match ID to find
     * @return index of target, or -1 if not found
     */
    public static int findMatchIterative(int[] sortedMatchIds, int target) {

       int low = 0;
        int high = sortedMatchIds.length - 1;

        while (low <= high) {

            int mid = (low + high) / 2;

            if (sortedMatchIds[mid] == target) {
                return mid;
            }

            if (target < sortedMatchIds[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    /**
     * Counts connected walkable tiles recursively.
     * Walkable tiles are represented by '.'.
     * Blocked tiles can be any other character.
     *
     * This method should count the size of the connected region starting at (startRow, startCol).
     * Count only vertical and horizontal neighbors, not diagonals.
     *
     * @param map mutable map of tiles
     * @param startRow starting row
     * @param startCol starting column
     * @return number of connected walkable tiles
     */
    public static int countConnectedTilesRecursive(char[][] map, int startRow, int startCol) {
        // Out of bounds
        if (isOutOfBounds(map, startRow, startCol)) {
            return 0;
        }

        // Not walkable
        if (map[startRow][startCol] != '.') {
            return 0;
        }

        // Mark visited
        map[startRow][startCol] = 'V';

        int count = 1;

        // Search neighbors
        count += countConnectedTilesRecursive(map, startRow - 1, startCol);
        count += countConnectedTilesRecursive(map, startRow + 1, startCol);
        count += countConnectedTilesRecursive(map, startRow, startCol - 1);
        count += countConnectedTilesRecursive(map, startRow, startCol + 1);

        return count;
    }

    /**
     * Counts connected walkable tiles iteratively using an explicit stack.
     *
     * @param map mutable map of tiles
     * @param startRow starting row
     * @param startCol starting column
     * @return number of connected walkable tiles
     */
    public static int countConnectedTilesIterative(char[][] map, int startRow, int startCol) {
        Deque<CellPosition> stack = new ArrayDeque<>();
        stack.push(new CellPosition(startRow, startCol));

        int count = 0;

        while (!stack.isEmpty()) {

            CellPosition current = stack.pop();

            int row = current.row();
            int col = current.col();

            // Skip invalid positions
            if (isOutOfBounds(map, row, col)) {
                continue;
            }

            // Skip non-walkable or visited tiles
            if (map[row][col] != '.') {
                continue;
            }

            // Mark visited
            map[row][col] = 'V';

            count++;

            // Push neighbors
            pushNeighbor(stack, row - 1, col);
            pushNeighbor(stack, row + 1, col);
            pushNeighbor(stack, row, col - 1);
            pushNeighbor(stack, row, col + 1);
        }

        return count;
    }

    /**
     * Returns true if the tournament bracket contains a match with the given target name.
     * This public method should call a recursive helper.
     *
     * @param root root of the bracket tree
     * @param target match name to search for
     * @return true if found, false otherwise
     */
    public static boolean containsMatch(BracketNode root, String target) {
        return containsMatchHelper(root, target);
    }

    /**
     * Helper method for recursive bracket tree search.
     *
     * @param node current node
     * @param target match name to search for
     * @return true if found, false otherwise
     */
    private static boolean containsMatchHelper(BracketNode node, String target) {
        // Base case
        if (node == null) {
            return false;
        }

        // Found target
        if (node.getMatchName().equals(target)) {
            return true;
        }

        // Search left or right subtree
        return containsMatchHelper(node.getLeft(), target)
                || containsMatchHelper(node.getRight(), target);
    }

    /**
     * Optional utility students may use if they want to avoid repeating bounds checks.
     */
    public static boolean isOutOfBounds(char[][] map, int row, int col) {
        return row < 0 || row >= map.length || col < 0 || col >= map[row].length;
    }

    /**
     * Optional utility students may use in the iterative flood-fill.
     */
    public static void pushNeighbor(Deque<CellPosition> stack, int row, int col) {
        stack.push(new CellPosition(row, col));
    }
}

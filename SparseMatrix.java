// Kelsey Terrel

import java.util.ArrayList;
import java.util.*;

public class SparseMatrix implements SparseInterface {

    // Determines size:
    private int numRows;
    private int numCols;
    private ArrayList<ArrayList<Integer>> InputMatrix; // Creates an Array List
    // called InputMatrix

    // Constructor for if no arg given:
    public SparseMatrix() {
        // Default Size is 5
        numRows = 5;
        numCols = 5;
        InputMatrix = new ArrayList<ArrayList<Integer>>(5); // Creates a list
        // size 5
        // For each value, input number into matrix:
        for (int i = 0; i < 5; i++) {
            InputMatrix.add(new ArrayList<>(5));

        }
    }

    // Constructor for given arg:
    public SparseMatrix(int numRows, int numCols) {
        this.numRows = numRows; // Refer data member in case of later conflict
        this.numCols = numCols;
        InputMatrix = new ArrayList<ArrayList<Integer>>(this.numRows);
        // Creates new matrix from sparse matrix:
        for (int i = 0; i < numRows; i++) {
            InputMatrix.add(new ArrayList<>(numCols));
        }
    }

    // Clears contents of SparseMatrix:
    public void clear() {
        // filters through all numRows and columns
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                // when there is an element located, remove said element
                if (getElement(i, j) != 0) {
                    removeElement(i, j);
                }
            }
        }
    }

    // Sets size of SparseMatrix as input size:
    public void setSize(int numRows, int numCols) {
        clear(); // clears all current values
        this.numRows = numRows;
        this.numCols = numCols;
        SparseMatrix InputMatrix = new SparseMatrix(numRows, numCols); // repopulates
    }

    // Returns number of rows
    public int getNumRows() {
        return numRows;
    }

    // Returns number of columns
    public int getNumCols() {
        return numCols;
    }

    // Inputs given data at given location:
    public void addElement(int row, int col, int data) {
        // Handles zeros: if data = 0, does not store. Removes instead
        if (data == 0) {
            removeElement(row, col);
        } else {
            try {
                InputMatrix.get(row).set(col, data);
            }
            // if row/column combination out of bounds:
            catch (IndexOutOfBoundsException e) {
                ArrayList<Integer> rowArr = InputMatrix.get(row);
                while (rowArr.size() <= col) {
                    rowArr.add(null);
                }
                rowArr.set(col, data);
            }
        }
    }

    // Removes data from given location
    public void removeElement(int row, int col) {
        try {
            InputMatrix.get(row).set(col, null);
        } catch (IndexOutOfBoundsException e) {
            // if no value, catch exception
        }
    }

    // Returns data from given location
    public int getElement(int row, int col) {
        // if given location is out of bounds, throw exception
        if (row >= numRows || row < 0 || col >= numCols || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        int element;
        try {
            // seeks for data at given row & column
            element = InputMatrix.get(row).get(col);
        } catch (IndexOutOfBoundsException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
        return element;
    }


    // Outputs SparseMatrix as a string
    public String toString() {
        StringBuilder stringbuild = new StringBuilder();
        // Each time an element is located, prints that element
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (getElement(i, j) != 0) {
                    stringbuild.append(i + " " + j + " " + getElement(i, j) + "\n");
                }
            }
        }
        String text = stringbuild.toString();
        return text;
    }

    public SparseInterface addMatrices(SparseInterface matrixToAdd) {
        // Check Bounds: # of rows of matrix 1 = # of rows of matrix 2 && # cols of matrix 1 = # cols of matrix 2
        if (matrixToAdd.getNumRows() == numRows && matrixToAdd.getNumCols() == numCols) {
            SparseMatrix finalMatrix = new SparseMatrix(numRows, numCols);
            // Perform Addition:
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < matrixToAdd.getNumCols(); j++) {
                    int element = 0;
                    element += getElement(i, j) + matrixToAdd.getElement(i, j);
                    finalMatrix.addElement(i, j, element);
                }
            }
            return finalMatrix;
        }
        // Out of Bounds: Return Null
        else {
            return null;
        }
    }


    public SparseInterface multiplyMatrices(SparseInterface matrixToMultiply) {
        // Check Bounds: # of rows of matrix 1 = # of columns of matrix 2
        if (matrixToMultiply.getNumRows() == numCols) {
            SparseMatrix finalMatrix = new SparseMatrix(numRows, matrixToMultiply.getNumCols());
            // Perform multiplication
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < matrixToMultiply.getNumCols(); j++) {
                    int element = 0;
                    for (int k = 0; k < numCols; k++) {
                        element += getElement(i, k) * matrixToMultiply.getElement(k, j);
                    }
                    finalMatrix.addElement(i, j, element);
                }
            }
            return finalMatrix;
        }
        // Out of Bounds: Return Null
        else {
            return null;
        }
    }


}


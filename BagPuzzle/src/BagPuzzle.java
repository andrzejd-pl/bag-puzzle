import org.testng.mustache.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.assignments.DecisionOperator;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

import java.util.Arrays;

public class BagPuzzle {
    //dane w formacie numer_komórki,wiersz,kolumna
    int[][] puzzle_1 = new int[][]{
            {4,0,2},
            {5,0,7},
            {2,0,9},
            {3,1,3},
            {2,2,5},
            {3,3,2},
            {3,3,3},
            {3,3,8},
            {4,4,0},
            {4,5,9},
            {2,6,1},
            {2,6,7},
            {5,7,4},
            {4,8,7},
            {3,9,0},
            {2,9,2},
            {4,9,8}};

    int[][] puzzle_2 = new int[][]{{6,0,4},
            {3,1,2},
            {2,1,4},
            {2,1,9},
            {5,2,2},
            {5,2,9},
            {5,3,6},
            {5,3,7},
            {4,4,0},
            {3,4,1},
            {7,5,8},
            {7,5,9},
            {3,6,2},
            {5,6,3},
            {2,7,0},
            {5,7,7},
            {3,8,0},
            {2,8,5},
            {5,8,7},
            {5,9,5}};
    int[][] puzzle_3 = new int[][]{
            {2,0,2},
            {3,0,9},
            {4,1,4},
            {3,2,1},
            {3,2,3},
            {2,3,7},
            {3,5,1},
            {4,5,3},
            {3,5,6},
            {3,6,7},
            {2,7,0},
            {4,8,4},
            {3,8,8},
            {2,9,0},
            {4,9,6}};

}

import org.testng.mustache.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.search.loop.monitors.IMonitorSolution;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.assignments.DecisionOperator;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.ArrayUtils;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;

public class BagPuzzle extends Application {
    //dane w formacie numer_komórki,wiersz,kolumna
    Integer[][] puzzle_1 = new Integer[][]{
            {4, 0, 2},
            {5, 0, 7},
            {2, 0, 9},
            {3, 1, 3},
            {2, 2, 5},
            {3, 3, 2},
            {3, 3, 3},
            {3, 3, 8},
            {4, 4, 0},
            {4, 5, 9},
            {2, 6, 1},
            {2, 6, 7},
            {5, 7, 4},
            {4, 8, 7},
            {3, 9, 0},
            {2, 9, 2},
            {4, 9, 8}};

    Integer[][] puzzle_2 = new Integer[][]{
            {6, 0, 4},
            {3, 1, 2},
            {2, 1, 4},
            {2, 1, 9},
            {5, 2, 2},
            {5, 2, 9},
            {5, 3, 6},
            {5, 3, 7},
            {4, 4, 0},
            {3, 4, 1},
            {7, 5, 8},
            {7, 5, 9},
            {3, 6, 2},
            {5, 6, 3},
            {2, 7, 0},
            {5, 7, 7},
            {3, 8, 0},
            {2, 8, 5},
            {5, 8, 7},
            {5, 9, 5}};
    Integer[][] puzzle_3 = new Integer[][]{
            {2, 0, 2},
            {3, 0, 9},
            {4, 1, 4},
            {3, 2, 1},
            {3, 2, 3},
            {2, 3, 7},
            {3, 5, 1},
            {4, 5, 3},
            {3, 5, 6},
            {3, 6, 7},
            {2, 7, 0},
            {4, 8, 4},
            {3, 8, 8},
            {2, 9, 0},
            {4, 9, 6}};
    //rozwiązanie problemu (dwie tablice reprezentują poziome i pionowe linie siatki, każdy true to pogrubiona linia przy odpowiadającej komórce)
    Boolean[][] horizontal = new Boolean[][]{
            {true, true, false, false, false, false, false, false, false, false},
            {false, false, true, false, false, false, false, false, false, false},
            {false, false, false, true, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, true, false, false},
            {false, false, false, false, false, false, false, false, true, false},
            {false, false, false, false, false, false, false, false, false, true}
    };
    Boolean[][] vertical = new Boolean[][]{
            {false, false, false, false, false, false, false, false, false, false, true},
            {false, false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, false, false, false},
            {false, false, false, false, true, false, false, false, false, false, false},
            {false, false, false, false, false, true, false, false, false, false, false},
            {false, false, false, false, false, false, false, false, true, false, false},
            {false, false, false, false, false, false, false, false, true, false, false},
            {false, false, false, false, false, false, false, false, true, false, false},
            {false, false, false, false, false, false, false, false, false, false, false},
            {true, false, false, false, false, false, false, false, false, false, false}
    };


    private static final int NUM_PER_ROW = 10;
    private static final int TILE_SIZE = 50;
    private static final int GAME_SQUARE_SIZE = NUM_PER_ROW * TILE_SIZE;

    private Pane drawPuzzle(Integer[][] puzzle) {
        Pane gameSquare = new Pane();
        gameSquare.setPrefSize(GAME_SQUARE_SIZE, GAME_SQUARE_SIZE);

        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (int i = 0; i < NUM_PER_ROW * NUM_PER_ROW; i++) {
            tiles.add(new Tile());
        }

        for (int i = 0; i < puzzle.length; i++) {
            tiles.get(puzzle[i][1] * NUM_PER_ROW + puzzle[i][2]).setText(puzzle[i][0].toString());
        }

        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);
            tile.setTranslateX(TILE_SIZE * (i % NUM_PER_ROW));
            tile.setTranslateY(TILE_SIZE * (i / NUM_PER_ROW));
            gameSquare.getChildren().add(tile);
        }

        ArrayList<Line> hlines = new ArrayList<Line>();
        for (int i = 0; i < NUM_PER_ROW; i++)
            for (int j = 0; j < NUM_PER_ROW + 1; j++)
                if (horizontal[j][i]) {
                    hlines.add(new Line(0.5 + i * TILE_SIZE, 0.5 + j * TILE_SIZE, 0.5 + (i + 1) * TILE_SIZE, 0.5 + j * TILE_SIZE));
                }

        for (int i = 0; i < hlines.size(); i++) {
            Line line = hlines.get(i);
            line.setStrokeWidth(3);
            gameSquare.getChildren().add(line);
        }

        ArrayList<Line> vlines = new ArrayList<Line>();
        for (int i = 0; i < NUM_PER_ROW + 1; i++)
            for (int j = 0; j < NUM_PER_ROW; j++)
                if (vertical[j][i]) {
                    vlines.add(new Line(0.5 + i * TILE_SIZE, 0.5 + j * TILE_SIZE, 0.5 + i * TILE_SIZE, 0.5 + (j + 1) * TILE_SIZE));
                }

        for (int i = 0; i < vlines.size(); i++) {
            Line line = vlines.get(i);
            line.setStrokeWidth(3);
            gameSquare.getChildren().add(line);
        }
        return gameSquare;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button solveBtn = new Button("Solve Puzzle");
        solveBtn.setMaxWidth(Double.MAX_VALUE);
        Button exitBtn = new Button("Exit");
        exitBtn.setMaxWidth(Double.MAX_VALUE);
        exitBtn.setOnAction(e -> Platform.exit());

        VBox controls = new VBox(10);
        controls.getChildren().addAll(solveBtn, exitBtn);

        HBox root = new HBox(10);
        root.setStyle("-fx-padding: 10;");
        root.getChildren().addAll(drawPuzzle(puzzle_2), controls);

        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Bag Puzzle Solver");
        primaryStage.show();
    }

    private class Tile extends StackPane {
        private Text text = new Text();

        public Tile() {
            Rectangle border = new Rectangle(TILE_SIZE, TILE_SIZE);
            border.setFill(Color.WHITE);
            border.setStroke(Color.BLACK);

            text.setText("");
            text.setFont(Font.font(30));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }

        public void setText(String value) {
            text.setText(value);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

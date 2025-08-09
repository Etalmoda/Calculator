import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculator extends Application {

  private TextField display;
  private double num1 = 0;
  private String operator = "";
  private boolean startNewNumber = false;

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Basic Calculator");

    // Create display
    display = new TextField("0");
    display.setEditable(false);
    display.setPrefHeight(50);

    // Create button grid
    GridPane grid = new GridPane();
    grid.setHgap(5);
    grid.setVgap(5);
    grid.setPadding(new Insets(10));

    // Add number buttons (7, 8, 9 in first row)
    grid.add(createNumberButton("7"), 0, 0);
    grid.add(createNumberButton("8"), 1, 0);
    grid.add(createNumberButton("9"), 2, 0);
    grid.add(createOperatorButton("+"), 3, 0);

    // Add more rows (4, 5, 6)
    grid.add(createNumberButton("4"), 0, 1);
    grid.add(createNumberButton("5"), 1, 1);
    grid.add(createNumberButton("6"), 2, 1);
    grid.add(createOperatorButton("-"), 3, 1);

    // Add more rows (1, 2, 3)
    grid.add(createNumberButton("1"), 0, 2);
    grid.add(createNumberButton("2"), 1, 2);
    grid.add(createNumberButton("3"), 2, 2);
    grid.add(createOperatorButton("*"), 3, 2);

    // Last row (0, Clear, =, /)
    grid.add(createNumberButton("0"), 0, 3);
    grid.add(createClearButton(), 1, 3);
    grid.add(createEqualsButton(), 2, 3);
    grid.add(createOperatorButton("/"), 3, 3);

    // Layout everything
    VBox root = new VBox(10);
    root.setPadding(new Insets(10));
    root.getChildren().addAll(display, grid);

    Scene scene = new Scene(root, 250, 300);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private Button createNumberButton(String number) {
    Button button = new Button(number);
    button.setPrefSize(50, 50);
    button.setOnAction(e -> handleNumber(number));
    return button;
  }

  private Button createOperatorButton(String op) {
    Button button = new Button(op);
    button.setPrefSize(50, 50);
    button.setOnAction(e -> handleOperator(op));
    return button;
  }

  private Button createClearButton() {
    Button button = new Button("C");
    button.setPrefSize(50, 50);
    button.setOnAction(e -> clear());
    return button;
  }

  private Button createEqualsButton() {
    Button button = new Button("=");
    button.setPrefSize(50, 50);
    button.setOnAction(e -> calculate());
    return button;
  }

  private void handleNumber(String number) {
    if (startNewNumber) {
      display.setText(number);
      startNewNumber = false;
    } else {
      String current = display.getText();
      if (current.equals("0")) {
        display.setText(number);
      } else {
        display.setText(current + number);
      }
    }
  }

  private void handleOperator(String op) {
    if (!operator.isEmpty() && !startNewNumber) {
      calculate();
    }

    num1 = Double.parseDouble(display.getText());
    operator = op;
    startNewNumber = true;
  }

  private void calculate() {
    if (operator.isEmpty()) {
      return;
    }

    double num2 = Double.parseDouble(display.getText());
    double result = 0;

    switch (operator) {
      case "+":
        result = num1 + num2;
        break;
      case "-":
        result = num1 - num2;
        break;
      case "*":
        result = num1 * num2;
        break;
      case "/":
        if (num2 != 0) {
          result = num1 / num2;
        } else {
          display.setText("Error");
          return;
        }
        break;
    }

    display.setText(String.valueOf(result));
    operator = "";
    startNewNumber = true;
  }

  private void clear() {
    display.setText("0");
    num1 = 0;
    operator = "";
    startNewNumber = false;
  }

  public static void main(String[] args) {
    launch(args);
  }
}

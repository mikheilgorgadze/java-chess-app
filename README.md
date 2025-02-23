# Chess Game

This is a Java-based chess game application with a graphical user interface (GUI) built using Swing. The project allows users to play chess by interacting with a visual chessboard.

## Features

- Graphical representation of a chessboard and pieces.
- Mouse interaction to select and move pieces.
- FEN (Forsyth-Edwards Notation) support for setting up the initial board state.
- Highlighting of selected squares.

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/chess-game.git
    cd chess-game
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn exec:java -Dexec.mainClass="org.chess.ChessGame"
    ```

## Usage

- Launch the application.
- Click on a piece to select it.
- Click on a destination square to move the selected piece.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
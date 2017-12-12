import java.util.*;

public class Computer {
  Piece [][] board;
  Set<Piece> pieces = new HashSet<>();

  public Computer(Piece [][] in) {
    board = in;
    for (Piece [] columns : board) {
      for (Piece pos : columns) {
        if (pos != null) {
          if (!pos.type()) {
            pieces.add(pos);
          }
        }
      }
    }
  }

  public void makeMove() {
    HashMap<Piece, Set<Tuple>> possible = new HashMap<>();
    for (Piece side : pieces) {
      possible.put(side, side.move(board));
    }

    List<Piece> selection = new ArrayList<>(possible.keySet());

    Random generator = new Random();

    Piece chosen = selection.get(generator.nextInt(selection.size()));

    List<Tuple> moves = new ArrayList<>(possible.get(chosen));

    Tuple picked = moves.get(generator.nextInt(moves.size()));

    board[chosen.getRow()][chosen.getColumn()] = null;
    chosen.setX(picked.getFirst());
    chosen.setY(picked.getSecond());
    board[chosen.getRow()][chosen.getColumn()] = chosen;
  }

  public void removePiece(Piece in) {
    pieces.remove(in);
  }
}

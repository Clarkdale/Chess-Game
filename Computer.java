import java.util.*;

public class Computer {
  Piece [][] board;
  Set<Piece> pieces;
  Set<Piece> others;

  public Computer(Piece [][] in) {
    board = in;
    pieces = new HashSet<>();
    others = new HashSet<>();
    for (Piece [] columns : board) {
      for (Piece pos : columns) {
        if (pos != null) {
          if (!pos.type()) {
            pieces.add(pos);
          } else {
            others.add(pos);
          }
        }
      }
    }
  }

  public void randomMove() {
    HashMap<Piece, Set<Tuple>> possible = new HashMap<>();
    for (Piece side : pieces) {
      possible.put(side, side.move(board));
    }

    List<Piece> selection = new ArrayList<>(possible.keySet());

    Random generator = new Random();

    Piece chosen = selection.get(generator.nextInt(selection.size()));

    int x = chosen.getColumn();
    int y = chosen.getRow();

    List<Tuple> moves = new ArrayList<>(possible.get(chosen));

    Tuple picked = moves.get(generator.nextInt(moves.size()));

    while (picked.getFirst() == x && picked.getSecond() == y) {
      chosen = selection.get(generator.nextInt(selection.size()));
      x = chosen.getColumn();
      y = chosen.getRow();
      moves = new ArrayList<>(possible.get(chosen));

      picked = moves.get(generator.nextInt(moves.size()));
    }
    chosen.setX(picked.getFirst());
    chosen.setY(picked.getSecond());
    board[y][x] = null;
    board[chosen.getRow()][chosen.getColumn()] = chosen;
  }

  public void makeMove() {
    int max = -9999;
    Piece picked = null;
    Tuple location = null;
  }


  public int checkSquare(Tuple in, Piece [][] game) {
    if (game[in.getSecond()][in.getFirst()] != null) {
      Piece holder = game[in.getSecond()][in.getFirst()];
      if (holder instanceof Pawn) {
        return 10;
      } else if (holder instanceof Knight) {
        return 30;
      } else if (holder instanceof Bishop) {
        return 30;
      } else if (holder instanceof Rook) {
        return 50;
      } else if (holder instanceof Queen) {
        return 90;
      } else if (holder instanceof King) {
        return 500;
      }
    }

    return 0;
  }

  public void removePiece(Piece in) {
    pieces.remove(in);
  }
}

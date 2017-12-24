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
    HashMap<Piece, Set<Tuple>> possible = new HashMap<>();
    for (Piece side : pieces) {
      possible.put(side, side.move(board));
    }

    Piece picked = null;

    Tuple bestMove = null;

    int bestVal = -1;

    for (Piece keys : possible.keySet()) {
      for (Tuple rank : possible.get(keys)) {
        int evaluation = checkSquare(rank);
        if (evaluation > bestVal) {
          picked = keys;
          bestMove = rank;
          bestVal = evaluation;
        }
      }
    }

    if (bestVal < 0) {
      randomMove();
      System.out.println("Moved.");
    } else {
      int x = picked.getColumn();
      int y = picked.getRow();
      picked.setX(bestMove.getFirst());
      picked.setY(bestMove.getSecond());
      board[y][x] = null;
      board[picked.getRow()][picked.getColumn()] = picked;
      System.out.println("Moved.");
    }
  }

  public int checkSquare(Tuple in) {
    if (board[in.getSecond()][in.getFirst()] != null) {
      Piece holder = board[in.getSecond()][in.getFirst()];
      boolean other = holder.type();
      if (other) {
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
          return 1;
        }
      }
    }

    return -1;
  }

  public void removePiece(Piece in) {
    pieces.remove(in);
  }
}

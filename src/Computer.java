/*====================================================================
    Class Name:  Computer
       Purpose:  AI for human vs computer
  Parent Class:  None
====================================================================*/
import java.util.*;

public class Computer {
  Piece [][] board;
  Set<Piece> pieces;
  Set<Piece> others;

  public Computer(Piece [][] in) {
    board = in;
    pieces = new HashSet<>();
    others = new HashSet<>();
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

  public Piece [][] deepCopy(Piece [][] in) {
    Piece [][] out = new Piece [8][8];
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece square = in[i][j];
        if (square != null) {
          if (square instanceof Pawn) {
            out[i][j] = new Pawn(j, i, square.type());
          } else if (square instanceof Rook) {
            out[i][j] = new Rook(j, i, square.type());
          } else if (square instanceof Knight) {
            out[i][j] = new Knight(j, i, square.type());
          } else if (square instanceof Bishop) {
            out[i][j] = new Bishop(j, i, square.type());
          } else if (square instanceof Queen) {
            out[i][j] = new Queen(j, i, square.type());
          } else if (square instanceof King) {
            out[i][j] = new King(j, i, square.type());
          } else {
            out[i][j] = null;
          }
        }
      }
    }

    return out;
  }

  public Set<Piece> getPieces(Piece [][] in, boolean type) {
    Set<Piece> out = new HashSet<>();
    for (Piece [] row : board) {
      for (Piece hand : row) {
        if (hand != null && hand.type() == type) {
          out.add(hand);
        }
      }
    }
    return out;
  }

  public void makeMove() {
    int max = 0;
    Piece picked = null;
    Tuple location = null;

    pieces = getPieces(board, false);

    for (Piece side : pieces) {
      Set<Tuple> possible = side.move(board);

      for (Tuple potential : possible) {
        int score = minimax(0, 1, 0, board, true);
        if (score >= max) {
          picked = side;
          location = potential;
          max = score;
        }
      }
    }

    board[picked.getRow()][picked.getColumn()] = null;
    picked.setX(location.getFirst());
    picked.setY(location.getSecond());
    board[location.getSecond()][location.getFirst()] = picked;
  }

  public int minimax(int depth, int maxDepth, int rank, Piece [][] game, boolean turn) {
    Piece [][] copy = deepCopy(game);

    Set<Piece> own = getPieces(copy, false);
    Set<Piece> opponent = getPieces(copy, true);

    if (depth == maxDepth) {
      int max = 0;
      for (Piece toMove : own) {
        Set<Tuple> moveSet = toMove.move(copy);
        for (Tuple move : moveSet) {
          int squareValue = checkSquare(move, copy);
          if (squareValue > max) {
            max = squareValue;
          }
        }
      }

      return rank + max;
    }

    if (turn) {
      int localMax = 0;
      int caught;
      for (Piece look : own) {
        Set<Tuple> moveSet = look.move(copy);
        for (Tuple move : moveSet) {
          copy[look.getRow()][look.getColumn()] = null;
          look.setX(move.getFirst());
          look.setY(move.getSecond());
          copy[move.getSecond()][move.getFirst()] = look;
          caught = minimax(depth, maxDepth, rank, copy, !turn);
          if (caught > localMax) {
            localMax = caught;
          }
        }
      }

      return rank + localMax;

    } else {
      int localMax = 0;
      int caught;
      for (Piece look : opponent) {
        Set<Tuple> moveSet = look.move(copy);
        for (Tuple move : moveSet) {
          copy[look.getRow()][look.getColumn()] = null;
          look.setX(move.getFirst());
          look.setY(move.getSecond());
          copy[move.getSecond()][move.getFirst()] = look;
          caught = minimax(depth++, maxDepth, rank, copy, !turn);
          if (caught > localMax) {
            localMax = caught;
          }
        }
      }

      return rank - localMax;
    }
  }


  public int checkSquare(Tuple in, Piece [][] game) {
    Piece holder = game[in.getSecond()][in.getFirst()];
    if (holder != null && holder.type()) {
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

  public void dummyMove() {
    int max = 0;
    Piece picked = null;
    Tuple location = null;

    pieces = getPieces(board, false);

    for (Piece side : pieces) {
      Set<Tuple> possible = side.move(board);

      for (Tuple potential : possible) {
        int score = checkSquare(potential, board);
        if (score > max) {
          picked = side;
          location = potential;
          max = score;
        }
      }
    }

    if (max == 0) {
      randomMove();
    } else {
      board[picked.getRow()][picked.getColumn()] = null;
      picked.setX(location.getFirst());
      picked.setY(location.getSecond());
      board[location.getSecond()][location.getFirst()] = picked;
    }
  }

  public void removePiece(Piece in) {
    pieces.remove(in);
  }
}

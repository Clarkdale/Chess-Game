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

  public void makeMove() {
    int max = 0;
    Piece picked = null;
    Tuple location = null;

    Piece [][] outsideCopy = deepCopy(board);

    Set<Piece> blacks = new HashSet<>();

    for (Piece [] row : outsideCopy) {
      for (Piece potential : row) {
        if (potential != null && !potential.type()) {
          blacks.add(potential);
        }
      }
    }

    for (Piece check : blacks) {

      Set<Tuple> moves = check.move(outsideCopy);

      for (Tuple mind : moves) {
        int x = check.getColumn();
        int y = check.getRow();

        outsideCopy[y][x] = null;

        check.setX(mind.getFirst());
        check.setY(mind.getSecond());
        outsideCopy[mind.getSecond()][mind.getFirst()] = check;

        int num = minimax(0, 0, outsideCopy, true);

        if (num >= max) {
          max = num;
          picked = board[y][x];
          location = mind;
        }
      }
    }

    board[picked.getRow()][picked.getColumn()] = null;
    picked.setX(location.getFirst());
    picked.setY(location.getSecond());
    board[location.getSecond()][location.getFirst()] = picked;
  }

  public int minimax(int depth, int maxDepth, Piece [][] game, boolean turn) {
    int max = 0;

    Piece [][] copy = deepCopy(game);


    Set<Piece> whiteSide = new HashSet<>();
    Set<Piece> blackSide = new HashSet<>();

    for (Piece [] rows : copy) {
      for (Piece individ : rows) {
        if (individ != null && individ.type()) {
          whiteSide.add(individ);
        } else if (individ != null && !individ.type()) {
          blackSide.add(individ);
        }
      }
    }

    if (depth == maxDepth) {
      for (Piece last : blackSide) {
        Set<Tuple> moves = last.move(game);
        Tuple toRemove = new Tuple(last.getColumn(), last.getRow());
        moves.remove(toRemove);
        for (Tuple move : moves) {
          int checked = checkSquare(move, game);
          if (checked > max) {
            max = checked;
          }
        }
      }

      return max;
    }

    int caught;

    int secondMax = 0;
    if (turn) {
      for (Piece toMove : blackSide) {
        Set<Tuple> possible = toMove.move(game);
        Tuple toRemove = new Tuple(toMove.getColumn(), toMove.getRow());
        possible.remove(toRemove);
        for (Tuple slide : possible) {
          copy[toMove.getRow()][toMove.getColumn()] = null;
          toMove.setX(slide.getFirst());
          toMove.setY(slide.getSecond());
          copy[slide.getSecond()][slide.getFirst()] = toMove;
          caught = minimax(depth, maxDepth, copy, !turn);
          if (caught > secondMax) {
            secondMax = caught;
          }
        }
      }

      return max + secondMax;

    } else {
      for (Piece toMove : whiteSide) {
        Set<Tuple> possible = toMove.move(game);
        Tuple toRemove = new Tuple(toMove.getColumn(), toMove.getRow());
        possible.remove(toRemove);
        for (Tuple slide : possible) {
          copy[toMove.getRow()][toMove.getColumn()] = null;
          toMove.setX(slide.getFirst());
          toMove.setY(slide.getSecond());
          copy[slide.getSecond()][slide.getFirst()] = toMove;
          caught = minimax(depth++, maxDepth, copy, !turn);
          if (caught > secondMax) {
            secondMax = caught;
          }
        }
      }

      return max - secondMax;
    }
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

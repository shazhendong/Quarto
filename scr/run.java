import java.util.ArrayList;


public class run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QuartoBoard board = new QuartoBoard(5, 5, 32, null);
		board.insertPieceOnBoard(3, 2, 1);
		board.insertPieceOnBoard(3, 1, 2);
		board.insertPieceOnBoard(3, 3, 3);
		board.insertPieceOnBoard(3, 4, 4);
		//SimulationTask task = new SimulationTask(board);
		//int score = 0;
		System.out.println("-------------------------TEST START-------------------------");
		/*
		for(int i = 0; i < 100; i++){
			//int t = task.PlayFromMov(5,0,0);
			int t = task.PlayFromSelection(5);
			System.out.println(t);
			score += t;
		}
		*/
		//System.out.println("the score is\t"+score);
		long start = System.currentTimeMillis();
		DecisionMaking task = new DecisionMaking();
		int bestpick = SZD2(board);
		System.out.println("Best Pick is:\t" + bestpick);
		long stop = System.currentTimeMillis();
		long duartion = stop - start;
		System.out.println("Duration: "+duartion);
		start = System.currentTimeMillis();
		DecisionMaking task2 = new DecisionMaking();
		int [] bestmove = SZD1(board, 5);
		//int [] bestmove = task2.BestMove(5, board);
		System.out.println("Best mov is:\t" + bestmove[0] + "\t" + bestmove[1]);
		stop = System.currentTimeMillis();
		duartion = stop - start;
		System.out.println("Duration: "+duartion);
		System.out.println("Finsh!!");
		
		}
	public static int[] SZD1(QuartoBoard quartoBoard, int piece){
		DecisionMaking task2 = new DecisionMaking();
		int [] bestmove = task2.BestMove(piece, quartoBoard);
		return bestmove;
		}
	public static int SZD2(QuartoBoard quatoBoard){
		int bestPick;
DecisionMaking task = new DecisionMaking();
		bestPick = task.BestPick(quatoBoard);
		return bestPick;
	}

	
}
class DecisionMaking{
	//private QuartoBoard quartoBoard;
	public DecisionMaking(){
		//quartoBoard = new QuartoBoard(board);
	}
	private boolean isAdjacent(QuartoBoard board, int r, int c){
		if(board.isSpaceTaken(r, c)||board.isSpaceTaken(r-1, c-1)||board.isSpaceTaken(r, c-1)||
				board.isSpaceTaken(r+1, c-1)||board.isSpaceTaken(r-1, c)||board.isSpaceTaken(r+1, c)||
				board.isSpaceTaken(r-1, c+1)||board.isSpaceTaken(r, c+1)||board.isSpaceTaken(r+1, c+1)){
			return true;
		}else{
			return false;
		}
	}
	public int[] BestMove(int piece, QuartoBoard quartoBoard){
		int best_move_row = -1;
		int best_move_col = -1;
		//int best_pick = -1;
		ArrayList<Integer> possibleMov_row = new ArrayList<Integer>();
		ArrayList<Integer> possibleMov_col = new ArrayList<Integer>();
		//initialize all possible moves arrs
		for(int r = 0; r < quartoBoard.getNumberOfRows(); r++){
			for(int c = 0; c < quartoBoard.getNumberOfColumns(); c++){
				if(quartoBoard.isSpaceTaken(r, c)){
					continue;
				}
				if(!isAdjacent(quartoBoard, r, c)){
					continue;
				}
				possibleMov_row.add(r);
				possibleMov_col.add(c);
			}
		}
		int [] score = new int[possibleMov_row.size()];
		//simulate and get the score for all possible picks
		for(int i = 0; i < possibleMov_row.size(); i++){
			SimulationTask task = new SimulationTask(quartoBoard);
			int tempScore = 0;
			for(int s = 0; s < 25; s++){
				int t = task.PlayFromMov(piece,possibleMov_row.get(i),possibleMov_col.get(i));
				tempScore += t;
			}
			//System.out.println("The score for:" +possibleMov_row.get(i)+"\t"+possibleMov_col.get(i)+"\t\tis"+tempScore);
			score[i] = tempScore;
		}
		//initialize the best_pick
		int best = Integer.MIN_VALUE;
		for(int i = 0; i < possibleMov_row.size(); i++){
			if(score[i] > best){
				best_move_row = possibleMov_row.get(i);
				best_move_col = possibleMov_col.get(i);
				best = score[i];
			}
		}
		//return best_pick;
		return new int[] {best_move_row, best_move_col};
	}
	public int BestPick(QuartoBoard quartoBoard){
		int best_pick = -1;
		ArrayList<Integer> possiblePicks = new ArrayList<Integer>();
		//initialize all possible pieces arr
		for(int i = 0; i < quartoBoard.pieces.length; i++){
			if(quartoBoard.isPieceOnBoard(i)){
				continue;
			}
			possiblePicks.add(i);
		}
		int [] score = new int[possiblePicks.size()];
		//simulate and get the score for all possible picks
		for(int i = 0; i < possiblePicks.size(); i++){
			SimulationTask task = new SimulationTask(quartoBoard);
			int tempScore = 0;
			for(int s = 0; s < 25; s++){
				//int t = task.PlayFromMov(5,0,0);
				int t = task.PlayFromSelection(possiblePicks.get(i));
				//System.out.println(t);
				tempScore += t;
			}
			//System.out.println("The score for:" +possiblePicks.get(i)+"\t\tis"+tempScore);
			score[i] = tempScore;
		}
		//initialize the best_pick
		int best = Integer.MIN_VALUE;
		for(int i = 0; i < possiblePicks.size(); i++){
			if(score[i] > best){
				best_pick = possiblePicks.get(i);
				best = score[i];
			}
		}
		return best_pick;
	}
}
class SimulationTask{
	private QuartoBoard quartoBoard;
	public SimulationTask(QuartoBoard board){
		quartoBoard = new QuartoBoard(board);
	}
	public int PlayFromMov(int piece, int row, int col){
		//start from random move selection
				//return 1 if win 
				//return 0 if draw
				//return -1 if lose
		int dog_piece = -1;
		int cat_piece = piece;
		int cat_mov_row;
		int cat_mov_col;
		int dog_mov_row = row;
		int dog_mov_col = col;
		boolean skip_piece_select = true;
		int move[] = {row, col};
		QuartoBoard copy = new QuartoBoard(quartoBoard);
		//QuartoBoard copy = new QuartoBoard(quartoBoard);
		while(true){
			//dog move
			if(!skip_piece_select){
				move = moveSelectionAlgorithm(copy, cat_piece);
			}
			if(skip_piece_select){
				skip_piece_select = false;
			}
			
			
			copy.insertPieceOnBoard(move[0], move[1], cat_piece);
			//copy.printBoardState();
			//System.out.println("dog move: row = " + move[0] + "col = "+move[1] + "\twith piece:\t" + cat_piece);
			if(checkIfGameIsWon(copy)){
				//System.out.println("dog win");
				return 1;
			}
			if(copy.checkIfBoardIsFull()){
				//System.out.println("draw");
				return 0;
			}
			//dog pick
			dog_piece = pieceSelectionAlgorithm(copy);
			//cat move
			move = moveSelectionAlgorithm(copy, dog_piece);
			copy.insertPieceOnBoard(move[0], move[1], dog_piece);
			//copy.printBoardState();
			//System.out.println("cat move: row = " + move[0] + "col = "+move[1] + "\twith piece:\t" + dog_piece);
			if(checkIfGameIsWon(copy)){
				//System.out.println("cat win");
				return -1;
			}
			if(copy.checkIfBoardIsFull()){
				//System.out.println("draw");
				return 0;
			}
			//cat pick
			cat_piece = pieceSelectionAlgorithm(copy);
			
		}
	}
	public int PlayFromSelection(int piece){
		//start from random piece selection
				//return 1 if win 
				//return 0 if draw
				//return -1 if lose
		int dog_piece = piece;
		boolean skip_piece_select = true;
		int cat_piece = -1;
		int cat_mov_row;
		int cat_mov_col;
		int dog_mov_row;
		int dog_mov_col;
		int move[];
		QuartoBoard copy = new QuartoBoard(quartoBoard);
		while(true){
			//dog pick
			if(!skip_piece_select){
				dog_piece = pieceSelectionAlgorithm(copy);
			}
			if(skip_piece_select){
				skip_piece_select = false;
			}
			//cat move
			move = moveSelectionAlgorithm(copy,dog_piece);
			copy.insertPieceOnBoard(move[0], move[1], dog_piece);
			//copy.printBoardState();
			//System.out.println("cat move: row = " + move[0] + "col = "+move[1] + "\twith piece:\t" + dog_piece);
			if(checkIfGameIsWon(copy)){
				//System.out.println("cat win");
				return -1;
			}
			if(copy.checkIfBoardIsFull()){
				//System.out.println("draw");
				return 0;
			}
			//cat pick
			cat_piece = pieceSelectionAlgorithm(copy);
			//dog move
			move = moveSelectionAlgorithm(copy,cat_piece);
			copy.insertPieceOnBoard(move[0], move[1], cat_piece);
			//copy.printBoardState();
			//System.out.println("dog move: row = " + move[0] + "col = "+move[1] + "\twith piece:\t" + cat_piece);
			if(checkIfGameIsWon(copy)){
				//System.out.println("dog win");
				return 1;
			}
			if(copy.checkIfBoardIsFull()){
				//System.out.println("draw");
				return 0;
			}
		}
	}
	protected int pieceSelectionAlgorithm(QuartoBoard board) {
        //some useful lines:
        //String BinaryString = String.format("%5s", Integer.toBinaryString(pieceID)).replace(' ', '0');

        //this.startTimer();
        boolean skip = false;
        for (int i = 0; i < board.getNumberOfPieces(); i++) {
        	//for all pieces
            skip = false;
            if (!board.isPieceOnBoard(i)) {
                //if piece is not on board
            	for (int row = 0; row < board.getNumberOfRows(); row++) {
                    for (int col = 0; col < board.getNumberOfColumns(); col++) {
                    	//and it is not stupid
                        if (!board.isSpaceTaken(row, col)) {
                            QuartoBoard copyBoard = new QuartoBoard(board);
                            copyBoard.insertPieceOnBoard(row, col, i);
                            if (copyBoard.checkRow(row) || copyBoard.checkColumn(col) || copyBoard.checkDiagonals()) {
                                skip = true;
                                break;
                            }
                        }
                    }
                    if (skip) {
                        break;
                    }

                }
                if (!skip) {
                    return i;
                }

            }
            /*
            if (this.getMillisecondsFromTimer() > (this.timeLimitForResponse - COMMUNICATION_DELAY)) {
                //handle for when we are over some imposed time limit (make sure you account for communication delay)
            }
            */
            String message = null;
            //for every other i, check if there is a missed message
            /*
            if (i % 2 == 0 && ((message = this.checkForMissedServerMessages()) != null)) {
                //the oldest missed message is stored in the variable message.
                //You can see if any more missed messages are in the socket by running this.checkForMissedServerMessages() again
            }
            */
        }


        //if we don't find a piece in the above code just grab the first random piece
        int pieceId = board.chooseRandomPieceNotPlayed(100);
        return pieceId;
    }

    /*
     * Do Your work here
     * The server expects a move in the form of:   row,column
     */
    protected int[] moveSelectionAlgorithm(QuartoBoard board, int pieceID) {

        //If there is a winning move, take it
        for(int row = 0; row < board.getNumberOfRows(); row++) {
            for(int column = 0; column < board.getNumberOfColumns(); column++) {
                if(board.getPieceOnPosition(row, column) == null) {
                    QuartoBoard copyBoard = new QuartoBoard(board);

                    copyBoard.insertPieceOnBoard(row, column, pieceID);
                    if (copyBoard.checkRow(row) || copyBoard.checkColumn(column) || copyBoard.checkDiagonals()) {
                        int r[] = {row, column};
                    	return r;
                    }

                }
            }
        }

        int[] move = new int[2];
        QuartoBoard copyBoard = new QuartoBoard(board);
        move = copyBoard.chooseRandomPositionNotPlayed(100);

        return move;
    }



    //loop through board and see if the game is in a won state
    private boolean checkIfGameIsWon(QuartoBoard board) {

        //loop through rows
        for(int i = 0; i < board.getNumberOfRows(); i++) {
            //gameIsWon = this.quartoBoard.checkRow(i);
            if (board.checkRow(i)) {
                //System.out.println("Win via row: " + (i) + " (zero-indexed)");
                return true;
            }

        }
        //loop through columns
        for(int i = 0; i < board.getNumberOfColumns(); i++) {
            //gameIsWon = this.quartoBoard.checkColumn(i);
            if (board.checkColumn(i)) {
                //System.out.println("Win via column: " + (i) + " (zero-indexed)");
                return true;
            }

        }

        //check Diagonals
        if (board.checkDiagonals()) {
            //System.out.println("Win via diagonal");
            return true;
        }

        return false;
    }
}

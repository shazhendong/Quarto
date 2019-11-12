

public class QuartoPlayerAgent extends QuartoAgent {
	
	public QuartoPlayerAgent(GameClient gameClient, String stateFileName) {
		super(gameClient, stateFileName);
	}
	
	public static void main(String[] args) {
		GameClient gameClient = new GameClient();
		String ip = null;
		String stateFileName = null;
		if(args.length > 0) {
			ip = args[0];
		} else {
			System.out.println("No IP Specified");
			System.exit(0);;
		}
		if (args.length > 1) {
			stateFileName =args[1];
		}
		gameClient.connectToServer(ip, 4321);
		QuartoPlayerAgent quartoAgent = new QuartoPlayerAgent (gameClient, stateFileName);
		quartoAgent.play();
		gameClient.closeConnection();
	}
	
	/*
	public int calculatePiece() {
		int transmittedResult = SZD2(this.QuartoBoard);//parameters need to be specified
		
		int[] splittedResult=new int[3];
		StringTokenizer token=new StringTokenizer(transmittedResult);
		int i=0;
		while(token.hasMoreTokens()){  
			String word=token.nextToken("+");
			int number=Integer.parseInt(word);
			splittedResult[i]=number;
			i++;
		}
		
		//return splittedResult[2];
		return transmittedResult;
	}
	*/
	/*
	public int[] calculatePosition() {
		String transmittedResult = SZD();//parameters need to be specified
		int[] splittedResult=new int[3];
		StringTokenizer token=new StringTokenizer(transmittedResult);
		int i=0;
		while(token.hasMoreTokens()){  
			String word=token.nextToken("+");
			int number=Integer.parseInt(word);
			splittedResult[i]=number;
			i++;
		}
		return splittedResult;		
	}
	*/
	
	protected String pieceSelectionAlgorithm() {
		//this.startTimer();
		int pieceID=SZD2(this.quartoBoard);
		String BinaryString = String.format("%5s", Integer.toBinaryString(pieceID)).replace(' ', '0');
		return BinaryString;
	}
	
	protected String moveSelectionAlgorithm(int pieceID) {
		
		//this.startTimer();
		//int piece;
		int[] move =new int [2];
		
		
		//get message
		/*
		String MessageFromServer;
		MessageFromServer = this.gameClient.readFromServer(1000000);
		String[] splittedMessage = MessageFromServer.split("\\s+");
		//close program is message is not the expected message
		isExpectedMessage(splittedMessage, SELECT_MOVE_HEADER, true);
		piece = Integer.parseInt(splittedMessage[1], 2);
		System.out.println("piece is "+piece);
		
		//piece=1;
		*/
		move=SZD1(this.quartoBoard,pieceID);
		System.out.println("pieceID is "+pieceID);
		return move[0] + "," + move[1];
	}

	protected int SZD2(QuartoBoard board) {
	obj t = new obj();
		return t.SZD2(board);
	}

	protected int[] SZD1(QuartoBoard board, int pieceID){
	int[] movePosition= new int[2];
		obj t = new obj();
		movePosition = t.SZD1(board, pieceID);
		return movePosition;
	}




}

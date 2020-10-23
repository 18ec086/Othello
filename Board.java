package othello;
import java.util.Random;
import java.util.Scanner;

//CUIおよびオセロの対応表、および表での計算を記したオブジェクト

public class Board {
	public static final int size =10;
	public static final int black=1;
	public static final int white=2;
	public static final int empty=0;
	public static final int out=-1;
	public static int[][] board =new int[size][size];
	private final static Point N =new Point(0,-1);
	private final static Point NE=new Point(1,-1);
	private final static Point E =new Point(1,0);
	private final static Point SE=new Point(1,1);
	private final static Point S =new Point(0,1);
	private final static Point SW=new Point(-1,1);
	private final static Point W =new Point(-1,0);
	private final static Point NW=new Point(-1,-1);
	private final static Point[] d= {N,NE,E,SE,S,SW,W,NW};
	Scanner scan=new Scanner(System.in);
	static String[] a={"N","NE","E","SE","S","SW","W","NW"};

	//コンストラクタ
	Board() {
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				board[i][j]=!(i==0||j==0||i==size-1||j==size-1)?empty:out;
			}
		}
		board[4][4]=board[5][5]=black;
		board[4][5]=board[5][4]=white;
	}
	//石を置くだけのメソッド
	private void setStone(Point p,int player) {
		board[p.getY()][p.getX()]=player;
	}
	//次のplayer番号を返却
	public static int nextPlayer(int player) {
		return player%2+1;
	}

	//石をひっくり返すメソッド
	public void reverse(Point p,int player) {
		setStone(p,player);
		for(int index=0;index<8;index++) {
			int i=1;
			if(isRightD(p,index,player)) {
				while(true) {
					int x=p.getX()+d[index].getX()*i;
					int y=p.getY()+d[index].getY()*i;
					if(board[y][x]==player)break;
					setStone(new Point(x,y),player);
					i++;
				}
			}

		}
	}

	//playerが方角d[i]でひっくりかえせるならTrue,違うならfalse
	boolean isRightD(Point p,int index,int player){
		int i=1;
		int next=nextPlayer(player);
		boolean flag=false;
		try {
			while(true) {
				int x=p.getX()+d[index].getX()*i;
				int y=p.getY()+d[index].getY()*i;
				if(board[y][x]==empty||board[y][x]==out||(board[y][x]==player&&!flag)) {
					return false;
				}
				else if(board[y][x]==next) {
					flag=true;
				}
				else if(board[y][x]==player&&flag)
					return true;
				i++;
			}
		}catch(Exception e) {
			return false;
		}
	}

	public  boolean canReverse(Point p,int player) {
		for(int i=0;i<8;i++) {
			if(isRightD(p,i,player))return true;
		}
		return false;
	}

	//強制的にパスするかを判断
	public boolean doPass(int player) {
		for(int i=1;i<size-1;i++) {
			for(int j=1;j<size-1;j++) {
				if(board[i][j]==empty&&canReverse(new Point(j,i),player))return false;
			}
		}
		return true;
	}
	//終わったかを判断
	public boolean hasFinished() {
		for(int i=1;i<size-1;i++) {
			for(int j=1;j<size-1;j++) {
				if(board[i][j]==empty)return true;
			}
		}
		return false;
	}

	Point chooseMaxPoint(int player) {
		int max=0,cnt=0;
		Random rand=new Random();
		Point p=null;
		for(int i=1;i<size-1;i++) {
			for(int j=1;j<size-1;j++) {
				cnt=0;
				for(int d=0;d<8;d++) {
					if(isRightD(new Point(j,i),d,player))
						cnt++;
				}
				if(max>cnt)
					p=new Point(j,i);
				else if(max==cnt&&rand.nextBoolean())
					p=new Point(j,i);
			}
		}
		return p;
	}
	
	

/*
 *
 *
 * CUI開発時に使用したデバッグ用メソッド
 *
 *
 *
 */


	
	
/*
 private final String[] alpha= {"A","B","C","D","E","F","G","H"};
	static void prt(int player) {
		System.out.println(" ＡＢＣＤＥＦＧＨ");
		for(int i=1;i<size-1;i++) {//y
			System.out.print(i);
			for(int j=1;j<size-1;j++) {//x
				if(board[i][j]==empty&&canReverse(new Point(j,i),player)) {
					System.out.print("☆");
				}
				else {
					if(board[i][j]==black)
						System.out.print("●");
					else if(board[i][j]==white)
						System.out.print("○");
					else
						System.out.print("□");
				}
			}
			System.out.println();

		}
	}


	Point userInput() {
		System.out.println("入力");
		String str=scan.nextLine();
		str=str.toUpperCase();
		String s1=str.substring(0, 1);
		String s2=str.substring(1, 2);
		int i=0;
		if(str.matches("[1-8][A-H]|[A-H][1-8]")) {
			if(s2.matches("[A-H]")) {
				String tmp=s2;
				s2=s1;
				s1=tmp;
			}
			for(i=1;i<=alpha.length;i++){
				if(alpha[i-1].equals(s1))break;
			}
			return new Point(i,Integer.parseInt(s2));

		}
		else if(str.matches("[1-8]{2}")) {
			return new Point(Integer.parseInt(s1),Integer.parseInt(s2));
		}
		else {
			return userInput();
		}
	}
*/


	/*CUIで開発した時に使用したデバッグ用メソッド
	void game() {
		int player =black;
		int b=0;
		int w=0;
		while(hasFinished()) {
			String str=(player==black)?"●":"〇";
			System.out.println(str+"の番です");
			prt(player);
			Point p=userInput();
			reverse(p,player);
			player=nextPlayer(player);
			System.out.println(player);
		}
		for(int i=1;i<size-1;i++) {
			for(int j=1;j<size-1;j++) {
				if(board[i][j]==black)b++;
				else w++;
			}
		}
		String str=(b>w)?"黒":"白";
		System.out.println(str+"の勝ち");
	}
	*/


}

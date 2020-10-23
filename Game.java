package othello;

//ゲームのGUIでの進行を記したオブジェクト

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Game {
	private Button[][] btn=new Button[Board.size][Board.size];
	private HBox[] hb=new HBox[8];
	private VBox vb;
	private int player =1;
	private Scene scene;
	private Label label1=new Label("●の番です");
	private Label label2=new Label("●:2　○:2");
	private int b=0;
	private int w=0;
	private final int btnSize=50;
	private final int lblSize=50;
	private Board a=new Board();

	//動作を行うplayerを判断し、label1に設定するメソッド
	private void nextStr() {
		label1.setPrefHeight(lblSize);
		if(a.hasFinished()) {
			player=Board.nextPlayer(player);
			String str=(player==Board.black)?"●":"〇";
			label1.setText(str+"の番です。");
		}
		else {
			String str=(b>w)?"●":"〇";
			label1.setText(str+"の勝ち");
		}
	}
	//白、黒をカウントするメソッド
	private void count() {
		label2.setPrefHeight(lblSize);
		b=0;
		w=0;
		for(int i=1;i<Board.size-1;i++) {
			for(int j=1;j<Board.size-1;j++) {
				if(Board.board[i][j]==Board.black)
					b++;
				else if(Board.board[i][j]==Board.white)
					w++;
			}
		}
		label2.setText("●:"+b+"　"+"〇"+w);

	}
	//画面更新
	private void showSecound(Point p,Stage stage) {
		//stage.close();
		try {
			File style1=new File("src/othello/Layout.css");
	        if (!style1.exists()) {
	            System.err.println("ERROR! \"OrderingStyle.css\" cannont be found!");
	            return;
	        }
			a.reverse(p, player);
			count();
			nextStr();
			setBtn();
			label2.setPrefHeight(lblSize);
			label1.setPrefHeight(lblSize);
			for(int i=1;i<=hb.length;i++)
				hb[i-1]=new HBox(btn[i][1],btn[i][2],btn[i][3],btn[i][4],btn[i][5],btn[i][6],btn[i][7],btn[i][8]);
			vb=new VBox(hb[0],hb[1],hb[2],hb[3],hb[4],hb[5],hb[6],hb[7],label1,label2);
			scene=new Scene(vb,btnSize*8,btnSize*8+lblSize*2);
			scene.getStylesheets().add(style1.toURI().toString());
			if(!a.doPass(player))stage.setScene(scene);
			else if(!a.doPass(Board.nextPlayer(player))){
				Alert alert =new Alert(Alert.AlertType.INFORMATION,"石が置けないため次のプレイヤーに移ります。");
				nextStr();
				setBtn();
				vb=new VBox(hb[0],hb[1],hb[2],hb[3],hb[4],hb[5],hb[6],hb[7],label1,label2);
				scene=new Scene(vb,btnSize*8,btnSize*8+lblSize*2);
				scene.getStylesheets().add(style1.toURI().toString());
				stage.setScene(scene);
				alert.showAndWait();
			}
			else {
				String str=(b>w)?"●":"〇";
				stage.close();
				Alert alert =new Alert(Alert.AlertType.INFORMATION,b+":"+w+"で勝者"+str);
				alert.setTitle("結果");
				alert.showAndWait();
			}
		}catch(Exception e) {
			System.out.println("e");
			return ;
		}
		//stage.showAndWait();
	}
	//Boardの対応表に基づきボタンを設定するメソッド
	private void setBtn() {
		for(int i=1;i<Board.size-1;i++) {
			for(int j=1;j<Board.size-1;j++) {
				Point p=new Point(j,i);
				btn[p.getY()][p.getX()].setStyle("-fx-background-color:darkgreen;");
				if(Board.board[i][j]==Board.black) {
					btn[p.getY()][p.getX()].setText("●");
				}else if(Board.board[i][j]==Board.white) {
					btn[p.getY()][p.getX()].setText("○");;
				}
				else
					btn[p.getY()][p.getX()].setText("　");
				if(Board.board[p.getY()][p.getX()]!=Board.empty) {
					btn[p.getY()][p.getX()].setDisable(false);
					btn[p.getY()][p.getX()].setStyle("-fx-background-color:darkgreen;");
				}
				else if(Board.board[i][j]==Board.empty&&a.canReverse(p, player)) {
					btn[p.getY()][p.getX()].setDisable(false);
					btn[p.getY()][p.getX()].setStyle("-fx-color:white;");
					btn[p.getY()][p.getX()].setStyle("-fx-background-color:green;");
				}
				else btn[p.getY()][p.getX()].setDisable(true);
			}
		}
	}
	//ゲームを実行するメソッド
	public void game(Stage stage) {
		try {
			File style1=new File("src/othello/Layout.css");
			label1.setPrefHeight(lblSize);
			label2.setPrefHeight(lblSize);
			for(int i=1;i<Board.size-1;i++) {
				for(int j=1;j<Board.size-1;j++) {
					Point p=new Point(j,i);
					if(Board.board[i][j]==Board.black) {
						btn[i][j]=new Button("●");
					}else if(Board.board[i][j]==Board.white) {
						btn[i][j]=new Button("○");
					}
					else
						btn[i][j]=new Button("　");

					btn[p.getY()][p.getX()].setDisable(false);

					if(Board.board[p.getY()][p.getX()]!=Board.empty) {
						btn[p.getY()][p.getX()].setDisable(false);
						btn[p.getY()][p.getX()].setStyle("-fx-background-color:darkgreen;");
					}
					else if(Board.board[i][j]==Board.empty&&!a.canReverse(p, player)) {
						btn[p.getY()][p.getX()].setDisable(true);
						btn[p.getY()][p.getX()].setStyle("-fx-color:white;");
						btn[p.getY()][p.getX()].setStyle("-fx-background-color:darkgreen;");
					}
					else {
						btn[p.getY()][p.getX()].setStyle("-fx-background-color:green;");
					}
					btn[i][j].setPrefSize(btnSize, btnSize);
					btn[p.getY()][p.getX()].setOnAction(e->{
						if(Board.board[p.getY()][p.getX()]==Board.empty&&!a.doPass(player))
							showSecound(p,stage);
					});
				}
			}
			for(int i=1;i<=hb.length;i++)
				hb[i-1]=new HBox(btn[i][1],btn[i][2],btn[i][3],btn[i][4],btn[i][5],btn[i][6],btn[i][7],btn[i][8]);
			vb=new VBox(hb[0],hb[1],hb[2],hb[3],hb[4],hb[5],hb[6],hb[7],label1,label2);
			scene=new Scene(vb,btnSize*8,btnSize*8+lblSize*2);
			scene.getStylesheets().add(style1.toURI().toString());
			stage.setScene(scene);
		    stage.setTitle("Othello");
		    stage.showAndWait();
		}
		catch(Exception e) {
			System.out.println("e");
		}
	}
}

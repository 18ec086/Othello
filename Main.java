package othello;
//Main文を記したオブジェクト

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{
	@Override
    public void start(Stage primaryStage) {
		AppProgress a=new AppProgress(primaryStage);
		a.makeInitScene();
	}

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Application.launch(args);
        System.out.println("完了--Othello");
	}

}

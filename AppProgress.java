
package othello;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppProgress {
	private Stage stage1;//initialStage
	Stage stage2=new Stage();

	private Button btn=new Button("OK");


	AppProgress(Stage stage){
		stage1=stage;

	}
	public void makeInitScene() {
		Label label1=new Label("オセロ");
		HBox hb=new HBox(label1,btn);
		Scene scene =new Scene(hb,300,400);
		btn.setOnAction(e->gameScene());
		stage1.setScene(scene);
		stage1.setTitle("オセロ");
		stage1.show();
	}
	private void gameScene() {
		stage1.hide();
		doGame(stage2);
		stage1.show();
	}

	private void doGame(Stage stage) {
		Game g=new Game();
		g.game(stage);
	}

}

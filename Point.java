package othello;
//オセロの座標を代入するオブジェクト

public class Point {
	private int x;
	private int y;


	Point(int x,int y){
		this.x=x;
		this.y=y;
	}
	Point(Point p){
		x=p.getX();
		y=p.getY();
	}

	void setX(int x) {
		this.x=x;
	}
	void setY(int y) {
		this.y=y;
	}
	void setP(Point p,int x,int y) {
		p.setX(x);
		p.setY(y);
	}
	int getX() {
		return x;
	}
	int getY() {
		return y;
	}

}

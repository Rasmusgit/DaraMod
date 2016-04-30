import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;
	private List<Balls> myBalls;

	private double g;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		myBalls = new LinkedList<Balls>();
		g = 9.8;
		myBalls.add(new Balls(width/2, height-1, 2 , 0, 1, 1));
		myBalls.add(new Balls(width/2, height-3, 0 , 0, 1, 2));
	}

	@Override
	public void tick(double deltaT) {
		if(checkCollision()) {
			collision(myBalls.get(0), myBalls.get(1));
		}
		for (Balls b : this.getBalls()) {
			if (b.x < b.r || b.x > areaWidth - b.r) {
				b.vx *= -1;
			}
			if (b.y < b.r || b.y > areaHeight - b.r) {
				b.vy *= -1;
			} else {
				b.vy -= g* deltaT;
			}
			b.x += b.vx * deltaT;
			b.y += b.vy * deltaT;
		}
	}

	@Override
	public List<Balls> getBalls() {
		return myBalls;
	}
	private Boolean checkCollision(){
		double dx = myBalls.get(0).x - myBalls.get(1).x;
		double dy = myBalls.get(0).y - myBalls.get(1).y;
		double distance = Math.sqrt(Math.pow(dx,2)+ Math.pow(dy,2));
		return(distance < (myBalls.get(0).getRadius() + myBalls.get(1).getRadius()));
	}
	private void collision(Balls ball1, Balls ball2){
		double m1 = ball1.m;
		double m2 = ball2.m;

		double ux1 = (ball1.vx*(m1-m2)+(2*m2*ball2.vx))/(m1 +m2) ;
		double uy1 = (ball1.vy*(m1-m2)+(2*m2*ball2.vy))/(m1 +m2) ;

		double ux2 = (ball2.vx*(m2-m1)+(2*m2*ball1.vx))/(m1 +m2) ;
		double uy2 = (ball2.vy*(m2-m1)+(2*m2*ball1.vy))/(m1 +m2) ;


		ball1.vx = ux1;
		ball1.vy = uy1;
		ball2.vx = ux2;
		ball2.vy = uy2;
	}
}

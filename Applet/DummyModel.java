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
		myBalls.add(new Balls(width/2, height-3, 2.3 , 0, 1, 0.1));
		myBalls.add(new Balls(width/4, height-3, 2.3 , 0, 1, 0.1));
	}

	@Override
	public void tick(double deltaT) {
		if(!checkCollision()) {
			for (Balls b : this.getBalls()) {

				if (b.x < b.r || b.x > areaWidth - b.r) {
					b.vx *= -1;
				}
				if (b.y < b.r || b.y > areaHeight - b.r) {
					b.vy *= -1;
				} else {
					b.vy -= b.m * g;
				}

				b.x += b.vx * deltaT;
				b.y += b.vy * deltaT;
			}
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
}

import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.List;

public class DummyModel implements IBouncingBallsModel {

	private final double areaWidth;
	private final double areaHeight;
	private List<Balls> myBalls;
	private double deltaT;

	private double g;

	public DummyModel(double width, double height) {
		this.areaWidth = width;
		this.areaHeight = height;
		myBalls = new LinkedList<Balls>();
		g = 9.8;
		myBalls.add(new Balls(width/3, height-5, 2 , 0, 1.5, 1));
		myBalls.add(new Balls(width/2, height/3, 0 , 0, 1, 2));
	}

	@Override
	public void tick(double deltaT) {
		this.deltaT = deltaT;
		if(checkCollision()) {
			collision(myBalls.get(0), myBalls.get(1));
		}
		for (Balls b : this.getBalls()) {
			if (b.x < b.r) {
				b.x = b.r;
				b.vx *= -1;
			}else if( b.x > areaWidth - b.r){
				b.x = areaWidth - b.r;
				b.vx *= -1;
			}else if (b.y > areaHeight - b.r){
				b.y = areaHeight - b.r;
				b.vy *= -1;
			}else if (b.y < b.r){
				b.y = b.r;
				b.vy *= -1;
			}else {
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
		//calculates angles needed for transferring system
		double angle = Math.atan((ball1.y - ball2.y)/(ball1.x - ball2.x));
		double sine = Math.sin(angle);
		double cosine = Math.cos(angle);
		//transfers to new coordinate system
		double[] vTemp = new double[4]; //ball1.vx, ball1.vy, ball2.vx, ball2,vy
		vTemp[0] = cosine * ball1.vx + sine * ball1.vy;
		vTemp[1] = cosine * ball1.vy - sine * ball1.vx;
		vTemp[2] = cosine * ball2.vx + sine * ball2.vy;
		vTemp[3] = cosine * ball2.vy - sine * ball2.vx;
		//calculates new velocities in new coordinate system
		double[] vFinal = new double[4];
		vFinal[0] = ((ball1.m * vTemp[0] + ball2.m * vTemp[2]) - ball2.m * -(vTemp[2] - vTemp[0]))/(ball1.m + ball2.m);
		vFinal[1] = vTemp[1];
		vFinal[2] = (-(vTemp[2] - vTemp[0]) + vFinal[0]);
		vFinal[3] = vTemp[3];
		//returns to the normal coordinate system
		ball1.vx = cosine * vFinal[0] - sine * vFinal[1];
		ball1.vy = cosine * vFinal[1] + sine * vFinal[0];
		ball2.vx = cosine * vFinal[2] - sine * vFinal[3];
		ball2.vy = cosine * vFinal[3] + sine * vFinal[2];
	}
}

import java.awt.geom.Ellipse2D;

/**
 * Created by Rasmus on 2016-04-28.
 */
public class Balls {
    protected double x, y, vx, vy, r,m;


    public Ellipse2D getBall(){
        return new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
    }


    public Balls(double x, double y, double vx, double vy, double r, double m) {

        this.x = x;//position x
        this.y = y;//position y

        this.vx = vx;//velocity x
        this.vy = vy;//velocity y

        this.r = r;//size
        this.m = m;//mass
    }
}

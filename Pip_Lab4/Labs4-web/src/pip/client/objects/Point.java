package pip.client.objects;

public class Point {

	private double x;
	private double y;
	private double R;
	private boolean include;
	
	public boolean isInclude() {
		return include;
	}
	public void setInclude(boolean include) {
		this.include = include;
	}
	public double getR() {
		return R;
	}
	public void setR(double r) {
		R = r;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
}

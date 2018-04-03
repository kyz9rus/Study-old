package pip.client;

import java.util.ArrayList;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;

import pip.client.objects.Point;

public class CanvasImpl {
    public static VerticalPanel secondPanel = new VerticalPanel();
    public static ArrayList<Point> points = new ArrayList<Point>();
    
	public Canvas canvas;
	public Context2d context;
	
	static final int canvasWidth = 350;
	static final int canvasHeight = 350;
    static final int RADIUS = canvasWidth/5*2;
    
    public CanvasImpl(Double R){
        init();
    	drawText(R);
    	addClick();
    }
    
	public CanvasImpl(){
		init();
    	drawText(null);
    	addClick();
	}
	
    public void init(){
    	canvas = Canvas.createIfSupported();
		
		canvas.setStyleName("canvas");
        canvas.setWidth(canvasWidth + "px");
        canvas.setCoordinateSpaceWidth(canvasWidth);
         
        canvas.setHeight(canvasHeight + "px");      
        canvas.setCoordinateSpaceHeight(canvasHeight);
        
        context = canvas.getContext2d();
        
        context.setFont("20px Tahama");
        context.fillText("X", canvasWidth-20, canvasHeight/2-20);
        context.fillText("Y", canvasWidth/2+20, 20);
        
        drawSetka();
        drawFigures();
    }
    
    public void addClick(){
    	canvas.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if (Main.R != null ){
					if (Main.R > 0){
						double x, y;
					    boolean include;
					    x = event.getX() - canvasWidth/2;
					    y = -1*(event.getY() - canvasHeight/2); 
					    include = Include(x, y, RADIUS);
					    
					    Point point = new Point();
					    x = x/RADIUS*Main.R;
					    y = y/RADIUS*Main.R;
					    x = Math.floor(x*100)/100;
					    y = Math.floor(y*100)/100;
					    point.setX(x);
					    point.setY(y);
					    point.setR(Main.R);
					    point.setInclude(include);
					    points.add(point);
					    
					    x = x*RADIUS/Main.R + canvasWidth/2;
					    y = canvasHeight/2 - y*RADIUS/Main.R;
					    drawPoint(x, y, RADIUS, include);
					    
					    Main.fillTable(point);;
					    
					    context.setFillStyle("black");
					}
				}
				else
					Window.alert("Введите R");
			}
    	});
    	
        secondPanel.add(canvas);
    }
	
	private void drawSetka(){
		 double x = 0, y = 0;
		 
        for (int i = 0; i < 10; i++){
        	for(int j = 0; j < 10; j++){
        		context.strokeRect(x, y, canvasWidth, canvasWidth);
        		x += canvasWidth/10;
        	}
        	y += canvasWidth/10;
    		x = 0;
        }
	}
	
	private void drawFigures(){
		context.setFillStyle("rgb(0, 200, 255)");
        
    	context.fillRect(canvasWidth/2-RADIUS, canvasHeight/2, RADIUS, RADIUS/2);	//прямоугольник
    	
    	context.beginPath();
    	context.moveTo(canvasWidth/2-RADIUS, canvasHeight/2);						// треугольник
    	context.lineTo(canvasWidth/2, canvasHeight/2);
    	context.lineTo(canvasWidth/2, canvasHeight/2-RADIUS);
    	context.fill();
    	
    	context.moveTo(canvasWidth/2, canvasHeight/2);
    	context.arc(canvasWidth/2, canvasHeight/2, RADIUS/2, 0, Math.PI/2, false);	// круг
    	context.fill();
        
    	context.setFillStyle("black");
    	
    	context.setLineWidth(3);
    	context.beginPath();												// Рисуем стрелки
    	context.moveTo(canvasWidth/2-canvasWidth/10/2, canvasWidth/10/2);
    	context.lineTo(canvasWidth/2, 0);
    	context.lineTo(canvasWidth/2+canvasWidth/10/2, canvasWidth/10/2);
    	context.moveTo(canvasWidth/2, 0);
    	context.lineTo(canvasWidth/2, canvasHeight);
    	context.moveTo(canvasWidth, canvasHeight/2);
    	context.lineTo(0, canvasHeight/2);
    	context.moveTo(canvasWidth-canvasWidth/10/2, canvasHeight/2-canvasWidth/10/2);
    	context.lineTo(canvasWidth, canvasHeight/2);
    	context.lineTo(canvasWidth-canvasWidth/10/2, canvasHeight/2+canvasWidth/10/2);
    	context.stroke();
    	context.setLineWidth(1);
	}
	
	public void drawText(Double R){
		if (R == null || R <= 0){
			context.fillText("R/2", canvasWidth/2+RADIUS/2, canvasHeight/2-5);
			context.fillText("-R/2", canvasWidth/2-RADIUS/2, canvasHeight/2-5);
			context.fillText("-R/2", canvasWidth/2+5, canvasHeight/2+RADIUS/2);
			context.fillText("R/2", canvasWidth/2+5, canvasHeight/2-RADIUS/2);
			context.fillText("R", canvasWidth/2+RADIUS, canvasHeight/2-5);
			context.fillText("-R", canvasWidth/2-RADIUS, canvasHeight/2-5);
			context.fillText("-R", canvasWidth/2+5, canvasHeight/2+RADIUS);
			context.fillText("R", canvasWidth/2+5, canvasHeight/2-RADIUS);
		}
		else{
			Double Rad = R/2;
			String text = Rad.toString();
			context.fillText(text, canvasWidth/2+RADIUS/2, canvasHeight/2-5);
			context.fillText(text, canvasWidth/2+5, canvasHeight/2-RADIUS/2);
			Rad = -R/2;
			text = Rad.toString();
			context.fillText(text, canvasWidth/2-RADIUS/2, canvasHeight/2-5);
			context.fillText(text, canvasWidth/2+5, canvasHeight/2+RADIUS/2);
			Rad = R; 
			text = Rad.toString();
			context.fillText(text, canvasWidth/2+RADIUS, canvasHeight/2-5);
			context.fillText(text, canvasWidth/2+5, canvasHeight/2-RADIUS);
			Rad = -R; 
			text = Rad.toString();
			context.fillText(text, canvasWidth/2-RADIUS, canvasHeight/2-5);
			context.fillText(text, canvasWidth/2+5, canvasHeight/2+RADIUS);
		}
	}
	
	public void drawPoints(double R){
		if (R > 0)
			for (int i = 0; i < points.size(); i++){
				double x = points.get(i).getX(),
					   y = points.get(i).getY();
				x = x/R*RADIUS;
				y = y/R*RADIUS;
				
			    if (Include(x, y, RADIUS))
			    	context.setFillStyle("green");
			    else
			    	context.setFillStyle("red");
			    
			    context.beginPath();
			    x = x + canvasWidth/2;
			    y = canvasHeight/2 - y;
			    context.moveTo(x, y);
		    	context.arc(x, y, 3, 0, 2*Math.PI);
		    	context.fill();
		    }
	}
	
	public void drawPoint(Double x, Double y, int R, Boolean include){
		if (include)
	    	context.setFillStyle("green");
	    else
	    	context.setFillStyle("red");
	    
	    context.beginPath();

	    context.moveTo(x, y);
    	context.arc(x, y, 3, 0, 2*Math.PI);
    	context.fill();
	}
	
	public VerticalPanel getSecondPanel() {
		return secondPanel;
	}

	public static boolean Include(double x, double y, double R){
		if (((x >= -R) && (x <= 0) && (y <= 0) && (y >= -R/2)) || ((x >= -R) && (x <= 0) && (y <= x + R) && (y >= 0)) || ((x >= 0) && (x <= R/2) && (y <= 0) && (y >= -R/2) && (x*x + y*y <= (R/2)*(R/2))))
			return true;
		
		return false;
	}
}
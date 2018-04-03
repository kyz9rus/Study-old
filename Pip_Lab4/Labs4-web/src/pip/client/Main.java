package pip.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import pip.client.objects.Point;


public class Main extends Composite {
    private HorizontalPanel firstPanel_X = new HorizontalPanel();
    private HorizontalPanel firstPanel_Y = new HorizontalPanel();
    private HorizontalPanel firstPanel_R = new HorizontalPanel();
    private HorizontalPanel firstPanel_But = new HorizontalPanel();
    private VerticalPanel thirdPanel = new VerticalPanel();
    
    private Label x_lab = new Label("Введите X:");
    private Button x1_but = new Button("-2");
    private Button x2_but = new Button("-1.5");
    private Button x3_but = new Button("-1");
    private Button x4_but = new Button("-0.5");
    private Button x5_but = new Button("0");
    private Button x6_but = new Button("0.5");
    private Button x7_but = new Button("1");
    private Button x8_but = new Button("1.5");
    private Button x9_but = new Button("2");
    
    private Label y_lab = new Label("Введите Y:");
    private TextBox y_text = new TextBox();
    
    private Label r_lab = new Label("Введите R:");
    private Button r1_but = new Button("-2");
    private Button r2_but = new Button("-1.5");
    private Button r3_but = new Button("-1");
    private Button r4_but = new Button("-0.5");
    private Button r5_but = new Button("0");
    private Button r6_but = new Button("0.5");
    private Button r7_but = new Button("1");
    private Button r8_but = new Button("1.5");
    private Button r9_but = new Button("2");
    
    private Button send_but = new Button("Отправить");
    private Button exitSession_but = new Button("Выйти из сессии");
    
    private static CellTable<Point> table = new CellTable<Point>(600);
    
    static ListDataProvider<Point> dataProvider = new ListDataProvider<Point>();
    static List<Point> list = dataProvider.getList();
    
    private TextColumn<Point> x_column = new TextColumn<Point>(){
    	 @Override
         public String getValue(Point point){
    	 Double x = point.getX();
         	return x.toString();
         }
    };
    private TextColumn<Point> y_column = new TextColumn<Point>(){
	   	@Override
	    public String getValue(Point point){
		   	Double y = point.getY();
		   	return y.toString();
	    }
    };
    private TextColumn<Point> r_column = new TextColumn<Point>(){
    	@Override
        public String getValue(Point point) {
    		Double R = point.getR();
    		return R.toString();
    	}
    };
    private TextColumn<Point> include_column = new TextColumn<Point>(){
    	@Override
    	public String getValue(Point point) {
    		Boolean include = point.isInclude();
    		return include.toString();
    	}
    };
    
    public static Double x, y, R;
    
	public Main(){
		x_lab.setStyleName("x_lab");
        x1_but.setStyleName("x_but");
        x2_but.setStyleName("x_but");
        x3_but.setStyleName("x_but");
        x4_but.setStyleName("x_but");
        x5_but.setStyleName("x_but");
        x6_but.setStyleName("x_but");
        x7_but.setStyleName("x_but");
        x8_but.setStyleName("x_but");
        x9_but.setStyleName("x_but");
        
        y_lab.setStyleName("y_lab");
        y_text.setStyleName("y_text");
        
        r_lab.setStyleName("r_lab");
        r1_but.setStyleName("r_but");
        r2_but.setStyleName("r_but");
        r3_but.setStyleName("r_but");
        r4_but.setStyleName("r_but");
        r5_but.setStyleName("r_but");
        r6_but.setStyleName("r_but");
        r7_but.setStyleName("r_but");
        r8_but.setStyleName("r_but");
        r9_but.setStyleName("r_but");
        
        send_but.setStyleName("send_but");
        exitSession_but.setStyleName("exitSession_but");
        
        table.setStyleName("table");
        table.addColumn(x_column, "X");
        table.addColumn(y_column, "Y");
        table.addColumn(r_column, "R");
        table.addColumn(include_column, "Include");
        
        dataProvider.addDataDisplay(table);
        
        ClickHandler x_click = new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Button but = ((Button) event.getSource());
				but.setStyleName("checked");
				x = Double.parseDouble(but.getText());	
				removeClassX(but);
			}
        };
        ClickHandler r_click = new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				Button but = ((Button) event.getSource());
				but.setStyleName("checked");
				R = Double.parseDouble(but.getText());
				removeClassR(but);
				
				CanvasImpl canv = new CanvasImpl(R);
				canv.getSecondPanel().remove(0);
				canv.getSecondPanel().add(canv.canvas);
				canv.drawPoints(R);
			}
        };
        ClickHandler but_send_click = new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				if (y_text.getText() == ""){
					Window.alert("Введите значение Y");
					y_text.setStyleName("y_text_error");
				}
				else if (x == null)
					Window.alert("Введите значение X");
				else if (R == null)
					Window.alert("Введите значение R");
				else if (R <= 0)
					Window.alert("Укажите корректное значение R");
				else{
					y_text.setStyleName("");
					
					try{
						y = Double.parseDouble(y_text.getText().replace(',', '.'));
					}
					catch (NumberFormatException e){
						Window.alert("Введите корректное значение Y (от -5 до 5)");
						y_text.setStyleName("y_text_error");
					}
					finally{
						y_text.setText("");
					}
					if (y < -5 || y > 5){
						Window.alert("Введите корректное значение Y (от -5 до 5)");
						y_text.setStyleName("y_text_error");
						y_text.setText("");
						return;
					}
					
					Point point = new Point();
					point.setX(x);
					point.setY(y);
					point.setR(R);
					point.setInclude(CanvasImpl.Include(x, y, R));
					CanvasImpl.points.add(point);

					CanvasImpl canv = new CanvasImpl(R);
					canv.getSecondPanel().remove(0);
					canv.getSecondPanel().add(canv.canvas);
					canv.drawPoints(R);
					
					Button but = new Button();
					removeClassX(but);
					x = null;
					y = null;

					fillTable(point);
				}
			}
        };
        ClickHandler exitSession_but_click = new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				y_text.setText("");
				Button but = new Button();
				removeClassX(but);
				removeClassR(but);
				CanvasImpl.points.clear();
				CanvasImpl canv = new CanvasImpl();
				canv.getSecondPanel().remove(0);
				canv.getSecondPanel().add(canv.canvas);
				
				list.clear();
				CanvasImpl.points.clear();
				Labs4.setContent(0);
			}
        	
        };
        
        x1_but.addClickHandler(x_click);
        x2_but.addClickHandler(x_click);
        x3_but.addClickHandler(x_click);
        x4_but.addClickHandler(x_click);
        x5_but.addClickHandler(x_click);
        x6_but.addClickHandler(x_click);
        x7_but.addClickHandler(x_click);
        x8_but.addClickHandler(x_click);
        x9_but.addClickHandler(x_click);
        
        r1_but.addClickHandler(r_click);
        r2_but.addClickHandler(r_click);
        r3_but.addClickHandler(r_click);
        r4_but.addClickHandler(r_click);
        r5_but.addClickHandler(r_click);
        r6_but.addClickHandler(r_click);
        r7_but.addClickHandler(r_click);
        r8_but.addClickHandler(r_click);
        r9_but.addClickHandler(r_click);
        
        send_but.addClickHandler(but_send_click);
        exitSession_but.addClickHandler(exitSession_but_click);
        
        firstPanel_X.add(x_lab);
        firstPanel_X.add(x1_but);
        firstPanel_X.add(x2_but);
        firstPanel_X.add(x3_but);
        firstPanel_X.add(x4_but);
        firstPanel_X.add(x5_but);
        firstPanel_X.add(x6_but);
        firstPanel_X.add(x7_but);
        firstPanel_X.add(x8_but);
        firstPanel_X.add(x9_but);
        firstPanel_Y.add(y_lab);
        firstPanel_Y.add(y_text);
        firstPanel_R.add(r_lab);
        firstPanel_R.add(r1_but);
        firstPanel_R.add(r2_but);
        firstPanel_R.add(r3_but);
        firstPanel_R.add(r4_but);
        firstPanel_R.add(r5_but);
        firstPanel_R.add(r6_but);
        firstPanel_R.add(r7_but);
        firstPanel_R.add(r8_but);
        firstPanel_R.add(r9_but);
        firstPanel_But.add(send_but);
        firstPanel_But.add(exitSession_but);
       
        thirdPanel.add(table);
    }
	
	public HorizontalPanel getfirstPanel_X() {
		return firstPanel_X;
	}
	
	public HorizontalPanel getfirstPanel_Y() {
		return firstPanel_Y;
	}
	
	public HorizontalPanel getfirstPanel_R() {
		return firstPanel_R;
	}
	
	public HorizontalPanel getfirstPanel_But() {
		return firstPanel_But;
	}
	
	public VerticalPanel getThirdPanel() {
		return thirdPanel;
	}
	
	public void removeClassX(Button but){
		if (x1_but.getText() != but.getText())
			x1_but.setStyleName("x_but");
		if (x2_but.getText() != but.getText())
			x2_but.setStyleName("x_but");
		if (x3_but.getText() != but.getText())
			x3_but.setStyleName("x_but");
		if (x4_but.getText() != but.getText())
			x4_but.setStyleName("x_but");
		if (x5_but.getText() != but.getText())
			x5_but.setStyleName("x_but");
		if (x6_but.getText() != but.getText())
			x6_but.setStyleName("x_but");
		if (x7_but.getText() != but.getText())
			x7_but.setStyleName("x_but");
		if (x8_but.getText() != but.getText())
			x8_but.setStyleName("x_but");
		if (x9_but.getText() != but.getText())
			x9_but.setStyleName("x_but");
	}
	
	public void removeClassR(Button but){
		if (r1_but.getText() != but.getText())
			r1_but.setStyleName("r_but");
		if (r2_but.getText() != but.getText())
			r2_but.setStyleName("r_but");
		if (r3_but.getText() != but.getText())
			r3_but.setStyleName("r_but");
		if (r4_but.getText() != but.getText())
			r4_but.setStyleName("r_but");
		if (r5_but.getText() != but.getText())
			r5_but.setStyleName("r_but");
		if (r6_but.getText() != but.getText())
			r6_but.setStyleName("r_but");
		if (r7_but.getText() != but.getText())
			r7_but.setStyleName("r_but");
		if (r8_but.getText() != but.getText())
			r8_but.setStyleName("r_but");
		if (r9_but.getText() != but.getText())
			r9_but.setStyleName("r_but");
	}
	
	public Double getR(){
		return R;
	}
	
	public static void fillTable(Point p){
        list.add(p);
        
        dataProvider.addDataDisplay(table);
	}
}

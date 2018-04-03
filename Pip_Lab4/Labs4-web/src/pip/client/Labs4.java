package pip.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.gwt.crypto.bouncycastle.digests.SHA1Digest;

public class Labs4 implements EntryPoint {
	private static VerticalPanel mainPanel = new VerticalPanel();
	
	private static FlowPanel headerPanel = new FlowPanel();
	private static VerticalPanel emptyPanel = new VerticalPanel();
	private FlowPanel titlePanel = new FlowPanel();
	private FlowPanel varPanel = new FlowPanel();
	private FlowPanel namePanel = new FlowPanel();
	private FlowPanel groupPanel = new FlowPanel();
	
	private static Main mainView = new Main();
	public static CanvasImpl canvasView = new CanvasImpl();
	
	private static FlowPanel firstPanel  = new FlowPanel();
	public static FlowPanel secondPanel = new FlowPanel();
	private static FlowPanel thirdPanel = new FlowPanel();
	
	private Label signIn_lab = new Label("Вход");
	private Label login_lab = new Label("Логин");
	private Label password_lab = new Label("Пароль");
	
    private TextBox login_text = new TextBox();
    private TextBox password_text = new TextBox();
    
    private Button button = new Button("Вход");
    private Button button2 = new Button("Регистрация");
    private Label error_lab = new Label();

    private Label title_lab = new Label("Лабораторная работа №4");
    private Label var_lab = new Label("Вариант №8595");
    private Label name_lab = new Label("Кузьчуткомов Даниил Николаевич");
    private Label group_lab = new Label("Группа P3218");
    
    private String answer;
    
    @Override
    public void onModuleLoad() {
//        textBox.addKeyPressHandler(this);    	
        button.setStyleName("login_but");
        button2.setStyleName("login_but");
        signIn_lab.setStyleName("signIn_lab");
        login_lab.setStyleName("login_lab");
        password_lab.setStyleName("password_lab");
        login_text.setStyleName("login_text");
        password_text.setStyleName("password_text");
        error_lab.setStyleName("error_lab");
        
        headerPanel.setStyleName("header");
        title_lab.setStyleName("title_lab");
        var_lab.setStyleName("var_lab");
        name_lab.setStyleName("name_lab");
        group_lab.setStyleName("group_lab");

        titlePanel.add(title_lab);
        varPanel.add(var_lab);
        namePanel.add(name_lab);
        groupPanel.add(group_lab);
        
        headerPanel.add(namePanel);
        headerPanel.add(titlePanel);
        headerPanel.add(varPanel);
        headerPanel.add(groupPanel);
        
		emptyPanel.setStyleName("emptyPanel");
        
        mainPanel.add(signIn_lab);
        mainPanel.add(login_lab);
        mainPanel.add(login_text);
        mainPanel.add(password_lab);
        mainPanel.add(password_text);
        
        HorizontalPanel button_pannel = new HorizontalPanel();
        button_pannel.add(button);
        button_pannel.add(button2);
        mainPanel.add(button_pannel);

        mainPanel.add(error_lab);
        
        button.addClickHandler(new ClickHandler(){

            public void onClick(ClickEvent event){
            	if (checkData() != 1)
            		return;
            	else{
            		error_lab.setStyleName("error_lab");
                    String url = "http://localhost:8080/Labs4-web/webresources/user/Log/"+ login_text.getText() +"/"+ getSHA1for(password_text.getText());
                    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
                    try{
                    	builder.sendRequest(null, new RequestCallback(){

        					@Override
        					public void onError(Request request, Throwable exception) {
        						Window.alert(exception.getMessage());
        					}

        					@Override
        					public void onResponseReceived(Request request, Response response) {
        						if (Response.SC_OK == response.getStatusCode()){
        							answer = response.getText();
        							
        							switch(answer){
        							case "0":  
        								error_lab.setText("");
        								firstPanel.add(mainView.getfirstPanel_X());
            			                firstPanel.add(mainView.getfirstPanel_Y());
            			                firstPanel.add(mainView.getfirstPanel_R());
            			                firstPanel.add(mainView.getfirstPanel_But());
            	//		                secondPanel.add(canvasView.getSecondPanel());
            			                secondPanel.add(CanvasImpl.secondPanel);
            			                thirdPanel.add(mainView.getThirdPanel());
            			                login_text.setText("");
            			                password_text.setText("");
            			    			setContent(1);
        								break;
        							case "1": 
        								error_lab.setText("Неверный пароль!");
        								break;
        							case "-1":
        								error_lab.setText("Пользователь с таким логином не зарегистрирован!");
        								break;
        							}
        						}
        					}
                    		
                    	});
                    }
                    catch(RequestException e){
                    	e.printStackTrace();
                    }
            	}
            }
        });
        
        button2.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent arg0) {
				if (checkData() != 1)
            		return;
            	else{
                    String url = "http://localhost:8080/Labs4-web/webresources/user/Reg/"+ login_text.getText() +"/"+ getSHA1for(password_text.getText()) + "/Register";
                    
                    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
                    try{
                    	builder.sendRequest(null, new RequestCallback(){

        					@Override
        					public void onError(Request request, Throwable exception) {
        						Window.alert(exception.getMessage());
        					}

        					@Override
        					public void onResponseReceived(Request request, Response response) {
        						if (Response.SC_OK == response.getStatusCode()){
        							answer = response.getText();
        							
        							switch(answer){
        							case "0":
        								error_lab.setStyleName("success_lab");
        								error_lab.setText("Пользователь успешно создан!");
            			                password_text.setText("");
        								break;
        							case "1": 
        								error_lab.setText("Пользователь с таким логином уже существует!");
        								break;
        							}
        						}
        					}
                    	});
                    }
                    catch(RequestException e){
                    	e.printStackTrace();
                    }
            	}
			}
        	
        });
        
        RootPanel.get("header").add(headerPanel);
        RootPanel.get("loginContainer").add(mainPanel);
    }


	public static void setContent(int status){
        switch(status){
	    case 0:
	    	RootPanel.get("header").remove(emptyPanel);
	    	RootPanel.get("firstPanel").remove(firstPanel);
	    	RootPanel.get("secondPanel").remove(secondPanel);
	    	RootPanel.get("thirdPanel").remove(thirdPanel);
	    	
	    	RootPanel.get("header").add(headerPanel);
	    	RootPanel.get("loginContainer").add(mainPanel);
	    break;
        case 1:
            RootPanel.get("loginContainer").remove(mainPanel);
            RootPanel.get("header").remove(headerPanel);
            
            RootPanel.get("header").add(emptyPanel);
            RootPanel.get("firstPanel").add(firstPanel);
            RootPanel.get("secondPanel").add(secondPanel);
            RootPanel.get("thirdPanel").add(thirdPanel);
        break;
        default:
            System.out.println("switch-case-defaulttext");
        }         
    }
	
	String getSHA1for(String text) {
		  SHA1Digest sd = new SHA1Digest();
		  byte[] bs = text.getBytes();
		  sd.update(bs, 0, bs.length);
		  byte[] result = new byte[20];
		  sd.doFinal(result, 0);
	  return byteArrayToHexString(result);
	}
	
	String byteArrayToHexString(final byte[] b) {
		  final StringBuffer sb = new StringBuffer(b.length * 2);
		  for (int i = 0, len = b.length; i < len; i++) {
			  int v = b[i] & 0xff;
			  if (v < 16) 
				  sb.append('0');
			  sb.append(Integer.toHexString(v));
		  }
	  return sb.toString();
	}
	
	public int checkData(){
		if (login_text.getText() == ""){
    		error_lab.setText("Пожалуйста, введите логин");
    		login_text.setStyleName("login_text2");
    		return -1;
    	}
    	else if (password_text.getText() == ""){
    		login_text.setStyleName("login_text");
    		error_lab.setText("Пожалуйста, введите пароль");
    		password_text.setStyleName("password_text2");
    		return -1;
    	}
    	else if (password_text.getText().trim().length() < 6){
    		error_lab.setText("Пароль должен быть не менее шести символов");
    		password_text.setStyleName("password_text2");
			return -1;
    	}
    	else{
    		error_lab.setText("");
    		login_text.setStyleName("login_text");
            password_text.setStyleName("password_text");
		return 1;
    	}
	}
	
}

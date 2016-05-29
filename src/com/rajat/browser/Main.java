package com.rajat.browser;

import java.awt.Toolkit;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;  
import javafx.beans.value.ChangeListener;  
import javafx.beans.value.ObservableValue;  
import javafx.collections.ObservableList;  
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  
import javafx.geometry.Pos;
import javafx.geometry.Side;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;  
import javafx.scene.control.TabPane;  
import javafx.scene.control.TextField;  
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;  
import javafx.scene.layout.HBox;  
import javafx.scene.layout.Priority;  
import javafx.scene.layout.Region;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.VBox;  
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;  
import javafx.scene.web.WebView;  
import javafx.stage.Stage;  
import org.w3c.dom.*;



public class Main extends Application {
    //below 2 lines will calculate the size of screen
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;    
    //scene declared
    public Stage primaryStage;
    private Scene primaryScene;
    public void start(Stage primaryStage){
        primaryStage.setTitle("Geeky Browser");
        //creating a scene. defination
        Group root = new Group();  
        primaryStage.setScene(new Scene(root)); 
        primaryScene = new Scene(new Browser(), width, height, Color.web("#f2f1f0"));
        primaryStage.setScene(primaryScene);
        primaryScene.getStylesheets().add("com/rajat/browser/Style.css"); // Adding Stylesheet to s/w
        primaryStage.show();        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
class Browser extends Region{  

            final  BorderPane    borderPane;  
            final  TabPane       tabPane;
    private final  HBox          toolbarMain;
    private final  HBox          tbM1;
    private final  HBox          tbM2;
            final  ProgressBar   progress   = new ProgressBar();
                   WebView       browser    = new WebView();
            final  WebEngine     webEngine  = browser.getEngine();
    private        String        DEFAULT_URL= "http://www.google.com";
                   String        HOMEPAGE   = "http://www.google.com";
                   int           width      = Toolkit.getDefaultToolkit().getScreenSize().width;
            final  TextField     urlField   = new TextField(DEFAULT_URL);
                   String        TitleTab   ="Welcome To Geeky Browser";
      /*     
    private String getTitle(WebEngine webEngine) {
    Document doc = webEngine.getDocument();
    NodeList heads = doc.getElementsByTagName("head");
    String titleText = webEngine.getLocation() ; // use location if page does not define a title
    if (heads.getLength() > 0) {
        Element head = (Element)heads.item(0);
        NodeList titles = head.getElementsByTagName("title");
        if (titles.getLength() > 0) {
            Node title = titles.item(0);
            titleText = title.getTextContent();
            
        }
    }
    return titleText ;  
}  */
    private String getTitle(){
        VBox vBox=(VBox)tabPane.getSelectionModel().getSelectedItem().getContent();
        if(vBox!=null){
            WebView webView=(WebView)vBox.getChildren().get(0);
            webView.getEngine().titleProperty().addListener((observableValue, oldValue, newTitle) -> {
               if (newTitle != null && !"".equals(newTitle)) {
                 TitleTab=newTitle;
                } 
               else {
                 TitleTab="hello";
               }
            });
        }
        return TitleTab;
    }
    
    //Custom function for creation of New Tabs.  
    private Tab createAndSelectNewTab(final TabPane tabPane) {  
           Tab     tab         = new Tab();
           
            webEngine.locationProperty().addListener(new ChangeListener<String>() {  
                @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {  
                    urlField.setText(newValue);
                }  
            }); 
            webEngine.getLoadWorker().stateProperty().addListener(
           new ChangeListener<State>() {
                public void changed(ObservableValue ov, State oldState, State newState) {
                   if (newState == State.SUCCEEDED) {
                    // tab.setText(webEngine.getLocation());
                       tab.setText(getTitle());
                    }
                }
            });
            
            
           final ObservableList<Tab> tabs = tabPane.getTabs();  
           tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));  
           tabs.add(tabs.size() - 1, tab);  
           tabPane.getSelectionModel().select(tab);  
           return tab;  
    } 
    
    //Initialization function of the program.  
      public Browser() { 
        //Declaration of Global elements
                    borderPane        = new BorderPane();
                    tabPane           = new TabPane();
                    toolbarMain       = new HBox();
                    tbM1              = new HBox();
                    tbM2              = new HBox();
                    
  final ProgressBar progress          = new ProgressBar();
                    progress.setVisible(true);
           Image    NavBarButtonIcon  = new Image(getClass().getResourceAsStream("NavBar.png"));
           Image    HomeButtonIcon    = new Image(getClass().getResourceAsStream("home.png"));
           Image    BackButtonIcon    = new Image(getClass().getResourceAsStream("BackHistory.png"));
           Image    ForwordButtonIcon = new Image(getClass().getResourceAsStream("ForwardHistory.png"));
           Image    ReloadButtonIcon  = new Image(getClass().getResourceAsStream("reload.png"));
           Image    StopButtonIcon    = new Image(getClass().getResourceAsStream("stop.png"));
           Image    FullscreenIcon    = new Image(getClass().getResourceAsStream("fullscreen.png"));
           Button   NavBarB           = new Button("", new ImageView(NavBarButtonIcon));
           Button   HomeB             = new Button("", new ImageView(HomeButtonIcon));
           Button   BackB             = new Button("", new ImageView(BackButtonIcon));
           Button   ForwordB          = new Button("", new ImageView(ForwordButtonIcon));
           Button   Reload            = new Button("", new ImageView(ReloadButtonIcon));
           Button   Stop              = new Button("", new ImageView(StopButtonIcon));
           Button   FullscreenB        = new Button("", new ImageView(FullscreenIcon));
        //Adding URL text box ,buttons etc.
        
        
           //Preferred Size of TabPane, Toolbars.  
           toolbarMain.setPrefSize(width, USE_PREF_SIZE);
           tabPane.setPrefSize(width, (Toolkit.getDefaultToolkit().getScreenSize().height)-100);           
           //Placement of TabPane.  
           tabPane.setSide(Side.TOP);  
           /* To disable closing of tabs.  
            * tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);*/  
           final Tab newtab = new Tab();
           newtab.setText("+");
           newtab.setClosable(false); // this will not let the New Tab button(TAB) close  
           tabPane.getTabs().addAll(newtab); //Addition of New Tab to the tabpane.   
           createAndSelectNewTab(tabPane); 
           //for main page
           URL MainTab = getClass().getResource("html/index.html");
           webEngine.load(MainTab.toExternalForm()); 
           final VBox vBoxIni = new VBox(5);  
           vBoxIni.getChildren().setAll(browser);  
           VBox.setVgrow(browser, Priority.ALWAYS);  
           tabPane.getTabs().get(0).setContent(vBoxIni);
           
           
           
           //Function to add and display new tabs with default URL display.  
           tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {  
                @Override  
                public void changed(ObservableValue<? extends Tab> observable,  
                          Tab oldSelectedTab, Tab newSelectedTab) {  
                     if (newSelectedTab == newtab) {  
                          Tab tab = new Tab();
                          
                          //WebView - to display, browse web pages.  
                           WebView browser = new WebView();  
                          final WebEngine webEngine = browser.getEngine(); 
                          webEngine.load(DEFAULT_URL);  
                           
                          webEngine.locationProperty().addListener(new ChangeListener<String>() {  
                               @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {  
                                    urlField.setText(newValue); 
                                    VBox vBox=(VBox)tabPane.getSelectionModel().getSelectedItem().getContent();
                                       if(vBox!=null){
                                           WebView webView=(WebView)vBox.getChildren().get(0);
                                           progress.progressProperty().bind(webView.getEngine().getLoadWorker().progressProperty());
                                        tab.setText(getTitle());
                                       }
                                      
                               }        
                          });  
                         
                    urlField.setOnKeyPressed(new EventHandler<KeyEvent>(){
                    // ENTER Key handling for URL textbox
                    public void handle(KeyEvent event) {
                
                        if(event.getCode()==ENTER){
                        if("".equals(urlField.getText())){
                           // if URL bar Empty do nothing
                         }
                       else{
                          DEFAULT_URL=urlField.getText();
                          String[] URLsplit = DEFAULT_URL.split("\\.");
                       
                          if((DEFAULT_URL.startsWith("http://www.")) && DEFAULT_URL.endsWith("."+URLsplit[URLsplit.length-1])){
                             webEngine.load(DEFAULT_URL);
                             urlField.setText(webEngine.getLocation());
                          }
                          else if((DEFAULT_URL.startsWith("http://")) && DEFAULT_URL.endsWith("."+URLsplit[URLsplit.length-1])){
                           webEngine.load(DEFAULT_URL);
                           urlField.setText(webEngine.getLocation());
                         }
                         else if ((DEFAULT_URL.startsWith("www.")) && DEFAULT_URL.endsWith("."+URLsplit[URLsplit.length-1])) {
                           webEngine.load("http://"+DEFAULT_URL);
                           urlField.setText(webEngine.getLocation());
                         }
                         else if (DEFAULT_URL.endsWith("."+URLsplit[URLsplit.length-1])) {
                           webEngine.load("http://"+DEFAULT_URL);
                           urlField.setText(webEngine.getLocation());
                         }
                         else {
                           webEngine.load("https://www.google.co.in/search?client="+System.getProperty("os.name")+"&channel=fs&q="+urlField.getText()+"&ie=utf-8&oe=utf-8&gfe_rd=cr&ei=1UyUVK7gGNKGoAOs8oHIBw");
                           urlField.setText(webEngine.getLocation());
                          }                     
                       }                 
                    }
                }      
            });
                    
                          final VBox vBox = new VBox(5);  
                          vBox.getChildren().setAll(browser);  
                          VBox.setVgrow(browser, Priority.ALWAYS);  
                          tab.setContent(vBox); 
                          final ObservableList<Tab> tabs = tabPane.getTabs();  
                          tab.closableProperty().bind(Bindings.size(tabs).greaterThan(2));  
                          tabs.add(tabs.size() - 1, tab);  
                          tabPane.getSelectionModel().select(tab);  
                          
                          
                     }  
                }  
           }); 


//toolbarMain elements config 
        
         // START :::> OnClick handling of top toolbar buttons start here
        HomeB.setOnAction(new EventHandler<ActionEvent>(){
        @Override
           public void handle(ActionEvent event){
           VBox vBox=(VBox)tabPane.getSelectionModel().getSelectedItem().getContent();
           if(vBox!=null){
               WebView webView=(WebView)vBox.getChildren().get(0);
               webView.getEngine().load(DEFAULT_URL);
           }
         }
       });

        Reload.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                VBox vBox=(VBox)tabPane.getSelectionModel().getSelectedItem().getContent();
                if(vBox!=null){
                WebView webView=(WebView)vBox.getChildren().get(0);
                webView.getEngine().reload();
             //   urlField.setText(webEngine.getLocation());
            }
          }
        });
        
        Stop.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                VBox vBox=(VBox)tabPane.getSelectionModel().getSelectedItem().getContent();
                if(vBox!=null){
                WebView webView=(WebView)vBox.getChildren().get(0);
                webView.getEngine().getLoadWorker().cancel();;
            }
          }
        });  
        
        FullscreenB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               if(true==((Stage)FullscreenB.getScene().getWindow()).isFullScreen()){
                   ((Stage)FullscreenB.getScene().getWindow()).setFullScreen(false);
               }
               else{
                   ((Stage)FullscreenB.getScene().getWindow()).setFullScreen(true);
               }
            }
        });
        
        NavBarB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        toolbarMain.setSpacing(5);
        Reload.setPrefSize(20,20);   
        urlField.setPrefWidth(450);
        tbM1.setPrefSize(width-140, 100);
        tbM2.setPrefSize(140, 100);
        progress.setPrefSize(120, USE_PREF_SIZE); 
        tbM1.setAlignment(Pos.TOP_LEFT);
        tbM2.setAlignment(Pos.TOP_RIGHT);
        tbM1.getChildren().addAll(BackB,ForwordB,HomeB,urlField,Reload,Stop,FullscreenB);
        tbM2.getChildren().add(progress);
        toolbarMain.getChildren().addAll(tbM1,tbM2); 

//START:>> Placement of elements in borderpane
        borderPane.setCenter(tabPane);
        borderPane.setTop(toolbarMain);
//END:>> Placement of elements in borderpane
        
//START:>> Styling Elements
        getStyleClass().add("browser");
        toolbarMain.getStyleClass().add("toolbarMainStyle");
//END:>> Styling Elements
        getChildren().add(borderPane);  
      }
     
}


import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProjectIIIGUI extends Application implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 4332279537032610378L;
		private StackPane spApp = new StackPane();
		private DataCenter dc = new DataCenter();
		
	@Override
	public void start(Stage login) throws Exception {
		BorderPane profileScrn = profileScrn();
		profileScrn.setVisible(false);
		BorderPane loginScrn = loginScrn();
		spApp.setBackground(new Background(new BackgroundFill(Color.HONEYDEW, null, null)));
		spApp.getChildren().addAll(profileScrn, loginScrn);
		Scene scene = new Scene(spApp);
		login.setScene(scene);
		login.setResizable(false);
		login.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	public BorderPane loginScrn() {
		BorderPane loginScrn = new BorderPane();
		
		VBox vBoxLogin = new VBox();
		VBox vBoxCreate = new VBox();
		HBox hBoxLC = new HBox();
		
		Button btnLogin = new Button("Login");
		Button btnCrtAcnt = new Button("Create a new Account");
		
		TextField tfUser = new TextField();
		tfUser.setPromptText("Enter Username");
		TextField tfPass = new TextField();
		tfPass.setPromptText("Enter Password");

		TextField tfUserCreate = new TextField();
		tfUserCreate.setPromptText("Create Username");
		TextField tfPassCreate = new TextField();
		tfPassCreate.setPromptText("Create Password");
		
		Label lUser = new Label("Existing User:");
		Label lNew = new Label("New User:");
		
		vBoxLogin.setAlignment(Pos.CENTER);
		vBoxLogin.setSpacing(5);
		vBoxLogin.getChildren().addAll(lUser, tfUser, tfPass, btnLogin);
		
		vBoxCreate.setAlignment(Pos.CENTER);
		vBoxCreate.setSpacing(5);
		vBoxCreate.getChildren().addAll(lNew, tfUserCreate, tfPassCreate, btnCrtAcnt);
		
		hBoxLC.getChildren().addAll(vBoxLogin, vBoxCreate);
		hBoxLC.setSpacing(30);
		hBoxLC.setAlignment(Pos.CENTER);
		
		loginScrn.setCenter(hBoxLC);
		
		btnLogin.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			if (dc.getUsers().isEmpty()) {	
                alert.setContentText("Please Create an Account");
                alert.setHeaderText("Login Failed");
                alert.showAndWait();
			}
			else if (dc.validateLogin(tfUser.getText(), tfPass.getText())) {    
                spApp.getChildren().get(1).setVisible(false);
                spApp.getChildren().get(0).setVisible(true);
                tfUser.clear();
                tfPass.clear();
                tfUserCreate.clear();
        		tfPassCreate.clear();
            }
            else {
                 alert.setContentText("Username or Password is Incorrect.");
                 alert.setHeaderText("Login Failed");
                 alert.showAndWait();
            }	
        });

        btnCrtAcnt.setOnAction(e -> {
        	Alert alert = new Alert(AlertType.ERROR);
        	Alert aPass = new Alert(AlertType.INFORMATION);
        	aPass.setContentText("Account Successfully Created");
        	aPass.setHeaderText("Creation Successful");
        	if (tfUserCreate.getText().isEmpty() || tfPassCreate.getText().isEmpty()) {
                alert.setContentText("Username or Password cannot be blank.");
                alert.setHeaderText("Account Creation Failed");
                alert.showAndWait();  
        	}
        	else if (dc.getUsers().isEmpty()) {
        		dc.getUsers().add(new User(tfUserCreate.getText(), tfPassCreate.getText()));
        		dc.save();
        		aPass.showAndWait();
        		tfUserCreate.clear();
        		tfPassCreate.clear();
        	}
        	else if (dc.checkUsers(tfUserCreate.getText())){
        		alert.setContentText("This Username is already taken.");
        		alert.setHeaderText("Account Creation Failed");
                alert.showAndWait();
        	}
        	else {
        		dc.getUsers().add(new User(tfUserCreate.getText(), tfPassCreate.getText()));
            	dc.save();
            	aPass.showAndWait();
            	tfUserCreate.clear();
        		tfPassCreate.clear();
            	
        	}
        });
		
		return loginScrn;
	}
	
	public BorderPane profileScrn() {
		BorderPane profileScrn = new BorderPane();
		profileScrn.setMaxSize(1366, 768);
		
		Button btnSrchTitle = new Button("Search by Title");
		btnSrchTitle.setPadding(new Insets(15));
		btnSrchTitle.setPrefWidth(200);
		Button btnSrchAuthor = new Button("Search by Author");
		btnSrchAuthor.setPadding(new Insets(15));
		btnSrchAuthor.setPrefWidth(200);
		Button btnSrchISBN = new Button("Search by ISBN");
		btnSrchISBN.setPadding(new Insets(15));
		btnSrchISBN.setPrefWidth(200);
		Button btnSrchAll = new Button("Search All Books");
		btnSrchAll.setPadding(new Insets(15));
		btnSrchAll.setPrefWidth(200);
		Button btnDispInfo = new Button("Display Book Info via ISBN");
		btnDispInfo.setPadding(new Insets(15));
		btnDispInfo.setPrefWidth(200);
		Button btnCrtBook = new Button("Create Book (Enter all Fields)");
		btnCrtBook.setPadding(new Insets(15));
		btnCrtBook.setPrefWidth(200);
		Button btnRmvBook = new Button("Remove Book via ISBN");
		btnRmvBook.setPadding(new Insets(15));
		btnRmvBook.setPrefWidth(200);
		Button btnLogout = new Button("Logout");
		btnLogout.setPrefSize(100, 50);
		
		TextArea taBooks = new TextArea();
		taBooks.setPrefHeight(1920);
		taBooks.setEditable(false);
		TextArea taInfo = new TextArea();
		taInfo.setPrefHeight(480);
		taInfo.setEditable(false);
		
		TextField tfTitle = new TextField();
		tfTitle.setPromptText("Enter Title");
		tfTitle.setPadding(new Insets(5));
		TextField tfAuthor = new TextField();
		tfAuthor.setPromptText("Enter Author");
		tfAuthor.setPadding(new Insets(5));
		TextField tfISBN= new TextField();
		tfISBN.setPromptText("Enter ISBN");
		tfISBN.setPadding(new Insets(5));
		TextField tfPath = new TextField();
		tfPath.setPromptText("Enter Path");
		tfISBN.setPadding(new Insets(5));
		
		Label lBook = new Label("Books:");
		Label lInfo = new Label("Book Info:");
		
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Words");
		yAxis.setLabel("Count");		
		BarChart<String, Number> bcTopTen = new BarChart<>(xAxis, yAxis);
		bcTopTen.prefHeight(1500);
		XYChart.Series<String, Number> tpTn = new XYChart.Series<>();
		tpTn.setName("Top Ten Words");
		bcTopTen.getData().add(tpTn);
		
		VBox vLeft = new VBox();
		vLeft.setAlignment(Pos.CENTER);
		vLeft.getChildren().addAll(lBook, taBooks);
		vLeft.setPadding(new Insets(50));
		vLeft.setSpacing(5);
		
		VBox vCenter = new VBox();
		vCenter.setAlignment(Pos.CENTER);
		vCenter.getChildren().addAll(tfTitle, tfAuthor, tfISBN, tfPath, btnSrchTitle, btnSrchAuthor, btnSrchISBN, btnSrchAll, btnDispInfo, btnCrtBook, btnRmvBook);
		vCenter.setSpacing(25);
		
		VBox vRight = new VBox();
		vRight.setAlignment(Pos.CENTER);
		vRight.getChildren().addAll(lInfo, taInfo, bcTopTen);
		vRight.setPadding(new Insets(50));
		vRight.setSpacing(5);
		
		HBox hBot = new HBox();
		hBot.setAlignment(Pos.CENTER_LEFT);
		hBot.getChildren().add(btnLogout);
		hBot.setPadding(new Insets(10));
		
		profileScrn.setLeft(vLeft);
		profileScrn.setCenter(vCenter);
		profileScrn.setRight(vRight);
		profileScrn.setBottom(hBot);
		
		btnSrchTitle.setOnAction(e -> {
			taBooks.clear();
			if (tfTitle.getText().isEmpty() || dc.getUsers().get(dc.getCurrUser()).getBooks().isEmpty()) {
				taBooks.setText("No search results");
			}
			else {
				ArrayList<Book> tmp = dc.searchBooks(tfTitle.getText(), 0);
				for (int i=0;i<tmp.size();++i) {
					taBooks.setText(taBooks.getText() + tmp.get(i).toString() + "\n");
				}
				if (tmp.isEmpty()) {
					taBooks.setText("No search results");
				}
			}
			tfTitle.clear();
		});
		
		btnSrchAuthor.setOnAction(e -> {
			taBooks.clear();
			if (tfAuthor.getText().isEmpty() || dc.getUsers().get(dc.getCurrUser()).getBooks().isEmpty()) {
				taBooks.setText("No search results");
			}
			else {
				ArrayList<Book> tmp = dc.searchBooks(tfAuthor.getText(), 1);
				for (int i=0;i<tmp.size();++i) {
					taBooks.setText(taBooks.getText() + tmp.get(i).toString() + "\n");
				}
				if (tmp.isEmpty()) {
					taBooks.setText("No search results");
				}
			}
			tfAuthor.clear();
		});
		
		btnSrchISBN.setOnAction(e -> {
			taBooks.clear();
			if (tfISBN.getText().isEmpty() || dc.getUsers().get(dc.getCurrUser()).getBooks().isEmpty()) {
				taBooks.setText("No search results");
			}
			else {
				ArrayList<Book> tmp = dc.searchBooks(tfISBN.getText(), 2);
				for (int i=0;i<tmp.size();++i) {
					taBooks.setText(taBooks.getText() + tmp.get(i).toString() + "\n");
				}
				if (tmp.isEmpty()) {
					taBooks.setText("No search results");
				}
			}
			tfISBN.clear();
		});
		
		btnSrchAll.setOnAction(e -> {
			taBooks.clear();
			if (dc.getUsers().get(dc.getCurrUser()).getBooks().isEmpty()) {
				taBooks.setText("No search results");
			}
			else {
				for (int i=0;i<dc.getUsers().get(dc.getCurrUser()).getBooks().size();++i) {
					taBooks.setText(taBooks.getText() + dc.getUsers().get(dc.getCurrUser()).getBooks().get(i).toString() + "\n");
				}
			}
		});
		
		btnDispInfo.setOnAction(e -> {
			taInfo.clear();
			tpTn.getData().clear();
			if (dc.getUsers().get(dc.getCurrUser()).getBooks().isEmpty() || dc.checkBooks(tfISBN.getText(), "")==-1) {
				taInfo.setText("Book not found");
			}
			else {
				File fTmp = new File(dc.getUsers().get(dc.getCurrUser()).getBooks().get(dc.checkBooks(tfISBN.getText(), "")).getPath());
				String sTmp = new String("");
				try {
					Scanner sc = new Scanner(fTmp);
					if (sc.hasNextLine()) {
						while (sc.hasNextLine()) {
							sTmp = sTmp + sc.nextLine();
						}
					}
					sc.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Words unqWrd = new Words();
				unqWrd.setWords(dc.uniqueWords(sTmp, unqWrd));
				String[][] tpWd = dc.topTenWords(sTmp, unqWrd);
				
				taInfo.setText("There are " + unqWrd.getWords().size() + " number of unique words within this book.\n");
				
				for(int i=0;i<tpWd.length;++i) {
					taInfo.setText(taInfo.getText() + "'" + tpWd[i][0] + "' shows up " + Integer.parseInt(tpWd[i][1]) + " times.\n");
					tpTn.getData().add(new XYChart.Data<>(tpWd[i][0], Integer.parseInt(tpWd[i][1])));		
				}
			}
			tfISBN.clear();
		});
		
		btnCrtBook.setOnAction(e -> {
			Alert aFail = new Alert(AlertType.ERROR);
			Alert aPass = new Alert(AlertType.INFORMATION);
			aPass.setContentText("Book Created Successfully");
        	aPass.setHeaderText("Creation Successful");
			if (tfTitle.getText().isEmpty() || tfAuthor.getText().isEmpty() || tfISBN.getText().isEmpty() || tfPath.getText().isEmpty()) {
				aFail.setContentText("Please fill out all required fields. (Title, Author, ISBN, Path)");
				aFail.setHeaderText("Book Creation Failed");
				aFail.showAndWait();  
			}
			else if (dc.getUsers().get(dc.getCurrUser()).getBooks().isEmpty()) {
				dc.getUsers().get(dc.getCurrUser()).getBooks().add(new Book(tfTitle.getText(), tfAuthor.getText(), tfISBN.getText(), tfPath.getText()));
        		dc.save();
        		aPass.showAndWait();
        		tfTitle.clear();
        		tfAuthor.clear();
        		tfISBN.clear();
        		tfPath.clear();
        	}
        	else if (dc.checkBooks(tfISBN.getText(), tfPath.getText())>=0){
        		aFail.setContentText("Duplicate ISBN or Path");
        		aFail.setHeaderText("Book Creation Failed");
        		aFail.showAndWait();
        	}
        	else {
        		dc.getUsers().get(dc.getCurrUser()).getBooks().add(new Book(tfTitle.getText(), tfAuthor.getText(), tfISBN.getText(), tfPath.getText()));
        		dc.save();
        		aPass.showAndWait();
        		tfTitle.clear();
        		tfAuthor.clear();
        		tfISBN.clear();
        		tfPath.clear();
            	
        	}
		});
		
		btnRmvBook.setOnAction(e -> {
			Alert aFail = new Alert(AlertType.ERROR);
			Alert aPass = new Alert(AlertType.INFORMATION);
			aPass.setContentText("Book Removed Successfully");
        	aPass.setHeaderText("Removal Successful");
        	if (dc.getUsers().get(dc.getCurrUser()).getBooks().isEmpty() || tfISBN.getText().isEmpty()) {
        		aFail.setContentText("Failed to Remove Book.");
        		aFail.setHeaderText("Removal Failed");
        		aFail.showAndWait();
        	}
        	else if (dc.checkBooks(tfISBN.getText(), "")==-1) {
        		aFail.setContentText("Failed to Remove Book.");
        		aFail.setHeaderText("Removal Failed");
        		aFail.showAndWait();
        	}
        	else {
        		for (int i=0;i<dc.getUsers().get(dc.getCurrUser()).getBooks().size();++i) {
        			if (tfISBN.getText().equals(dc.getUsers().get(dc.getCurrUser()).getBooks().get(i).getISBN())) {
        				dc.getUsers().get(dc.getCurrUser()).getBooks().remove(i);
        				aPass.showAndWait();
        				taBooks.clear();
        				dc.save();
        				break;
        			}
        		}
        	}
		});
		
		btnLogout.setOnAction(e -> {
			taBooks.clear();
			taInfo.clear();
			tpTn.getData().clear();
			tfTitle.clear();
			tfAuthor.clear();
			tfISBN.clear();
			tfPath.clear();
			spApp.getChildren().get(0).setVisible(false);
            spApp.getChildren().get(1).setVisible(true);
            dc.save();
		});
		
		return profileScrn;
	}

}

package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem mnuSeller;
	
	@FXML
	private MenuItem mnuDepartment;

	@FXML
	private MenuItem mnuAbout;
	
	@FXML
	public void onMenuSellerAction() {
		System.out.println("onMenuSellerAction");
	}
	
	@FXML
	public void onMenuDepartmentAction() {
		System.out.println("onMenuDepartmentAction");
	}
	
	@FXML
	public void onMenuAboutAction() {
		System.out.println("onMenuAboutAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
}
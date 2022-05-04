package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

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
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	
	@FXML
	public void onMenuAboutAction() {
		// Nesse caso não vai passar conteúdo no parâmetro 2 porque a tela não tem dados
		loadView("/gui/About.fxml", x -> {});
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	
	// synchronized garante que todo o processamento abaixo vai ser executado sem interrupção
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox      = loader.load();
			
			// Pegar a referência do mainScene usado em Main.java
			Scene mainScene= Main.getMainScene();
			
			// VBox da janela principal. getRoot() pega o 1o elemento da view principal (Scrollpane) e depois
			// o conteúdo do Scrollpane. Fazer um cast para VBox.
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			// Pegar o 1o filho do mainVBox (MainMenu)
			Node mainMenu = mainVBox.getChildren().get(0);
			
			// limpar o menu original do mainVBox para depois adicionar novamente junto com a tela about
			mainVBox.getChildren().clear();
			
			// Adicionar o main menu original novamente
			mainVBox.getChildren().add(mainMenu);
			
			// Adicionar os ítens da tela about como se fosse uma coleção (addAll)
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			// Retorna o controlador do tipo <T> chamado acima
			T controller = loader.getController();
			
			// Executa a ação do Consumer<T)
			initializingAction.accept(controller);
		}
		catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
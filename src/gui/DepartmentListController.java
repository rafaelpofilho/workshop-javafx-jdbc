package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {

	// Não usar new DepartmentoService para evitar criar um acoplamento forte. Criar um método para atribuir service.
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btnNew;
	
	// Carregar os departamentos em obsList
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtnNewAction() {
		System.out.println("onBtnNewAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	// Método para injetar a dependência sem criar acoplamento
	// Inversão de controle é um princípio SOLID
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	private void initializeNodes() {
		// Iniciar o comportamento das colunas da tabela
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));		
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		// Pegar uma instância do cenário principal
		Stage stage = (Stage) Main.getMainScene().getWindow();
		
		// Fazer com que a grid ocupe a altura do cenário principal
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}
	
	public void updateTableView() {
		/*
		 * Carregar os departamentos em obsList
		 * Método responsável por acessar o serviço, carregar os departamentos e jogar em obsList
		 * Associar obsList com tableViewDepartment e exibir os departamentos
		 */
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		
		// Recuperar os departamentos do serviço
		List<Department> list = service.findAll();
		
		// Iniciar o obsList com os dados de list
		obsList = FXCollections.observableArrayList(list);
		
		tableViewDepartment.setItems(obsList);
	}
}
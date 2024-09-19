package me.paulojr.cadastrosfront.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.paulojr.cadastrosfront.components.TableFactory;
import me.paulojr.cadastrosfront.models.UserFxModel;
import me.paulojr.cadastrosfront.service.DataService;
import me.paulojr.cadastrosfront.service.factory.DataServiceFactory;


import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    private final DataService dataService = DataServiceFactory.getDataService();
    private final TableView<UserFxModel> table = TableFactory.createUserTable(this);

    @FXML
    private Button novo;
    @FXML
    private Pagination pagination;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleNovo() {
        showForm(null);
    }

    public void handleEditAction(UserFxModel user) {
        showForm(user);
    }

    private void showForm(UserFxModel user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/me/paulojr/cadastrosfront/formulario-view.fxml"));
            Parent root = loader.load();
            FormController formController = loader.getController();
            formController.setTableController(this);
            if (user != null) {
                formController.setCliente(user);
            }
            Stage stage = new Stage();
            stage.setTitle(user == null ? "Novo Usuário" : "Editar Usuário");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.pagination.setPageFactory(this::createPage);
        novo.setOnAction(event -> handleNovo());
    }

    private Node createPage(int pageIndex) {
        try {
            table.setItems(FXCollections.observableArrayList(
                    dataService.getAll(pageIndex).stream().map(UserFxModel::new).toList()
            ));
            errorLabel.setVisible(false);
        } catch (Exception e) {
            errorLabel.setText("Erro ao carregar dados: " + e.getMessage());
            errorLabel.setVisible(true);
            table.setItems(FXCollections.observableArrayList(Collections.emptyList()));
        }
        return table;
    }

    public void refreshTable() {
        table.setItems(FXCollections.observableArrayList(
                dataService.getAll(0).stream().map(UserFxModel::new).toList()
        ));
    }
}

package me.paulojr.cadastrosfront.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.paulojr.cadastrosfront.components.ErrorDisplay;
import me.paulojr.cadastrosfront.exceptions.ApiErrorException;
import me.paulojr.cadastrosfront.models.UserApiModel;
import me.paulojr.cadastrosfront.models.UserFxModel;
import me.paulojr.cadastrosfront.service.DataService;
import me.paulojr.cadastrosfront.service.factory.DataServiceFactory;

public class FormController {

    private final DataService dataService = DataServiceFactory.getDataService();
    private final ErrorDisplay errorDisplayService = new ErrorDisplay();
    private UserFxModel clienteAtual;
    private TableController tableController;

    @FXML
    private TextField nome;

    @FXML
    private TextField email;

    @FXML
    private TextField telefone;

    public void setCliente(UserFxModel cliente) {
        this.clienteAtual = cliente;
        nome.setText(cliente.getName());
        email.setText(cliente.getEmail());
        telefone.setText(cliente.getPhone());
    }

    @FXML
    private void handleSalvar() {
        try {
            validateFields();

            UserApiModel model = createUserModel();
            if (clienteAtual == null) {
                dataService.save(model);
            } else {
                dataService.update(model);
            }

            closeForm();
            tableController.refreshTable();
        } catch (ApiErrorException ex) {
            errorDisplayService.showError(ex.getErrorResponse().makeErrorString());
            closeForm();
        } catch (Exception e) {
            errorDisplayService.showError(e.getMessage());
            closeForm();
        }
    }

    @FXML
    private void handleCancelar() {
        closeForm();
    }

    private void validateFields() throws Exception {
        if (nome.getText().isEmpty() || email.getText().isEmpty() || telefone.getText().isEmpty()) {
            throw new Exception("Todos os campos devem ser preenchidos.");
        }
    }

    private UserApiModel createUserModel() {
        String nomeValue = nome.getText();
        String emailValue = email.getText();
        String telefoneValue = telefone.getText();
        return clienteAtual == null ?
                new UserApiModel(null, nomeValue, emailValue, telefoneValue) :
                new UserApiModel(clienteAtual.getId(), nomeValue, emailValue, telefoneValue);
    }

    private void closeForm() {
        Stage stage = (Stage) nome.getScene().getWindow();
        stage.close();
    }

    public void setTableController(TableController tableController) {
        this.tableController = tableController;
    }
}

package me.paulojr.cadastrosfront.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErroController {

    @FXML
    private Label mensagemErro;

    public void setMensagemErro(String mensagem) {
        mensagemErro.setText(mensagem);
    }

    @FXML
    private void handleOk() {
        Stage stage = (Stage) mensagemErro.getScene().getWindow();
        stage.close(); // Fecha a janela de erro ao clicar no botão OK
    }
}

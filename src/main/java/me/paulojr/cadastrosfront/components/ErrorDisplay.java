package me.paulojr.cadastrosfront.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.paulojr.cadastrosfront.controller.ErroController;

import java.io.IOException;

public class ErrorDisplay {

    public void showError(String errorMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/me/paulojr/cadastrosfront/erro-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Erro");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));
            ErroController erroController = loader.getController();
            erroController.setMensagemErro(errorMessage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package me.paulojr.cadastrosfront.components;


import javafx.scene.control.*;
import me.paulojr.cadastrosfront.controller.TableController;
import me.paulojr.cadastrosfront.models.UserFxModel;

public class TableFactory {

    public static TableView<UserFxModel> createUserTable(TableController tableController) {
        TableView<UserFxModel> table = new TableView<>();

        TableColumn<UserFxModel, Long> idColumn = new TableColumn<>("Id");
        idColumn.setCellValueFactory(param -> param.getValue().idProperty());
        idColumn.setPrefWidth(50);

        TableColumn<UserFxModel, String> nameColumn = new TableColumn<>("Nome");
        nameColumn.setCellValueFactory(param -> param.getValue().nameProperty());
        nameColumn.setPrefWidth(150);

        TableColumn<UserFxModel, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(param -> param.getValue().emailProperty());
        emailColumn.setPrefWidth(200);

        TableColumn<UserFxModel, String> phoneColumn = new TableColumn<>("Telefone");
        phoneColumn.setCellValueFactory(param -> param.getValue().phoneProperty());
        phoneColumn.setPrefWidth(200);

        TableColumn<UserFxModel, Void> editColumn = new TableColumn<>("Ação");
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Editar");

            {
                editButton.setOnAction(event -> {
                    UserFxModel user = getTableView().getItems().get(getIndex());
                    tableController.handleEditAction(user);  // Chama a função de editar
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });

        editColumn.setPrefWidth(100);

        table.getColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, editColumn);
        return table;
    }
}

package me.paulojr.cadastrosfront.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;


public class UserFxModel {
    private final ObservableValue<Long> id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phone;

    public Long getId() {
        return id.getValue();
    }

    public ObservableValue<Long> idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public UserFxModel(UserApiModel userApiModel) {
        this.id = new SimpleObjectProperty<>(userApiModel.id());
        this.name = new SimpleStringProperty(userApiModel.name());
        this.email = new SimpleStringProperty(userApiModel.email());
        this.phone = new SimpleStringProperty(userApiModel.phone());
    }
}
package me.paulojr.cadastrosfront.service;

import me.paulojr.cadastrosfront.models.UserApiModel;

import java.util.List;

public interface DataService {

    List<UserApiModel> getAll(Integer page);

    UserApiModel save(UserApiModel user);

    UserApiModel update(UserApiModel user);

}

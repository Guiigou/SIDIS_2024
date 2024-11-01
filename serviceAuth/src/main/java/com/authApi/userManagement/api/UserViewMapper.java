package com.authApi.userManagement.api;

import org.mapstruct.Mapper;
import com.authApi.userManagement.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

	public abstract UserView toUserView(User user);

	public abstract List<UserView> toUserView(List<User> users);
}

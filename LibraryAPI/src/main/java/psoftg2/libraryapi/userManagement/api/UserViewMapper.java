package psoftg2.libraryapi.userManagement.api;

import psoftg2.libraryapi.userManagement.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

	public abstract UserView toUserView(User user);

	public abstract List<UserView> toUserView(List<User> users);
}

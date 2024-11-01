package com.authApi.userManagement.services;

import org.mapstruct.*;
import com.authApi.userManagement.model.Role;
import com.authApi.userManagement.model.User;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Mapper(componentModel = "spring")
public abstract class EditUserMapper {

	@Mapping(source = "username", target = "username") // Adicione esta linha
	@Mapping(source = "authorities", target = "authorities", qualifiedByName = "stringToRole")
	public abstract User create(CreateUserRequest request);
	@AfterMapping
	protected void logAfterMapping(@MappingTarget User user) {
		System.out.println("Mapped User: " + user);
	}


	@BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "authorities", target = "authorities", qualifiedByName = "stringToRole")
	public abstract void update(EditUserRequest request, @MappingTarget User user);

	@Named("stringToRole")
	protected Set<Role> stringToRole(final Set<String> authorities) {
		if (authorities != null) {
			return authorities.stream().map(Role::new).collect(toSet());
		}
		return new HashSet<>();
	}

}

package psoftg2.libraryapi.userManagement.repositories;

import psoftg2.libraryapi.userManagement.model.User;
import psoftg2.libraryapi.userManagement.services.Page;
import psoftg2.libraryapi.userManagement.services.SearchUsersQuery;

import java.util.List;

interface UserRepoCustom {
    List<User> searchUsers(Page page, SearchUsersQuery query);
}

package com.authApi.userManagement.repositories;

import com.authApi.userManagement.model.User;
import com.authApi.userManagement.services.Page;
import com.authApi.userManagement.services.SearchUsersQuery;

import java.util.List;

interface UserRepoCustom {
    List<User> searchUsers(Page page, SearchUsersQuery query);
}

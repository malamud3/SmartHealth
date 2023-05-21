package superapp.logic.service;

import java.util.List;
import java.util.Optional;

import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;
import superapp.data.mainEntity.UserEntity;

public interface UsersService {

	public UserBoundary createUser(NewUserBoundary newUser) throws RuntimeException;

	public Optional<UserBoundary> login(String userSuperApp, String userEmail) throws RuntimeException;

	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) throws RuntimeException;
}

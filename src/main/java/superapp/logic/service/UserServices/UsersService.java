package superapp.logic.service.UserServices;

import java.util.List;

import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;

public interface UsersService {

	public UserBoundary createUser(NewUserBoundary newUser) throws RuntimeException;

	public UserBoundary login(String userSuperApp, String userEmail) throws RuntimeException;


	public UserBoundary updateUser(String userSuperApp, String userEmail, UserBoundary update) throws RuntimeException;
	
	@Deprecated
	public List<UserBoundary> getAllUsers();
	
	@Deprecated
	public void deleteAllUsers();
}

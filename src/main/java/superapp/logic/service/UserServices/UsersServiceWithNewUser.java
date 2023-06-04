package superapp.logic.service.UserServices;

import superapp.Boundary.User.NewUserBoundary;
import superapp.Boundary.User.UserBoundary;

public interface UsersServiceWithNewUser extends UsersService{
	
	public UserBoundary createUser(NewUserBoundary newUser) throws RuntimeException;

}

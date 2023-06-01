package superapp.Boundary.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;

import java.util.Objects;


public class UserId {
		private String superapp;
		private String email;
	


	public UserId(String superapp,String email) {
		this.email = email;
		this.superapp = superapp;
	}

	public UserId() {

	}




	public String getSuperapp() {
		return superapp;
	}
	
	public void setSuperapp(String superapp) {
		this.superapp = superapp;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		return Objects.hash(superapp, email);
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserId other = (UserId) obj;
		return email.equals(other.email) && superapp.equals(other.superapp) ;
	}

	@Override
	public String toString() {
		return "UserId{" +
				"superapp='" + superapp + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}

package beans;
import javax.persistence.*;

@Entity
@Table(name="users") 
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "login")
	private String login;
	private String password;
	
	public Integer getId() { 
		return id; 
	}
	
	public void setId(Integer id) {
		this.id = id;  
	} 

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
} 
package services;

import beans.User;
import beans.UserLocal;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("user")
public class UserRESTService {
    
	@EJB
	UserLocal userLocal;
	
    @PersistenceContext(unitName="user_dates")
    private EntityManager manager;
    
    @Resource
    private UserTransaction userTransaction;

    @Path("say")
    @GET
    @Produces("text/plain")
    public String sayHello() {
        return "Hello";
    }
    
    @GET
    @Path("Reg/{login}/{pass}/{prov}")
    public String Reg(@PathParam("login") String Log, @PathParam("pass") String Pass, @PathParam("prov") String Prov) throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException{

    	List<User> result = userLocal.findUser(Log);

        if (result.isEmpty()){
        	User user = new User();
        	user.setLogin(Log);
        	user.setPassword(Pass);
        	
        	userLocal.createUser(user);

        	return "0"; // Пользователь успешно создан
        }
        else
        	return "1"; // Пользователь с таким логином уже существует
        
    }

    @GET
    @Path("Log/{login}/{pass}")
    public String Login(@PathParam("login") String login, @PathParam("pass") String pass) {
    	
        List<User> result = userLocal.findUser(login);
        if (result.isEmpty())
        	return "-1"; //		НЕ существует
        else{
        	User user = result.get(0);
        	if (user.getPassword().equals(pass))
        		return "0"; //		SUCCESS
        	else
        		return "1"; // 		Неверный пароль
        }
    }
} 
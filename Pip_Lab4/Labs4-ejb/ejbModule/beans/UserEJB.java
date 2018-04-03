package beans;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class UserEJB implements UserLocal{

	@PersistenceContext(unitName="user_dates")
    private EntityManager manager;
    
    @Resource
    private UserTransaction userTransaction;
	
	@Override
	public List<User> findUser(String login) {
		@SuppressWarnings("unchecked")
		List<User> result = (List<User>) manager.createQuery ("SELECT x FROM User x WHERE x.login = '" + login + "'").getResultList();
		return result;
	}

	@Override
	public void createUser(User user) {
		try {
			userTransaction.begin();
			manager.persist(user);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}
	}
}
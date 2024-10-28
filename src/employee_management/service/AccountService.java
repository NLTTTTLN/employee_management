package employee_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import employee_management.bean.Account;
import employee_management.dao.AccountDAO;

@Service
public class AccountService {
	private final AccountDAO accountDAO;//Khởi tạo AccountDAO 

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO; 
    }

    public Account authenticate(String username, String password) {
        return accountDAO.authenticate(username, password); //Tiến hành authenticate 
    }
}
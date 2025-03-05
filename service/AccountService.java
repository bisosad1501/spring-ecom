package bisosad.kt2.service;

import bisosad.kt2.model.Account;
import bisosad.kt2.model.User;
import bisosad.kt2.repository.AccountRepository;
import bisosad.kt2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
    
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public boolean login(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null) {
            return account.getPassword().equals(password); // So sánh mật khẩu trực tiếp
        }
        return false;
    }
    // Kiểm tra xem người dùng có phải là admin hay không
    public boolean isAdmin(String username) {
        Account account = accountRepository.findByUsername(username);

        // Kiểm tra nếu tài khoản tồn tại và có vai trò là admin
        return account != null && "ROLE_ADMIN".equals(account.getRole());
    }

}

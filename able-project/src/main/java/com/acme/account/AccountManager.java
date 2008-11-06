package com.acme.account;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.acme.user.User;
import com.acme.user.UserManager;
import com.wideplay.warp.persist.Transactional;
import org.hibernate.Session;

@Singleton
public class AccountManager {
    private Provider<Session> session;
    private UserManager userManager;


    @Inject
    public AccountManager(Provider<Session> session, UserManager userManager) {
        this.session = session;
        this.userManager = userManager;
    }

    @Transactional
    public void createAccount(User user) {
        Account account = new Account();
        session.get().save(account);
        account.addUser(user);
        userManager.create(user);
    }

    @Transactional
    public void update(Account account) {
        session.get().update(account);
    }
}

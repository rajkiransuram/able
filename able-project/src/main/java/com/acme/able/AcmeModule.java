package com.acme.able;

import able.guice.AbleModule;
import able.stripes.StripesModule;
import com.acme.account.AccountAccess;
import com.acme.account.AccountManager;
import com.acme.user.UserAccess;
import com.acme.user.UserManager;
import com.wideplay.warp.persist.UnitOfWork;
import com.google.inject.Binder;

public class AcmeModule extends AbleModule {
    public AcmeModule() {
        super(new AcmeConfiguration(), new StripesModule());
    }

    public void configure(Binder binder) {
        super.configure(binder);

        binder.bind(AccountManager.class);
        binder.bind(UserManager.class);
    }
}

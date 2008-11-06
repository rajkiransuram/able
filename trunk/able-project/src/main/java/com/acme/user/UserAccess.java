package com.acme.user;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

public interface UserAccess {
    @Finder(namedQuery = User.Q_BY_USER_ID)
    User findById(@Named("userId")long userId);

    @Finder(namedQuery = User.Q_BY_EMAIL_AND_PASSWORD)
    User findByEmailAndPassword(@Named("email")String email, @Named("password")String password);

    @Finder(namedQuery = User.Q_BY_EMAIL)
    User findByEmail(@Named("email")String email);
}

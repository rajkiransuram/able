package com.acme.stripes;

import able.stripes.StripesModule;
import com.acme.stripes.config.AuthRequiredIntereptor;
import com.acme.stripes.config.AuthRequired;
import com.google.inject.Binder;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;

import java.lang.reflect.Method;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;

public class AcmeStripesModule extends StripesModule {
    public void configure(Binder binder) {
        super.configure(binder);

        Matcher<Method> stripesActionMatcher = Matchers.returns(Matchers.subclassesOf(Resolution.class))
                .or(Matchers.annotatedWith(DefaultHandler.class).or(Matchers.annotatedWith(HandlesEvent.class)));

        AuthRequiredIntereptor authRequiredIntereptor = new AuthRequiredIntereptor();
        binder.bindInterceptor(Matchers.any(),
                stripesActionMatcher.and(Matchers.annotatedWith(AuthRequired.class)),
                authRequiredIntereptor);

        binder.bindInterceptor(Matchers.annotatedWith(AuthRequired.class),
                stripesActionMatcher,
                authRequiredIntereptor);

    }
}

package able.stripes;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import able.stripes.util.AuthRequired;
import able.stripes.util.AuthRequiredIntereptor;
import able.stripes.util.ResourceBundleReset;
import able.guice.GuiceServletContextListner;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.util.ResolverUtil;
import net.sourceforge.stripes.util.ObjectFactory;

import java.lang.reflect.Method;
import java.util.Set;

public class StripesModule implements Module {
    public void configure(Binder binder) {
        Matcher<Method> stripesActionMatcher = Matchers.returns(Matchers.subclassesOf(Resolution.class))
                .or(Matchers.annotatedWith(DefaultHandler.class).or(Matchers.annotatedWith(HandlesEvent.class)));

        AuthRequiredIntereptor authRequiredIntereptor = new AuthRequiredIntereptor();
        binder.bindInterceptor(Matchers.any(),
                stripesActionMatcher.and(Matchers.annotatedWith(AuthRequired.class)),
                authRequiredIntereptor);

        binder.bindInterceptor(Matchers.annotatedWith(AuthRequired.class),
                stripesActionMatcher,
                authRequiredIntereptor);

        ResolverUtil<ActionBean> resolver = new ResolverUtil<ActionBean>();
        ResolverUtil<ActionBean> resolverUtil = resolver.findImplementations(ActionBean.class, getClass().getPackage().getName());
        Set<Class<? extends ActionBean>> classes = resolverUtil.getClasses();
        for (Class<? extends ActionBean> actionBeanClass : classes) {
            binder.bind(actionBeanClass);
        }

        binder.bind(ResourceBundleReset.class);

        ObjectFactory.setInstance(new ObjectFactory() {
            public <T> T newInstance(Class<T> clazz) {
                return GuiceServletContextListner.newInstance(clazz);
            }
        });
    }
}

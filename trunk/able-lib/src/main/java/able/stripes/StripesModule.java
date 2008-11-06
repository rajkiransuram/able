package able.stripes;

import able.guice.AbleContextListener;
import com.google.inject.Binder;
import com.google.inject.Module;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.util.ObjectFactory;
import net.sourceforge.stripes.util.ResolverUtil;

import java.util.Set;

public class StripesModule implements Module {
    public void configure(Binder binder) {
        ResolverUtil<ActionBean> resolver = new ResolverUtil<ActionBean>();
        ResolverUtil<ActionBean> resolverUtil = resolver.findImplementations(ActionBean.class, getClass().getPackage().getName());
        Set<Class<? extends ActionBean>> classes = resolverUtil.getClasses();
        for (Class<? extends ActionBean> actionBeanClass : classes) {
            binder.bind(actionBeanClass);
        }

        binder.bind(ResourceBundleReset.class);

        ObjectFactory.setInstance(new ObjectFactory() {
            public <T> T newInstance(Class<T> clazz) {
                return AbleContextListener.newInstance(clazz);
            }
        });
    }
}

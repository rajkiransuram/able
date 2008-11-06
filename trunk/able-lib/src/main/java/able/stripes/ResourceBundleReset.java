package able.stripes;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.Stage;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.ResourceBundle;

@Singleton
public class ResourceBundleReset {
    private Stage stage;

    @Inject
    public ResourceBundleReset(Stage stage) {
        this.stage = stage;
    }

    public void reset() {
        if (stage == Stage.DEVELOPMENT) {
            try {
                Class type = ResourceBundle.class;
                Field cacheList = type.getDeclaredField("cacheList");
                cacheList.setAccessible(true);
                ((Map) cacheList.get(ResourceBundle.class)).clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package able.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.wideplay.warp.persist.PersistenceService;

@Singleton
public class AbleInitialization {
    private PersistenceService persistenceService;

    @Inject
    public AbleInitialization(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public void init() {
        // start Warp-Persist/Hibernate
        persistenceService.start();
    }
}

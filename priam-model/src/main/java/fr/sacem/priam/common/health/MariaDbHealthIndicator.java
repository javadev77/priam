package fr.sacem.priam.common.health;

import fr.sacem.priam.model.dao.jpa.RoleDao;
import fr.sacem.priam.model.util.ConnectionInfo;
import org.hibernate.internal.SessionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:xchen@palo-it.com">Xi CHEN</a>
 * @since 23/03/15.
 */
@Component
public class MariaDbHealthIndicator extends AbstractHealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MariaDbHealthIndicator.class);

    private final RoleDao roleDao;
    private EntityManagerFactory entityManagerFactory;
    private Map<String, Object> productInfoMap;

    @Autowired
    public MariaDbHealthIndicator(EntityManagerFactory entityManagerFactory, RoleDao roleDao) {
        this.roleDao = roleDao;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (this.productInfoMap == null) {
            this.productInfoMap = getProductInfo();
        }
        doDataSourceHealthCheck(builder);
    }

    private void doDataSourceHealthCheck(Health.Builder builder) {
        try {
            builder.withDetail("driver", productInfoMap);
            builder.withDetail("result", roleDao.count());
            builder.up();
        } catch (Exception ex) {
            builder.down(ex);
        }
    }

    private Map<String, Object> getProductInfo() {
        EntityManager entityManager = null;
        try {
            ConnectionInfo connectionInfo = new ConnectionInfo();
            entityManager = entityManagerFactory.createEntityManager();
            SessionImpl sessionImpl = entityManager.unwrap(SessionImpl.class);
            sessionImpl.doWork(connectionInfo);

            Map<String, Object> map = new HashMap<>();
            map.put("url", connectionInfo.getDataBaseUrl());
            map.put("name", connectionInfo.getDriverName());
            map.put("version", connectionInfo.getDriverVersion());

            Map<String, Object> dbInfo = new HashMap<>();
            dbInfo.put("name", connectionInfo.getDataBaseProductName());
            dbInfo.put("version", connectionInfo.getDatabaseProductVersion());
            map.put("database", dbInfo);
            return map;
        } catch (Exception ex) {
            LOGGER.warn("Page de santé: Erreur lors de l'accès à MariaDB", ex);
            return null;
        } finally {
            if(entityManager != null){
                try {
                    entityManager.close();
                } catch (Exception e) {
                    //ignore
                }
            }
        }
    }

}

package za.co.ajk.incidentman.config.audit;

import za.co.ajk.incidentman.config.Constants;
import za.co.ajk.incidentman.security.SecurityUtils;
import org.javers.spring.auditable.AuthorProvider;
import org.springframework.stereotype.Component;

@Component
public class JaversAuthorProvider implements AuthorProvider {

   @Override
   public String provide() {
       String userName = SecurityUtils.getCurrentUserLogin();
       return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
   }
}

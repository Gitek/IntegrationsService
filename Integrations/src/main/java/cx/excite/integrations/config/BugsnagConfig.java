package cx.excite.integrations.config;

import com.bugsnag.Bugsnag;
import com.bugsnag.BugsnagSpringConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BugsnagSpringConfiguration.class)
public class BugsnagConfig {
    @Value("${build.version}")
    private String buildVersion;

    @Value("${bugsnag.release-stage}")
    private String releaseStage;

    @Bean
    public Bugsnag bugsnag() {
        Bugsnag bugsnag = new Bugsnag("FILL IN KEY HERE");
        bugsnag.setAppVersion(buildVersion);
        bugsnag.setReleaseStage(releaseStage);
        bugsnag.setNotifyReleaseStages("production","staging");

        return bugsnag;
    }
}
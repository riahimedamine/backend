package com.siga.ecp.tn;

import com.siga.ecp.tn.config.ApplicationProperties;
import com.siga.ecp.tn.config.CRLFLogConverter;
import com.siga.ecp.tn.domain.Authority;
import com.siga.ecp.tn.domain.User;
import com.siga.ecp.tn.repository.AuthorityRepository;
import com.siga.ecp.tn.repository.UserRepository;
import com.siga.ecp.tn.security.AuthoritiesConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import tech.jhipster.config.DefaultProfileUtil;
import tech.jhipster.config.JHipsterConstants;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
@EnableConfigurationProperties({LiquibaseProperties.class, ApplicationProperties.class})
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final Environment env;

    public Application(Environment env) {
        this.env = env;
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
            .ofNullable(env.getProperty("server.servlet.context-path"))
            .filter(StringUtils::isNotBlank)
            .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
            CRLFLogConverter.CRLF_SAFE_MARKER,
            "\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "External: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );
    }

    /**
     * Initializes backend.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
                activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)
        ) {
            log.error(
                "You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time."
            );
        }
        if (
            activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) &&
                activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)
        ) {
            log.error(
                "You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time."
            );
        }
    }

    @Component
    static class CLI implements CommandLineRunner {
        private final AuthorityRepository authorityRepository;
        private final UserRepository userRepository;

        public CLI(AuthorityRepository authorityRepository, UserRepository userRepository) {
            this.authorityRepository = authorityRepository;
            this.userRepository = userRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            log.debug("Initializing Users and Authorities...");

            if (authorityRepository.count() == 0) {
                log.debug("Creating authorities");
                Authority userRole = authorityRepository.save(new Authority(AuthoritiesConstants.USER));
                Authority adminRole = authorityRepository.save(new Authority(AuthoritiesConstants.ADMIN));
                authorityRepository.save(new Authority(AuthoritiesConstants.RH));
                authorityRepository.save(new Authority(AuthoritiesConstants.ANONYMOUS));

                if (userRepository.count() == 0) {
                    log.debug("Creating default user");
                    userRepository.save(new User(
                        "admin",
                        "admin",
                        "admin@email.com",
                        true,
                        Set.of(userRole, adminRole)
                    ));
                }
            }

            log.debug("Users and Authorities initialized successfully.");
        }
    }
}

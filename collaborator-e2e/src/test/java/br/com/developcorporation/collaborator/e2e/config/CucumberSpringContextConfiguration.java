package br.com.developcorporation.collaborator.e2e.config;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration
public class CucumberSpringContextConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(CucumberSpringContextConfiguration.class);

    @Before
    public void setUp() {
        LOG.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}

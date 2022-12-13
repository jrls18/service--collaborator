package br.com.developcorporation.collaborator.e2e;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.Ignore;
import org.junit.runner.RunWith;


//@Ignore
@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/e2e"},
        plugin = {"pretty", "json:target/cucumber.json"},
        glue = {"br.com.developcorporation.collaborator.e2e"},
        tags = "@geral",
        monochrome = true
)
public class RunCucumberTest {
}

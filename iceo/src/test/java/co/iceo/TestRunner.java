package co.iceo;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "co.iceo" }, plugin = { "pretty", "html:target/cucumber-reports/Report.html" })
public class TestRunner {

}
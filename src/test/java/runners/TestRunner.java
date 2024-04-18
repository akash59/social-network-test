package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to the feature files
        glue = "stepDefinitions",                // Path to the step definitions
        plugin = {"pretty", "html:target/cucumber-reports", "json:target/cucumber.json"}, // Plugins for reporting
        monochrome = true,                        // Display the console output in a proper readable format
        strict = true,                            // Treat undefined and pending steps as errors
        tags = "@SmokeTest or @RegressionTest"    // Filter what to run based on tags
)
public class TestRunner {
    // No need to write additional code. The annotations handle it all.
}

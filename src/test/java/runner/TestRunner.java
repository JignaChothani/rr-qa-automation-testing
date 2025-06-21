package runner;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features/FilteringAndPagination.feature"},
        glue = {"steps"},
        plugin = {"pretty",
                "html:target/cucumber-reports.html"// Generate HTML report
                //"json:target/cucumber.json"//Generate JSON report
                //"junit:target/cucumber.xml" //Generate JUnit XML report
        },
        monochrome = true, // Readable console output
        //tags = "@smoke",// Optional: Run specific scenarios tagged with @smoke
        dryRun = false
)
public class TestRunner {
}
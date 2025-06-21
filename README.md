# rr-qa-automation-testing for TMBD Discover website

# This project is a Cucumber-BDD test automation approach for the TMBD Discover website.
# To run the tests, you can use either of the following methods:
1. From the Test Runner Class (IDE):
   - Navigate to the `TestRunner.java` file located in the `src/test/java/runner` directory.
   - Run the class as a JUnit test.
   - This will execute all the feature files in the `src/test/resources/features` directory.
2. From Maven Command Line: 
   - mvn clean test (Run all features)
   - Run specific feature file(s): 
     - mvn clean test -Dcucumber.options="src/test/resources/features/FilteringAndPagination.feature"
   - Run a specific scenario by line number: 
     - mvn clean test -Dcucumber.options="src/test/resources/features/FilteringAndPagination.feature:3"
# (where 3 is the line number where the scenario starts)
3. To Generate reports:
 - mvn clean test -Dcucumber.options="--plugin html:target/cucumber-reports/html-report.html"

### For CI Integration:
- Ensure you have a CI/CD pipeline set up (e.g., GitHub Actions, Jenkins, etc.).
- Configure the pipeline to run the Maven commands as part of the build process.
- For GitHub Actions:
  - Create a `.github/workflows/ci.yml` file in your repository.
  - Use the following template to set up the workflow:
  - ```yaml
name: CI Pipeline
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '' # or your required JDK version
      - name: Build with Maven
        run: mvn clean install
      - name: Run tests
        run: mvn test -Dcucumber.options="--plugin html:target/cucumber-reports/html-report.html"
        - name: Upload test report

         
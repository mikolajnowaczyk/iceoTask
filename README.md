# iceoTask
Recturment task for ICEO

Description for the task is as below:

Cover
https://api.apilayer.com/exchangerates_data/latest endpoint with the tests:
- Create BDD scenarios with Cucumber in Java/JavaScript
- Automate scenarios
- Ensure that api codes 200, 400, 401, 403, 404 are covered.
Documentation: https://apilayer.com/marketplace/exchangerates_data-api

Solution:
BDD scenarios are located in getLatest folder (src/test/java/co/iceo/getLatest). Codes covered:
- 200 - cases for single base currency (with no desired currency) and scenario for specific pairs (including default no -> EUR)
- 400 - cases for wrong non-existent base currency and non-existent desired currency
- 401 - cases for no and wrong api key - unauthorized
- 403 - no cases, becasue api doesn't handle such an option - either have correct api key (200) or not (401)
- 404 - single case for typo in resource

Tests are done in Java with Rest Assured and JUnit report generation. After test run simple report is generated in target/cucumber-report folder.

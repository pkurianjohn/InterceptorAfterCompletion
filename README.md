# InterceptorAfterCompletion

In order to test from any HTTP client, user below URLs after starting the application:

1. String content with no wait. Response will be immediate : http://localhost:8080/string-response
2. String content with wait. Response will be immediate : http://localhost:8080/string-response?wait
3. Non String content with no wait. Response will be immediate : http://localhost:8080/non-string-response
4. Non String content with wait. Response will be only after the audit event is completed. Three seconds delay is added to simulate audit event : http://localhost:8080/non-string-response?wait

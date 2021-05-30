package com.devcortes.aws;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {AwsApplication.class})
@ActiveProfiles({"test"})
class AwsApplicationTests {


}

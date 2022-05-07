package com.jazz.kafkases.services.aws;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

public class Client {
    public static SesClient getSESClient() {
        final Region region = Region.US_EAST_1;

        SesClient client = SesClient.builder()
                .credentialsProvider(CredentialsProvider.returnCredentials())
                .region(region)
                .build();

        return client;
    }
}

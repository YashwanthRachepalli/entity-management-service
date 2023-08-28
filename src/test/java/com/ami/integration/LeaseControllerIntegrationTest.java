package com.ami.integration;

import com.ami.dataprovider.LeaseDataProvider;
import com.ams.EntityManagementServiceApplication;
import com.ams.config.SpringConfig;
import com.ams.model.LeaseDto;
import com.ams.model.LeaseRequest;
import com.ams.service.LeaseService;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = { SpringConfig.class, EntityManagementServiceApplication.class } )
@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class,
        ManagementWebSecurityAutoConfiguration.class,
        OAuth2ClientAutoConfiguration.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class LeaseControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private LeaseService leaseService;

    @Test
    public void test() {
        UUID tenantId = UUID.randomUUID();

        LeaseRequest leaseRequest = LeaseRequest.builder()
                .tenantId(tenantId)
                .apartmentName("A301")
                .leaseDto(LeaseDataProvider.getLeaseDtoWithEmptySeurityDeposit())
                .build();
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<LeaseDto> response = testRestTemplate.exchange(
                "/api/lease",
                HttpMethod.POST,
                new HttpEntity<>(leaseRequest, httpHeaders),
                LeaseDto.class
        );

        Truth.assertThat(response.getStatusCode().value()).isEqualTo(400);
    }
}

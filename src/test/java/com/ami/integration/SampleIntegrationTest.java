package com.ami.integration;

import com.ami.dataprovider.LeaseDataProvider;
import com.ami.dataprovider.TenantDataProvider;
import com.ami.dataprovider.VisitorDataProvider;
import com.ams.EntityManagementServiceApplication;
import com.ams.config.SpringConfig;
import com.ams.dgs.client.GetVisitorByIdGraphQLQuery;
import com.ams.dgs.client.GetVisitorByIdProjectionRoot;
import com.ams.model.LeaseDto;
import com.ams.model.LeaseRequest;
import com.ams.model.TenantDto;
import com.ams.model.VisitorDto;
import com.ams.service.LeaseService;
import com.ams.service.VisitorService;
import com.google.common.truth.Truth;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import graphql.ExecutionResult;
import org.junit.jupiter.api.Assertions;
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
public class SampleIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;

    @Autowired
    private VisitorService visitorService;

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

    @Test
    public void testTenantController() {
        UUID tenantId = UUID.randomUUID();

        TenantDto tenantDto = TenantDataProvider.getTenantDto();
        tenantDto.setPassword("test");
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<TenantDto> response = testRestTemplate.exchange(
                "/api/tenant",
                HttpMethod.POST,
                new HttpEntity<>(tenantDto, httpHeaders),
                TenantDto.class
        );

        Truth.assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    public void testGetVisitorById() {
        VisitorDto visitor = visitorService.createVisitor(VisitorDataProvider.getVisitorDto());
        Truth.assertThat(visitor.getFirstName()).isEqualTo("test_first_name");

        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                GetVisitorByIdGraphQLQuery.newRequest().id(visitor.getVisitorId().toString()).build(),
                new GetVisitorByIdProjectionRoot().firstName()
        );

        String firstName =
                dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest.serialize(),
                        "data.getVisitorById.firstName");
        Truth.assertThat(firstName).isEqualTo("test_first_name");
    }
}

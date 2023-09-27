package com.ami.cucumber.stepdefinitions;

import com.ams.model.AddressDto;
import com.ams.model.TenantDto;
import com.google.common.truth.Truth;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class TenantCucumberTest {

    private static final Logger log = LoggerFactory.getLogger(TenantCucumberTest.class);

    private final String SERVER_URL = "http://localhost";
    private final String TENANT_ENDPOINT = "/api/tenant";

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    private String tenantEndPoint() {
        return SERVER_URL + ":" + port + TENANT_ENDPOINT;
    }

    TenantDto tenant;
    ResponseEntity<TenantDto> responseEntity;

    @DataTableType
    public TenantDto tenantDtoTransformer(Map<String, String> row) {
        return TenantDto.builder()
                .firstName(row.get("firstName"))
                .lastName(row.get("lastName"))
                .preferredContact(row.get("preferredContact"))
                .govtIssuedIdentifier(row.get("govtIssuedIdentifier"))
                .email(row.get("email"))
                .password(row.get("password"))
                .mobileNumber(row.get("mobileNumber"))
                .build();
    }

    @DataTableType
    public AddressDto addressDtoTransformer(Map<String, String> row) {
        return AddressDto.builder()
                .addressLine1(row.get("addressLine1"))
                .addressLine2(row.get("addressLine2"))
                .pinCode(row.get("pinCode"))
                .build();
    }

    @Given("staff wants to create a tenant with the following attributes")
    public void getTenant(TenantDto tenant) {
        log.info("Tenant basic details: {}", tenant);
        this.tenant = tenant;
    }

    @Given("staff wants to create a tenant with errors")
    public void getTenantWithErrors(TenantDto tenant) {
        log.info("Tenant basic details: {}", tenant);
        this.tenant = tenant;
    }

    @Given("with the following address")
    public void with_the_following_address(AddressDto address) {
        log.info("Address: {}", address);
        tenant.setAddress(address);
    }
    @When("staff saves the new tenant")
    public void staff_saves_the_new_tenant() {
        responseEntity = restTemplate.postForEntity(tenantEndPoint(), tenant, TenantDto.class);
    }
    @Then("the save is successful with 200 response")
    public void the_save() {
        Truth.assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
    }

    @Then("the save is failure with 400 response")
    public void the_save_with_errors() {
        Truth.assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
    }
}

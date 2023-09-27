Feature: Create Tenant

  Scenario: WITH ALL REQUIRED FIELDS IS SUCCESSFUL

    Given staff wants to create a tenant with the following attributes
      | firstName | lastName | govtIssuedIdentifier | email        | mobileNumber | preferredContact | password  |
      | Rachel    | Green    | 2707890128969        | rg@gamil.com | 9618324002   | SMS              | Commit@23 |

    And with the following address
      | addressLine1   | addressLine2 | pinCode |
      | testLine1      | testLine2    | 515003  |

    When staff saves the new tenant
    Then the save is successful with 200 response

  Scenario: WITH ALL REQUIRED FIELDS WITH ERRORS IS FAILURE

    Given staff wants to create a tenant with errors
      | firstName | lastName | govtIssuedIdentifier | email        | mobileNumber | preferredContact | password  |
      | Rachel    | Green    | 2707890128969        | rg@gamil.com | 9618324002   | SMS              | Commit@23 |

    And with the following address
      | addressLine1   | addressLine2 | pinCode |
      | testLine1      | testLine2    | 515003  |

    When staff saves the new tenant
    Then the save is failure with 400 response

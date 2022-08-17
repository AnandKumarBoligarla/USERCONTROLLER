Feature: UserController feature
  Background: create a user
    Given user details

  Scenario: verify if user resources can be added
    When creating a user
    Then user must be created

    Scenario: Verify the user is able to create with no marks
      When creating a user with no marks
      Then user can be created with zero marks

    Scenario: Verify the user is not created with no name
      When creating a user with no name
      Then Name is required error message thrown

      Scenario: Verify the user is not created with no address
        When creating a user with no address
        Then Address is required error message thrown

        Scenario: Verify the user name is updated
          When Updating a user name
          Then User name must be updated

          Scenario: Verify the user address is updated
            When updating a user address
            Then user address must be updated

            Scenario: verify the user marks is updated
              When updating users marks
              Then user marks must be updated

              Scenario: verify the users list is displayed
               When creating a user
                Then users list must be displayed

                Scenario: verify that the particular user in the list is displayed
                  When creating a user
                  Then user with particular id must be displayed

                  Scenario: verify that the invalid id user is displayed
                    When creating a user
                    Then Blank page must be displayed

                    Scenario:Verify that the user is deleted
                      When creating a user
                      Then user got deleted

                      Scenario: verify that the invalid user is deleted
                        When creating a user
                        Then internal server error message displayed

                        Scenario: verify that the multiple users are added

                          When creating multiple users
                          Then multiple users must be created




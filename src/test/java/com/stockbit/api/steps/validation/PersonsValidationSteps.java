package com.stockbit.api.steps.validation;

import com.stockbit.api.api.common.ApiResponse;
import com.stockbit.api.api.response.PersonResponsePayload;
import com.stockbit.api.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class PersonsValidationSteps {

    private final TestContext context;

    public PersonsValidationSteps() {
        this.context = TestContext.getInstance();
    }

    private ApiResponse<PersonResponsePayload> getPersonsResponse() {
        return context.get("lastResponse", ApiResponse.class);
    }

    private List<PersonResponsePayload> getPersons() {
        ApiResponse<PersonResponsePayload> response = getPersonsResponse();
        return response.getData();
    }

    @When("I request {int} persons from API")
    public void iRequestPersonsFromAPI(int quantity) {
        ApiResponse<PersonResponsePayload> response = getPersonsResponse();
        Assert.assertNotNull("Response should not be null", response);
    }

    @When("I request {int} persons with gender {string} from API")
    public void iRequestPersonsWithGenderFromAPI(int quantity, String gender) {
        ApiResponse<PersonResponsePayload> response = getPersonsResponse();
        Assert.assertNotNull("Response should not be null", response);
    }

    @When("I request {int} persons with query parameters:")
    public void iRequestPersonsWithQueryParams(int quantity, Map<String, Object> params) {
        ApiResponse<PersonResponsePayload> response = getPersonsResponse();
        Assert.assertNotNull("Response should not be null", response);
    }

    @Then("the persons API response should be successful")
    public void thePersonsApiResponseShouldBeSuccessful() {
        ApiResponse<PersonResponsePayload> response = getPersonsResponse();
        Assert.assertTrue("API response was not successful", response.isSuccess());
    }

    @Then("the persons response data should contain {int} persons")
    public void thePersonsResponseDataShouldContainPersons(int expectedCount) {
        List<PersonResponsePayload> persons = getPersons();
        Assert.assertEquals(expectedCount, persons.size());
    }

    @Then("the first person's ID should not be null")
    public void theFirstPersonSIDShouldNotBeNull() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull(firstPerson.getId());
    }

    @Then("the first person's first name should not be empty")
    public void theFirstPersonSFirstNameShouldNotBeEmpty() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull(firstPerson.getFirstname());
        Assert.assertFalse(firstPerson.getFirstname().isEmpty());
    }

    @Then("the first person's last name should not be empty")
    public void theFirstPersonSLastNameShouldNotBeEmpty() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull(firstPerson.getLastname());
        Assert.assertFalse(firstPerson.getLastname().isEmpty());
    }

    @Then("the first person's email should be valid")
    public void theFirstPersonSEmailShouldBeValid() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        String email = firstPerson.getEmail();
        Assert.assertNotNull(email);
        Assert.assertTrue("Email should contain @", email.contains("@"));
    }

    @Then("the first person's phone should not be empty")
    public void theFirstPersonSPhoneShouldNotBeEmpty() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull(firstPerson.getPhone());
        Assert.assertFalse(firstPerson.getPhone().isEmpty());
    }

    @Then("the first person's gender should be {string}")
    public void theFirstPersonSGenderShouldBe(String expectedGender) {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertEquals(expectedGender, firstPerson.getGender());
    }

    @Then("the first person's gender should not be null")
    public void theFirstPersonSGenderShouldNotBeNull() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull(firstPerson.getGender());
    }

    @Then("the first person's birthday should be in valid format")
    public void theFirstPersonSBirthdayShouldBeInValidFormat() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        String birthday = firstPerson.getBirthday();
        Assert.assertNotNull(birthday);
        Assert.assertTrue("Birthday should match YYYY-MM-DD format", birthday.matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Then("the first person's address should not be null")
    public void theFirstPersonSAddressShouldNotBeNull() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull(firstPerson.getAddress());
    }

    @Then("the first person's website should not be empty")
    public void theFirstPersonSWebsiteShouldNotBeEmpty() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull(firstPerson.getWebsite());
        Assert.assertFalse(firstPerson.getWebsite().isEmpty());
    }

    @Then("the first person's image should not be empty")
    public void theFirstPersonSImageShouldNotBeEmpty() {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull(firstPerson.getImage());
        Assert.assertFalse(firstPerson.getImage().isEmpty());
    }

    @Then("all persons should have gender {string}")
    public void allPersonsShouldHaveGender(String expectedGender) {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            Assert.assertEquals("Gender mismatch", expectedGender, person.getGender());
        }
    }

    @Then("all persons should have valid email format")
    public void allPersonsShouldHaveValidEmailFormat() {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            String email = person.getEmail();
            Assert.assertNotNull("Email should not be null", email);
            Assert.assertTrue("Email should contain @: " + email, email.contains("@"));
        }
    }

    @Then("all persons should have valid birthday format")
    public void allPersonsShouldHaveValidBirthdayFormat() {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            String birthday = person.getBirthday();
            Assert.assertNotNull("Birthday should not be null", birthday);
            Assert.assertTrue("Birthday should match YYYY-MM-DD format: " + birthday, birthday.matches("\\d{4}-\\d{2}-\\d{2}"));
        }
    }

    @Then("all persons should have valid phone format")
    public void allPersonsShouldHaveValidPhoneFormat() {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            String phone = person.getPhone();
            Assert.assertNotNull("Phone should not be null", phone);
            Assert.assertTrue("Phone length should be at least 8 chars: " + phone, phone.length() >= 8);
        }
    }

    @Then("all persons should have birthday between {int} and {int}")
    public void allPersonsShouldHaveBirthdayBetween(int startYear, int endYear) {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            String birthday = person.getBirthday();
            int year = Integer.parseInt(birthday.substring(0, 4));
            Assert.assertTrue("Birthday year " + year + " not in range [" + startYear + "," + endYear + "]",
                    year >= startYear && year <= endYear);
        }
    }

    @Then("all persons should have field {string}")
    public void allPersonsShouldHaveField(String field) {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            Assert.assertNotNull("Person missing field: " + field, getFieldValue(person, field));
        }
    }

    @And("verify first person has field {string}")
    public void verifyFirstPersonHasField(String field) {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Assert.assertNotNull("First person missing field: " + field, getFieldValue(firstPerson, field));
    }

    @And("verify person address has field {string}")
    public void verifyPersonAddressHasField(String field) {
        PersonResponsePayload firstPerson = getPersons().get(0);
        Map<String, Object> address = firstPerson.getAddress();
        Assert.assertNotNull("Person address should not be null", address);
        Assert.assertTrue("Person address missing field: " + field, address.containsKey(field));
    }

    @And("verify all persons have gender {string}")
    public void verifyAllPersonsHaveGender(String expectedGender) {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            Assert.assertEquals("Gender mismatch", expectedGender, person.getGender());
        }
    }

    @And("verify all persons have birthday between {int} and {int}")
    public void verifyAllPersonsBirthdayInRange(int startYear, int endYear) {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            String birthday = person.getBirthday();
            int year = Integer.parseInt(birthday.substring(0, 4));
            Assert.assertTrue("Birthday year " + year + " not in range [" + startYear + "," + endYear + "]",
                    year >= startYear && year <= endYear);
        }
    }

    @And("verify all persons have valid birthday format")
    public void verifyAllPersonsHaveValidBirthdayFormat() {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            String birthday = person.getBirthday();
            Assert.assertNotNull("Birthday should not be null", birthday);
            Assert.assertTrue("Invalid birthday format: " + birthday, birthday.matches("\\d{4}-\\d{2}-\\d{2}"));
        }
    }

    @And("verify all persons have valid email format")
    public void verifyAllPersonsHaveValidEmailFormat() {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            String email = person.getEmail();
            Assert.assertNotNull("Email should not be null", email);
            Assert.assertTrue("Invalid email format: " + email, email.contains("@"));
        }
    }

    @And("verify all persons have valid phone format")
    public void verifyAllPersonsHaveValidPhoneFormat() {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            String phone = person.getPhone();
            Assert.assertNotNull("Phone should not be null", phone);
            Assert.assertTrue("Invalid phone format: " + phone, phone.length() >= 8);
        }
    }

    @And("verify all persons have field {string}")
    public void verifyAllPersonsHaveField(String field) {
        List<PersonResponsePayload> persons = getPersons();
        for (PersonResponsePayload person : persons) {
            Assert.assertNotNull("Person missing field: " + field, getFieldValue(person, field));
        }
    }

    private Object getFieldValue(PersonResponsePayload person, String fieldName) {
        switch (fieldName) {
            case "id": return person.getId();
            case "firstname": return person.getFirstname();
            case "lastname": return person.getLastname();
            case "email": return person.getEmail();
            case "phone": return person.getPhone();
            case "gender": return person.getGender();
            case "birthday": return person.getBirthday();
            case "address": return person.getAddress();
            case "website": return person.getWebsite();
            case "image": return person.getImage();
            default: return null;
        }
    }
}

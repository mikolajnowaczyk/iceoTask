package co.iceo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StepDefinitions {
	private final String VALID_API_KEY = "PUT_YOUR_VALID_API_KEY_HERE";
	private final String INVALID_API_KEY = "DEFINITELY_WRONG_API_KEY";

	private static final String BASE_URL = "https://api.apilayer.com";
	private static final String PATH_PARAMS = "exchangerates_data/latest";
	private static final String WRONG_PATH_PARAMS = "exchangerate_data/latest";
	private String baseCurrency;
	private String desiredCurrencies;

	private static Response response;
	private static RequestSpecification request;

	public StepDefinitions() {
		RestAssured.baseURI = BASE_URL;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	};

	public void setDesiredCurrencies(String desiredCurrencies) {
		this.desiredCurrencies = desiredCurrencies;
	}

	public String getDesiredCurrencies() {
		return desiredCurrencies;
	};

	@Given("As a valid user I want to get latest exchange rate for {string}")
	public void as_a_valid_user_i_want_to_get_latest_exchange_rate_for(String baseCurrency) {
		setBaseCurrency(baseCurrency);
		request = RestAssured.given().header("Content-Type", "application/json").header("apikey", VALID_API_KEY)
				.queryParam("base", getBaseCurrency());
	}

	@Given("As a valid user I want to get latest exchange rate between base {string} and {string}")
	public void as_a_valid_user_i_want_to_get_latest_exchange_rate_between_base_and(String baseCurrency,
			String desiredCurrencies) {
		setBaseCurrency(baseCurrency.equals("-----") ? "EUR" : baseCurrency);
		setDesiredCurrencies(desiredCurrencies);
		request = RestAssured.given().header("Content-Type", "application/json").header("apikey", VALID_API_KEY)
				.queryParam("base", getBaseCurrency()).queryParam("symbols", getDesiredCurrencies());
	}

	@Given("As a {string} I want to get latest exchange rate for USD")
	public void as_an_user_i_want_to_get_latest_exchange_rate_for(String authMethod) {
		setBaseCurrency("USD");
		request = RestAssured.given().header("Content-Type", "application/json").queryParam("base", getBaseCurrency());
		if (authMethod.equals("WRONG_AUTH")) {
			request.header("apikey", INVALID_API_KEY);
		}
	}

	@When("I send a request")
	public void i_send_a_request() {
		response = request.when().get(PATH_PARAMS);
	}

	@When("I send a request to non-existent endpoint")
	public void i_send_a_request_to_non_existent_endpoint() {
		response = request.when().get(WRONG_PATH_PARAMS);
	}

	@Then("Response status code is {int}")
	public void response_status_code_is(Integer status_code) {
		response.then().assertThat().statusCode(status_code);
	}

	@Then("Response contains field {string} with value {string}")
	public void response_contains_field_with_value(String field, String expectedFieldValue) {
		expectedFieldValue = expectedFieldValue.equals("-----") ? "EUR" : expectedFieldValue;
		response.then().body(field, equalTo(expectedFieldValue));
	}

	@Then("Response contains a list of currency rates")
	public void response_contains_a_list_of_currency_rates() {
		response.then().assertThat().body("rates", not(anyOf(nullValue(), empty())));
		response.then().assertThat().body("rates", hasKey("USD")).body("rates", hasKey("GBP")).body("rates",
				hasKey(getBaseCurrency()));
	}

	@Then("Response contains a list of currency rates limeted only to desired")
	public void response_contains_a_list_of_currency_rates_limeted_only_to_desired() {
		List<String> list = new ArrayList<String>(Arrays.asList(getDesiredCurrencies().split(",")));
		for (String curr : list) {
			response.then().assertThat().body("rates", hasKey(curr)).body("rates." + curr, isA(Number.class));
		}
	}

	@Then("Response contains error code {string}")
	public void response_contains_error_code(String errorCode) {
		response.then().assertThat().body("error.code", equalTo(errorCode));
	}
}

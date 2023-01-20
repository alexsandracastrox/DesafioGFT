import Entities.Booking;
import Entities.BookingDates;
import Entities.User;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class BookingTest {
   public static Faker faker;
   public static RequestSpecification request;
   private static Booking booking;
   private static BookingDates bookingDates;
   private static User user;

   @BeforeAll
   public static void Setup() {
      RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
      faker = new Faker();
      user = new User(faker.name().username(),
         faker.name().firstName(),
         faker.name().lastName(),
         faker.internet().safeEmailAddress(),
         faker.internet().password(8, 10),
         faker.number().toString());

      bookingDates = new BookingDates("2018-02-08", "2018-03-01");
      booking = new Booking(user.getFirstName(), user.getLastName(), (float) faker.number().randomDouble(2, 50,
         100000000), true, bookingDates, "");
      RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());

   }

   @BeforeEach
   void setRequest() {
      request = given().config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
         .contentType(ContentType.JSON)
         .auth().basic("admin", "password123");
   }

   @Test
   void getBooksById_ReturnOK() {
      Response response = request
         .when()
         .get("/booking")
         .then()
         .extract().response();

      Assertions.assertNotNull(response);
      Assertions.assertEquals(200, response.statusCode());
   }

   @Test
   void getAllBookingByUserFirstName_BookExist_returnOK() {
      request
         .when()
         .queryParam("firstName", "Carol")
         .get("/booking")
         .then()
         .assertThat()
         .statusCode(200)
         .contentType(ContentType.JSON)
         .and()
         .body("results", hasSize(greaterThan(0)));
   }

   @Test
   void CreateBooking_WithValidatData_returnOk() {
      Booking test = booking;
      given()
         .config(RestAssured.config().logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
         .contentType(ContentType.JSON)
         .when()
         .body(test)
         .post("/booking/")
         .then()
         .body(matchesJsonSchemaInClasspath("createBookingResponseSchema.json"))
         .and()
         .assertThat()
         .statusCode(200)
         .contentType(ContentType.JSON).and().time(lessThan(9000L));
   }
}
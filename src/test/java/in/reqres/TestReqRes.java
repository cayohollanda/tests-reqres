package in.reqres;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.Ignore;
import org.junit.Test;

import io.restassured.http.ContentType;

public class TestReqRes extends BaseUriReqRes {

	@Test
	public void testGetAllUsersPaged() {
		given()
		.when()
			.get("/api/users?page=2")
		.then()
			.contentType(ContentType.JSON)
			.statusCode(200)
			.body("data[0].id", equalTo(4))
			.body("data[0].first_name", equalTo("Eve"))
			.body("data[0].last_name", equalTo("Holt"))
			.body("data[0].avatar", equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/marcoramires/128.jpg"));
	}
	
	@Test
	public void testGetAllUsersPagedDelayed() {
		given()
		.when()
			.get("/api/users?delay=3")
		.then()
			.contentType(ContentType.JSON)
			.statusCode(200)
			.body("data[0].id", equalTo(1))
			.body("data[0].first_name", equalTo("George"))
			.body("data[0].last_name", equalTo("Bluth"))
			.body("data[0].avatar", equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg"))
			.time(lessThan(4000L));
	}
	
	@Test
	public void testGetOnlyUser() {
		given()
		.when()
			.get("/api/users/2")
		.then()
			.contentType(ContentType.JSON)
			.statusCode(200)
			.body("data.first_name", equalTo("Janet"))
			.body("data.last_name", equalTo("Weaver"))
			.body("data.avatar", equalTo("https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg"));
	}

	@Test
	public void testPostRegisterUser() {
		final String json = "{\"email\": \"sydney@fife\", \"password\": \"pistol\"}";

		given()
			.contentType("application/json")
			.body(json)
		.when()
			.post("/api/register")
		.then()
			.statusCode(201)
			.contentType("application/json")
			.body("token", equalTo("QpwL5tke4Pnpja7X"));
	}

	@Test
	public void testPostRegisterUserUnsuccessful() {
		final String json = "{\"email\": \"sydney@fife\"}";
		
		given()
			.contentType("application/json")
			.body(json)
		.when()
			.post("/api/register")
		.then()
			.statusCode(400)
			.contentType("application/json")
			.body("error", equalTo("Missing password"));
	}
	
	@Test
	public void testGetSingleUserNotFound() {
		given()
		.when()
			.get("/api/users/23")
		.then()
			.statusCode(404);

	}
	
	@Test
	public void testGetApiUnknown() {
		given()
		.when()
			.get("/api/unknown")
		.then()
			.statusCode(200)
			.body("data[0].id", equalTo(1))
			.body("data[0].name", equalTo("cerulean"))
			.body("data[0].year", equalTo(2000))
			.body("data[0].color", equalTo("#98B2D1"))
			.body("data[0].pantone_value", equalTo("15-4020"));

	}
	
	@Test
	public void testGetApiSingleOnlyUnknown() {
		given()
		.when()
			.get("/api/unknown/2")
		.then()
			.statusCode(200)
			.body("data.id", equalTo(2))
			.body("data.name", equalTo("fuchsia rose"))
			.body("data.year", equalTo(2001))
			.body("data.color", equalTo("#C74375"))
			.body("data.pantone_value", equalTo("17-2031"));

	}
	
	@Test
	public void testGetApiSingleOnlyUnknownNotFound() {
		given()
		.when()
			.get("/api/unknown/23")
		.then()
			.statusCode(404);
	}
	
	@Test
	public void testPostCreate() {
		final String json = "{\"name\": \"morpheus\", \"job\": \"leader\"}";
		
		given()
			.contentType("application/json")
			.body(json)
		.when()
			.post("/api/users")
		.then()
			.statusCode(201)
			.contentType("application/json")
			.body("name", equalTo("morpheus"))
			.body("job", equalTo("leader"));
	}
	
	@Test
	public void testPutUpdate() {
		final String json = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";
		
		given()
			.contentType("application/json")
			.body(json)
		.when()
			.put("/api/users/2")
		.then()
			.statusCode(200)
			.contentType("application/json")
			.body("name", equalTo("morpheus"))
			.body("job", equalTo("zion resident"));
	}
	
	@Test
	public void testPatch() {
		final String json = "{\"name\": \"morpheus\", \"job\": \"zion resident\"}";
		
		given()
			.contentType("application/json")
			.body(json)
		.when()
			.patch("/api/users/2")
		.then()
			.statusCode(200)
			.contentType("application/json")
			.body("name", equalTo("morpheus"))
			.body("job", equalTo("zion resident"));
	}
	
	@Test
	public void testDelete() {
		given()
		.when()
			.delete("/api/users/2")
		.then()
			.statusCode(204);
	}
	
	@Test
	public void testLoginSuccessful() {
		final String json = "{\"email\": \"peter@klaven\", \"password\": \"cityslicka\"}";
		
		given()
			.contentType("application/json")
			.body(json)
		.when()
			.post("/api/login")
		.then()
			.statusCode(200)
			.contentType("application/json")
			.body("token", equalTo("QpwL5tke4Pnpja7X"));
	}
	
	@Test
	public void testLoginUnsuccessful() {
		final String json = "{\"email\": \"peter@klaven\"}";
		
		given()
			.contentType("application/json")
			.body(json)
		.when()
			.post("/api/login")
		.then()
			.statusCode(400)
			.contentType("application/json")
			.body("error", equalTo("Missing password"));
	}

}

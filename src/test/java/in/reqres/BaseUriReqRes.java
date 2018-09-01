package in.reqres;

import org.junit.Before;

import static io.restassured.RestAssured.baseURI;

public class BaseUriReqRes {

	@Before
	public void before() {
		baseURI = "https://reqres.in/";
	}
}

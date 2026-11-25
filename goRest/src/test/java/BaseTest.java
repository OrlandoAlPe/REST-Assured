import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

public class BaseTest {

	private String idCreado;

	@Test
	public void f() {
		given().baseUri("https://reqres.in/api").when().get("/users/2").then().statusCode(200)
				.body("data.email", equalTo("janet.weaver@reqres.in")).log().all(); // Esto imprime en consola todo lo
																					// que responde la API
	}

	@Test
	public void tiempoDeRespuestaMenorDe300ms() {
		given().baseUri("https://reqres.in/api").when().get("/users/2").then().statusCode(200)
				.time(lessThan(350L)); // Tiempo en milisegundos
	}

	@Test
	public void getUsuario() {
		given().baseUri("https://reqres.in/api").when().get("/users/2").then().statusCode(200)
				.body("data.id", equalTo(2));
	}

	@Test
	public void postCrearUsuario() {
		idCreado = given().baseUri("https://reqres.in/api").contentType("application/json")
				.body("{\"name\":\"morpheus\",\"job\":\"leader\"}").when().post("/users").then().statusCode(201)
				.body("name", equalTo("morpheus")).body("id", notNullValue()).extract().path("id");
	}

	@Test
	public void putActualizarUsuario() {
		given().baseUri("https://reqres.in/api").contentType("application/json")
				.body("{\"name\":\"morpheus\",\"job\":\"zion resident\"}").when().put("/users/2").then()
				.statusCode(200).body("job", equalTo("zion resident")).body("updatedAt", notNullValue());
	}

	@Test(dependsOnMethods = "postCrearUsuario")
	public void deleteUsuario() {
		given().baseUri("https://reqres.in/api").when().delete("/users/" + idCreado).then().statusCode(204);
	}

}

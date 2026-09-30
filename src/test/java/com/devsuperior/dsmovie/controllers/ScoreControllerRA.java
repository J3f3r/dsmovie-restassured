package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class ScoreControllerRA {

	private Long existingMovieId, nonExistingMovieId;
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken, invalidToken;
	
	private Map<String, Object> putScoreInstance;
	
	@BeforeEach
	public void setUp() throws JSONException{
		
		baseURI = "http://localhost:8080";
		
		existingMovieId = 1L;
		nonExistingMovieId = 100L;
		
		clientUsername = "alex@gmail.com";
		clientPassword = "123456";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";// invalid Token
		
		putScoreInstance = new HashMap<>();
		putScoreInstance.put("movieId", 1);
		putScoreInstance.put("score", 4);
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		
		// 1. Informa um movieId INEXISTENTE dentro do corpo do JSON
		putScoreInstance.put("movieId", nonExistingMovieId);
		putScoreInstance.put("score", 4.0);
		
		JSONObject newScore = new JSONObject(putScoreInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(404)
			.body("status", is(404))
			.body("error", equalTo("Recurso não encontrado"));
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		
		// 1. Remove a chave "movieId" da instancia do mapa
	    putScoreInstance.remove("movieId");
	    putScoreInstance.put("score", 4.0);
		
		JSONObject newScore = new JSONObject(putScoreInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		
		Map<String, Object> invalidScoreInstance = new HashMap<>();
	    invalidScoreInstance.put("movieId", existingMovieId);
	    invalidScoreInstance.put("score", -1.0);

		
		JSONObject newScore = new JSONObject(invalidScoreInstance);
		
		given()
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer " + clientToken)
			.body(newScore)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/scores")
		.then()
			.statusCode(422)
			.body("status", is(422))
			.body("error", equalTo("Dados inválidos"))
			.body("errors[0].message", equalTo("Score should be greater than or equal to zero"))
			.body("errors[0].fieldName", equalTo("score"));
	}
}

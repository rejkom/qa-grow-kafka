package com.kafka.training.rest;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/*
https://docs.confluent.io/platform/current/kafka-rest/quickstart.html
 */
public class RestProxyTest {

    private static final String CONTENT_TYPE = "application/vnd.kafka.json.v2+json";

    @Test
    void produceRecordsByRest() {

        String restMessage = "{ \"records\": [{ \"value\": \"Hello Rest service on Friday 2 :)\" }]}";
        Response response = given()
                .contentType(CONTENT_TYPE)
                .body(restMessage).post("http://192.168.122.68:8082/topics/qa-grow")
                .then().extract().response();
        System.out.println(response.body().prettyPrint());
    }

    @Test
    void consumeRecordsByRest() {
        createConsumer().subscribeToTopic();
        Response response = given()
                .accept(CONTENT_TYPE)
                .get("http://192.168.122.68:8082/consumers/qa-grow-michal/instances/michal-consumer-instance/records")
                .then().extract().response();
        System.out.println(response.body().prettyPrint());
        unsubscribeConsumer();
    }

    private RestProxyTest createConsumer() {
        /*
        <consumer-group>: The consumer group name to which the consumer belongs.
        <consumer-instance>: The unique identifier for the consumer instance (group.id), e.g. qa-grow-michal
        (doesn't need to be specified in Kafka server config/properties), e.g. michal-consumer-instance
        <topic-name>: The name of the Kafka topic from which you want to consume messages, e.g. 'qa-grow'
         */
        Response consumerCreationResponse = given()
                .contentType(CONTENT_TYPE)
                .body("{\"name\": \"michal-consumer-instance\", \"format\": \"json\", \"auto.offset.reset\": \"earliest\"}")
                .post("http://192.168.122.68:8082/consumers/qa-grow-michal")
                .then().extract().response();
        System.out.println(consumerCreationResponse.body().prettyPrint());
        return this;
    }

    private void subscribeToTopic() {
        //Subscribe to a topic
        Response consumerCreationResponse = given()
                .contentType(CONTENT_TYPE)
                .body("{\"topics\":[\"qa-grow\"]}")
                .post("http://192.168.122.68:8082/consumers/qa-grow-michal/instances/michal-consumer-instance/subscription")
                .then().extract().response();
        System.out.println(consumerCreationResponse.body().prettyPrint());
    }

    private void unsubscribeConsumer() {
        //Leave the group and clean up
        given().contentType(CONTENT_TYPE)
                .delete("http://192.168.122.68:8082/consumers/qa-grow-michal/instances/michal-consumer-instance")
                .then().extract().response();
    }

}

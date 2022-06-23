package com.learnkafka.learnkafka.controller;

import domain.Book;
import domain.LibraryEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"library-events"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers = ${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap-servers = ${spring.embedded.kafka.brokers}"})
public class LibraryEventsControllerIntegrationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void postLibraryEvent(){
        Book book = Book.builder()
                .bookId(123)
                .bookAuthor("Reese Yancey")
                .bookName("Kafka using Spring Boot")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<LibraryEvent> request = new HttpEntity<>(libraryEvent,headers);

        ResponseEntity<LibraryEvent> responseEntity= restTemplate.exchange("/v1/libraryevent", HttpMethod.POST, request, LibraryEvent.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }
}

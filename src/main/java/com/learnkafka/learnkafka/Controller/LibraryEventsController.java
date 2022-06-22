package com.learnkafka.learnkafka.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.learnkafka.producer.LibraryEventProducer;
import domain.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
public class LibraryEventsController {

    @Autowired
    LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        //invoke kafka producer
        //showing how this call is asynchronous using logs
        log.info("Before sendLibraryEvent");
//        libraryEventProducer.sendLibraryEvent(libraryEvent);
//        SendResult<Integer, String> sendResult = libraryEventProducer.sendLibraryEventSynchronous(libraryEvent);
        libraryEventProducer.sendLibraryEventUsingProducerRecord(libraryEvent);
//        log.info("SendResult is {}", sendResult.toString());
        log.info("After sendLibraryEvent");
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}

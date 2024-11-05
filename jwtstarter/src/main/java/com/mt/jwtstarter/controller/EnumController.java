package com.mt.jwtstarter.controller;


import com.mt.jwtstarter.enums.Channel;
import com.mt.jwtstarter.enums.Priority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enum")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EnumController {
    @GetMapping("/channels")
    public ResponseEntity<List<Channel>> getChannels() {
        return ResponseEntity.ok(Arrays.asList(Channel.values()));
    }

    @GetMapping("/priorities")
    public ResponseEntity<List<Priority>> getPriorities() {
        return ResponseEntity.ok(Arrays.asList(Priority.values()));
    }
}

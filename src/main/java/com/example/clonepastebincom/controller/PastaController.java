package com.example.clonepastebincom.controller;

import com.example.clonepastebincom.dto.PastaDTO;
import com.example.clonepastebincom.model.Pasta;
import com.example.clonepastebincom.service.PastaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.Deque;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping
public class PastaController {

    private final PastaService service;


    @PostMapping
    public URL add(@RequestBody PastaDTO pastaDTO) {
        return service.save(pastaDTO);
    }

    @GetMapping("/last")
    public Deque<Pasta> last() {
        return service.getCacheAll();
    }

    @GetMapping("/{hash}")
    public Pasta get(@PathVariable String hash) {
        return service.get(hash);
    }

    @GetMapping("/all")
    public List<Pasta> all() {
        return service.getAll();
    }
}

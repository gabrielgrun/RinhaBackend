package com.ggrun.rinha.api;

import com.ggrun.rinha.entity.Pessoa;
import com.ggrun.rinha.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody Pessoa pessoa){
        if(!pessoa.isValid() || pessoaService.findByApelido(pessoa.getApelido()) != null){
            return ResponseEntity.unprocessableEntity().build();
        }

        UUID id = pessoaService.create(pessoa);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/pessoas/" + id);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public void get(@PathVariable("id") String id){}

    @GetMapping("/")
    public void search(@RequestParam("t") String term){}

    @GetMapping("/contagem-pessoas")
    public Integer totalCount(){
        return pessoaService.count();
    }
}

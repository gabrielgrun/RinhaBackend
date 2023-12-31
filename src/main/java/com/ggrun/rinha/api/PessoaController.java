package com.ggrun.rinha.api;

import com.ggrun.rinha.cache.PessoaCache;
import com.ggrun.rinha.entity.Pessoa;
import com.ggrun.rinha.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @Autowired
    PessoaCache pessoaCache;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.unprocessableEntity().build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody Pessoa pessoa) {
        if (!pessoa.isValid() || pessoaCache.hasApelido(pessoa.getApelido())) {
            return ResponseEntity.unprocessableEntity().build();
        }

        pessoa.setId(UUID.randomUUID());
        pessoaService.create(pessoa);
        addCache(pessoa);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/pessoas/" + pessoa.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    private void addCache(Pessoa pessoa) {
        pessoaCache.addApelido(pessoa.getApelido());
        pessoaCache.addPessoa(pessoa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> get(@PathVariable("id") String id) {
        Pessoa pessoa;
        pessoa = pessoaCache.getPessoaById(id);

        if(pessoa == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("")
    public ResponseEntity<List<Pessoa>> search(@RequestParam("t") String termo) {
        if(termo == null || termo.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        List<Pessoa> pessoas;

        try {
            pessoas = pessoaService.findByTermo(termo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/contagem-pessoas")
    public Integer totalCount() {
        return pessoaService.count();
    }
}

package com.ggrun.rinha.service;

import com.ggrun.rinha.entity.Pessoa;
import com.ggrun.rinha.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    PessoaRepository repository;

    @Override
    public UUID create(Pessoa pessoa){
        Pessoa pessoaInserida = repository.save(pessoa);
        return pessoaInserida.getId();
    }

    @Override
    public Pessoa getById(String id){
        Optional<Pessoa> pessoaEntity = repository.findById(id);
        return pessoaEntity.orElseGet(() -> null);
    }

    @Override
    public Pessoa findByApelido(String apelido){
        return repository.findByApelido(apelido);
    }

    @Override
    public Integer count(){
        return repository.findAll().size();
    }
}

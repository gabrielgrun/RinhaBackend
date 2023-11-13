package com.ggrun.rinha.service;

import com.ggrun.rinha.entity.Pessoa;
import com.ggrun.rinha.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class PessoaServiceImpl implements PessoaService {

    @Autowired
    PessoaRepository repository;

    private static final ConcurrentLinkedQueue<Pessoa> pessoas = new ConcurrentLinkedQueue<>();

    @Override
    public void create(Pessoa pessoa){
        pessoas.add(pessoa);
    }

    @Override
    public Pessoa getById(String id) throws Exception {
        Optional<Pessoa> pessoaEntity = repository.findById(UUID.fromString(id));
        return pessoaEntity.orElseThrow(() -> new Exception("Pessoa with id " + id + " not found"));
    }

    @Override
    public Pessoa findByApelido(String apelido){
        return repository.findByApelido(apelido);
    }

    @Override
    public List<Pessoa> findByTermo(String termo){
        return repository.findByTermo(termo);
    }

    @Override
    public Integer count(){
        return repository.findAll().size();
    }

    @Scheduled(fixedDelay = 2000)
    public void batchInsertScheduled() {
        final Queue<Pessoa> pessoasQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < pessoas.size(); i++) {
            pessoasQueue.add(pessoas.poll());
        }
        batchInsert(pessoasQueue);
    }

    public void batchInsert(final Queue<Pessoa> pessoasQueue) {
        if (pessoasQueue.isEmpty()) return;
        repository.saveAll(pessoasQueue);
    }
}

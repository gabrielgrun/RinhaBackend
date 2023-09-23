package com.ggrun.rinha.service;

import com.ggrun.rinha.entity.Pessoa;

import java.util.List;
import java.util.UUID;

public interface PessoaService {

    public UUID create(Pessoa pessoa);

    public Pessoa getById(String id) throws Exception;

    public Pessoa findByApelido(String apelido);

    public List<Pessoa> findByTermo(String termo);

    public Integer count();
}

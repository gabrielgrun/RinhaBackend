package com.ggrun.rinha.service;

import com.ggrun.rinha.entity.Pessoa;

import java.util.UUID;

public interface PessoaService {

    public UUID create(Pessoa pessoa);

    public Pessoa getById(String id);

    public Pessoa findByApelido(String apelido);

    public Integer count();
}

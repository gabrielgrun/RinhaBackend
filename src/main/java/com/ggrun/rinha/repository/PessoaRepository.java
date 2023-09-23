package com.ggrun.rinha.repository;

import com.ggrun.rinha.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    public Pessoa findByApelido(String apelido);
}

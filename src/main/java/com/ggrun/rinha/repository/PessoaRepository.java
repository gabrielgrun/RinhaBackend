package com.ggrun.rinha.repository;

import com.ggrun.rinha.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {

    public Pessoa findByApelido(String apelido);
}

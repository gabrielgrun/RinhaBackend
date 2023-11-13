package com.ggrun.rinha.repository;

import com.ggrun.rinha.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    public Pessoa findByApelido(String apelido);

    @Query(value = "select id, apelido, nome, nascimento, stack from pessoa where termo ilike %:termo% limit 50",
            nativeQuery = true)
    public List<Pessoa> findByTermo(@Param("termo") String termo);
}

package com.ggrun.rinha.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggrun.rinha.entity.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PessoaCache {

    @Autowired
    private RedisTemplate<String, String> cache;
    @Autowired
    private ObjectMapper json;

    public void addApelido(String apelido){
        cache.opsForValue().set(apelido, "3");
    }

    public boolean hasApelido(String apelido){
        String hasApelido = cache.opsForValue().get(apelido);
        return hasApelido != null;
    }

    public void addPessoa(Pessoa pessoa){
        try {
            cache.opsForValue().set(pessoa.getId().toString(), json.writeValueAsString(pessoa));
        } catch (JsonProcessingException e) {
        }
    }

    public Pessoa getPessoaById(String id){
        String pessoaJson = cache.opsForValue().get(id);

        if(pessoaJson == null || pessoaJson.isEmpty()){
            return null;
        }

        try {
            return json.readValue(pessoaJson, Pessoa.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}

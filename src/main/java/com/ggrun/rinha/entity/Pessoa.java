package com.ggrun.rinha.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "pessoa", schema = "public")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;
    @Column(name = "apelido", length = 32, unique = true)
    String apelido;
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O nome deve conter apenas letras.")
    @Column(name = "nome", length = 100)
    String nome;
    @Column(name = "nascimento")
    String nascimento;
    @Column(name = "stack", columnDefinition = "text")
    List<String> stack;

    public Pessoa(){

    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @JsonIgnore
    public boolean isValid(){
        return isValidApelido() && isValidNome() && isValidNascimento() && isValidStack();
    }
    private boolean isValidApelido(){
        return apelido != null && !apelido.isEmpty() && apelido.length() < 32;
    }
    private boolean isValidNome(){
        return nome != null && !nome.isEmpty() && nome.length() < 100;
    }
    private boolean isValidNascimento(){
        return nascimento != null && isValidDate(nascimento);
    }
    private boolean isValidStack(){
        return stack == null || (!stack.isEmpty() && stack.stream().allMatch(el -> el != null && el.length() < 32));
    }

    private boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

package com.tito.crud.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tito.crud.domain.model.Pessoa;



@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
package com.pw3.aluno.repository;

import com.pw3.aluno.model.Aluno;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}

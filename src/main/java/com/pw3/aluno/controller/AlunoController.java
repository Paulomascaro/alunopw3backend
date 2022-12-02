package com.pw3.aluno.controller;

import com.pw3.aluno.model.Aluno;
import com.pw3.aluno.model.Periodo;
import com.pw3.aluno.model.Sexo;
import com.pw3.aluno.repository.AlunoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ExportException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("http://localhost:4200")
@RestController
public class AlunoController {
    @Autowired
    AlunoRepository alunoRepository;

    @GetMapping(value = "api/alunos")
    public ResponseEntity<List<Aluno>> getAlunos() {
        try{
            List<Aluno> alunos = alunoRepository.findAll();
            return new ResponseEntity<>(alunos, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "api/alunos/{id}")
    public ResponseEntity<Aluno> getAluno(@PathVariable(value = "id")long id) {
        try {
            Optional<Aluno>aluno = alunoRepository.findById(id);
            return aluno.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @PostMapping(value = "api/alunos")
    public ResponseEntity<Aluno> postAluno(@RequestParam Map<String, String> newAluno){
        try{
            Aluno aluno = new Aluno();
            aluno.setName(newAluno.get("nome"));
            aluno.setIdade(Integer.parseInt(newAluno.get("idade")));
            aluno.setEndereco(newAluno.get("endereco"));
            aluno.setSexo(Sexo.values()[Integer.parseInt(newAluno.get("sexo"))]);
            aluno.setEmail(newAluno.get("email"));
            aluno.setPeriodo(Periodo.values()[Integer.parseInt(newAluno.get("periodo"))]);

            Aluno alunoSalvo = alunoRepository.save(aluno); //salvando post dos atributos
            return new ResponseEntity<>(alunoSalvo, HttpStatus.CREATED); //OK/CREATED 200/201
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "api/alunos/{id}")
    public ResponseEntity<Aluno> putAluno(@PathVariable(value = "id")long id, @RequestParam Map<String, String> newAluno){
        Optional<Aluno>aluno = alunoRepository.findById(id);
        if (aluno.isPresent()){
            Aluno alunoEditado = aluno.get();
            alunoEditado.setIdade(Integer.parseInt(newAluno.get("idade")));
            alunoEditado.setEndereco(newAluno.get("endereco"));
            alunoEditado.setEmail(newAluno.get("email"));
            alunoEditado.setPeriodo(Periodo.values()[Integer.parseInt(newAluno.get("periodo"))]);

            try{
                Aluno alunoPut = alunoRepository.save(alunoEditado);
                return new ResponseEntity<>(alunoPut, HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @DeleteMapping(value = "api/alunos/{id}")
    public ResponseEntity<Aluno> deleteAluno(@PathVariable(value = "id")long id){
        Optional<Aluno>aluno = alunoRepository.findById(id);
        if(aluno.isPresent()){
            try{
                alunoRepository.delete(aluno.get());
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}


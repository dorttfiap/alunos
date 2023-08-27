package com.fiap.alunos.controllers;

import com.fiap.alunos.models.Alumn;
import com.fiap.alunos.repositories.AlumnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/alunos")
public class AlunosController {

    private AlumnRepository alumnRepository;

    @Autowired
    public AlunosController(AlumnRepository alumnRepository){
        this.alumnRepository = alumnRepository;
    }

    @PostMapping
    public ResponseEntity<Alumn> save(@RequestBody Alumn alumn){
        alumnRepository.save(alumn);
        return new ResponseEntity<>(alumn, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Alumn>> getAll(){
        List<Alumn> alumns = new ArrayList<>();
        alumns = alumnRepository.findAll();
        return new ResponseEntity<>(alumns, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<Optional<Alumn>> getById(@PathVariable Integer id){
        Optional<Alumn> alumn;
        try {
            alumn = alumnRepository.findById(id);
            return new ResponseEntity<Optional<Alumn>>(alumn, HttpStatus.OK);
        } catch (NoSuchElementException exc) {
            return new ResponseEntity<Optional<Alumn>>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path="/{id}")
    public ResponseEntity<Optional<Alumn>> deleteById(@PathVariable Integer id){
        try {
            alumnRepository.deleteById(id);
            return new ResponseEntity<Optional<Alumn>>(HttpStatus.OK);
        } catch (NoSuchElementException exc) {
            return new ResponseEntity<Optional<Alumn>>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<Alumn> update(@PathVariable Integer id, @RequestBody Alumn newAlumn){
        return alumnRepository.findById(id)
                .map(alumn -> {
                    alumn.setName(newAlumn.getName());
                    alumn.setEnabled(newAlumn.getEnabled());
                    Alumn alumnUpdated = alumnRepository.save(alumn);
                    return ResponseEntity.ok().body(alumnUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

}

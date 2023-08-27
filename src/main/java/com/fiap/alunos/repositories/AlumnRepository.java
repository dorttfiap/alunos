package com.fiap.alunos.repositories;

import com.fiap.alunos.models.Alumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnRepository extends JpaRepository<Alumn, Integer> {
}

package br.ufrn.imd.sa.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ufrn.imd.sa.sa.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

}

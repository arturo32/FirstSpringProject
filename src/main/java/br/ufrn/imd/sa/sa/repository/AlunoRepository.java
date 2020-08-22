package br.ufrn.imd.sa.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ufrn.imd.sa.sa.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

}

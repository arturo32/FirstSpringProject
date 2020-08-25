package br.ufrn.imd.sa.sa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufrn.imd.sa.sa.model.Professor;
import br.ufrn.imd.sa.sa.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {
	
	public Turma findBySala(String sala);
	
	public Turma findByProfessor(Professor professor);

}

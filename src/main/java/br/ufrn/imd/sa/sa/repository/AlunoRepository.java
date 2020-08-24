package br.ufrn.imd.sa.sa.repository;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import br.ufrn.imd.sa.sa.model.Aluno;
import br.ufrn.imd.sa.sa.model.Turma;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
	
	public long countByTurma(Turma turma);
	
	@Transactional
	public void deleteByTurma(Turma turma);
}

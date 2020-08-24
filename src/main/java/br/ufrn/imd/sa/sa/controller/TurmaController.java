package br.ufrn.imd.sa.sa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.sa.sa.model.Professor;
import br.ufrn.imd.sa.sa.model.Turma;
import br.ufrn.imd.sa.sa.repository.AlunoRepository;
import br.ufrn.imd.sa.sa.repository.ProfessorRepository;
import br.ufrn.imd.sa.sa.repository.TurmaRepository;

@Controller
public class TurmaController {
	
	@Autowired
	TurmaRepository TurmaDB;
	
	@Autowired
	ProfessorRepository ProfessorDB;
	
	@Autowired
	AlunoRepository AlunoDB;
	
	@RequestMapping("/cadastroturma")
	public String cadastroTurma( ) {
		return "cadastroturma";
	}
	
	//Salva nova turma no banco de dados
	@PostMapping("/turma")
	public String salvarTurma(Turma turma) {
		
		Professor professorDaTurma = ProfessorDB.findByNome(turma.getProfessor().getNome());
		
		/*Se o professor não existir no banco de dados, 
		a mesma página de cadastro é retornada*/
		if(professorDaTurma == null) {
			return "cadastroturma";
		}
		
		//Se não, a turma é acrescentada ao banco de dados
		turma.setProfessor(professorDaTurma);
		TurmaDB.save(turma);
		
		return "index";
	}
	
	//Lista turmas
	@RequestMapping("/turmas")
	public ModelAndView listarTurmas() {
		
		ModelAndView mv = new ModelAndView("consulta");
		List<Turma> turmas = TurmaDB.findAll();
		
		int[] alunos = new int[turmas.size()]; 
		
		for(int i = 0; i < turmas.size(); ++i) {
			alunos[i] = (int) AlunoDB.countByTurma(turmas.get(i));
		}
		
		String titulo = "Turmas";

		mv.addObject("turmas", turmas);
		mv.addObject("alunosDasTurmas", alunos);
		mv.addObject("titulo", titulo);
		return mv;
	}

	
}

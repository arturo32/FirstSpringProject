package br.ufrn.imd.sa.sa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.sa.sa.model.Professor;
import br.ufrn.imd.sa.sa.model.Turma;
import br.ufrn.imd.sa.sa.repository.AlunoRepository;
import br.ufrn.imd.sa.sa.repository.ProfessorRepository;
import br.ufrn.imd.sa.sa.repository.TurmaRepository;

@Controller
public class TurmaController {
	
	@Autowired
	TurmaRepository TurmaBC;
	
	@Autowired
	ProfessorRepository ProfessorBC;
	
	@Autowired
	AlunoRepository AlunoBC;
	
	
	//Retorna página de cadastro de turma
	@GetMapping("/cadastroturma")
	public String cadastroTurma( ) {
		return "cadastroturma";
	}
	
	//Salva nova turma no banco de dados
	@PostMapping("/turma")
	public ModelAndView salvarTurma(Turma turma) {
		
		Professor professorDaTurma = ProfessorBC.findByNome(turma.getProfessor().getNome());
		
		ModelAndView mv = new ModelAndView("cadastroturma");
		
		String mensagemErro = null;
		
		/*Se o professor não existir no banco de dados, 
		a mesma página de cadastro é retornada com uma mensagem de erro*/
		if(professorDaTurma == null) {
			mensagemErro = turma.getProfessor().getNome() + " não está cadastrado no banco de dados. Tente novamente.";
			
		}
		
		/*Se o professor ja estive em uma turma, a mesma página de 
		cadastro é retornada com uma mensagem de erro*/
		else if(TurmaBC.findByProfessor(professorDaTurma) != null) {
			mensagemErro = turma.getProfessor().getNome() + " já está em uma turma. Tente novamente.";
		}
		
		/*Se não, a turma é acrescentada ao banco de dados
		e a página principal é retornada*/
		else {		
			turma.setProfessor(professorDaTurma);
			TurmaBC.save(turma);
			mv.clear();
			mv.setViewName("index");
		}
		
		mv.addObject("mensagemErro", mensagemErro);
		
		return mv;
	}
	
	//Lista turmas
	@GetMapping("/turmas")
	public ModelAndView listarTurmas() {
		
		ModelAndView mv = new ModelAndView("consulta");
		List<Turma> turmas = TurmaBC.findAll();
		
		int[] alunos = new int[turmas.size()]; 
		
		for(int i = 0; i < turmas.size(); ++i) {
			alunos[i] = (int) AlunoBC.countByTurma(turmas.get(i));
		}
		
		String titulo = "Turmas";

		mv.addObject("turmas", turmas);
		mv.addObject("alunosDasTurmas", alunos);
		mv.addObject("titulo", titulo);
		return mv;
	}
	
	//Retorna página para atualizar dados de um turma
	@GetMapping("/atualizarturma/{id}")
	public ModelAndView atualizaTurma(@PathVariable int id) {
		
		ModelAndView mv = new ModelAndView("cadastroturma");
		
		String titulo = "Atualizar";
		
		Turma turma = TurmaBC.findById(id).orElse(new Turma());
	
		mv.addObject("turma", turma);
		mv.addObject("titulo", titulo);
		
		return mv;
	}
	
	//Atualiza dados de uma turma no banco de dados
	@PostMapping("atualizarturma/{id}")
	public ModelAndView atualizarTurma(@PathVariable int id, Turma turmaAtualizada) {
		
		//Procura professor da turma pelo nome
		Professor professorDaTurma =  ProfessorBC.findByNome(turmaAtualizada.getProfessor().getNome());
		
		ModelAndView mv = new ModelAndView("index");
		
		String mensagemErro = null;
		
		/*Se o professor não existir no banco de dados, 
		a página principal é retornada com um mensagem de erro*/
		if(professorDaTurma == null) {
			mensagemErro = turmaAtualizada.getProfessor().getNome() + " não está cadastrado no banco de dados. Tente novamente.";
		}
		
		/*Se o professor ja estive em uma turma, a mesma página de 
		cadastro é retornada com uma mensagem de erro*/
		else if(TurmaBC.findByProfessor(professorDaTurma) != null) {
			mensagemErro = turmaAtualizada.getProfessor().getNome() + " já está em uma turma. Tente novamente.";
		}
		
		
		/*Se existir, a turma atualizada é salva no banco de dados 
		  e a página principal é retornada*/
		else {
			//Acha antiga turma no banco de dados pelo ID
			Turma turma = TurmaBC.findById(id).orElse(new Turma());
			
			/*Define o ID da nova turma como o ID da antiga turma
			e atualiza seus dados no banco de dados*/
			turmaAtualizada.setId(turma.getId());
			turmaAtualizada.setProfessor(professorDaTurma);
			TurmaBC.save(turmaAtualizada);
			
			//A página pricipal é retornada
			mv.clear();
			mv.setViewName("index");
		}
		
		mv.addObject("mensagemErro", mensagemErro);

		
		return mv;
	}
	
	@GetMapping("deletarturma/{id}")
	public String deletarTurma(@PathVariable int id) {
		
		//Deleta, primeiramente, todos os alunos que fazem parte da turma
//		AlunoBC.deleteByTurma(TurmaBC.findById(id).orElse(new Turma()));
		
		//Deleta turma
		TurmaBC.deleteById(id);
		
		//Retorna para página principal
		return "index";
	}

	
}

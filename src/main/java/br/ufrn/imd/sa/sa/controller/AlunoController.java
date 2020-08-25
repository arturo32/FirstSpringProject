package br.ufrn.imd.sa.sa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.sa.sa.model.Aluno;
import br.ufrn.imd.sa.sa.model.Turma;
import br.ufrn.imd.sa.sa.repository.AlunoRepository;
import br.ufrn.imd.sa.sa.repository.TurmaRepository;

@Controller
public class AlunoController {
	
	@Autowired
	AlunoRepository AlunoBC;
	
	@Autowired
	TurmaRepository TurmaBC;
	
	//Retorna a página de cadastro do aluno
	@RequestMapping("/cadastroaluno")
	public String cadastroAluno() {
		return "cadastroaluno";
	}
	
	//Salva novo aluno no banco de dados
	@PostMapping("/aluno")
	public ModelAndView salvarAluno(Aluno aluno) {

		Turma turmaDoAluno =  TurmaBC.findBySala(aluno.getTurma().getSala());
		
		String mensagemErro = null;
		
		ModelAndView mv = new ModelAndView("cadastroaluno");
		
		/*Se a turma não existir no banco de dados, 
		a mesma página de cadastro é retornada*/
		if(turmaDoAluno == null) {
			mensagemErro =  "Não há nenhuma turma com a sala " + aluno.getTurma().getSala() + ". Tente novamente.";
		}	
		
		/*Se existir, a turma é adicionada ao aluno e este,
		acrescentado ao banco de dados*/
		else {
			aluno.setTurma(turmaDoAluno);
			AlunoBC.save(aluno);
			mv.clear();
			mv.setViewName("index");
		}
		
		mv.addObject("mensagemErro", mensagemErro);
		
		return mv;
	}
	
	//Lista alunos
	@RequestMapping("/alunos")
	public ModelAndView listarTurmas() {
		
		ModelAndView mv = new ModelAndView("consulta");
		List<Aluno> alunos = AlunoBC.findAll();
			
		String titulo = "Alunos";

		mv.addObject("alunos", alunos);
		mv.addObject("titulo", titulo);
		return mv;
	}
	
	//Retorna página para atualizar dados de um aluno
	@RequestMapping("/atualizaraluno/{id}")
	public ModelAndView atualizaAluno(@PathVariable int id) {
		
		ModelAndView mv = new ModelAndView("cadastroaluno");
			
		String titulo = "Atualizar";
		
		Aluno aluno = AlunoBC.findById(id).orElse(new Aluno());
	
		mv.addObject("aluno", aluno);
		mv.addObject("titulo", titulo);
		return mv;
	}
	
	
	//Atualiza dados de um aluno no banco de dados
	@PostMapping("atualizaraluno/{id}")
	public ModelAndView atualizarAluno(@PathVariable int id, Aluno alunoAtualizado) {

		Aluno aluno = AlunoBC.findById(id).orElse(new Aluno());
		
		//Procura turma pelo número da sala
		Turma turmaDoAluno =  TurmaBC.findBySala(alunoAtualizado.getTurma().getSala());
		
		ModelAndView mv = new ModelAndView("index");
		
		String mensagemErro = null;
		
		/*Se a turma não existir no banco de dados, 
		a mesma página de cadastro é retornada*/
		if(turmaDoAluno == null) {
			mensagemErro =  "Não há nenhuma turma com a sala " + alunoAtualizado.getTurma().getSala() + ". Tente novamente.";
		}
		
		/*Se não, seus dados são atualizados no banco de dados e
		 a página principal é retornada*/
		else {
			alunoAtualizado.setId(aluno.getId());
			alunoAtualizado.setTurma(turmaDoAluno);
			AlunoBC.save(alunoAtualizado);
			mv.clear();
			mv.setViewName("index");
		}
		
		mv.addObject("mensagemErro", mensagemErro);
		
		return mv;
	}
	
	//Deleta aluno do banco de dados
	@GetMapping("deletaraluno/{id}")
	public String deletarAluno(@PathVariable int id) {
		
		//Deleta aluno
		AlunoBC.deleteById(id);		
		
		//Retorna para página principal
		return "index";
	}
	
}

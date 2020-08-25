package br.ufrn.imd.sa.sa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.ufrn.imd.sa.sa.model.Professor;
import br.ufrn.imd.sa.sa.repository.ProfessorRepository;

@Controller
public class ProfessorController {
	
	@Autowired
	ProfessorRepository ProfessorBC;
	
	
	//Retorna página de cadastro de professor
	@RequestMapping("/cadastroprofessor")
	public String cadastroProfessor( ) {
		return "cadastroprofessor";
	}
	
	//Salva novo professor no banco de dados
	@PostMapping("/professor")
	public String salvarProfessor(Professor professor) {
		ProfessorBC.save(professor);
		return "index";
	}
	
	//Lista professores
	@RequestMapping("/professores")
	public ModelAndView listarTurmas() {
		
		ModelAndView mv = new ModelAndView("consulta");
		List<Professor> professores = ProfessorBC.findAll();
			
		String titulo = "Professores";

		mv.addObject("professores", professores);
		mv.addObject("titulo", titulo);
		return mv;
	}
	
	//Retorna página para atualizar dados de um professor
	@RequestMapping("/atualizarprofessor/{id}")
	public ModelAndView atualizaProfessor(@PathVariable int id) {
		
		ModelAndView mv = new ModelAndView("cadastroprofessor");
			
		String titulo = "Atualizar";
		
		Professor professor = ProfessorBC.findById(id).orElse(new Professor());
	
		mv.addObject("professor", professor);
		mv.addObject("titulo", titulo);
		return mv;
	}
	
	//Atualiza dados de um professor no banco de dados
	@PostMapping("atualizarprofessor/{id}")
	public String atualizarProfessor(@PathVariable int id, Professor professorAtualizado) {

		//Acha antigo professor no banco de dados pelo ID
		Professor professor = ProfessorBC.findById(id).orElse(new Professor());
		
		/*Define o ID do novo professor como o ID do antigo professor
		e atualiza seus dados no banco de dados*/
		professorAtualizado.setId(professor.getId());
		ProfessorBC.save(professorAtualizado);
		
		return "index";
	}
	
	/*Deleta professor do banco de dados,
	junto com sua turma e os alunos dessa
	turma*/
	@GetMapping("deletarprofessor/{id}")
	public String deletarTurma(@PathVariable int id) {
		
		//Deleta professor
		ProfessorBC.deleteById(id);		
		
		//Retorna para página principal
		return "index";
	}
	
}

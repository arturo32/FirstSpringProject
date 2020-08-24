package br.ufrn.imd.sa.sa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufrn.imd.sa.sa.repository.AlunoRepository;
import br.ufrn.imd.sa.sa.repository.ProfessorRepository;
import br.ufrn.imd.sa.sa.repository.TurmaRepository;

@Controller
public class GeralController {
	
	@Autowired
	ProfessorRepository ProfessorDB;
	
	@Autowired
	AlunoRepository AlunoDB;
	
	@Autowired
	TurmaRepository TurmaDB;
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	

}

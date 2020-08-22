package br.ufrn.imd.sa.sa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufrn.imd.sa.sa.repository.TurmaRepository;

@Controller
public class TurmaController {
	
	@Autowired
	TurmaRepository TurmaDB;
	
	@RequestMapping("/")
	public String home() {
		return "OlaaaaA";
	}
	

	
}

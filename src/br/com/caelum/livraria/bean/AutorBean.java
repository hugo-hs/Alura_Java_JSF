package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.lx.Transacional;
import br.com.caelum.livraria.modelo.Autor;

@Named
@ViewScoped
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();

	@Inject
	private AutorDao dao;

	@Transacional
	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null)
			this.dao.adiciona(this.autor);
		else
			this.dao.atualiza(this.autor);
		
		this.autor = new Autor();
	}
	
	public void carregaPelaId() {
	    Integer id = this.autor.getId();
	    this.autor = this.dao.buscaPorId(id);
	}
	
	public List<Autor> getAutores(){
		return this.dao.listaTodos();
	}
	
	@Transacional
	public void remover(Autor autor){
		this.dao.remove(autor);
	}
	
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
}

package br.com.caelum.livraria.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@ManagedBean
@ViewScoped
public class AutorBean {

	private Autor autor = new Autor();


	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null)
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		else
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		
		this.autor = new Autor();
	}
	
	public void carregaPelaId() {
	    Integer id = this.autor.getId();
	    this.autor = new DAO<Autor>(Autor.class).buscaPorId(id);
	}
	
	public List<Autor> getAutores(){
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public void remover(Autor autor){
		new DAO<Autor>(Autor.class).remove(autor);
	}
	
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
}

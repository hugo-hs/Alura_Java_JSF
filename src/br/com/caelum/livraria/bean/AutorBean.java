package br.com.caelum.livraria.bean;

import javax.faces.bean.ManagedBean;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;

@ManagedBean
public class AutorBean {

	private Autor autor = new Autor();

	public Autor getAutor() {
		return autor;
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		new DAO<Autor>(Autor.class).adiciona(this.autor);
	}
	
	public void carregaPelaId() {
	    Integer id = this.autor.getId();
	    this.autor = new DAO<Autor>(Autor.class).buscaPorId(id);
	}
}

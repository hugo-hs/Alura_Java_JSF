package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.LivroDataModel;

@Named
@ViewScoped
public class LivroBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();
	
	private List<Livro> livros;
	
	@Inject
	private LivroDataModel livroDataModel;
	
	private List<String> generos = Arrays.asList("Romance", "Drama", "A��o");
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private AutorDao autorDao;

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	private Integer autorId;
	
	public List<Livro> getLivros() {
		
		if(this.livros == null)
		  this.livros = livroDao.listaTodos();
		
		return livros;
	}
	
	public List<Autor> getAutoresDoLivro() {
        return this.livro.getAutores();
    }

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}

	public Livro getLivro() {
		return livro;
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor",  new FacesMessage("Livro deve ter pelo menos um Autor"));
            return;
		}

	    if (this.livro.getId() == null) {
	        livroDao.adiciona(this.livro);        
	    } else {
	        livroDao.atualiza(this.livro);
	    }
		
		this.livro = new Livro();
	}
	
	
	public void comecaComDigitoUm(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		
		String valor = value.toString();
		
		if(!valor.startsWith("1")){
			FacesContext.getCurrentInstance().addMessage("isbn",  new FacesMessage("ISBN Deveria come�ar com 1"));
            return;
		}
		
	}

	public void remover(Livro livro) {
	    System.out.println("Removendo livro " + livro.getTitulo());
	    livroDao.remove(livro);
	}
	
	public void carregar(Livro livro) {
	    System.out.println("Carregando livro " + livro.getTitulo());
	    this.livro = livro;
	}
	
	public void removerAutorDoLivro(Autor autor) {
	    this.livro.removeAutor(autor);
	}
	
	public void carregaPelaId() {
        Integer id = this.livro.getId();
        this.livro = livroDao.buscaPorId(id);
    }
	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) { // java.util.Locale

        //tirando espa�os do filtro
        String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

        System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

        // o filtro � nulo ou vazio?
        if (textoDigitado == null || textoDigitado.equals("")) {
            return true;
        }

        // elemento da tabela � nulo?
        if (valorColuna == null) {
            return false;
        }

        try {
            // fazendo o parsing do filtro para converter para Double
            Double precoDigitado = Double.valueOf(textoDigitado);
            Double precoColuna = (Double) valorColuna;

            // comparando os valores, compareTo devolve um valor negativo se o value � menor do que o filtro
            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {

            // usuario nao digitou um numero
            return false;
        }
}

	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}

	public void setLivroDataModel(LivroDataModel livroDataModel) {
		this.livroDataModel = livroDataModel;
	}

	public List<String> getGeneros() {
		return generos;
	}

	public void setGeneros(List<String> generos) {
		this.generos = generos;
	}
}

package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class LoginBean  implements Serializable{

	private Usuario usuario = new Usuario();
    
	@Inject
	private UsuarioDao dao;
	
	@Inject
	private FacesContext context;
	
    public String deslogar(){
    	
    	context.getExternalContext().getSessionMap().remove("usuarioLogado");
    	
    	return "login?faces-redirect=true";
    }

    public String efetuaLogin() {
        System.out.println("Fazendo login do usuário "
                + this.usuario.getEmail());

        boolean existe = dao.existe(this.usuario);

        if (existe) {
        	
        	context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
            return "livro?faces-redirect=true";
        }
        
        context.getExternalContext().getFlash().setKeepMessages(true);
        context.addMessage(null, new FacesMessage("Dados Inválidos"));
        
        return null;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
}
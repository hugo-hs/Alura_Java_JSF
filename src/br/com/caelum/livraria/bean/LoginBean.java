package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@Named
@ViewScoped
public class LoginBean  implements Serializable{

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
    
    public String deslogar(){
    	
    	FacesContext context = FacesContext.getCurrentInstance();
    	context.getExternalContext().getSessionMap().remove("usuarioLogado");
    	
    	return "login?faces-redirect=true";
    }

    public String efetuaLogin() {
        System.out.println("Fazendo login do usuário "
                + this.usuario.getEmail());

        FacesContext context = FacesContext.getCurrentInstance();
        boolean existe = new UsuarioDao().existe(this.usuario);

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
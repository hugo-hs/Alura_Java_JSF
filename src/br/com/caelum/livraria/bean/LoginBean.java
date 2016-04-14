package br.com.caelum.livraria.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

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
package br.com.klund.locacao.converter;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.klund.locacao.modelo.dao.ClienteDao;
import br.com.klund.locacao.modelo.negocio.Cliente;

@FacesConverter(value = "ClienteConverter")    
//@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {
	Cliente cliente = new Cliente();	
	ClienteDao dao = new ClienteDao();
	
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
        	cliente.setNome("Klund");
           			System.out.println("valor recebido é " + value);
    		  return cliente;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof Cliente) {
            Cliente entity= (Cliente) value;
            if (entity != null && entity instanceof Cliente && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}
package br.com.klund.locacao.converter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="LocalDateConverter")
public class LocalDateConverter implements Converter, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String valor) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(valor,formatter);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object objeto) {
        LocalDate localDate = (LocalDate) objeto;
        return localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
package br.com.klund.locacao.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="localDateConverter")
public class LocalDateConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Locale BRAZIL = new Locale("pt", "BR");
        return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(BRAZIL));
    }
	@
	Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		 LocalDate dateValue = (LocalDate) value;

	        return dateValue.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	    }

}

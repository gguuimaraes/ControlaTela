package br.com.vitral.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.vitral.modelo.TVModel;

@FacesConverter(value = "tvConverter", forClass = TVModel.class)
public class TVConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value != null && !value.isEmpty()) {
			return uiComponent.getAttributes().get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value instanceof TVModel) {
			TVModel entity = (TVModel) value;
			if (entity instanceof TVModel && entity.getId() != null) {
				uiComponent.getAttributes().put(String.valueOf(entity.getId()), entity);
				return String.valueOf(entity.getId());
			}
		}
		return "";
	}
}
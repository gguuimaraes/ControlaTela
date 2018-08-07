package br.com.vitral.modelo;

import java.io.Serializable;

import br.com.vitral.entidade.Usuario.TipoUsuario;

public class TelaModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String url;
	private Integer segundos;
	private int posicao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSegundos() {
		return segundos;
	}

	public void setSegundos(Integer segundos) {
		this.segundos = segundos;
	}	

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}
	
}
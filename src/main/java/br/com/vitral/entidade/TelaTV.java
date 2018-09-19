package br.com.vitral.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "telaTV")
@Entity

@NamedQueries({

		@NamedQuery(name = "TelaTV.findTela", query = "SELECT t FROM TelaTV t WHERE t.url = :url"),
		@NamedQuery(name = "TelaTV.findAll", query = "SELECT t FROM TelaTV t ORDER BY t.posicao"),
		@NamedQuery(name = "TelaTV.findPorPosicao", query = "SELECT t FROM TelaTV t WHERE t.posicao = :posicao")

})
public class TelaTV implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "url")
	private String url;

	@Column(name = "segundos")
	private Integer segundos;
	
	@Column(name = "posicao")
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
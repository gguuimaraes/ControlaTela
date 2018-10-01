package br.com.vitral.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Table(name = "tv")
@Entity

@NamedQueries({

		@NamedQuery(name = "TV.findAll", query = "SELECT t FROM TV t ORDER BY t.id")

})
public class TV implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6788537740840720346L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nome", unique = true, nullable = false)
	private String nome;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("posicao ASC")
	private List<TelaTV> telas;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<TelaTV> getTelas() {
		return telas;
	}

	public void setTelas(List<TelaTV> telas) {
		this.telas = telas;
	}
}
package com.mcbain.sitewatcher.domain;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

@Entity @Table(name="probes")
public class Probe  {
	@Id @GeneratedValue(generator = "uuid2") 
	@GenericGenerator(name = "uuid2",strategy = "uuid2")
	private String id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String url;
	
	public Probe() {}
	public Probe(String id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;
	}
	public Probe(String name, String url) {
		this.name = name;
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	@Override
	public String toString() {
		return "Probe [id=" + id + ", name=" + name + ", url=" + url + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Probe other = (Probe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	

}

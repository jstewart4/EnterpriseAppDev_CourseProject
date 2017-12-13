package northwind.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the Territories database table.
 * 
 */
@Entity
@Table(name="Territories")
@NamedQuery(name="Territory.findAll", query="SELECT t FROM Territory t")
@XmlAccessorType(XmlAccessType.FIELD)
public class Territory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TerritoryID")
	private String territoryID;

	@Column(name="TerritoryDescription")
	private String territoryDescription;

	@XmlTransient
	//bi-directional many-to-one association to Region
	@ManyToOne
	@JoinColumn(name="RegionID")
	private Region region;

	public Territory() {
	}

	public String getTerritoryID() {
		return this.territoryID;
	}

	public void setTerritoryID(String territoryID) {
		this.territoryID = territoryID;
	}

	public String getTerritoryDescription() {
		return this.territoryDescription;
	}

	public void setTerritoryDescription(String territoryDescription) {
		this.territoryDescription = territoryDescription;
	}

	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

}
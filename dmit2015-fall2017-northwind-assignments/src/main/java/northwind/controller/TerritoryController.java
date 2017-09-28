package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import northwind.data.TerritoryRepository;
import northwind.model.Territory;

@Model
public class TerritoryController {

	@Inject
	private TerritoryRepository territoryRepository;
	
	private List<Territory> territories;
	
	@PostConstruct
	void init() {
		territories = territoryRepository.findAll();
	}

	public List<Territory> getTerritories() {
		return territories;
	}
	
}
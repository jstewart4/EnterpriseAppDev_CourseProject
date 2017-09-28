package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import northwind.data.RegionRepository;
import northwind.model.Region;

@Model
public class RegionController {
	
	@Inject
	private RegionRepository regionRepository;
	
	private List<Region> regions;
	
	@PostConstruct
	void init() {
		regions = regionRepository.findAll();
	}
	
	public List<Region> getRegions() {
		return regions;
	}
}

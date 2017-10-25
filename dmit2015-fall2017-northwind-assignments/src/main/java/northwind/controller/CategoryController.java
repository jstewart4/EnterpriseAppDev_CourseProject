package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import northwind.data.CategoryRepository;
import northwind.model.Category;
import northwind.report.CategorySalesReport;

@Model
public class CategoryController {

	@Inject
	private CategoryRepository categoryRepository;
	
	private List<Category> categories;
	
	@PostConstruct
	void init() {
		categories = categoryRepository.findAll();
	}

	public List<Category> getCategories() {
		return categories;
	}
	
	public List<CategorySalesReport> retrieveCategorySales(int year) {
		return categoryRepository.findCategoryRevenue(year);
	}
	
}


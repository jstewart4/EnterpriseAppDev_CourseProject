package northwind.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.Part;

import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;

import northwind.data.CategoryRepository;
import northwind.model.Category;
import northwind.report.CategorySalesReport;
import northwind.service.CategoryService;

@Model
public class CategoryController {
	
	private Part uploadedFile; //getter+setter

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
	
	@Inject
	private CategoryService categoryService;
	
	public List<CategorySalesReport> retrieveCategorySales(int year) {
		return categoryRepository.findCategoryRevenue(year);
	}
	
	@NotBlank(message="Category Name value is required.")
	private String categoryName; //getter+setter
	
	private String description; //getter+setter
	
	public void createNewCategory() {
		byte[] picture = null;
		if(uploadedFile != null) {
			long size = uploadedFile.getSize();
			InputStream content;
			try {
				content = uploadedFile.getInputStream();
				picture = new byte[(int) size];
				content.read(picture);
			} catch(Exception e) {
				Messages.addGlobalError("File upload was not successful.");
			}
		}
		try {
			categoryService.createCategory(categoryName, description, picture);
			Messages.addGlobalInfo("Create category was successful");
			categoryName = "";
			description = "";
		} catch(Exception e) {
			Messages.addGlobalError("Error creating category with exception: {0}", e.getMessage());
		}
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
	
}


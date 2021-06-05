package com.example.Periodicals.commands.admin;

import com.example.Periodicals.Controller.dto.CategoryInput;
import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.Controller.validation.ValidationError;
import com.example.Periodicals.Controller.validation.ValidationFactory;
import com.example.Periodicals.Controller.validation.rules.category.CategoryRule;
import com.example.Periodicals.Controller.validation.rules.magcreation.MagCreationRule;
import com.example.Periodicals.Controller.validation.validators.Validator;
import com.example.Periodicals.commands.Command;
import com.example.Periodicals.exceptions.CategoryAlreadyExists;
import com.example.Periodicals.model.entity.Category;
import com.example.Periodicals.model.service.CategoryService;
import com.example.Periodicals.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AddCategoryCommand implements Command {
    private ValidationFactory validationFactory = ValidationFactory.getInstance();
    @Override
    public String execute(HttpServletRequest request) {
        List<ValidationError> errors=new ArrayList<>();
        CategoryInput input=buildInput(request);
        Validator<CategoryRule, CategoryInput> validator=validationFactory.getCategoryValidator();
        errors = validator.validate(input);
        if(!errors.isEmpty()){
            request.setAttribute("errors",errors);
            return "/WEB-INF/jsp/add_category.jsp";
        }
        CategoryService categoryService=new CategoryService();
        try {
            Category category = categoryService.addCategory(input);
            request.setAttribute("category",category);
        } catch (CategoryAlreadyExists e){
            errors.add(ValidationError.CATEGORY_EXISTS);
            return "/WEB-INF/jsp/add_category.jsp";
        }
        request.setAttribute("errors",errors);
        return "redirect:/addCategoryPage";
    }

    private CategoryInput buildInput(HttpServletRequest request) {

        CategoryInput input=new CategoryInput(request.getParameter("cat_name"),request.getParameter("description"));
        return input;
    }
}

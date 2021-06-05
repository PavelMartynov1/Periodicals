package com.example.Periodicals.commands.admin;

import com.example.Periodicals.Controller.dto.MagazineCreationInput;
import com.example.Periodicals.Controller.validation.ValidationError;
import com.example.Periodicals.Controller.validation.ValidationFactory;
import com.example.Periodicals.Controller.validation.rules.magcreation.MagCreationRule;
import com.example.Periodicals.Controller.validation.validators.Validator;
import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.entity.Category;
import com.example.Periodicals.model.service.CategoryService;
import com.example.Periodicals.model.service.MagazineService;
import com.example.Periodicals.model.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AddMagazineCommand implements Command {
    CategoryService categoryService=ServiceFactory.getCategoryService();
    MagazineService magazineService= ServiceFactory.getMagazineService();
    Map<String,Category> categories=new HashMap<>();
    private ValidationFactory validationFactory = ValidationFactory.getInstance();
    @Override
    public String execute(HttpServletRequest request) {

        MagazineCreationInput input=buildMagInput(request);
        Validator<MagCreationRule,MagazineCreationInput> validator=validationFactory.getMagazineCreationValidator();
        List<ValidationError> errors = validator.validate(input);
        if(!errors.isEmpty()){
            request.setAttribute("errors",errors);
            return "redirect:/getMagCreationPage";
            //return "/WEB-INF/jsp/create_magazine.jsp";

        }
        magazineService.addMagazine(input);
        return "";
    }
    public MagazineCreationInput buildMagInput(HttpServletRequest request){
        return new MagazineCreationInput.Builder()
                .setName(request.getParameter("name"))
                .setPublisher(request.getParameter("publisher"))
                .setDescription(request.getParameter("description"))
                .setCategory(makeUniqueCategory(request))
                .setPrice(request.getParameter("price")).build();

    }
    public Category makeUniqueCategory(HttpServletRequest request){
        Optional<Category> category=buildCategory(request);
        if(category.isPresent()){
            categories.putIfAbsent(category.get().getName(),category.get());
             return categories.get(category.get().getName());
        } else{
            throw new RuntimeException();

        }
    }
    public Optional<Category> buildCategory(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("category_id"));
        return categoryService.findById(id);
    }
}

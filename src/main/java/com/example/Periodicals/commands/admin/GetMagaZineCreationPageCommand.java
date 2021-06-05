package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;
import com.example.Periodicals.model.service.CategoryService;

import javax.servlet.http.HttpServletRequest;

public class GetMagaZineCreationPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("categories",new CategoryService().findAll());
        return "/WEB-INF/jsp/create_magazine.jsp";
    }
}

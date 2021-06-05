package com.example.Periodicals.commands.admin;

import com.example.Periodicals.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class AddCategoryPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/jsp/add_category.jsp";
    }
}

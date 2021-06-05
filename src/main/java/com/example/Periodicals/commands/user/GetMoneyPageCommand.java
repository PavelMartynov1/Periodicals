package com.example.Periodicals.commands.user;

import com.example.Periodicals.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class GetMoneyPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/WEB-INF/jsp/money_page.jsp";
    }
}

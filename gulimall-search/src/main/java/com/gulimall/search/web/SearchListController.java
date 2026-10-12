package com.gulimall.search.web;

import com.gulimall.search.service.SearchListService;
import com.gulimall.search.vo.SearchParam;
import com.gulimall.search.vo.SearchResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SearchListController {

    @Autowired
    SearchListService searchListService;

    /**
     * 自动将页面提交过来的所有请求查询参数封装成指定的对象
     * @param param
     * @param model
     * @return
     */
    @GetMapping("/list.html")
    public String list(SearchParam param, Model model, HttpServletRequest request) {
        param.set_queryString(request.getQueryString());
        SearchResult result = searchListService.search(param);
        model.addAttribute("searchResult", result);
        return "index";
    }
}

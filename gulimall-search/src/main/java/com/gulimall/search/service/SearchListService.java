package com.gulimall.search.service;

import com.gulimall.search.vo.SearchParam;
import com.gulimall.search.vo.SearchResult;

import java.util.List;

public interface SearchListService {
    SearchResult search(SearchParam param);
}

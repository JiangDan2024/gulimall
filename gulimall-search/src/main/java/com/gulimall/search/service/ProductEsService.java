package com.gulimall.search.service;


import com.gulimall.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductEsService {
    boolean productUp(List<SkuEsModel> esModels) throws IOException;
}

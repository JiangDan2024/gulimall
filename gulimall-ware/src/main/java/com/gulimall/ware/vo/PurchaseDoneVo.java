package com.gulimall.ware.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PurchaseDoneVo {
    @NotNull
    Long id;
    List<PurchaseDetailtemsVo> items;
}

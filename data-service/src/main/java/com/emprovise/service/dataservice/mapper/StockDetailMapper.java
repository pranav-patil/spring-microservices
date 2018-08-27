package com.emprovise.service.dataservice.mapper;


import com.emprovise.service.dataservice.dto.StockDetail;
import com.emprovise.service.dataservice.model.Stock;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockDetailMapper {

    StockDetail mapToStockDetail(Stock stock);

    @InheritInverseConfiguration
    Stock mapToStock(StockDetail userBean);
}

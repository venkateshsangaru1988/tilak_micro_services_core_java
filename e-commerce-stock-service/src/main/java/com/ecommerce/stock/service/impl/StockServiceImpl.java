package com.ecommerce.stock.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.stock.constants.UtilConstants;
import com.ecommerce.stock.dto.ProductAvailabilityRequestDto;
import com.ecommerce.stock.dto.ProductAvailabilityResponseDto;
import com.ecommerce.stock.entity.StockEntity;
import com.ecommerce.stock.repository.StockRepository;
import com.ecommerce.stock.service.StockService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

	private final StockRepository stockRepository;

	@Override
	public List<ProductAvailabilityResponseDto> stockAvailability(List<ProductAvailabilityRequestDto> requestList) {

		List<ProductAvailabilityResponseDto> responseList = new ArrayList<>();

		requestList.forEach(req -> {
			Long stockAvailable = stockRepository.findAvailableStockByProductId(req.getProductId());
			ProductAvailabilityResponseDto dto = new ProductAvailabilityResponseDto();

			dto.setProductId(req.getProductId());
			dto.setStockAvailable(stockAvailable);
			responseList.add(dto);

		});
		return responseList;
	}

	@Override
	public String stockUpdate(List<ProductAvailabilityRequestDto> reqObjList, String operationType) {
		reqObjList.forEach(reqObj -> {
			StockEntity stockObj = stockRepository.findByProductId(reqObj.getProductId());

			if (UtilConstants.STOCK_MINUS.equalsIgnoreCase(operationType)) {
				stockObj.setStockAvaliable(stockObj.getStockAvaliable() - reqObj.getQty());
			} else if (UtilConstants.STOCK_PLUS.equalsIgnoreCase(operationType)) {
				stockObj.setStockAvaliable(stockObj.getStockAvaliable() + reqObj.getQty());
			}
			stockRepository.save(stockObj);
		});
		return "Stock Updated";
	}

}

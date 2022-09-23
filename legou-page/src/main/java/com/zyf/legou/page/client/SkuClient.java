package com.zyf.legou.page.client;

import com.zyf.legou.item.api.SkuApi;
import com.zyf.legou.item.po.Sku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "item-service", contextId = "p2", fallback = SkuClient.SkuClientFallback.class)
public interface SkuClient  extends SkuApi {

    @Component
    @RequestMapping("/sku-fallback2")
    class SkuClientFallback implements SkuClient{

        private static final Logger LOGGER = LoggerFactory.getLogger(SkuClientFallback.class);

        @Override
        public List<Sku> selectSkusBySpuId(Long spuId) {
            LOGGER.error("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public Sku edit(Long id) {
            LOGGER.error("异常发生，进入fallback方法");
            return null;
        }

        @Override
        public void decrCount(Integer num, Long skuId) {
            LOGGER.error("异常发生，进入fallback方法");
        }
    }
}
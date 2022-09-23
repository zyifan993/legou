package com.zyf.legou.order.client;

import com.zyf.legou.item.api.SkuApi;
import com.zyf.legou.item.po.Sku;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "item-service",/*fallback = SkuClient.SkuClientFallBack.class*/fallbackFactory = SkuClient.SkuClientFallbackFactory.class)
public interface SkuClient extends SkuApi {

    @Component
    @RequestMapping("/sku-fallback")
    class SkuClientFallBack implements SkuClient{

        private static final Logger LOGGER = LoggerFactory.getLogger(SkuClientFallBack.class);

        @Override
        public List<Sku> selectSkusBySpuId(Long spuId) {
            LOGGER.error("异常发⽣，进⼊fallback⽅法");
            return null;
        }

        @Override
        public Sku edit(Long id) {
            LOGGER.error("异常发⽣，进⼊fallback⽅法");
            return null;
        }

        @Override
        public void decrCount(Integer num, Long skuId) {
            LOGGER.error("异常发⽣，进⼊fallback⽅法");
        }
    }

    @Component
    @RequestMapping("/sku-fallback-factory")
    class SkuClientFallbackFactory implements FallbackFactory<SkuClient> {

        Logger logger = LoggerFactory.getLogger(SkuClientFallbackFactory.class);

        @Override
        public SkuClient create(Throwable cause) {
            cause.printStackTrace();
            logger.error(cause.getMessage());
            return new SkuClient() {
                @Override
                public List<Sku> selectSkusBySpuId(Long spuId) {
                    return null;
                }

                @Override
                public Sku edit(Long id) {
                    return null;
                }

                @Override
                public void decrCount(Integer num, Long skuId) {
                }
            };
        }
    }
}

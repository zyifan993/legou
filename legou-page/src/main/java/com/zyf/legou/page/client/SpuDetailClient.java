package com.zyf.legou.page.client;

import com.zyf.legou.item.api.SpuDetailApi;
import com.zyf.legou.item.po.SpuDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "item-service",contextId = "p4",fallback = SpuDetailClient.SpuDetailClientFallback.class)
public interface SpuDetailClient extends SpuDetailApi {

    @Component
    @RequestMapping("/spuDetail-fallback2")
    class SpuDetailClientFallback implements SpuDetailClient{

        private static final Logger LOGGER = LoggerFactory.getLogger(SpuDetailClientFallback.class);
        @Override
        public SpuDetail edit(Long id) {
            LOGGER.info("发生异常，执行fallback方法");
            return null;
        }
    }
}

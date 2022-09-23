package com.zyf.legou.page.client;

import com.zyf.legou.item.api.SpuApi;
import com.zyf.legou.item.po.Spu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "item-service",contextId = "p3",fallback = SpuClient.SpuClientFallback.class)
public interface SpuClient extends SpuApi {

    @Component
    @RequestMapping("/spu-fallback2")
    class SpuClientFallback implements SpuClient{

        private static final Logger LOGGER = LoggerFactory.getLogger(SpuClientFallback.class);
        @Override
        public List<Spu> selectAll() {
            LOGGER.info("发生异常，执行fallback方法");
            return null;
        }

        @Override
        public Spu edit(Long id) {
            LOGGER.info("发生异常，执行fallback方法");
            return null;
        }
    }
}

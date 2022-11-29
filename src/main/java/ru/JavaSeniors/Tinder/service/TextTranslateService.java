package ru.JavaSeniors.Tinder.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${consumer.feign.translateText.name}", url = "${consumer.feign.translateText.url}")
public interface TextTranslateService {
    @GetMapping("/translate")
    String textTranslate(@RequestParam("resource") String resource);
}

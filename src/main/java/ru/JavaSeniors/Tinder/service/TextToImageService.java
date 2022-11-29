package ru.JavaSeniors.Tinder.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${consumer.feign.textToImage.name}", url = "${consumer.feign.textToImage.url}", decode404 = true)
public interface TextToImageService {
    @GetMapping(value = "/internal/image/from/text/", produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<byte[]> textToImage(@RequestParam("description") String description);
}
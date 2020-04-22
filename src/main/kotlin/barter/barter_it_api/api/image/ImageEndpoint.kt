package barter.barter_it_api.api.image

import barter.barter_it_api.domain.images.ImageService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Validated
@RestController
@RequestMapping("/image")
class ImageEndpoint(private val service: ImageService) {

    @GetMapping("/{id}")
    fun byId(@PathVariable(name = "id", required = true) id: String): ByteArray? = service.getImage(id)

    @PostMapping("/add")
    fun add(@RequestParam("image", required = true) image: MultipartFile): String? = service.addImage(image)

    @DeleteMapping("/{id}")
    fun removeById(@PathVariable(name = "id", required = true) id: String) = service.removeImage(id)
}
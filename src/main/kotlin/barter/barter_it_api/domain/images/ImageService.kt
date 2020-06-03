package barter.barter_it_api.domain.images

import barter.barter_it_api.api.ValidationException
import barter.barter_it_api.infrastructure.image.ImageRepository
import com.google.common.io.ByteStreams.toByteArray
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Service
class ImageService(private val repository: ImageRepository) {

    fun addImage(file: MultipartFile): String? = repository.store(file)

    fun getImage(id: String): ByteArray? {
        val resource: InputStream = repository.loadResource(id) ?: throw ValidationException("Image does not exist")
        return toByteArray(resource)
    }

    fun removeImage(id: String) {
        repository.delete(id)
    }
}


package barter.barter_it_api.domain.images

import barter.barter_it_api.infrastructure.image.ImageRepository
import com.google.common.io.ByteStreams
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(private val repository: ImageRepository) {

    fun addImage(file: MultipartFile): String? = repository.store(file)

    fun getImage(id: String): ByteArray? = repository.loadResource(id).let { input ->
        return input?.let { it -> ByteStreams.toByteArray(it) }
    }

    fun removeImage(id: String) {
            repository.delete(id)
    }
}


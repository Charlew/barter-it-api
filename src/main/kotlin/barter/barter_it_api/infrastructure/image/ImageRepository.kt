package barter.barter_it_api.infrastructure.image

import com.mongodb.client.gridfs.model.GridFSFile
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Repository
interface ImageRepository : MongoRepository<Image, String>, CustomImageRepository

interface CustomImageRepository {

    fun store(file: MultipartFile): String?
    fun delete(id: String?)
    fun find(id: String?): GridFSFile
    fun loadResource(id: String?): InputStream?
}

class Image
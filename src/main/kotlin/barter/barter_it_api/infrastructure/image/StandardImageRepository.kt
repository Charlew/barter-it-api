package barter.barter_it_api.infrastructure.image

import com.mongodb.client.gridfs.model.GridFSFile
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsOperations
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedInputStream
import java.io.InputStream
import java.util.*

class CustomImageRepositoryImpl(
        private val gridFsTemplate: GridFsTemplate,
        private val operations: GridFsOperations
) : CustomImageRepository {

    override fun store(file: MultipartFile): String? {
        val content = BufferedInputStream(file.inputStream)
        val fileName = UUID.randomUUID().toString()

        gridFsTemplate.store(content, fileName).let { return it.toString() }
    }

    override fun delete(id: String?) = gridFsTemplate
            .delete(Query(Criteria
                    .where("_id")
                    .`is`(ObjectId(id))
            ))

    override fun find(id: String?): GridFSFile = gridFsTemplate
            .findOne(Query(Criteria
                    .where("_id")
                    .`is`(ObjectId(id))
            ))

    override fun loadResource(id: String?): InputStream? = operations
            .findOne(Query(Criteria
                    .where("_id")
                    .`is`(id))
            )?.let {
                return gridFsTemplate.getResource(it).inputStream
            }
}
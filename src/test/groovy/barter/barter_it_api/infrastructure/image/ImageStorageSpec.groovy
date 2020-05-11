package barter.barter_it_api.infrastructure.image

import org.junit.Ignore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.TestPropertySource
import org.testcontainers.shaded.org.apache.commons.io.IOUtils
import spock.lang.Specification

@Ignore("until testcontainers is repaired")
@TestPropertySource(value = "classpath:application-test.properties")
@SpringBootTest
class ImageStorageSpec extends Specification {

    @Autowired
    ImageRepository imageRepository

    def cleanup() {
        imageRepository.deleteAll()
    }

    def 'should store image'() {
        given:
            def file = new File(getClass().getResource('/cat.jpg').toURI())
            def input = new FileInputStream(file)
            def image = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input))
        when:
            def id = imageRepository.store(image)
        and:
            def result = imageRepository.loadResource(id)
        then:
            result != null
    }

    def 'should delete image'() {
        given:
            def file = new File(getClass().getResource('/cat.jpg').toURI())
            def input = new FileInputStream(file)
            def image = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input))
        when:
            def id = imageRepository.store(image)
            imageRepository.delete(id)
        and:
            def result = imageRepository.loadResource(id)
        then:
            result == null
    }
}

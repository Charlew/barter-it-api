package barter.barter_it_api.domain.images

import barter.barter_it_api.api.ValidationException
import barter.barter_it_api.infrastructure.image.ImageRepository
import spock.lang.Specification
import spock.lang.Subject

class ImageServiceSpec extends Specification {

    @Subject
    ImageService imageService

    ImageRepository imageRepository

    def setup() {
        imageRepository = Mock()
        imageService = new ImageService(imageRepository)
    }

    def "should throw ValidationException when there is no image with given id"() {
        given:
            imageRepository.loadResource(_ as String) >> null
        when:
            imageService.getImage(_ as String)
        then:
            ValidationException ex = thrown()
            ex.errors.contains("Image does not exist")
    }
}

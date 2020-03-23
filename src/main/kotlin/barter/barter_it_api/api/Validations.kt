package barter.barter_it_api.api

import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.util.stream.Collectors.toList
import javax.validation.Validation

@Component
class Validations {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Throws(ValidationException::class)
    fun <T> validate(t: T) {
        val violations = validator.validate(t)

        when {
            violations.isNotEmpty() -> {
                throw ValidationException.of(
                        violations.stream()
                                .map { "${it.propertyPath}.${it.message}" }
                                .collect(toList())
                )
            }
        }
    }
}

class ValidationException private constructor(var errors: List<String>) : RuntimeException() {

    companion object {
        fun of(errors: List<String>): ValidationException = ValidationException(errors)
    }
}

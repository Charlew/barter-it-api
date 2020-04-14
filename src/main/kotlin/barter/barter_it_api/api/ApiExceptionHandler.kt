package barter.barter_it_api.api

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseBody
@ControllerAdvice
class ApiExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(ApiExceptionHandler::class.java)

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException::class)
    fun onValidationException(exception: ValidationException): Problem {
        exception.errors.let {
            return Problem(it)
        }
    }
}

@ApiModel(description = "RepeatableEntity returned in case of any errors")
class Problem(
        @ApiModelProperty(notes = "Contains error codes specific for invoked operation")
        val codes: List<String>? = null
)

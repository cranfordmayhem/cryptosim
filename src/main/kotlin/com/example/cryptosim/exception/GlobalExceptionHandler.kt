package com.example.cryptosim.exception

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    object ErrorUtil{
        fun errorHandler(error: String): Unit {
            val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
            return logger.error("ERROR: $error")
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidTokenException::class)
    fun handleInvalidTokenException(handler: InvalidTokenException, request: HttpServletRequest):
            Map<String, Any> {
        val error = handler.message.toString()

        return mapOf(
            "timestamp" to LocalDateTime.now(),
            "status" to HttpStatus.BAD_REQUEST.value(),
            "error" to error,
            "path" to request.requestURI
        ).also {
            ErrorUtil.errorHandler(error)
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(handler: UserNotFoundException, request: HttpServletRequest):
            Map<String, Any> {
        val error = handler.message.toString()

        return mapOf(
            "timestamp" to LocalDateTime.now(),
            "status" to HttpStatus.NOT_FOUND.value(),
            "error" to error,
            "path" to request.requestURI
        ).also {
            ErrorUtil.errorHandler(error)
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationUserException::class)
    fun handleAuthenticationUserException(handler: AuthenticationUserException, request: HttpServletRequest):
            Map<String, Any> {
        val error = handler.message.toString()

        return mapOf(
            "timestamp" to LocalDateTime.now(),
            "status" to HttpStatus.UNAUTHORIZED.value(),
            "error" to error,
            "path" to request.requestURI
        ).also {
            ErrorUtil.errorHandler(error)
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FieldTakenException::class)
    fun handleFieldTakenException(handler: FieldTakenException, request: HttpServletRequest):
            Map<String, Any> {
        val error = handler.message.toString()

        return mapOf(
            "timestamp" to LocalDateTime.now(),
            "status" to HttpStatus.BAD_REQUEST.value(),
            "error" to error,
            "path" to request.requestURI
        ).also {
            ErrorUtil.errorHandler(error)
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException::class)
    fun handleIdNotFoundException(handler: IdNotFoundException, request: HttpServletRequest):
            Map<String, Any> {
        val error = handler.message.toString()

        return mapOf(
            "timestamp" to LocalDateTime.now(),
            "status" to HttpStatus.NOT_FOUND.value(),
            "error" to error,
            "path" to request.requestURI
        ).also {
            ErrorUtil.errorHandler(error)
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnAuthorizedException(handler: UnauthorizedException, request: HttpServletRequest):
            Map<String, Any>{
        val error = handler.message.toString()

        return mapOf(
            "timestamp" to LocalDateTime.now(),
            "status" to HttpStatus.UNAUTHORIZED.value(),
            "error" to error,
            "path" to request.requestURI
        ).also {
            ErrorUtil.errorHandler(error)
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AssetIdNotFoundException::class)
    fun handleAssetIdNotFoundException(handler: AssetIdNotFoundException, request: HttpServletRequest):
            Map<String, Any> {
        val error = handler.message.toString()

        return mapOf(
            "timestamp" to LocalDateTime.now(),
            "status" to HttpStatus.NOT_FOUND.value(),
            "error" to error,
            "path" to request.requestURI
        ).also {
            ErrorUtil.errorHandler(error)
        }
    }
}

class InvalidTokenException(string: String): RuntimeException(string)
class UserNotFoundException(username: String): RuntimeException("$username not found")
class AuthenticationUserException(): RuntimeException("Username or Password is invalid")
class FieldTakenException(email: String): RuntimeException("$email already taken")
class IdNotFoundException(id: Long): RuntimeException("$id not found")
class UnauthorizedException(action: String, id: Long): RuntimeException("Not authorized to $action $id")
class AssetIdNotFoundException(id: String): RuntimeException("$id not found")
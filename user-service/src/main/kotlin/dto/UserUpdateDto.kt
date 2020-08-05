package com.lorenzoog.gitkib.userservice.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateDto(
  val username: String?,
  val email: String?,
  val password: String?
)

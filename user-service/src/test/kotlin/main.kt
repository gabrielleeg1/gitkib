package com.lorenzoog.gitkib.userservice.tests

import com.lorenzoog.gitkib.userservice.Application
import com.lorenzoog.gitkib.userservice.setupDefaultBeans
import org.jetbrains.exposed.sql.Database
import org.springframework.context.support.BeanDefinitionDsl

@Suppress("NOTHING_TO_INLINE")
inline fun connectToDatabase() = Database.connect(
  url = "jdbc:h2:mem:local;MODE=POSTGRESQL;DATABASE_TO_UPPER=FALSE",
  driver = "org.h2.Driver"
)

@Suppress("NOTHING_TO_INLINE")
inline fun createApplication(crossinline setupBeans: BeanDefinitionDsl.() -> Unit = {}) =
  Application {
    setupDefaultBeans()
    setupBeans()
  }

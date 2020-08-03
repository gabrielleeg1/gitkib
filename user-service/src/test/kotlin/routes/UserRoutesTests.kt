package com.lorenzoog.gitkib.userservice.tests.routes

import com.lorenzoog.gitkib.userservice.dto.Page
import com.lorenzoog.gitkib.userservice.dto.UserResponseDto
import com.lorenzoog.gitkib.userservice.entities.User
import com.lorenzoog.gitkib.userservice.services.UserService
import com.lorenzoog.gitkib.userservice.tests.createApplication
import com.lorenzoog.gitkib.userservice.tests.factories.UserFactory
import com.lorenzoog.gitkib.userservice.tests.utils.requestAs
import io.ktor.application.Application
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.properties.Delegates.notNull

@KtorExperimentalAPI
class UserRoutesTests : Spek({
  val application = createApplication {
    put("database.url", "jdbc:h2:mem:local;MODE=POSTGRESQL;DATABASE_TO_UPPER=FALSE")
    put("database.driver", "org.h2.Driver")
    put("database.user", "root")
    put("database.password", "")
  }

  fun application(): () -> Application = { application.application }

  beforeEachTest { application.start(true) }
  afterEachTest { application.stop(1000, 1000) }

  Feature("user-rest-api") {
    val userService by di(application()).instance<UserService>()
    val userFactory = UserFactory()

    val client = HttpClient(CIO)
    val json = Json(JsonConfiguration.Stable)

    var user: User by notNull()
    var response: HttpResponse by notNull()

    Scenario("list all users") {
      Given("user with permission: users.view") {
        user = userFactory.createWithPermissions(listOf("users.view"))
      }

      When("request with GET to endpoint /users") {
        response = runBlocking {
          client.requestAs<HttpStatement>(user, "/users") {
            method = HttpMethod.Get
          }.execute()
        }
      }

      Then("it should show a response with status: 200") {
        assertThat(response.status, equalTo(HttpStatusCode.OK))
      }

      And("the content should be: a page with the users") {
        runBlocking {
          val expected = userService.findPaginated(0).map(User::toDto)

          response.content.read {
            assertThat(
              json.parse(
                Page.serializer(UserResponseDto.serializer()),
                Charsets.UTF_8.decode(it).toString()
              ),
              equalTo(expected)
            )
          }
        }
      }
    }

    Scenario("show one user") {


    }
  }
})

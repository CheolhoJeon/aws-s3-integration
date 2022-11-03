import aws.sdk.kotlin.services.s3.*
import aws.sdk.kotlin.services.s3.model.CorsConfiguration
import aws.sdk.kotlin.services.s3.model.CorsRule
import aws.sdk.kotlin.services.s3.model.GetBucketCorsRequest
import aws.sdk.kotlin.services.s3.model.PutBucketCorsRequest
import kotlinx.coroutines.runBlocking

const val REGION = "ap-northeast-2"
const val BUCKET = "test-bucket-for-cheolho"

fun main(): Unit = runBlocking {
    S3Client
        .fromEnvironment { region = REGION }
        .use { s3 ->
            val originCorsConfig = s3.getBucketCors(GetBucketCorsRequest {
                bucket = BUCKET
            })

            val newRule = CorsRule {
                allowedMethods = listOf("PUT")
                allowedOrigins = listOf("charlie")
            }

            val corsConfig = CorsConfiguration {
                corsRules = listOf(newRule) + originCorsConfig.corsRules!!
            }

            val request = PutBucketCorsRequest {
                bucket = BUCKET
                corsConfiguration = corsConfig
            }

            s3.putBucketCors(request)
        }
}

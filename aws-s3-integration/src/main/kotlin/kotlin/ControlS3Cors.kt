import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.CorsConfiguration
import aws.sdk.kotlin.services.s3.model.CorsRule
import aws.sdk.kotlin.services.s3.model.GetBucketCorsRequest
import aws.sdk.kotlin.services.s3.model.PutBucketCorsRequest
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.auth.awscredentials.CredentialsProvider
import kotlinx.coroutines.runBlocking

const val REGION = "ap-northeast-2"
const val BUCKET = "test-bucket-for-cheolho"

/**
 * 작업
 * 1. 전달 받은 액세스 키로 인증하기
 * 2. AWS SDK를 사용해서 CORS 설정 조작하기
 *
 * 아래의 S3 작업(Action)에 대한 권한이 필요하다
 * 1. s3:GetBucketCORS
 * 2. s3:PutBucketCORS
 */
fun main(): Unit = runBlocking {
    S3Client
        .fromEnvironment {
            region = REGION
            credentialsProvider = object : CredentialsProvider {
                override suspend fun getCredentials(): Credentials {
                    return Credentials(
                        accessKeyId = "AKIAU53Y436U4MFQ6NFG",
                        secretAccessKey = "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",
                    )
                }
            }
        }
        .use { s3 ->
            val originCorsConfig = s3.getBucketCors(GetBucketCorsRequest {
                bucket = BUCKET
            })

            val newRule = CorsRule {
                allowedMethods = listOf("DELETE")
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

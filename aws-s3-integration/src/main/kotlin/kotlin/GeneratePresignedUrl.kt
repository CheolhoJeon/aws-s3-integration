import aws.sdk.kotlin.services.s3.*
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.presigners.presign
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.auth.awscredentials.CredentialsProvider
import aws.smithy.kotlin.runtime.http.request.HttpRequest
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration

/**
 * 작업
 * 1. 버킷에 저장된 객체의 PreSignedUrl 받아오기
 *
 * 아래의 S3 작업(Action)에 대한 권한이 필요하다
 * 1. s3:GetObject
 *
 * 아래의 S3 리소스(resource)에 대한 권한이 필요하다
 * 1. arn:aws:s3:::[bucket_name]\*
 *
 * TMI
 * 위에 나열된 권한이 없더라도 PresignedURL을 생성할 수는 있으나, URL로 접근했을 때 해당 객체를 조회할 수 없다
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
            val req = GetObjectRequest {
                bucket = BUCKET
                key = "image-2.png"
            }
            val httpReq: HttpRequest = req.presign(s3.config, Duration.parse("PT30M"))

            println(httpReq.url)
        }
}

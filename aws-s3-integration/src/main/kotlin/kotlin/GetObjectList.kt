import aws.sdk.kotlin.services.s3.*
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import aws.smithy.kotlin.runtime.auth.awscredentials.CredentialsProvider
import kotlinx.coroutines.runBlocking

/**
 * 작업
 * 1. 버킷에 저장된 객체 리스트 받아오기
 *
 * 아래의 S3 작업(Action)에 대한 권한이 필요하다
 * 1. s3:ListBucket
 *
 * 아래의 S3 리소스(resource)에 대한 권한이 필요하다
 * 1. arn:aws:s3:::[bucket_name]\*
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
            val res = s3.listObjects {
                bucket = BUCKET
            }

            println(res)
        }
}

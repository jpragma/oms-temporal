package com.jpragma.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.google.protobuf.ByteString
import io.temporal.api.common.v1.Payload
import io.temporal.common.converter.DataConverterException
import io.temporal.common.converter.PayloadConverter
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.jvm.internal.Reflection
import kotlin.reflect.full.createType

@OptIn(ExperimentalSerializationApi::class)
class KotlinSerializationJsonPayloadConverter : PayloadConverter {
    private val encodingTypeJson = "json/plain"
    private val encodingMetaData = ByteString.copyFrom(encodingTypeJson, StandardCharsets.UTF_8)
    override fun getEncodingType(): String {
        return encodingTypeJson
    }

    override fun toData(value: Any?): Optional<Payload> {
        try {
            val serialized = Json.encodeToString(serializerForClass(value!!.javaClass), value).toByteArray()
            return Optional.of(
                Payload.newBuilder()
                    .putMetadata("encoding", encodingMetaData)
                    .setData(ByteString.copyFrom(serialized))
                    .build()
            )
        } catch (e: JsonProcessingException) {
            throw DataConverterException(e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @OptIn(ExperimentalStdlibApi::class)
    override fun <T : Any?> fromData(content: Payload?, valueClass: Class<T>?, valueType: Type?): T? {
        if (content!!.data.isEmpty) {
            return null
        }
        try {
            val jsonString = content.data.toString(StandardCharsets.UTF_8)
            return Json.decodeFromString(serializerForClass(valueClass), jsonString)
        } catch (e: IOException) {
            throw DataConverterException(e)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any?> serializerForClass(valueClass: Class<T>?): KSerializer<T> {
        val kClass = Reflection.createKotlinClass(valueClass)
        val kType = kClass.createType()
        return serializer(kType) as KSerializer<T>
    }

}
package com.bhardwaj.models.job

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Job(
    @BsonId
    val postId: String = ObjectId().toString(),
    val companyLogo: String? = null,
    val companyName: String? = null,
    val postTitle: String? = null,
    val postDescription: String? = null,
    val applyNowPage: String? = null,
    val jobTitle: String? = null,
    val jobDuration: String? = null,
    val jobLocation: String? = null,
    val jobRequirement: String? = null,
    val jobEligibility: String? = null,
    val aboutCompany: String? = null,
    val lastUpdated: Long? = null,
)
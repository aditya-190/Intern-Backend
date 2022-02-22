# Define here the models for your scraped items
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/items.html

import scrapy


class InternItem(scrapy.Item):
    jobId = scrapy.Field()
    companyLogo = scrapy.Field()
    companyName = scrapy.Field()
    postTitle = scrapy.Field()
    postDescription = scrapy.Field()
    applyNowPage = scrapy.Field()
    jobTitle = scrapy.Field()
    jobDuration = scrapy.Field()
    jobLocation = scrapy.Field()
    jobRequirement = scrapy.Field()
    jobEligibility = scrapy.Field()
    aboutCompany = scrapy.Field()
    lastUpdated = scrapy.Field()

import json
import logging
import os
import re

import dateparser
import html2text
import requests
import scrapy
from scrapy.crawler import CrawlerProcess

from ..items import InternItem


def next_page(response):
    items = InternItem()

    items['companyLogo'] = response.css(".artdeco-entity-image--square-5").xpath(
        "@data-delayed-url").extract_first().strip()
    items['companyName'] = response.css(".topcard__flavor--black-link::text").extract_first().strip()
    items['postTitle'] = response.css(".topcard__title::text").extract_first().strip()
    items['jobTitle'] = response.css(".topcard__title::text").extract_first().strip()
    items['jobLocation'] = response.css(".topcard__flavor--bullet::text").extract_first().strip()
    items['lastUpdated'] = int(dateparser.parse(
        response.css(".topcard__flavor--metadata::text").extract_first().strip()).timestamp())
    items['jobDuration'] = "Nothing Here"
    items['aboutCompany'] = "Nothing Here"

    apply_link = response.css(".top-card-layout__cta--primary").xpath("@href").extract_first()
    if not apply_link:
        items['applyNowPage'] = response.css(".topcard__link").xpath("@href").extract_first().strip()
    else:
        items['applyNowPage'] = apply_link.strip()

    html_parser = html2text.HTML2Text()
    html_parser.ignore_links = False
    html_parser.ignore_emphasis = True
    html_parser.ul_item_mark = "\u2022 "
    html_parser.strong_mark = ""
    html_parser.emphasis_mark = ""
    html_parser.strong_mark = ""

    raw_description = "".join(response.css(".show-more-less-html__markup--clamp-after-5").extract()).strip()
    items['postDescription'] = html_parser.handle(raw_description)
    items['jobRequirement'] = items['postDescription']
    items['jobEligibility'] = items['postDescription']

    yield items


class LinkedinSpider(scrapy.Spider):
    name = "linkedin"

    def __init__(self, number_of_pages=1, keywords="", location="", **kwargs):
        super().__init__(**kwargs)
        self.number_of_pages = number_of_pages
        self.keywords = keywords
        self.location = location
        logging.getLogger('scrapy').setLevel(logging.WARNING)

    def start_requests(self):
        for pages in range(0, self.number_of_pages * 25, 25):
            yield scrapy.Request(
                url='https://www.linkedin.com/jobs-guest/jobs/api/seeMoreJobPostings/search?start={}&keywords={}&location={}'.format(
                    pages, self.keywords.strip().replace(" ", "%20"), self.location.strip().replace(" ", "%20")),
                callback=self.parse)

    def parse(self, response, **kwargs):
        data_entity_urn = response.css(".base-card").xpath("@data-entity-urn").extract()
        data_search_id = response.css(".base-card").xpath("@data-search-id").extract()
        data_tracking_id = response.css(".base-card").xpath("@data-tracking-id").extract()

        for i in range(0, len(data_entity_urn)):
            current_job_posting_id = re.sub("urn:li:jobPosting:", "", data_entity_urn[i])
            current_data_search_id = data_search_id[i]
            current_data_tracking_id = data_tracking_id[i]

            next_page_url = "https://www.linkedin.com/jobs-guest/jobs/api/jobPosting/{}?refId={}%3D%3D&trackingId={}%3D%3D".format(
                current_job_posting_id, current_data_search_id, current_data_tracking_id)

            yield scrapy.Request(url=next_page_url, callback=next_page)


def send_data(mode):
    production_url = "https://aditya-intern-backend.herokuapp.com/job/all"
    development_url = "http://0.0.0.0:8080/job/all"

    if mode == "PRODUCTION":
        base_url = production_url
    else:
        base_url = development_url

    headers = {
        "Content-Type": "application/json; charset=utf-8",
        "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwL2hlbGxvIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODA4MC8iLCJlbWFpbCI6ImFhZGkuYmJoYXJkd2FqQGdtYWlsLmNvbSJ9.3jdo9WUyeASv9GbTWHjRPjLrk5sg0cCKgzcMcC5EF4w"
    }
    json_data = json.load(open('output.json'))
    requests.post(base_url, headers=headers, json=json_data)


def main(number_of_pages, keywords, location, mode):
    if os.path.exists("output.json"):
        os.remove("output.json")

    process = CrawlerProcess(settings={
        'FEED_URI': 'output.json',
        'FEED_FORMAT': 'json',
        'USER_AGENT': 'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)',
        'ROBOTSTXT_OBEY': 'True',
        'DOWNLOAD_DELAY': '1'
    })

    process.crawl(LinkedinSpider, number_of_pages=number_of_pages, keywords=keywords, location=location)
    process.start()
    send_data(mode=mode)

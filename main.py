import sys

from rq import Queue

import intern.spiders.Linkedin as linkedinSpider
from worker import conn


def task():
    linkedinSpider.main(number_of_pages=sys.argv[1], keywords=sys.argv[2], location=sys.argv[3], mode=sys.argv[4])


q = Queue(name="some_queue", connection=conn).enqueue(task)

import sys

import intern.spiders.Linkedin as linkedinSpider

linkedinSpider.main(number_of_pages=int(sys.argv[1]), keywords=sys.argv[2], location=sys.argv[3], mode=sys.argv[4])

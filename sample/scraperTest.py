from webScraper import WebScraper

"""
Sample usage of the web Scraper.
"""

WebScraper(number=5, d_name="datalake1").start()

WebScraper(number=5, d_name="datalake", metadata=False).start()

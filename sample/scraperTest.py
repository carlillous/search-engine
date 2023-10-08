from webScraper import WebScraper

"""
Sample usage of the web Scraper.
"""

WebScraper(number=20, d_name="datalake1").start()

WebScraper(number=20, d_name="datalake", metadata=False).start()

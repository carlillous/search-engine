from webScraper import WebScraper

"""
Sample usage of the web Scraper.
"""

scraper1 = WebScraper(number=5, d_name="datalake1")

scraper2 = WebScraper(number=10, d_name="datalake2")

scraper3 = WebScraper(number=7, d_name="datalake3")
scraper3.random_range = (1, 30000) 

scraper4 = WebScraper(number=2, d_name="datalake4")
scraper4.random_range = (30000, 35000)  


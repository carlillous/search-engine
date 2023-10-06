from webScraper import WebScraper

# 1: Download 5 books and create a folder called "datalake1" where they are saved.
scraper1 = WebScraper(number=5, d_name="datalake1")

# 2: Download 10 books and create a folder called "datalake2" where they are saved.
scraper2 = WebScraper(number=10, d_name="datalake2")

# 3: Download 10 books and create a folder called "datalake3" where they are saved and increase the range of random numbers
scraper3 = WebScraper(number=7, d_name="datalake3")
scraper3.random_range = (1, 30000) 

# 4: Download 10 books and create a folder called "datalake4" where they are saved and adjust the custom random number range
scraper4 = WebScraper(number=2, d_name="datalake4")
scraper4.random_range = (30000, 35000)  


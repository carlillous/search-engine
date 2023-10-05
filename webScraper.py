import os
import requests
import re
import random


class WebScraper:

    def __init__(self, number, d_name="datalake"):
        self.n = number
        self.directory_name = d_name
        self._start()

    def _start(self):

        if not os.path.exists(self.directory_name):
            os.makedirs(self.directory_name)

        book_content_folder = os.path.join(self.directory_name, "book_content")
        if not os.path.exists(book_content_folder):
            os.makedirs(book_content_folder)

        metadata_folder = os.path.join(self.directory_name, "metadata")
        if not os.path.exists(metadata_folder):
            os.makedirs(metadata_folder)

        for i in range(self.n):
            num_str = str(random.randint(1, 40000))
            url = "https://www.gutenberg.org/cache/epub/" + num_str + "/pg" + num_str + ".txt"

            response = requests.get(url)

            if response.status_code == 200:
                content = response.text

                lines = content.split("\n")

                title = None
                for line in lines:
                    if line.startswith("Title:"):
                        title = line.replace("Title:", "").strip()
                        break

                book_name = title + ".txt"
                content_path = os.path.join(book_content_folder, book_name)

                result = re.search(r"\*\*\* START OF THE PROJECT GUTENBERG EBOOK(.*)", content, re.DOTALL)
                ebook_content = result.group(1).strip()

                if not os.path.exists(content_path):
                    with open(content_path, "w", encoding="utf-8") as file:
                        file.write(ebook_content.split("***")[1])
                    print(f"[SCRAPER]: Book {title} saved.")

                result2 = re.search(r"(.+?)\*\*\* START OF THE PROJECT GUTENBERG EBOOK", content, re.DOTALL)
                metadata = result2.group(1).strip()
                metadata_name = "md_" + title + ".txt"
                metadata_file = os.path.join(metadata_folder, metadata_name)

                if not os.path.exists(metadata_file):
                    with open(metadata_file, "w", encoding="utf-8") as file:
                        file.write(metadata)
                    print(f"[SCRAPER]: Metadata of '{title}' saved as {metadata_name}.")

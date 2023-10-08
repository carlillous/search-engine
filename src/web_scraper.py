import os
import requests
import re
import random


class WebScraper:

    def __init__(self, number, d_name="datalake", metadata=True):
        self.n = number
        self.directory_name = d_name
        self.metadata_option = metadata

    def start(self):
        print(f"[SCRAPER]:------------------- Starting -------------------")
        current_directory = os.path.dirname(os.path.abspath(__file__))
        new_directory_path = os.path.join(current_directory, self.directory_name)

        if not os.path.exists(self.directory_name):
            os.makedirs(new_directory_path)

        book_content_folder = os.path.join(new_directory_path, "book_content")
        if not os.path.exists(book_content_folder):
            os.makedirs(book_content_folder)

        if self.metadata_option:
            metadata_folder = os.path.join(new_directory_path, "metadata")
            if not os.path.exists(metadata_folder):
                os.makedirs(metadata_folder)

        i = 0
        while i < self.n:
            num_str = str(random.randint(1, 70000))
            url = "https://www.gutenberg.org/cache/epub/" + num_str + "/pg" + num_str + ".txt"

            response = requests.get(url)

            if response.status_code == 200:
                content = response.text

                md_pattern1 = re.search(r"(.+?)\*\*\* START OF THE PROJECT GUTENBERG EBOOK", content, re.DOTALL)
                if md_pattern1:
                    metadata = md_pattern1.group(1).strip()
                else:
                    md_pattern1 = re.search(r"(.+?)\*\*\* START OF THIS PROJECT GUTENBERG EBOOK", content, re.DOTALL)
                    if md_pattern1:
                        metadata = md_pattern1.group(1).strip()
                    else:
                        continue

                lines = metadata.split("\n")
                title = None
                for line in lines:
                    if line.startswith("Title:"):
                        title = line.replace("Title:", "").strip()
                        break
                chars = str.maketrans({':': ' ', ' ': '_', ',': '', '.': '',
                                       '(': ' ', ')': '', '-': '', '/': '',
                                       ';': '', '"': '', '—': '', '?': '' })

                content_path = os.path.join(book_content_folder, title.translate(chars)+".txt")

                result_1 = re.search(r"\*\*\* START OF THE PROJECT GUTENBERG EBOOK(.*)", content, re.DOTALL)
                result_2 = re.search(r"\*\*\* START OF THIS PROJECT GUTENBERG EBOOK(.*)", content, re.DOTALL)

                if result_1:
                    ebook_content = result_1.group(1).strip()
                elif result_2:
                    ebook_content = result_2.group(1).strip()
                else:
                    continue

                if not os.path.exists(content_path):
                    with open(content_path, "w", encoding="utf-8") as file:
                        file.write(ebook_content.split("***")[1])
                    print(f"[SCRAPER]: Book {title} saved.")

                if self.metadata_option:
                    metadata_name = "md_" + title.translate(chars) + ".txt"
                    metadata_file = os.path.join(metadata_folder, metadata_name)
                    if not os.path.exists(metadata_file):
                        with open(metadata_file, "w", encoding="utf-8") as file:
                            file.write(metadata)
                        print(f"[SCRAPER]: Metadata of '{title}' saved as {metadata_name}.")

                i += 1
            else:
                print(f"[SCRAPER]: There is no book with that number. ")
        print(f"[SCRAPER]:---------- Successfully completed ----------")

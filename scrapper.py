import os
import requests
from bs4 import BeautifulSoup


class Scrapper:
    directory_name = "datalake"

    if not os.path.exists(directory_name):
        os.makedirs(directory_name)

    book_content_folder = os.path.join(directory_name, "book_content")
    if not os.path.exists(book_content_folder):
        os.makedirs(book_content_folder)

    metadata_folder = os.path.join(directory_name, "metadata")
    if not os.path.exists(metadata_folder):
        os.makedirs(metadata_folder)

    num_start = 1
    num_books = 5

    for i in range(num_books):
        num_str = str(num_start)
        url = "https://www.gutenberg.org/cache/epub/" + num_str + "/pg" + num_str + ".txt"

        response = requests.get(url)

        if response.status_code == 200:
            content = response.text

            lines = content.split("\n")
            start_index = 0
            for i, line in enumerate(lines):
                if line.startswith("* START OF THE PROJECT"):
                    start_index = i + 1
                    break

            book_content = "\n".join(lines[start_index:])

            title = None
            for line in lines:
                if line.startswith("Title:"):
                    title = line.replace("Title:", "").strip()
                    break

            bookname = title + ".txt"
            content_path = os.path.join(book_content_folder, bookname)

            if not os.path.exists(content_path):
                with open(content_path, "w", encoding="utf-8") as file:
                    file.write("\n".join(lines[start_index:]))
                print(f"Book {num_start} saved as {bookname}")

            metadata = "\n".join(lines[:start_index])
            metadata_name = "metadata_of_" + title + ".txt"
            metadata_file = os.path.join(metadata_folder, metadata_name)

            if not os.path.exists(metadata_file):
                with open(metadata_file, "w", encoding="utf-8") as file:
                    file.write(metadata)
                print(f"Metadata of '{title}' saved as {metadata_name}")

        num_start += 1
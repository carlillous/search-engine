# Branch Adam
# Branch Carlos the first
from databaseIndexer import DatabaseIndexer

# Usage
directory_path = "datalake"  # Provide the desired directory path

indexer = DatabaseIndexer()
# indexer.index_all(directory_path)
# indexer.index_one("datalake/B1.txt", 0)
# indexer.index_one("datalake/B2.txt", 0)
print(indexer.get_list_of_books_for_word("hello"))
print(indexer.get_list_of_books_for_word("yes"))
print(indexer.get_list_of_books_for_word("hi"))
print(indexer.get_books())
# print(indexer.get_words())
# print(indexer.get_books_words())

# Close the database connection when done

indexer.close()

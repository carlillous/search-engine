from src.database_indexer import DatabaseIndexer
from src.dictionary_indexer import DictionaryIndexer

BOOKS_PATH_MAC = "../datalake/book_content"
BOOKS_PATH_WIN = "???"

BOOKS_PATH = BOOKS_PATH_MAC
QUERY_WORD = "island"

"""
Sample usage of database indexer.
- note: Run close() after using the indexer to stop connection with db.
"""

db_indexer = DatabaseIndexer()
db_indexer.index_all(BOOKS_PATH)
print(db_indexer.get_list_of_books_for_word(QUERY_WORD))
db_indexer.close()

"""
Sample usage of dictionary indexer.
"""

dict_indexer = DictionaryIndexer()
dict_indexer.index_all(BOOKS_PATH)
print(dict_indexer.get_list_of_books_for_word(QUERY_WORD))

from src.database_indexer import DatabaseIndexer
from src.dictionary_indexer import DictionaryIndexer

BOOKS_PATH_MAC = "../datalake/book_content"
BOOKS_PATH_WIN = "???"

BOOKS_PATH = BOOKS_PATH_MAC
QUERY_WORD = "island"

"""
Sample usage of dictionary indexer.
"""

dict_indexer = DictionaryIndexer()
dict_indexer.index_all(BOOKS_PATH)
print(dict_indexer.get_list_of_books_for_word(QUERY_WORD))
dict_indexer.get_inverted_indexes()

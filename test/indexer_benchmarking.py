from src.database_indexer import DatabaseIndexer
from src.dictionary_indexer import DictionaryIndexer
import pytest


@pytest.mark.benchmark1
def test_database(benchmark):
    benchmark.pedantic(DatabaseIndexer().index_all,
                        args = ("datalake/book_content",)
                       ,rounds=3,warmup_rounds=2,iterations=4)


@pytest.mark.benchmark2
def test_dictionary(benchmark):
    benchmark.pedantic(DictionaryIndexer().index_all,
                       args=("datalake/book_content",)
                       , rounds=3, warmup_rounds=2, iterations=4)


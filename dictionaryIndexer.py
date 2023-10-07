import os
import persistence

from reader import Reader


class DictionaryIndexer:

    def __init__(self, book_names_path="book_names.json", word_indexes_path="word_indexes.json"):
        self.persistence = persistence.JsonDictionaryPersistence()

        self._book_names_path = book_names_path
        self._word_indexes_path = word_indexes_path

        self._load()

    def _index_one(self, path, index):
        reader = Reader()
        book_name, words = reader.read_book(path)
        self._book_names[index] = book_name
        print(f"[INDEXER]: Indexing book: \"{book_name}\"")
        for word in words:
            if word not in self._word_indexes:
                self._word_indexes[word] = {index}
            else:
                self._word_indexes[word].add(index)

    def index_all(self, directory):
        print("[INDEXER]: ------------------ Indexing starting -------------------")
        for i, filename in enumerate(os.listdir(directory)):
            file = os.path.join(directory, filename)
            # checking if it is a file
            if os.path.isfile(file):
                self._index_one(file, i)
        print("[INDEXER]: -------------------- Indexing ended --------------------")
        self._save()

    def _get_indexes_of_word(self, word):
        return self._word_indexes[word]

    def _get_book_name(self, index):
        return self._book_names[index]

    def _get_book_names_for_indexes(self, indexes):
        book_names = []
        for index in indexes:
            book_names.append(self._book_names[index])
        return book_names

    def _save(self):
        self.persistence.dump_dict(self._book_names_path, self._book_names)
        print(f"[INDEXER]: Saving book names to {self._book_names_path} ...")
        serialized_dict = {key: list(value) for key, value in self._word_indexes.items()}
        self.persistence.dump_dict(self._word_indexes_path, serialized_dict)
        print(f"[INDEXER]: Saving word indexes to {self._word_indexes_path} ...")

    def _load(self):
        if os.path.exists(self._book_names_path):
            print(f"[INDEXER]: Loading book names from {self._book_names_path} ...")
            loaded_book_names = self.persistence.load_dict(self._book_names_path)
            self._book_names = {int(key): value for key, value in loaded_book_names.items()}
        else:
            print(f"[INDEXER]: Creating empty book names dictionary ...")
            self._book_names = {}

        if os.path.exists(self._word_indexes_path):
            print(f"[INDEXER]: Loading word indexes from {self._book_names_path}...")
            loaded_word_indexes = self.persistence.load_dict(self._word_indexes_path)
            self._word_indexes = {key: set(value) for key, value in loaded_word_indexes.items()}
        else:
            print(f"[INDEXER]: Creating empty word indexes dictionary ...")
            self._word_indexes = {}

    def get_list_of_books_for_word(self, word):
        indexes = self._get_indexes_of_word(word)
        return self._get_book_names_for_indexes(indexes)

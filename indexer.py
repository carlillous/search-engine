from reader import Reader
import os



class Indexer:

    _book_names = {
    }

    _word_indexes = {
    }

    def _index_one(self, path, index):
        reader = Reader()
        book_name, words = reader.read_book(path)
        self._book_names[index] = book_name

        for word in words:
            if word not in self._word_indexes:
                self._word_indexes[word] = {index}
            else:
                self._word_indexes[word].add(index)


    def index_all(self, directory):
        """
        """
        for i, filename in enumerate(os.listdir(directory)):
            file = os.path.join(directory, filename)
            # checking if it is a file
            if os.path.isfile(file):
                self._index_one(file, i)

    def get_indexes_of_word(self, word):
        return self._word_indexes[word]


    def get_book_name(self, index):
        return self._book_names[index]

    def get_book_names_for_indexes(self, indexes):
        book_names = []
        for index in indexes:
            book_names.append(self._book_names[index])
        return book_names



indexer = Indexer()
indexer.index_all("datalake")
print(indexer._book_names)
print(indexer.get_book_names_for_indexes(indexer.get_indexes_of_word("Yellow")))

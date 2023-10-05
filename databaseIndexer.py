import os
import sqlite3
from reader import Reader


class DatabaseIndexer:
    def __init__(self, db_file = "indexer_sqlite.db"):
        self.conn = sqlite3.connect(db_file)
        self.cursor = self.conn.cursor()
        self._create_tables()

    def _create_tables(self):
        self.cursor.execute('''
            CREATE TABLE IF NOT EXISTS books (
                book_id INTEGER PRIMARY KEY AUTOINCREMENT,
                book_name TEXT
            )
        ''')

        self.cursor.execute('''
            CREATE TABLE IF NOT EXISTS words (
                word_id INTEGER PRIMARY KEY AUTOINCREMENT,
                word_text TEXT UNIQUE
            )
        ''')

        self.cursor.execute('''
            CREATE TABLE IF NOT EXISTS books_words (
                book_id INTEGER,
                word_id INTEGER,
                UNIQUE(book_id, word_id),
                FOREIGN KEY (book_id) REFERENCES books(book_id),
                FOREIGN KEY (word_id) REFERENCES words(word_id)
            )
        ''')

        self.conn.commit()


    def index_one(self, path, book_index):
        reader = Reader()
        book_name, words = reader.read_book(path)
        print(f"[INDEXER]: Indexing book: \"{book_name}\"")
        self.cursor.execute('INSERT INTO books (book_name) VALUES (?)', (book_name,))
        book_id = self.cursor.lastrowid

        for word in words:
            self.cursor.execute('INSERT OR IGNORE INTO words (word_text) VALUES (?)', (word,))

            self.cursor.execute("SELECT word_id FROM words WHERE word_text = ?", (word,))
            word_id = self.cursor.fetchone()[0]

            self.cursor.execute('INSERT OR IGNORE INTO books_words VALUES (?, ?)', (book_id, word_id))
        self.conn.commit()

    def index_all(self, directory):
        print("[INDEXER]: ------------------ Indexing starting -------------------")
        for i, filename in enumerate(os.listdir(directory)):
            file = os.path.join(directory, filename)
            if os.path.isfile(file):
                self.index_one(file, i)
        print("[INDEXER]: -------------------- Indexing ended --------------------")

    def get_list_of_books_for_word(self, word):
        self.cursor.execute('''
            SELECT book_name
            FROM books_words
            JOIN books ON books.book_id = books_words.book_id
            JOIN words ON words.word_id = books_words.word_id
            WHERE word_text = ?
        ''', (word,))

        return [name[0] for name in self.cursor.fetchall()]


    def close(self):
        self.cursor.close()
        self.conn.close()

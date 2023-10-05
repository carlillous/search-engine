import nltk
from nltk.tokenize import WhitespaceTokenizer
from nltk.corpus import stopwords
import re
from scrapper import Scrapper
class Reader:
    def __init__(self):
        nltk.download('stopwords',quiet='True')
        self.stopwords_eng = set(stopwords.words('english'))

    def preprocessing(self, texto):
        """Preprocess the text of the book and return it in a list"""

        tokens = nltk.word_tokenize(texto.replace('—',' ').replace('.', '').replace('_', '').replace('=','').replace('-',' '))
        filtered = [word.lower() for word in tokens if any(letter.isalpha() for letter in word)]
        words = [re.sub(r'^[^a-zA-Z\s]+|[^a-zA-Z\s]+$', '', words) for words in filtered if words not in self.stopwords_eng]
        return words

    def read_book(self, path):
        """Read a book at a given path, return a tuple of name of the book and list of words"""
        book_name = path.split("\\")[-1].split(".")[0]

        try:
            with open(path, 'r',encoding='utf-8') as file:
                book_content = file.read()
                words = self.preprocessing(book_content)


        except Exception as e:
            print(f"Exception occurred: {e}")

        return (book_name, words)

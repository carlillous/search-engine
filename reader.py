import nltk
from nltk.tokenize import WhitespaceTokenizer
from nltk.corpus import stopwords
import re
class Reader:
    def __init__(self):
        nltk.download('stopwords',quiet='True')
        self.stopwords_eng = set(stopwords.words('english'))

    def preprocessing(self, texto):
        """Preprocess the text of the book and return it in a list"""

        tokens = nltk.word_tokenize(texto.replace('—',' ').replace('.', '').replace('_', '').replace('=','').replace('-',' '))
        lista_filtrada = [cadena for cadena in tokens if any(letra.isalpha() for letra in cadena)]
        words = [re.sub(r'^[^a-zA-Z\s]+|[^a-zA-Z\s]+$', '', words) for words in lista_filtrada if words not in self.stopwords_eng]
        return words

    def read_book(self, path):
        """Read a book at a given path, return a tuple of name of the book and list of words"""
        book_name = path.split("\\")[-1].split(".")[0]

        try:
            with open(path, 'r',encoding='utf-8') as file:
                file_contents = file.read()
                book_content = file_contents.split("***")[2].lower()
                words = self.preprocessing(book_content)


        except Exception as e:
            print(f"Exception occurred: {e}")

        return (book_name, words)

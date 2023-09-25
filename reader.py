class Reader:


    def read_book(self, path):
        """Read a book at a given path, return a tuple of name of the book and list of words
        """
        #TODO: implement this method, do preprocessing of words
        words = {"hello", "word", "hi"}
        bookname = "dracula"
        return (bookname, words)



print(Reader().read_book("path"))
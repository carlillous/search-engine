class Reader:

    letters_replace = ["\n", "\t", "\r", ",", "?", ". ", "!"] #TODO: Add more?
    def read_book(self, path):
        """Read a book at a given path, return a tuple of name of the book and list of words
        """
        #TODO: implement this method, do preprocessing of words
        book_name = path.split("/")[-1].split(".")[0]
        words = []

        try:
            with open(path, 'r') as file:
                file_contents = file.read()
                contents_replaced = file_contents
                book_content = contents_replaced.split("***")[2]
                words = book_content.split(" ")
        except Exception as e:
            print(f"Exception occurred: {e}")

        return (book_name, words)
